package interfaz;

import modelo.MovimientoCustodia;
import modelo.ObjetoEncontrado;
import modelo.Ubicacion;
import modelo.Usuario;
import negocio.GestorCustodia;
import negocio.GestorReportesHallazgo;
import javax.swing.JOptionPane;
import java.time.format.DateTimeFormatter;

/** Presenta las operaciones administrativas sobre recepción, ubicaciones y traslados. */
public class InterfazCustodia {
    /** Gestor compartido que aplica las reglas de custodia. */
    private GestorCustodia gestorCustodia;
    /** Gestor compartido de avisos de hallazgo pendientes. */
    private GestorReportesHallazgo gestorHallazgos;

    /** Recibe los gestores utilizados también por los demás módulos. */
    public InterfazCustodia(GestorCustodia gestorCustodia, GestorReportesHallazgo gestorHallazgos) {
        this.gestorCustodia = gestorCustodia; this.gestorHallazgos = gestorHallazgos;
    }

    /** Solicita el código de un hallazgo y la ubicación física elegida. */
    public void registrarRecepcion(Usuario encargado) {
        String codigo = pedir("Código del reporte de hallazgo:"); if (codigo == null) return;
        String ubicacion = elegirUbicacion("Ubicación donde quedará el objeto:"); if (ubicacion == null) return;
        try {
            ObjetoEncontrado objeto = gestorCustodia.registrarRecepcion(codigo, ubicacion, encargado, gestorHallazgos);
            JOptionPane.showMessageDialog(null, "Recepción confirmada. " + objeto.getCodigo() + " está EN_CUSTODIA en " + ubicacion + ".");
        } catch (IllegalArgumentException error) { mostrarError(error); }
    }

    /** Muestra capacidad, ocupación, espacios, categorías y disponibilidad por ubicación. */
    public void consultarDisponibilidad() {
        StringBuilder texto = new StringBuilder();
        for (Ubicacion ubicacion : gestorCustodia.getUbicaciones()) {
            texto.append(ubicacion.getNombre()).append(" | Capacidad: ").append(ubicacion.getCapacidadMaxima())
                    .append(" | Ocupación: ").append(ubicacion.getOcupacionActual()).append(" | Espacios: ")
                    .append(ubicacion.getEspaciosDisponibles()).append(" | Categorías: ").append(ubicacion.getCategoriasAdmitidas())
                    .append(" | Estado: ").append(ubicacion.isHabilitada() ? "HABILITADA" : "INHABILITADA").append("\n");
        }
        JOptionPane.showMessageDialog(null, texto.toString(), "Disponibilidad de ubicaciones", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Solicita datos y realiza el traslado mediante el gestor, que valida antes de cambiar listas. */
    public void trasladar(Usuario encargado) {
        String codigo = pedir("Código del objeto (código HAL):"); if (codigo == null) return;
        String destino = elegirUbicacion("Seleccione la nueva ubicación:"); if (destino == null) return;
        String motivo = pedir("Motivo obligatorio del traslado:"); if (motivo == null) return;
        try { gestorCustodia.trasladar(codigo, destino, motivo, encargado); JOptionPane.showMessageDialog(null, "Traslado registrado."); }
        catch (IllegalArgumentException error) { mostrarError(error); }
    }

    /** Presenta ubicación actual e historial administrativo de un objeto recibido. */
    public void consultarUbicacionEHistorial() {
        String codigo = pedir("Código del objeto (código HAL):"); if (codigo == null) return;
        ObjetoEncontrado objeto = gestorCustodia.buscarObjeto(codigo);
        if (objeto == null) { JOptionPane.showMessageDialog(null, "No se encontró un objeto recibido con ese código."); return; }
        StringBuilder texto = new StringBuilder("Objeto: ").append(objeto.getCodigo()).append(" | Estado: ")
                .append(objeto.getEstadoCustodia()).append(" | Ubicación: ").append(objeto.getUbicacionActual().getNombre()).append("\n\nHistorial:\n");
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (MovimientoCustodia movimiento : gestorCustodia.consultarHistorial(codigo)) {
            texto.append(movimiento.getUbicacionOrigen() == null ? "ORIGEN EXTERNO" : movimiento.getUbicacionOrigen().getNombre())
                    .append(" -> ").append(movimiento.getUbicacionDestino().getNombre()).append(" | ")
                    .append(movimiento.getFechaHora().format(formato)).append(" | Motivo: ").append(movimiento.getMotivo())
                    .append(" | Encargado: ").append(movimiento.getEncargado().getNombres()).append("\n");
        }
        JOptionPane.showMessageDialog(null, texto.toString(), "Ubicación e historial", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Presenta únicamente las tres ubicaciones físicas configuradas. */
    private String elegirUbicacion(String mensaje) {
        String[] opciones = {"GARITA", "BIBLIOTECA", "RECEPCION"};
        Object seleccion = JOptionPane.showInputDialog(null, mensaje, "Seleccionar ubicación", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        return seleccion == null ? null : seleccion.toString();
    }
    /** Solicita texto y permite cancelar sin ejecutar una operación. */
    private String pedir(String mensaje) { return JOptionPane.showInputDialog(null, mensaje); }
    /** Informa una validación fallida sin ocultar el motivo. */
    private void mostrarError(Exception error) { JOptionPane.showMessageDialog(null, error.getMessage(), "No se pudo completar", JOptionPane.ERROR_MESSAGE); }
}
