package negocio;

import modelo.ReporteHallazgo;
import modelo.Usuario;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Registra, valida, edita y consulta avisos de objetos encontrados. */
public class GestorReportesHallazgo {
    /** Avisos de hallazgo que no necesariamente representan objetos recibidos. */
    private ArrayList<ReporteHallazgo> reportes;
    /** Secuencia que diferencia los códigos de los reportes. */
    private int siguienteCodigo;

    /** Inicializa la colección en memoria. */
    public GestorReportesHallazgo() { reportes = new ArrayList<ReporteHallazgo>(); siguienteCodigo = 1; }

    /** Crea el aviso en PENDIENTE_RECEPCION; no crea ni almacena el objeto físico. */
    public ReporteHallazgo registrar(Usuario reportante, String categoria, String marca, String color,
                                     String descripcion, String caracteristicas, LocalDateTime fecha,
                                     String lugar, String fotografia) {
        if (reportante == null) throw new IllegalArgumentException("Debe iniciar sesión para registrar un hallazgo.");
        validar(categoria, color, descripcion, fecha, lugar);
        ReporteHallazgo reporte = new ReporteHallazgo("HAL-" + siguienteCodigo++, reportante, categoria.trim(),
                limpiarOpcional(marca), color.trim(), descripcion.trim(), limpiarOpcional(caracteristicas),
                fecha, lugar.trim(), limpiarOpcional(fotografia), "PENDIENTE_RECEPCION");
        reportes.add(reporte);
        return reporte;
    }

    /** Edita un aviso propio mientras no se haya asociado a una recepción física. */
    public void editar(String codigo, Usuario reportante, String categoria, String marca, String color,
                       String descripcion, String caracteristicas, LocalDateTime fecha, String lugar, String fotografia) {
        ReporteHallazgo reporte = buscarPorCodigo(codigo);
        if (reporte == null) throw new IllegalArgumentException("No existe el reporte indicado.");
        if (reporte.getUsuarioReportante() != reportante) throw new IllegalArgumentException("Solo quien registró el hallazgo puede editarlo.");
        if (reporte.getObjetoEncontrado() != null) throw new IllegalArgumentException("El objeto ya fue recibido; sus datos de identificación no se pueden editar.");
        validar(categoria, color, descripcion, fecha, lugar);
        reporte.setCategoria(categoria.trim()); reporte.setMarca(limpiarOpcional(marca)); reporte.setColor(color.trim());
        reporte.setDescripcion(descripcion.trim()); reporte.setCaracteristicas(limpiarOpcional(caracteristicas));
        reporte.setFechaHallazgo(fecha); reporte.setLugar(lugar.trim()); reporte.setFotografia(limpiarOpcional(fotografia));
    }

    /** Busca un aviso de hallazgo por su código. */
    public ReporteHallazgo buscarPorCodigo(String codigo) {
        if (codigo == null) return null;
        for (ReporteHallazgo reporte : reportes) if (reporte.getCodigo().equalsIgnoreCase(codigo.trim())) return reporte;
        return null;
    }
    /** Comprueba los campos requeridos y rechaza fechas posteriores al momento actual. */
    private void validar(String categoria, String color, String descripcion, LocalDateTime fecha, String lugar) {
        obligatorio(categoria, "La categoría es obligatoria."); obligatorio(color, "El color es obligatorio.");
        obligatorio(descripcion, "La descripción es obligatoria."); obligatorio(lugar, "El lugar es obligatorio.");
        if (fecha == null) throw new IllegalArgumentException("La fecha del hallazgo es obligatoria.");
        if (fecha.isAfter(LocalDateTime.now())) throw new IllegalArgumentException("La fecha del hallazgo no puede estar en el futuro.");
    }
    /** Comprueba que un dato requerido tenga contenido. */
    private void obligatorio(String valor, String mensaje) { if (valor == null || valor.trim().isEmpty()) throw new IllegalArgumentException(mensaje); }
    /** Convierte los campos opcionales nulos en texto vacío. */
    private String limpiarOpcional(String valor) { return valor == null ? "" : valor.trim(); }
    public ArrayList<ReporteHallazgo> getReportes() { return reportes; }
}
