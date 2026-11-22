package negocio;

import modelo.MovimientoCustodia;
import modelo.ObjetoEncontrado;
import modelo.ReporteHallazgo;
import modelo.Ubicacion;
import modelo.Usuario;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Registra recepciones y traslados físicos, y conserva las ubicaciones e historiales. */
public class GestorCustodia {
    private ArrayList<Ubicacion> ubicaciones; //Tres ubicaciones físicas configuradas para el campus.
    private ArrayList<ObjetoEncontrado> objetos; //Objetos que sí se recibieron físicamente.
    private ArrayList<MovimientoCustodia> movimientos; //Historial general de las recepciones iniciales y los
    // traslados realizados.
    private int siguienteMovimiento; //Próximo número para asignar un código de movimiento.
    private String mensajeError; //Explicación de la última operación rechazada.

    /** Capacidades de demostración ajustables; no representan límites oficiales de la UDLA. */
    public static final int CAPACIDAD_DEMO_GARITA = 30;
    public static final int CAPACIDAD_DEMO_BIBLIOTECA = 30;
    public static final int CAPACIDAD_DEMO_RECEPCION = 30;

    /** Crea Garita, Biblioteca y Recepción con las categorías iniciales permitidas. */
    public GestorCustodia() {
        ubicaciones = new ArrayList<Ubicacion>();
        objetos = new ArrayList<ObjetoEncontrado>();
        movimientos = new ArrayList<MovimientoCustodia>();
        siguienteMovimiento = 1;
        mensajeError = "";
        ArrayList<String> categorias = new ArrayList<String>();
        categorias.add("Accesorios");
        categorias.add("Electrónica");
        categorias.add("Ropa");
        categorias.add("Documentos");
        categorias.add("Otros");
        ubicaciones.add(new Ubicacion("UBI-01", "GARITA", CAPACIDAD_DEMO_GARITA, new ArrayList<String>(categorias)));
        ubicaciones.add(new Ubicacion("UBI-02", "BIBLIOTECA", CAPACIDAD_DEMO_BIBLIOTECA, new ArrayList<String>(categorias)));
        ubicaciones.add(new Ubicacion("UBI-03", "RECEPCION", CAPACIDAD_DEMO_RECEPCION, new ArrayList<String>(categorias)));
    }

    /** Recibe un hallazgo en la ubicación elegida; devuelve null y un mensaje si alguna regla falla. */
    public ObjetoEncontrado registrarRecepcion(String codigoHallazgo, String nombreUbicacion,
                                               Usuario encargado, GestorReportesHallazgo gestorHallazgos) {
        mensajeError = "";
        ReporteHallazgo reporte = null;
        if (gestorHallazgos != null) reporte = gestorHallazgos.buscarPorCodigo(codigoHallazgo);
        Ubicacion destino = buscarUbicacion(nombreUbicacion);
        if (!esEncargado(encargado)) mensajeError = "Solo un encargado activo puede confirmar una recepción.";
        else if (reporte == null) mensajeError = "No existe el reporte de hallazgo indicado.";
        else if (reporte.getObjetoEncontrado() != null) mensajeError = "Este objeto ya fue recibido.";
        else if (destino == null) mensajeError = "La ubicación seleccionada no existe.";
        else if (!destino.isHabilitada()) mensajeError = "La ubicación está inhabilitada.";
        else if (!destino.tieneEspacio()) mensajeError = "La ubicación no tiene espacios disponibles.";
        else if (!destino.admiteCategoria(reporte.getCategoria())) mensajeError = "La ubicación no admite esa categoría.";
        if (!mensajeError.isEmpty()) return null;

        ObjetoEncontrado objeto = new ObjetoEncontrado(reporte.getCodigo(), reporte, null, "EN_CUSTODIA");
        if (!destino.registrarEntrada(objeto)) {
            mensajeError = "No se pudo agregar el objeto a la ubicación.";
            return null;
        }
        objetos.add(objeto);
        reporte.confirmarRecepcion(objeto);
        // La recepción inicial también forma parte del recorrido, aunque el origen sea externo al campus.
        MovimientoCustodia movimiento = new MovimientoCustodia("MOV-" + siguienteMovimiento, objeto,
                null, destino, LocalDateTime.now(), "Recepción inicial", "", encargado);
        siguienteMovimiento++;
        movimientos.add(movimiento);
        objeto.agregarMovimiento(movimiento);
        return objeto;
    }

