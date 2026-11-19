package interfaz;

import modelo.Usuario;
import negocio.GestorCustodia;
import negocio.GestorCoincidencias;
import negocio.GestorReportesHallazgo;
import negocio.GestorReportesPerdida;
import javax.swing.JOptionPane;

/** Muestra las operaciones administrativas disponibles para un encargado autorizado. */
public class MenuEncargado {
    /** Encargado autenticado y responsable de las operaciones. */
    private Usuario encargado;
    /** Interfaz administrativa para recepción, ubicaciones y traslados. */
    private InterfazCustodia interfazCustodia;
    /** Interfaz que permite consultar los reportes registrados. */
    private InterfazReportes interfazReportes;

    /** Recibe el usuario y los gestores compartidos por la ventana principal. */
    public MenuEncargado(Usuario encargado, GestorReportesHallazgo hallazgos, GestorCustodia custodia,
                         GestorReportesPerdida perdidas, GestorCoincidencias coincidencias) {
        this.encargado = encargado; interfazCustodia = new InterfazCustodia(custodia, hallazgos);
        interfazReportes = new InterfazReportes(perdidas, hallazgos, coincidencias);
    }

    /** Repite el menú administrativo hasta cerrar la sesión. */
    public void mostrar() {
        String[] opciones = {"Registrar recepción física", "Consultar disponibilidad de ubicaciones", "Trasladar un objeto",
                "Consultar ubicación e historial", "Consultar reportes registrados", "Cerrar sesión"};
        while (true) {
            int opcion = JOptionPane.showOptionDialog(null, "Sesión de encargado: " + encargado.getNombres(), "Menú de encargado",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            if (opcion == 0) interfazCustodia.registrarRecepcion(encargado);
            else if (opcion == 1) interfazCustodia.consultarDisponibilidad();
            else if (opcion == 2) interfazCustodia.trasladar(encargado);
            else if (opcion == 3) interfazCustodia.consultarUbicacionEHistorial();
            else if (opcion == 4) interfazReportes.consultarTodos();
            else return;
        }
    }
}
