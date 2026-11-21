package interfaz;

import modelo.Usuario;
import negocio.GestorCoincidencias;
import negocio.GestorCustodia;
import negocio.GestorReportesHallazgo;
import negocio.GestorReportesPerdida;

/** Presenta las operaciones administrativas disponibles para un encargado activo. */
public class MenuEncargado {
    private Usuario encargado; //Encargado autenticado y responsable de las operaciones de custodia.
    private InterfazCustodia interfazCustodia; //Interfaces de consola conectadas a los gestores compartidos.
    private InterfazReportes interfazReportes; //Interfaces de consola conectadas a los gestores compartidos.

    /** Recibe el encargado y los gestores de la misma sesión de aplicación. */
    public MenuEncargado(Usuario encargado, GestorReportesHallazgo hallazgos,
                         GestorCustodia custodia, GestorReportesPerdida perdidas,
                         GestorCoincidencias coincidencias) {
        this.encargado = encargado;
        interfazCustodia = new InterfazCustodia(custodia, hallazgos);
        interfazReportes = new InterfazReportes(perdidas, hallazgos, coincidencias);
    }

    /** Repite el menú del encargado hasta que cierre su sesión. */
    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n=== Menú de encargado: " + encargado.getNombreCompleto() + " ===");
            System.out.println("1. Registrar recepción física de un objeto");
            System.out.println("2. Consultar disponibilidad de ubicaciones");
            System.out.println("3. Trasladar un objeto");
            System.out.println("4. Consultar ubicación e historial de movimientos");
            System.out.println("5. Consultar reportes registrados");
            System.out.println("6. Cerrar sesión");
            opcion = Validacion.leerOpcion("Seleccione una opción: ", 1, 6);
            switch (opcion) {
                case 1: interfazCustodia.registrarRecepcion(encargado); break;
                case 2: interfazCustodia.consultarDisponibilidad(); break;
                case 3: interfazCustodia.trasladar(encargado); break;
                case 4: interfazCustodia.consultarUbicacionEHistorial(); break;
                case 5: interfazReportes.consultarTodos(); break;
                case 6: System.out.println("Sesión de encargado cerrada."); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 6);
    }
}
