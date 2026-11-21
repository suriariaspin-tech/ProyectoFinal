package interfaz;

import modelo.Usuario;
import negocio.GestorUsuarios;

/** Atiende por consola el registro, el inicio de sesión y el perfil de las personas. */
public class InterfazUsuarios {
    private GestorUsuarios gestor; //Gestor compartido que verifica y conserva las cuentas.

    /** Recibe el gestor de usuarios que se conserva durante toda la ejecución. */
    public InterfazUsuarios(GestorUsuarios gestor) { this.gestor = gestor; }

    /** Lee los datos de registro y crea un usuario MIEMBRO si todos son válidos. */
    public void registrar() {
        System.out.println("\n--- Registro de miembro ---");
        String nombre = Validacion.leerTextoObligatorio("Nombre completo: ");
        String idBanner = Validacion.leerTextoObligatorio("ID Banner: ");
        String correo = Validacion.leerTextoObligatorio("Correo electrónico: ");
        String telefono = Validacion.leerTextoObligatorio("Teléfono: ");
        String contrasena = Validacion.leerTextoObligatorio("Contraseña (mínimo 8 caracteres): ");
        Usuario usuario = gestor.registrarUsuario(nombre, idBanner, correo, telefono, contrasena);
        if (usuario == null) System.out.println("No se pudo registrar: " + gestor.getMensajeError());
        else System.out.println("Registro completo. Código de usuario: " + usuario.getIdUsuario() + ". Rol: MIEMBRO.");
    }

    /** Lee las credenciales e inicia sesión o devuelve null si no son válidas. */
    public Usuario iniciarSesion() {
        System.out.println("\n--- Inicio de sesión ---");
        String correo = Validacion.leerTextoObligatorio("Correo electrónico: ");
        String contrasena = Validacion.leerTextoObligatorio("Contraseña: ");
        Usuario usuario = gestor.iniciarSesion(correo, contrasena);
        if (usuario == null) System.out.println("Correo, contraseña o estado de cuenta no válido.");
        return usuario;
    }

    /** Muestra los datos registrados del usuario sin solicitar cambios. */
    public void consultarPerfil(Usuario usuario) {
        System.out.println("\n--- Mi perfil ---");
        System.out.println(usuario);
    }

    /** Solicita cambios permitidos y actualiza nombres, correo y teléfono sin tocar ID Banner ni rol. */
    public void editarPerfil(Usuario usuario) {
        System.out.println("\n--- Editar perfil ---");
        String nombre = Validacion.leerTextoObligatorio("Nombre completo: ");
        String correo = Validacion.leerTextoObligatorio("Correo electrónico: ");
        String telefono = Validacion.leerTextoObligatorio("Teléfono: ");
        if (gestor.editarPerfil(usuario, nombre, correo, telefono)) {
            System.out.println("Perfil actualizado. El ID Banner y el rol no se modificaron.");
        } else {
            System.out.println("No se pudo actualizar el perfil: " + gestor.getMensajeError());
        }
    }
}
