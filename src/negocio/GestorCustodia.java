package negocio;

import modelo.MovimientoCustodia;
import modelo.ObjetoEncontrado;
import modelo.ReporteHallazgo;
import modelo.Ubicacion;
import modelo.Usuario;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Administra ubicaciones físicas, recepciones y traslados autorizados. */
public class GestorCustodia {
    /** Ubicaciones físicas existentes en el campus. */
    private ArrayList<Ubicacion> ubicaciones;
    /** Objetos que han sido recibidos físicamente. */
    private ArrayList<ObjetoEncontrado> objetos;
    /** Historial que se conserva al mover objetos. */
    private ArrayList<MovimientoCustodia> movimientos;

    /** Capacidades de demostración centralizadas; deben ajustarse a las capacidades reales autorizadas. */
    public static final int CAPACIDAD_DEMO_GARITA = 30;
    public static final int CAPACIDAD_DEMO_BIBLIOTECA = 30;
    public static final int CAPACIDAD_DEMO_RECEPCION = 30;

    /** Crea las tres ubicaciones iniciales con categorías comunes y colecciones vacías. */
    public GestorCustodia() {
        ubicaciones = new ArrayList<Ubicacion>(); objetos = new ArrayList<ObjetoEncontrado>();
        movimientos = new ArrayList<MovimientoCustodia>();
        ArrayList<String> categorias = new ArrayList<String>();
        categorias.add("Accesorios"); categorias.add("Electrónica"); categorias.add("Ropa");
        categorias.add("Documentos"); categorias.add("Otros");
        ubicaciones.add(new Ubicacion("UBI-01", "GARITA", CAPACIDAD_DEMO_GARITA, new ArrayList<String>(categorias)));
        ubicaciones.add(new Ubicacion("UBI-02", "BIBLIOTECA", CAPACIDAD_DEMO_BIBLIOTECA, new ArrayList<String>(categorias)));
        ubicaciones.add(new Ubicacion("UBI-03", "RECEPCION", CAPACIDAD_DEMO_RECEPCION, new ArrayList<String>(categorias)));
    }

    /** Confirma la recepción solo tras validar reporte, permiso y espacio de destino. */
    public ObjetoEncontrado registrarRecepcion(String codigoHallazgo, String nombreUbicacion, Usuario encargado,
                                               GestorReportesHallazgo gestorHallazgos) {
        validarEncargado(encargado);
        ReporteHallazgo reporte = gestorHallazgos.buscarPorCodigo(codigoHallazgo);
        if (reporte == null) throw new IllegalArgumentException("No existe el reporte de hallazgo indicado.");
        if (reporte.getObjetoEncontrado() != null || "RECIBIDO".equals(reporte.getEstado()))
            throw new IllegalArgumentException("Este objeto ya fue recibido anteriormente.");
        Ubicacion destino = buscarUbicacion(nombreUbicacion);
        validarDestino(destino, reporte.getCategoria());

        // Se crea y asocia el objeto físico solo después de superar todas las validaciones.
        ObjetoEncontrado objeto = new ObjetoEncontrado(reporte.getCodigo(), reporte, destino, "EN_CUSTODIA");
        destino.getObjetos().add(objeto); objetos.add(objeto);
        reporte.setObjetoEncontrado(objeto); reporte.setEstado("RECIBIDO");
        movimientos.add(new MovimientoCustodia(objeto, null, destino, LocalDateTime.now(), "Recepción física inicial", encargado));
        return objeto;
    }

    /** Mueve un objeto después de validar todas las condiciones sin alterar el estado ante un error. */
    public void trasladar(String codigoObjeto, String nombreDestino, String motivo, Usuario encargado) {
        validarEncargado(encargado);
        if (motivo == null || motivo.trim().isEmpty()) throw new IllegalArgumentException("El motivo del traslado es obligatorio.");
        ObjetoEncontrado objeto = buscarObjeto(codigoObjeto);
        if (objeto == null || !"EN_CUSTODIA".equals(objeto.getEstadoCustodia()))
            throw new IllegalArgumentException("El objeto no está actualmente bajo custodia.");
        Ubicacion origen = objeto.getUbicacionActual();
        Ubicacion destino = buscarUbicacion(nombreDestino);
        if (destino == origen) throw new IllegalArgumentException("La ubicación de destino debe ser diferente de la actual.");
        validarDestino(destino, objeto.getReporteHallazgo().getCategoria());

        // Las dos listas, la ubicación actual y el historial cambian solo después de validar el traslado completo.
        origen.getObjetos().remove(objeto); destino.getObjetos().add(objeto); objeto.setUbicacionActual(destino);
        movimientos.add(new MovimientoCustodia(objeto, origen, destino, LocalDateTime.now(), motivo.trim(), encargado));
    }

    /** Busca una ubicación por nombre, sin distinguir mayúsculas. */
    public Ubicacion buscarUbicacion(String nombre) {
        if (nombre == null) throw new IllegalArgumentException("Debe seleccionar una ubicación.");
        for (Ubicacion ubicacion : ubicaciones) if (ubicacion.getNombre().equalsIgnoreCase(nombre.trim())) return ubicacion;
        throw new IllegalArgumentException("La ubicación indicada no existe.");
    }
    /** Busca un objeto físico por el código de su reporte de hallazgo. */
    public ObjetoEncontrado buscarObjeto(String codigo) {
        if (codigo == null) return null;
        for (ObjetoEncontrado objeto : objetos) if (objeto.getCodigo().equalsIgnoreCase(codigo.trim())) return objeto;
        return null;
    }
    /** Obtiene el historial completo de un objeto, incluyendo su recepción inicial. */
    public ArrayList<MovimientoCustodia> consultarHistorial(String codigoObjeto) {
        ArrayList<MovimientoCustodia> historial = new ArrayList<MovimientoCustodia>();
        for (MovimientoCustodia movimiento : movimientos) if (movimiento.getObjeto().getCodigo().equalsIgnoreCase(codigoObjeto)) historial.add(movimiento);
        return historial;
    }
    /** Revisa todas las condiciones del destino antes de modificar la custodia. */
    private void validarDestino(Ubicacion destino, String categoria) {
        if (!destino.isHabilitada()) throw new IllegalArgumentException("La ubicación está inhabilitada.");
        if (destino.getEspaciosDisponibles() <= 0) throw new IllegalArgumentException("La ubicación no tiene espacios disponibles.");
        if (!destino.admiteCategoria(categoria)) throw new IllegalArgumentException("La ubicación no admite la categoría " + categoria + ".");
    }
    /** Protege las operaciones administrativas para que solo las realice un encargado activo. */
    private void validarEncargado(Usuario encargado) {
        if (encargado == null || !"ENCARGADO".equals(encargado.getRol()) || !"ACTIVA".equals(encargado.getEstado()))
            throw new IllegalArgumentException("Esta operación requiere una cuenta de encargado autorizada.");
    }
    public ArrayList<Ubicacion> getUbicaciones() { return ubicaciones; }
    public ArrayList<ObjetoEncontrado> getObjetos() { return objetos; }
    public ArrayList<MovimientoCustodia> getMovimientos() { return movimientos; }
}
