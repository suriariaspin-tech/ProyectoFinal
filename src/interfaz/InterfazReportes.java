package interfaz;

import modelo.Coincidencia;
import modelo.ReporteHallazgo;
import modelo.ReportePerdida;
import modelo.Usuario;
import negocio.GestorCoincidencias;
import negocio.GestorReportesHallazgo;
import negocio.GestorReportesPerdida;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Lee y presenta por consola reportes de pérdida y reportes de hallazgo por separado. */
public class InterfazReportes {
    private GestorReportesPerdida gestorPerdidas; //Gestor compartido de los reportes de pérdida.
    private GestorReportesHallazgo gestorHallazgos; //Gestor compartido de los reportes de hallazgo.
    private GestorCoincidencias gestorCoincidencias; //Gestor compartido para mostrar estado de posibles coincidencias.

    /** Recibe los gestores compartidos de reportes y coincidencias. */
    public InterfazReportes(GestorReportesPerdida perdidas, GestorReportesHallazgo hallazgos,
                            GestorCoincidencias coincidencias) {
        gestorPerdidas = perdidas;
        gestorHallazgos = hallazgos;
        gestorCoincidencias = coincidencias;
    }

    /** Solicita datos y registra un reporte de pérdida independiente. */
    public void registrarPerdida(Usuario usuario) {
        System.out.println("\n--- Registrar objeto perdido ---");
        String categoria = Validacion.seleccionarCategoria();
        String marca = Validacion.leerTextoOpcional("Marca (opcional, Enter para omitir): ");
        String color = Validacion.leerTextoObligatorio("Color: ");
        String descripcion = Validacion.leerTextoObligatorio("Descripción: ");
        String caracteristicas = Validacion.leerTextoOpcional("Características distintivas (opcional): ");
        LocalDateTime fecha = Validacion.leerFechaNoFutura("Fecha y hora aproximada de pérdida");
        String lugar = Validacion.leerTextoObligatorio("Lugar donde se perdió: ");
        String fotografia = Validacion.leerTextoOpcional("Ruta de fotografía (opcional): ");
        ReportePerdida reporte = gestorPerdidas.registrar(usuario, categoria, marca, color,
                descripcion, caracteristicas, fecha, lugar, fotografia);
        if (reporte == null) System.out.println("No se pudo registrar: " + gestorPerdidas.getMensajeError());
        else System.out.println("Reporte de pérdida " + reporte.getCodigo() + " registrado en estado ABIERTO.");
    }

    /** Solicita datos y registra un aviso de hallazgo todavía no recibido físicamente. */
    public void registrarHallazgo(Usuario usuario) {
        System.out.println("\n--- Registrar objeto encontrado ---");
        String categoria = Validacion.seleccionarCategoria();
        String marca = Validacion.leerTextoOpcional("Marca (opcional, Enter para omitir): ");
        String color = Validacion.leerTextoObligatorio("Color: ");
        String descripcion = Validacion.leerTextoObligatorio("Descripción: ");
        String caracteristicas = Validacion.leerTextoOpcional("Características distintivas (opcional): ");
        LocalDateTime fecha = Validacion.leerFechaNoFutura("Fecha y hora del hallazgo");
        String lugar = Validacion.leerTextoObligatorio("Lugar donde se encontró: ");
        String fotografia = Validacion.leerTextoOpcional("Ruta de fotografía (opcional): ");
        ReporteHallazgo reporte = gestorHallazgos.registrar(usuario, categoria, marca, color,
                descripcion, caracteristicas, fecha, lugar, fotografia);
        if (reporte == null) System.out.println("No se pudo registrar: " + gestorHallazgos.getMensajeError());
        else System.out.println("Hallazgo " + reporte.getCodigo() + " creado. Estado: PENDIENTE_RECEPCION.");
    }

    /** Muestra pérdidas propias y el estado de coincidencias de cada reporte. */
    public void consultarPerdidas(Usuario usuario) {
        ArrayList<ReportePerdida> reportes = gestorPerdidas.consultarPorPropietario(usuario);
        if (reportes.isEmpty()) {
            System.out.println("No tiene reportes de pérdida.");
            return;
        }
        gestorCoincidencias.recalcular(gestorPerdidas.getReportes(), gestorHallazgos.getReportes());
        for (ReportePerdida reporte : reportes) {
            boolean tieneCoincidencia = false;
            for (Coincidencia coincidencia : gestorCoincidencias.getCoincidencias()) {
                if (coincidencia.getReportePerdida() == reporte) tieneCoincidencia = true;
            }
            String estadoCoincidencia;
            if (tieneCoincidencia) estadoCoincidencia = "SÍ";
            else estadoCoincidencia = "NO";
            System.out.println(resumen(reporte) + " | Estado: " + reporte.getEstado()
                    + " | Posibles coincidencias: " + estadoCoincidencia);
        }
    }

