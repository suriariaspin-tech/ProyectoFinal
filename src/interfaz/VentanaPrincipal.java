package interfaz;

import negocio.GestorCoincidencias;
import negocio.GestorCustodia;
import negocio.GestorReportesHallazgo;
import negocio.GestorReportesPerdida;
import negocio.GestorUsuarios;
import modelo.Usuario;
import javax.swing.JOptionPane;

/** Mantiene las instancias compartidas de negocio y presenta el menú inicial. */
public class VentanaPrincipal {
    /** Gestor compartido que conserva las cuentas durante la sesión. */
    private GestorUsuarios gestorUsuarios;
    /** Gestor compartido que conserva reportes de pérdida. */
    private GestorReportesPerdida gestorPerdidas;
    /** Gestor compartido que conserva reportes de hallazgo. */
    private GestorReportesHallazgo gestorHallazgos;
    /** Gestor compartido que calcula coincidencias entre los reportes. */
    private GestorCoincidencias gestorCoincidencias;
    /** Gestor compartido que conserva objetos y movimientos de custodia. */
    private GestorCustodia gestorCustodia;

    /** Construye la aplicación y conecta las interfaces con sus gestores compartidos. */
    public VentanaPrincipal() {
        gestorUsuarios = new GestorUsuarios();
        gestorPerdidas = new GestorReportesPerdida();
        gestorHallazgos = new GestorReportesHallazgo();
        gestorCoincidencias = new GestorCoincidencias();
        gestorCustodia = new GestorCustodia();
    }

    /** Repite registro e inicio de sesión hasta que la persona elija salir. */
    public void iniciar() {
        InterfazUsuarios interfazUsuarios = new InterfazUsuarios(gestorUsuarios);
        while (true) {
            String[] opciones = {"Registrarse", "Iniciar sesión", "Salir"};
            int opcion = JOptionPane.showOptionDialog(null, "Bienvenido a Reencuentra", "Reencuentra",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
            if (opcion == 0) interfazUsuarios.registrar();
            else if (opcion == 1) {
                Usuario usuario = interfazUsuarios.iniciarSesion();
                if (usuario != null) abrirMenu(usuario);
            } else return;
        }
    }

    /** Dirige a cada persona al menú permitido por su rol. */
    private void abrirMenu(Usuario usuario) {
        if ("ENCARGADO".equals(usuario.getRol())) {
            new MenuEncargado(usuario, gestorHallazgos, gestorCustodia, gestorPerdidas, gestorCoincidencias).mostrar();
        } else {
            new MenuUsuario(usuario, gestorUsuarios, gestorPerdidas, gestorHallazgos, gestorCoincidencias).mostrar();
        }
    }
}
