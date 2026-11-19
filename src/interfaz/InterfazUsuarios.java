package interfaz;

import modelo.Usuario;
import negocio.GestorUsuarios;
import javax.swing.JOptionPane;

/** Solicita datos de cuenta y presenta perfiles usando cuadros de diálogo. */
public class InterfazUsuarios {
    /** Gestor que valida y conserva las cuentas. */
    private GestorUsuarios gestor;

    /** Recibe el gestor compartido de usuarios. */
    public InterfazUsuarios(GestorUsuarios gestor) { this.gestor = gestor; }

    /** Pide los campos necesarios para el registro público de un miembro. */
    public void registrar() {
        try {
            String nombres = pedir("Nombres y apellidos:"); if (nombres == null) return;
            String identificador = pedir("ID Banner:"); if (identificador == null) return;
            String correo = pedir("Correo electrónico:"); if (correo == null) return;
            String telefono = pedir("Número de contacto:"); if (telefono == null) return;
            String contrasena = pedir("Contraseña (mínimo 8 caracteres):"); if (contrasena == null) return;
            Usuario usuario = gestor.registrarUsuario(nombres, identificador, correo, telefono, contrasena);
            JOptionPane.showMessageDialog(null, "Registro completo. Su código es " + usuario.getCodigo() + ".");
        } catch (IllegalArgumentException error) { mostrarError(error); }
    }

    /** Solicita credenciales y devuelve la cuenta autenticada o null. */
    public Usuario iniciarSesion() {
        String correo = pedir("Correo electrónico:"); if (correo == null) return null;
        String contrasena = pedir("Contraseña:"); if (contrasena == null) return null;
        Usuario usuario = gestor.iniciarSesion(correo, contrasena);
        if (usuario == null) JOptionPane.showMessageDialog(null, "Correo, contraseña o estado de cuenta no válido.", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
        return usuario;
    }

    /** Muestra el perfil y ofrece editar únicamente los campos permitidos. */
    public void consultarYEditar(Usuario usuario) {
        JOptionPane.showMessageDialog(null, usuario.toString(), "Mi perfil", JOptionPane.INFORMATION_MESSAGE);
        int editar = JOptionPane.showConfirmDialog(null, "¿Desea editar nombres, correo y contacto?", "Editar perfil", JOptionPane.YES_NO_OPTION);
        if (editar != JOptionPane.YES_OPTION) return;
        try {
            String nombres = pedir("Nombres y apellidos:"); if (nombres == null) return;
            String correo = pedir("Correo electrónico:"); if (correo == null) return;
            String telefono = pedir("Número de contacto:"); if (telefono == null) return;
            gestor.editarPerfil(usuario, nombres, correo, telefono);
            JOptionPane.showMessageDialog(null, "Perfil actualizado. El identificador y el rol no se modificaron.");
        } catch (IllegalArgumentException error) { mostrarError(error); }
    }

    /** Solicita un dato textual o devuelve null si se cancela el cuadro. */
    private String pedir(String mensaje) { return JOptionPane.showInputDialog(null, mensaje); }
    /** Presenta al usuario el motivo de una validación rechazada. */
    private void mostrarError(Exception error) { JOptionPane.showMessageDialog(null, error.getMessage(), "No se pudo completar", JOptionPane.ERROR_MESSAGE); }
}