    /** Selecciona una pérdida propia y edita sus datos solo si continúa abierta. */
    public void editarPerdida(Usuario usuario) {
        consultarPerdidas(usuario);
        String codigo = Validacion.leerTextoObligatorio("Código del reporte de pérdida a editar: ");
        ReportePerdida reporte = gestorPerdidas.buscarPorCodigo(codigo);
        if (reporte == null || reporte.getPropietario() != usuario) {
            System.out.println("El reporte no existe o no le pertenece.");
            return;
        }
        System.out.println("Ingrese los nuevos datos del reporte.");
        String categoria = Validacion.seleccionarCategoria();
        String marca = Validacion.leerTextoOpcional("Marca (opcional): ");
        String color = Validacion.leerTextoObligatorio("Color: ");
        String descripcion = Validacion.leerTextoObligatorio("Descripción: ");
        String caracteristicas = Validacion.leerTextoOpcional("Características distintivas (opcional): ");
        LocalDateTime fecha = Validacion.leerFechaNoFutura("Fecha y hora aproximada de pérdida");
        String lugar = Validacion.leerTextoObligatorio("Lugar donde se perdió: ");
        String fotografia = Validacion.leerTextoOpcional("Ruta de fotografía (opcional): ");
        if (gestorPerdidas.editar(codigo, usuario, categoria, marca, color, descripcion,
                caracteristicas, fecha, lugar, fotografia)) System.out.println("Reporte actualizado.");
        else System.out.println("No se pudo editar: " + gestorPerdidas.getMensajeError());
    }

    /** Muestra los hallazgos registrados por el usuario y el estado de su recepción. */
    public void consultarHallazgos(Usuario usuario) {
        ArrayList<ReporteHallazgo> reportes = usuario.getReportesHallazgo();
        if (reportes.isEmpty()) {
            System.out.println("No tiene reportes de hallazgo.");
            return;
        }
        for (ReporteHallazgo reporte : reportes) System.out.println(resumen(reporte)
                + " | Recepción: " + reporte.getEstadoRecepcion());
    }

    /** Edita un hallazgo propio solo si el objeto sigue sin recepción física. */
    public void editarHallazgo(Usuario usuario) {
        consultarHallazgos(usuario);
        String codigo = Validacion.leerTextoObligatorio("Código del hallazgo a editar: ");
        ReporteHallazgo reporte = gestorHallazgos.buscarPorCodigo(codigo);
        if (reporte == null || reporte.getUsuarioReportante() != usuario) {
            System.out.println("El reporte no existe o no le pertenece.");
            return;
        }
        String categoria = Validacion.seleccionarCategoria();
        String marca = Validacion.leerTextoOpcional("Marca (opcional): ");
        String color = Validacion.leerTextoObligatorio("Color: ");
        String descripcion = Validacion.leerTextoObligatorio("Descripción: ");
        String caracteristicas = Validacion.leerTextoOpcional("Características distintivas (opcional): ");
        LocalDateTime fecha = Validacion.leerFechaNoFutura("Fecha y hora del hallazgo");
        String lugar = Validacion.leerTextoObligatorio("Lugar donde se encontró: ");
        String fotografia = Validacion.leerTextoOpcional("Ruta de fotografía (opcional): ");
        if (gestorHallazgos.editar(codigo, usuario, categoria, marca, color, descripcion,
                caracteristicas, fecha, lugar, fotografia)) System.out.println("Hallazgo actualizado.");
        else System.out.println("No se pudo editar: " + gestorHallazgos.getMensajeError());
    }

    /** Imprime todos los reportes para que un encargado encuentre hallazgos pendientes. */
    public void consultarTodos() {
        System.out.println("\n--- Reportes de hallazgo ---");
        if (gestorHallazgos.getReportes().isEmpty()) System.out.println("No hay reportes de hallazgo.");
        for (ReporteHallazgo reporte : gestorHallazgos.getReportes()) {
            System.out.println(resumen(reporte) + " | Recepción: " + reporte.getEstadoRecepcion());
        }
        System.out.println("\n--- Reportes de pérdida ---");
        if (gestorPerdidas.getReportes().isEmpty()) System.out.println("No hay reportes de pérdida.");
        for (ReportePerdida reporte : gestorPerdidas.getReportes()) System.out.println(resumen(reporte) + " | Estado: " + reporte.getEstado());
    }

    /** Prepara un resumen de pérdida para presentar en la consola. */
    private String resumen(ReportePerdida reporte) {
        return reporte.getCodigo() + " | " + reporte.getCategoria() + " | " + reporte.getDescripcion()
                + " | " + reporte.getFechaHoraPerdida() + " | " + reporte.getLugarPerdida();
    }

    /** Prepara un resumen de hallazgo para presentar en la consola. */
    private String resumen(ReporteHallazgo reporte) {
        return reporte.getCodigo() + " | " + reporte.getCategoria() + " | " + reporte.getDescripcion()
                + " | " + reporte.getFechaHoraHallazgo() + " | " + reporte.getLugarHallazgo();
    }
}
