package interfaz;

import modelo.Usuario;
import negocio.GestorCoincidencias;
import negocio.GestorReportesHallazgo;
import negocio.GestorReportesPerdida;
import negocio.GestorUsuarios;
import javax.swing.JOptionPane;

/** Muestra las operaciones permitidas para el rol MIEMBRO. */
public class MenuUsuario {
    /** Cuenta autenticada que utilizará las operaciones de este menú. */
    private Usuario usuario;
    /** Interfaz para consultar y actualizar el perfil. */
    private InterfazUsuarios interfazUsuarios;
    /** Interfaz para registrar y consultar reportes propios. */
    private InterfazReportes interfazReportes;
    /** Interfaz para consultar las posibles coincidencias del usuario. */
    private InterfazCoincidencias interfazCoincidencias;

    /** Recibe la cuenta y gestores ya creados por la ventana principal. */
    public MenuUsuario(Usuario usuario, GestorUsuarios usuarios, GestorReportesPerdida perdidas,
                       GestorReportesHallazgo hallazgos, GestorCoincidencias coincidencias) {
        this.usuario = usuario; interfazUsuarios = new InterfazUsuarios(usuarios);
        interfazReportes = new InterfazReportes(perdidas, hallazgos, coincidencias);
        interfazCoincidencias = new InterfazCoincidencias(coincidencias, perdidas, hallazgos);
    }

    /** Repite el menú del miembro hasta que cierre su sesión. */
    public void mostrar() {
        String[] opciones = {"Registrar objeto perdido", "Registrar objeto encontrado", "Consultar/editar mis pérdidas",
                "Consultar posibles coincidencias", "Consultar/editar mis hallazgos", "Consultar y editar perfil", "Cerrar sesión"};
        while (true) {
            int opcion = JOptionPane.showOptionDialog(null, "Sesión de " + usuario.getNombres(), "Menú de miembro",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            if (opcion == 0) interfazReportes.registrarPerdida(usuario);
            else if (opcion == 1) interfazReportes.registrarHallazgo(usuario);
            else if (opcion == 2) interfazReportes.consultarYEditarPerdidas(usuario);
            else if (opcion == 3) interfazCoincidencias.consultar(usuario);
            else if (opcion == 4) interfazReportes.consultarYEditarHallazgos(usuario);
            else if (opcion == 5) interfazUsuarios.consultarYEditar(usuario);
            else return;
        }
    }
}
