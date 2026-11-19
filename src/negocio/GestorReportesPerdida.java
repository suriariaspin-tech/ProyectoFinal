package negocio;

import modelo.ReportePerdida;
import modelo.Usuario;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Registra, edita y busca reportes de objetos perdidos. */
public class GestorReportesPerdida {
    /** Reportes de pérdida guardados durante esta ejecución. */
    private ArrayList<ReportePerdida> reportes;
    /** Próximo número para asignar códigos únicos. */
    private int siguienteCodigo;
    /** Explicación de la última operación rechazada. */
    private String mensajeError;

    /** Crea una colección vacía de reportes. */
    public GestorReportesPerdida() {
        reportes = new ArrayList<ReportePerdida>();
        siguienteCodigo = 1;
        mensajeError = "";
    }

    /** Valida y registra una pérdida abierta; devuelve null y guarda un mensaje si hay errores. */
    public ReportePerdida registrar(Usuario propietario, String categoria, String marca, String color,
                                    String descripcion, String caracteristicas, LocalDateTime fecha,
                                    String lugar, String fotografia) {
        mensajeError = validar(propietario, categoria, color, descripcion, fecha, lugar);
        if (!mensajeError.isEmpty()) return null;
        ReportePerdida reporte = new ReportePerdida("PER-" + siguienteCodigo, propietario,
                categoria.trim(), limpiarOpcional(marca), color.trim(), descripcion.trim(),
                limpiarOpcional(caracteristicas), fecha, lugar.trim(), limpiarOpcional(fotografia), "ABIERTO");
        siguienteCodigo++;
        reportes.add(reporte);
        propietario.agregarReportePerdida(reporte);
        return reporte;
    }

    /** Edita los datos de un reporte abierto únicamente si pertenece al usuario indicado. */
    public boolean editar(String codigo, Usuario propietario, String categoria, String marca, String color,
                          String descripcion, String caracteristicas, LocalDateTime fecha,
                          String lugar, String fotografia) {
        ReportePerdida reporte = buscarPorCodigo(codigo);
        mensajeError = "";
        if (reporte == null) mensajeError = "No existe el reporte indicado.";
        else if (reporte.getPropietario() != propietario) mensajeError = "El reporte no pertenece a esta cuenta.";
        else if (!reporte.getEstado().equals("ABIERTO")) mensajeError = "No se puede editar un reporte cerrado.";
        else mensajeError = validar(propietario, categoria, color, descripcion, fecha, lugar);
        if (!mensajeError.isEmpty()) return false;
        reporte.setCategoria(categoria.trim());
        reporte.setMarca(limpiarOpcional(marca));
        reporte.setColor(color.trim());
        reporte.setDescripcion(descripcion.trim());
        reporte.setCaracteristicas(limpiarOpcional(caracteristicas));
        reporte.setFechaHoraPerdida(fecha);
        reporte.setLugarPerdida(lugar.trim());
        reporte.setFotografia(limpiarOpcional(fotografia));
        return true;
    }

    /** Busca un reporte por código o devuelve null si no se encuentra. */
    public ReportePerdida buscarPorCodigo(String codigo) {
        if (codigo == null) return null;
        for (ReportePerdida reporte : reportes) {
            if (reporte.getCodigo().equalsIgnoreCase(codigo.trim())) return reporte;
        }
        return null;
    }

    /** Devuelve reportes cuyo propietario es la cuenta recibida. */
    public ArrayList<ReportePerdida> consultarPorPropietario(Usuario propietario) {
        ArrayList<ReportePerdida> propios = new ArrayList<ReportePerdida>();
        for (ReportePerdida reporte : reportes) {
            if (reporte.getPropietario() == propietario) propios.add(reporte);
        }
        return propios;
    }

    /** Revisa los datos obligatorios y que la fecha del suceso no sea futura. */
    private String validar(Usuario propietario, String categoria, String color, String descripcion,
                           LocalDateTime fecha, String lugar) {
        if (propietario == null) return "Debe iniciar sesión para registrar una pérdida.";
        if (vacio(categoria)) return "La categoría es obligatoria.";
        if (vacio(color)) return "El color es obligatorio.";
        if (vacio(descripcion)) return "La descripción es obligatoria.";
        if (vacio(lugar)) return "El lugar donde se perdió es obligatorio.";
        if (fecha == null) return "La fecha de pérdida es obligatoria.";
        if (fecha.isAfter(LocalDateTime.now())) return "La fecha de pérdida no puede estar en el futuro.";
        return "";
    }

    /** Comprueba si falta un dato de texto obligatorio. */
    private boolean vacio(String texto) { return texto == null || texto.trim().isEmpty(); }
    /** Convierte un campo opcional nulo en texto vacío. */
    private String limpiarOpcional(String texto) {
        if (texto == null) return "";
        return texto.trim();
    }
    /** Devuelve el mensaje de la última operación rechazada. */
    public String getMensajeError() { return mensajeError; }
    /** Devuelve todos los reportes de pérdida de la sesión. */
    /** Devuelve una copia de la lista de pérdidas conservando las mismas entidades. */
    public ArrayList<ReportePerdida> getReportes() { return new ArrayList<ReportePerdida>(reportes); }
}