    /** Traslada un objeto tras validar todos los datos; si falla, no cambia ninguna colección. */
    public boolean trasladar(String codigoObjeto, String nombreDestino, String motivo, Usuario encargado) {
        mensajeError = "";
        ObjetoEncontrado objeto = buscarObjeto(codigoObjeto);
        Ubicacion destino = buscarUbicacion(nombreDestino);
        if (!esEncargado(encargado)) mensajeError = "Solo un encargado activo puede realizar traslados.";
        else if (motivo == null || motivo.trim().isEmpty()) mensajeError = "El motivo del traslado es obligatorio.";
        else if (objeto == null) mensajeError = "No existe un objeto recibido con ese código.";
        else if (!"EN_CUSTODIA".equals(objeto.getEstadoCustodia())) mensajeError = "El objeto no está bajo custodia.";
        else if (destino == null) mensajeError = "La ubicación de destino no existe.";
        else if (objeto.getReporteHallazgo() == null) mensajeError = "El objeto no tiene un reporte de hallazgo válido.";
        else if (objeto.getUbicacionActual() == null) mensajeError = "El objeto no tiene una ubicación de origen válida.";
        else if (objeto.getUbicacionActual() == destino) mensajeError = "El destino debe ser diferente de la ubicación actual.";
        else if (!destino.isHabilitada()) mensajeError = "La ubicación de destino está inhabilitada.";
        else if (!destino.tieneEspacio()) mensajeError = "La ubicación de destino no tiene espacios disponibles.";
        else if (!destino.admiteCategoria(objeto.getReporteHallazgo().getCategoria())) mensajeError = "La ubicación de destino no admite esa categoría.";
        else if (!objeto.getUbicacionActual().validarSalida(objeto)) mensajeError = "El objeto no figura en su ubicación de origen.";
        else if (!destino.validarEntrada(objeto)) mensajeError = "No se pudo validar la entrada en el destino.";
        if (!mensajeError.isEmpty()) return false;

        Ubicacion origen = objeto.getUbicacionActual();
        // Todas las validaciones anteriores terminan antes de actualizar las dos ubicaciones y el historial.
        if (!origen.registrarSalida(objeto)) {
            mensajeError = "No se pudo retirar el objeto de su ubicación de origen.";
            return false;
        }
        if (!destino.registrarEntrada(objeto)) {
            // Si el destino rechaza la entrada, se restaura la salida para no dejar cambios parciales.
            origen.registrarEntrada(objeto);
            mensajeError = "No se pudo agregar el objeto al destino; se restauró la ubicación de origen.";
            return false;
        }
        MovimientoCustodia movimiento = new MovimientoCustodia("MOV-" + siguienteMovimiento, objeto,
                origen, destino, LocalDateTime.now(), motivo.trim(), "", encargado);
        siguienteMovimiento++;
        movimientos.add(movimiento);
        objeto.agregarMovimiento(movimiento);
        return true;
    }

    /** Busca una ubicación por nombre, sin distinguir mayúsculas; devuelve null si no existe. */
    public Ubicacion buscarUbicacion(String nombre) {
        if (nombre == null) return null;
        for (Ubicacion ubicacion : ubicaciones) {
            if (ubicacion.getNombre().equalsIgnoreCase(nombre.trim())) return ubicacion;
        }
        return null;
    }

    /** Busca un objeto recibido por código; devuelve null si no existe. */
    public ObjetoEncontrado buscarObjeto(String codigo) {
        if (codigo == null) return null;
        for (ObjetoEncontrado objeto : objetos) {
            if (objeto.getCodigoObjeto().equalsIgnoreCase(codigo.trim())) return objeto;
        }
        return null;
    }

    /** Devuelve una copia del historial de movimientos del objeto indicado. */
    public ArrayList<MovimientoCustodia> consultarHistorial(String codigoObjeto) {
        ArrayList<MovimientoCustodia> historial = new ArrayList<MovimientoCustodia>();
        ObjetoEncontrado objeto = buscarObjeto(codigoObjeto);
        if (objeto != null) historial.addAll(objeto.getMovimientos());
        return historial;
    }

    /** Comprueba que la cuenta tenga el rol y estado correctos para una operación de custodia. */
    private boolean esEncargado(Usuario usuario) {
        return usuario != null && "ENCARGADO".equals(usuario.getRol())
                && "ACTIVA".equals(usuario.getEstadoCuenta());
    }

    public String getMensajeError(){
        return mensajeError; } //Devuelve el motivo de la última operación de custodia rechazada.
    public ArrayList<Ubicacion> getUbicaciones(){
        return new ArrayList<Ubicacion>(ubicaciones); } //Devuelve las ubicaciones disponibles en la sesión.
    public ArrayList<ObjetoEncontrado> getObjetos(){
        return new ArrayList<ObjetoEncontrado>(objetos); } //Devuelve los objetos recibidos en la sesión.
    public ArrayList<MovimientoCustodia> getMovimientos(){
        return new ArrayList<MovimientoCustodia>(movimientos); } //Devuelve los movimientos hechos durante la sesión.
}
