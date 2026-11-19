package interfaz;

import modelo.ReporteHallazgo;
import modelo.ReportePerdida;
import modelo.Usuario;
import negocio.GestorCoincidencias;
import negocio.GestorReportesHallazgo;
import negocio.GestorReportesPerdida;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/** Contiene formularios independientes para reportes perdidos y encontrados. */
public class InterfazReportes {
    /** Gestor de reportes de pérdida. */
    private GestorReportesPerdida gestorPerdidas;
    /** Gestor de reportes de hallazgo. */
    private GestorReportesHallazgo gestorHallazgos;
    /** Comparador compartido para indicar el estado de coincidencias en las consultas. */
    private GestorCoincidencias gestorCoincidencias;
    /** Formato claro que se solicita para fecha y hora. */
    private DateTimeFormatter formatoFecha;

    /** Recibe ambos gestores compartidos y configura el formato de fecha. */
    public InterfazReportes(GestorReportesPerdida gestorPerdidas, GestorReportesHallazgo gestorHallazgos,
                            GestorCoincidencias gestorCoincidencias) {
        this.gestorPerdidas = gestorPerdidas; this.gestorHallazgos = gestorHallazgos;
        this.gestorCoincidencias = gestorCoincidencias;
        formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    /** Solicita los datos y registra únicamente un reporte de pérdida. */
    public ReportePerdida registrarPerdida(Usuario usuario) {
        try {
            String[] datos = pedirDatos("pérdida"); if (datos == null) return null;
            ReportePerdida reporte = gestorPerdidas.registrar(usuario, datos[0], datos[1], datos[2], datos[3], datos[4], fecha(datos[5]), datos[6], datos[7]);
            JOptionPane.showMessageDialog(null, "Reporte de pérdida registrado: " + reporte.getCodigo());
            return reporte;
        } catch (IllegalArgumentException error) { mostrarError(error); return null; }
    }

    /** Solicita los datos y registra únicamente un reporte de hallazgo pendiente de recepción. */
    public ReporteHallazgo registrarHallazgo(Usuario usuario) {
        try {
            String[] datos = pedirDatos("hallazgo"); if (datos == null) return null;
            ReporteHallazgo reporte = gestorHallazgos.registrar(usuario, datos[0], datos[1], datos[2], datos[3], datos[4], fecha(datos[5]), datos[6], datos[7]);
            JOptionPane.showMessageDialog(null, "Hallazgo registrado como PENDIENTE_RECEPCION. Código: " + reporte.getCodigo());
            return reporte;
        } catch (IllegalArgumentException error) { mostrarError(error); return null; }
    }

    /** Muestra reportes propios y permite seleccionar uno para editarlo. */
    public void consultarYEditarPerdidas(Usuario usuario) {
        ArrayList<ReportePerdida> lista = gestorPerdidas.consultarPorPropietario(usuario);
        if (lista.isEmpty()) { JOptionPane.showMessageDialog(null, "No tiene reportes de pérdida."); return; }
        gestorCoincidencias.recalcular(gestorPerdidas.getReportes(), gestorHallazgos.getReportes());
        StringBuilder texto = new StringBuilder();
        for (ReportePerdida reporte : lista) {
            boolean tieneCoincidencia = false;
            for (modelo.Coincidencia coincidencia : gestorCoincidencias.getCoincidencias()) {
                if (coincidencia.getReportePerdida() == reporte) { tieneCoincidencia = true; break; }
            }
            texto.append(resumen(reporte)).append(" | Estado: ").append(reporte.getEstado())
                    .append(" | Posibles coincidencias: ").append(tieneCoincidencia ? "SÍ" : "NO").append("\n");
        }
        JOptionPane.showMessageDialog(null, texto.toString(), "Mis reportes de pérdida", JOptionPane.INFORMATION_MESSAGE);
        String codigo = JOptionPane.showInputDialog(null, "Ingrese el código de un reporte ABIERTO para editarlo (Cancelar para volver):");
        if (codigo == null || codigo.trim().isEmpty()) return;
        ReportePerdida reporte = gestorPerdidas.buscarPorCodigo(codigo);
        if (reporte == null || reporte.getPropietario() != usuario) { JOptionPane.showMessageDialog(null, "El reporte no existe o no le pertenece.", "Error", JOptionPane.ERROR_MESSAGE); return; }
        try {
            String[] datos = pedirDatos("pérdida"); if (datos == null) return;
            gestorPerdidas.editar(codigo, usuario, datos[0], datos[1], datos[2], datos[3], datos[4], fecha(datos[5]), datos[6], datos[7]);
            JOptionPane.showMessageDialog(null, "Reporte actualizado.");
        } catch (IllegalArgumentException error) { mostrarError(error); }
    }

    /** Muestra los hallazgos del usuario y permite editar uno aún no recibido. */
    public void consultarYEditarHallazgos(Usuario usuario) {
        ArrayList<ReporteHallazgo> lista = gestorHallazgos.getReportes();
        StringBuilder texto = new StringBuilder();
        for (ReporteHallazgo reporte : lista) if (reporte.getUsuarioReportante() == usuario) texto.append(resumen(reporte)).append(" Estado de recepción: ").append(reporte.getEstadoRecepcion()).append("\n");
        if (texto.length() == 0) { JOptionPane.showMessageDialog(null, "No tiene reportes de hallazgo."); return; }
        JOptionPane.showMessageDialog(null, texto.toString(), "Mis reportes de hallazgo", JOptionPane.INFORMATION_MESSAGE);
        String codigo = JOptionPane.showInputDialog(null, "Código de hallazgo pendiente que desea editar (Cancelar para volver):");
        if (codigo == null || codigo.trim().isEmpty()) return;
        try {
            String[] datos = pedirDatos("hallazgo"); if (datos == null) return;
            gestorHallazgos.editar(codigo, usuario, datos[0], datos[1], datos[2], datos[3], datos[4], fecha(datos[5]), datos[6], datos[7]);
            JOptionPane.showMessageDialog(null, "Reporte de hallazgo actualizado.");
        } catch (IllegalArgumentException error) { mostrarError(error); }
    }

    /** Presenta todos los reportes para que el encargado encuentre hallazgos pendientes. */
    public void consultarTodos() {
        StringBuilder texto = new StringBuilder("REPORTES DE HALLAZGO\n");
        for (ReporteHallazgo reporte : gestorHallazgos.getReportes()) texto.append(resumen(reporte)).append(" Estado de recepción: ").append(reporte.getEstadoRecepcion()).append("\n");
        texto.append("\nREPORTES DE PÉRDIDA\n");
        for (ReportePerdida reporte : gestorPerdidas.getReportes()) texto.append(resumen(reporte)).append(" Estado: ").append(reporte.getEstado()).append("\n");
        if (gestorHallazgos.getReportes().isEmpty() && gestorPerdidas.getReportes().isEmpty()) texto.append("No hay reportes registrados.");
        JOptionPane.showMessageDialog(null, texto.toString(), "Reportes", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Solicita los campos de identificación para un solo tipo de reporte. */
    private String[] pedirDatos(String tipo) {
        String[] etiquetas = {"Categoría (Accesorios, Electrónica, Ropa, Documentos u Otros):", "Marca (opcional):", "Color:",
                "Descripción:", "Características distintivas (opcional):", "Fecha y hora (AAAA-MM-DD HH:MM):", "Lugar:", "Fotografía (ruta opcional):"};
        String[] datos = new String[etiquetas.length];
        for (int i = 0; i < etiquetas.length; i++) {
            datos[i] = JOptionPane.showInputDialog(null, "Reporte de " + tipo + " - " + etiquetas[i]);
            if (datos[i] == null) return null;
        }
        return datos;
    }
    /** Convierte la fecha escrita y traduce errores de formato a un mensaje comprensible. */
    private LocalDateTime fecha(String texto) {
        try { return LocalDateTime.parse(texto.trim(), formatoFecha); }
        catch (DateTimeParseException error) { throw new IllegalArgumentException("Use el formato de fecha AAAA-MM-DD HH:MM."); }
    }
    /** Resume los datos visibles de un reporte de pérdida. */
    private String resumen(ReportePerdida reporte) {
        return reporte.getCodigo() + " | " + reporte.getCategoria() + " | " + reporte.getDescripcion() + " | " + reporte.getFechaPerdida() + " | " + reporte.getLugar();
    }
    /** Resume los datos visibles de un reporte de hallazgo. */
    private String resumen(ReporteHallazgo reporte) {
        return reporte.getCodigo() + " | " + reporte.getCategoria() + " | " + reporte.getDescripcion() + " | " + reporte.getFechaHallazgo() + " | " + reporte.getLugar();
    }
    /** Informa la causa por la que no se pudo registrar o editar un reporte. */
    private void mostrarError(Exception error) { JOptionPane.showMessageDialog(null, error.getMessage(), "No se pudo completar", JOptionPane.ERROR_MESSAGE); }
}
