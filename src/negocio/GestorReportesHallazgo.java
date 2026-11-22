package negocio;

import modelo.ReporteHallazgo;
import modelo.Usuario;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Registra y administra los avisos de objetos encontrados, estén recibidos o no. */
public class GestorReportesHallazgo {
    private ArrayList<ReporteHallazgo> reportes; //Reportes de hallazgo que se mantienen separados de los objetos físicos.
    private int siguienteCodigo; //Próximo número para asignar códigos únicos.
    private String mensajeError; //Explicación de la última operación rechazada.

    /** Crea una colección vacía de reportes de hallazgo. */
    public GestorReportesHallazgo() {
        reportes = new ArrayList<ReporteHallazgo>();
        siguienteCodigo = 1;
        mensajeError = "";
    }

    /** Registra un hallazgo como pendiente de recepción sin crear aún el objeto físico. */
    public ReporteHallazgo registrar(Usuario reportante, String categoria, String marca, String color,
                                     String descripcion, String caracteristicas, LocalDateTime fecha,
                                     String lugar, String fotografia) {
        mensajeError = validar(reportante, categoria, color, descripcion, fecha, lugar);
        if (!mensajeError.isEmpty()) return null;
        ReporteHallazgo reporte = new ReporteHallazgo("HAL-" + siguienteCodigo, reportante,
                categoria.trim(), limpiarOpcional(marca), color.trim(), descripcion.trim(),
                limpiarOpcional(caracteristicas), fecha, lugar.trim(), limpiarOpcional(fotografia), "PENDIENTE_RECEPCION");
        siguienteCodigo++;
        reportes.add(reporte);
        reportante.agregarReporteHallazgo(reporte);
        return reporte;
    }

    /** Edita un hallazgo propio solo si el objeto todavía no fue recibido físicamente. */
    public boolean editar(String codigo, Usuario reportante, String categoria, String marca, String color,
                          String descripcion, String caracteristicas, LocalDateTime fecha,
                          String lugar, String fotografia) {
        ReporteHallazgo reporte = buscarPorCodigo(codigo);
        mensajeError = "";
        if (reporte == null) mensajeError = "No existe el reporte indicado.";
        else if (reporte.getUsuarioReportante() != reportante) mensajeError = "El reporte no pertenece a esta cuenta.";
        else if (reporte.getObjetoEncontrado() != null) mensajeError = "El objeto ya fue recibido; sus datos no se pueden editar.";
        else mensajeError = validar(reportante, categoria, color, descripcion, fecha, lugar);
        if (!mensajeError.isEmpty()) return false;
        reporte.setCategoria(categoria.trim());
        reporte.setMarca(limpiarOpcional(marca));
        reporte.setColor(color.trim());
        reporte.setDescripcion(descripcion.trim());
        reporte.setCaracteristicas(limpiarOpcional(caracteristicas));
        reporte.setFechaHoraHallazgo(fecha);
        reporte.setLugarHallazgo(lugar.trim());
        reporte.setFotografia(limpiarOpcional(fotografia));
        return true;
    }

    /** Busca un reporte por código o devuelve null si no se encuentra. */
    public ReporteHallazgo buscarPorCodigo(String codigo) {
        if (codigo == null) return null;
        for (ReporteHallazgo reporte : reportes) {
            if (reporte.getCodigo().equalsIgnoreCase(codigo.trim())) return reporte;
        }
        return null;
    }

    /** Valida el reportante, los textos requeridos y la fecha del hallazgo. */
    private String validar(Usuario reportante, String categoria, String color, String descripcion,
                           LocalDateTime fecha, String lugar) {
        if (reportante == null) return "Debe iniciar sesión para registrar un hallazgo.";
        if (vacio(categoria)) return "La categoría es obligatoria.";
        if (vacio(color)) return "El color es obligatorio.";
        if (vacio(descripcion)) return "La descripción es obligatoria.";
        if (vacio(lugar)) return "El lugar donde se encontró es obligatorio.";
        if (fecha == null) return "La fecha del hallazgo es obligatoria.";
        if (fecha.isAfter(LocalDateTime.now())) return "La fecha del hallazgo no puede estar en el futuro.";
        return "";
    }

    private boolean vacio(String texto){ //Comprueba si falta un dato de texto obligatorio.
        return texto == null || texto.trim().isEmpty();
    }
    private String limpiarOpcional(String texto){ //Convierte un campo opcional nulo en texto vacío.
        if (texto == null) return "";
        return texto.trim();
    }

    public String getMensajeError(){ //Devuelve el mensaje de la última operación rechazada.
        return mensajeError;
    }

    /** Devuelve todos los avisos de hallazgo de la sesión. */
    /** Devuelve una copia de la lista de hallazgos conservando las mismas entidades. */
    public ArrayList<ReporteHallazgo> getReportes(){
        return new ArrayList<ReporteHallazgo>(reportes);
    }
}
