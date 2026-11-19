package negocio;

import modelo.ReportePerdida;
import modelo.Usuario;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Registra, valida, edita y consulta reportes de objetos perdidos. */
public class GestorReportesPerdida {
    /** Reportes creados durante la ejecución. */
    private ArrayList<ReportePerdida> reportes;
    /** Secuencia que diferencia los códigos de los reportes. */
    private int siguienteCodigo;

    /** Inicializa la colección en memoria. */
    public GestorReportesPerdida() { reportes = new ArrayList<ReportePerdida>(); siguienteCodigo = 1; }

    /** Valida los datos y crea un reporte nuevo en estado ABIERTO. */
    public ReportePerdida registrar(Usuario propietario, String categoria, String marca, String color,
                                    String descripcion, String caracteristicas, LocalDateTime fecha,
                                    String lugar, String fotografia) {
        if (propietario == null) throw new IllegalArgumentException("Debe iniciar sesión para reportar una pérdida.");
        validar(categoria, color, descripcion, fecha, lugar);
        ReportePerdida reporte = new ReportePerdida("PER-" + siguienteCodigo++, propietario, categoria.trim(),
                limpiarOpcional(marca), color.trim(), descripcion.trim(), limpiarOpcional(caracteristicas),
                fecha, lugar.trim(), limpiarOpcional(fotografia), "ABIERTO");
        reportes.add(reporte);
        return reporte;
    }

    /** Permite al propietario editar un reporte abierto, sin modificar dueño o estado. */
    public void editar(String codigo, Usuario propietario, String categoria, String marca, String color,
                       String descripcion, String caracteristicas, LocalDateTime fecha, String lugar, String fotografia) {
        ReportePerdida reporte = buscarPorCodigo(codigo);
        if (reporte == null) throw new IllegalArgumentException("No existe el reporte indicado.");
        if (reporte.getPropietario() != propietario) throw new IllegalArgumentException("Solo el propietario puede editar este reporte.");
        if (!"ABIERTO".equals(reporte.getEstado())) throw new IllegalArgumentException("No se puede editar un reporte cerrado.");
        validar(categoria, color, descripcion, fecha, lugar);
        reporte.setCategoria(categoria.trim()); reporte.setMarca(limpiarOpcional(marca)); reporte.setColor(color.trim());
        reporte.setDescripcion(descripcion.trim()); reporte.setCaracteristicas(limpiarOpcional(caracteristicas));
        reporte.setFechaPerdida(fecha); reporte.setLugar(lugar.trim()); reporte.setFotografia(limpiarOpcional(fotografia));
    }

    /** Devuelve un reporte con el código indicado o null si no existe. */
    public ReportePerdida buscarPorCodigo(String codigo) {
        for (ReportePerdida reporte : reportes) if (reporte.getCodigo().equalsIgnoreCase(codigo)) return reporte;
        return null;
    }

    /** Devuelve solamente los reportes de pérdida pertenecientes a una persona. */
    public ArrayList<ReportePerdida> consultarPorPropietario(Usuario propietario) {
        ArrayList<ReportePerdida> propios = new ArrayList<ReportePerdida>();
        for (ReportePerdida reporte : reportes) if (reporte.getPropietario() == propietario) propios.add(reporte);
        return propios;
    }

    /** Comprueba los campos obligatorios y que la fecha no sea futura. */
    private void validar(String categoria, String color, String descripcion, LocalDateTime fecha, String lugar) {
        obligatorio(categoria, "La categoría es obligatoria."); obligatorio(color, "El color es obligatorio.");
        obligatorio(descripcion, "La descripción es obligatoria."); obligatorio(lugar, "El lugar es obligatorio.");
        if (fecha == null) throw new IllegalArgumentException("La fecha de pérdida es obligatoria.");
        if (fecha.isAfter(LocalDateTime.now())) throw new IllegalArgumentException("La fecha de pérdida no puede estar en el futuro.");
    }
    /** Comprueba que un dato requerido tenga contenido. */
    private void obligatorio(String valor, String mensaje) { if (valor == null || valor.trim().isEmpty()) throw new IllegalArgumentException(mensaje); }
    /** Convierte los campos opcionales nulos en texto vacío. */
    private String limpiarOpcional(String valor) { return valor == null ? "" : valor.trim(); }
    public ArrayList<ReportePerdida> getReportes() { return reportes; }
}
