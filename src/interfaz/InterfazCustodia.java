package interfaz;

import modelo.MovimientoCustodia;
import modelo.ObjetoEncontrado;
import modelo.Ubicacion;
import modelo.Usuario;
import negocio.GestorCustodia;
import negocio.GestorReportesHallazgo;
import java.util.ArrayList;

/** Lee y presenta las operaciones de recepción y custodia en la consola. */
public class InterfazCustodia {
    /** Gestor compartido que mantiene ubicaciones, objetos e historial. */
    private GestorCustodia gestorCustodia;
    /** Gestor compartido que contiene los reportes pendientes de recepción. */
    private GestorReportesHallazgo gestorHallazgos;

    /** Recibe los gestores de custodia y hallazgos de la misma sesión. */
    public InterfazCustodia(GestorCustodia custodia, GestorReportesHallazgo hallazgos) {
        gestorCustodia = custodia;
        gestorHallazgos = hallazgos;
    }

    /** Recibe físicamente un reporte pendiente en la ubicación que seleccionó el encargado. */
    public void registrarRecepcion(Usuario encargado) {
        System.out.println("\n--- Recepción física ---");
        String codigo = Validacion.leerTextoObligatorio("Código del reporte de hallazgo: ");
        mostrarUbicaciones();
        String ubicacion = elegirUbicacion();
        ObjetoEncontrado objeto = gestorCustodia.registrarRecepcion(codigo, ubicacion, encargado, gestorHallazgos);
        if (objeto == null) System.out.println("No se pudo registrar la recepción: " + gestorCustodia.getMensajeError());
        else System.out.println("Recepción confirmada. " + objeto.getCodigoObjeto() + " está EN_CUSTODIA en " + ubicacion + ".");
    }

    /** Muestra ocupación, espacios, categorías y estado de cada ubicación. */
    public void consultarDisponibilidad() {
        mostrarUbicaciones();
        for (Ubicacion ubicacion : gestorCustodia.getUbicaciones()) {
            System.out.println(ubicacion.getNombre() + " | Capacidad: " + ubicacion.getCapacidadMaxima()
                    + " | Ocupación: " + ubicacion.getOcupacionActual()
                    + " | Espacios disponibles: " + ubicacion.getEspaciosDisponibles()
                    + " | Categorías: " + ubicacion.getCategoriasAdmitidas()
                    + " | Estado: " + (ubicacion.isHabilitada() ? "HABILITADA" : "INHABILITADA"));
        }
    }

    /** Traslada un objeto bajo custodia después de pedir destino y motivo. */
    public void trasladar(Usuario encargado) {
        System.out.println("\n--- Trasladar objeto ---");
        String codigo = Validacion.leerTextoObligatorio("Código del objeto: ");
        mostrarUbicaciones();
        String destino = elegirUbicacion();
        String motivo = Validacion.leerTextoObligatorio("Motivo del traslado: ");
        if (gestorCustodia.trasladar(codigo, destino, motivo, encargado)) {
            System.out.println("Traslado registrado correctamente.");
        } else {
            System.out.println("No se pudo realizar el traslado: " + gestorCustodia.getMensajeError());
        }
    }

    /** Muestra la ubicación actual y todos los traslados históricos del objeto. */
    public void consultarUbicacionEHistorial() {
        String codigo = Validacion.leerTextoObligatorio("Código del objeto: ");
        ObjetoEncontrado objeto = gestorCustodia.buscarObjeto(codigo);
        if (objeto == null) {
            System.out.println("No se encontró un objeto recibido con ese código.");
            return;
        }
        System.out.println("Objeto: " + objeto.getCodigoObjeto() + " | Estado: " + objeto.getEstadoCustodia()
                + " | Ubicación actual: " + objeto.getUbicacionActual().getNombre());
        ArrayList<MovimientoCustodia> historial = gestorCustodia.consultarHistorial(codigo);
        if (historial.isEmpty()) System.out.println("El objeto aún no tiene traslados.");
        for (MovimientoCustodia movimiento : historial) {
            System.out.println(movimiento.getCodigoMovimiento() + " | " + movimiento.getUbicacionOrigen().getNombre()
                    + " -> " + movimiento.getUbicacionDestino().getNombre()
                    + " | Fecha: " + movimiento.getFechaHora()
                    + " | Motivo: " + movimiento.getMotivo()
                    + " | Encargado: " + movimiento.getEncargado().getNombreCompleto());
        }
    }

    /** Imprime las ubicaciones existentes antes de solicitar el nombre de una. */
    private void mostrarUbicaciones() {
        System.out.println("Ubicaciones disponibles: 1. GARITA  2. BIBLIOTECA  3. RECEPCION");
    }

    /** Convierte la opción numérica del usuario al nombre de una ubicación existente. */
    private String elegirUbicacion() {
        int opcion = Validacion.leerOpcion("Seleccione la ubicación (1-3): ", 1, 3);
        if (opcion == 1) return "GARITA";
        if (opcion == 2) return "BIBLIOTECA";
        return "RECEPCION";
    }
}
