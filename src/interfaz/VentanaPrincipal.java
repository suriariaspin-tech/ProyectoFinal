package interfaz;

import modelo.Usuario;
import negocio.GestorCoincidencias;
import negocio.GestorCustodia;
import negocio.GestorReportesHallazgo;
import negocio.GestorReportesPerdida;
import negocio.GestorUsuarios;

/** Crea los gestores compartidos y controla el menú inicial de Reencuentra. */
public class VentanaPrincipal {
    private GestorUsuarios gestorUsuarios; //Gestor único de cuentas para toda la ejecución.
    private GestorReportesPerdida gestorPerdidas; //Gestor único de pérdidas para toda la ejecución.
    private GestorReportesHallazgo gestorHallazgos; //Gestor único de hallazgos para toda la ejecución.
    private GestorCoincidencias gestorCoincidencias; //Gestor único de coincidencias para toda la ejecución.
    private GestorCustodia gestorCustodia; //Gestor único de ubicaciones, objetos y movimientos para toda la ejecución.

    /** Inicializa una sola instancia de cada gestor para compartir las colecciones. */
    public VentanaPrincipal() {
        gestorUsuarios = new GestorUsuarios();
        gestorPerdidas = new GestorReportesPerdida();
        gestorHallazgos = new GestorReportesHallazgo();
        gestorCoincidencias = new GestorCoincidencias();
        gestorCustodia = new GestorCustodia();
    }

    /** Muestra y repite el menú inicial hasta que la persona elija salir. */
    public void iniciar() {
        InterfazUsuarios interfazUsuarios = new InterfazUsuarios(gestorUsuarios);
        int opcion;
        do {
            System.out.println("\n====== REENCUENTRA ======");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            opcion = Validacion.leerOpcion("Seleccione una opción: ", 1, 3);
            switch (opcion) {
                case 1:
                    interfazUsuarios.registrar();
                    break;
                case 2:
                    Usuario usuario = interfazUsuarios.iniciarSesion();
                    if (usuario != null) abrirMenu(usuario);
                    break;
                case 3:
                    System.out.println("Gracias por utilizar Reencuentra.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 3);
    }

    /** Dirige a la cuenta autenticada al menú permitido por su rol. */
    private void abrirMenu(Usuario usuario) {
        if (usuario == null) return;
        if ("ENCARGADO".equals(usuario.getRol())) {
            MenuEncargado menu = new MenuEncargado(usuario, gestorHallazgos, gestorCustodia,
                    gestorPerdidas, gestorCoincidencias);
            menu.mostrar();
        } else if ("MIEMBRO".equals(usuario.getRol())) {
            MenuUsuario menu = new MenuUsuario(usuario, gestorUsuarios, gestorPerdidas,
                    gestorHallazgos, gestorCoincidencias);
            menu.mostrar();
        } else {
            System.out.println("La cuenta tiene un rol no válido y no puede acceder a un menú.");
        }
    }
}
