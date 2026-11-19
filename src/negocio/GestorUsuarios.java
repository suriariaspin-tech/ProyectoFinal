package negocio;

import modelo.Usuario;
import java.util.ArrayList;

/** Registra cuentas, verifica sesiones y edita los datos personales permitidos. */
public class GestorUsuarios {
    /** Cuentas disponibles durante la ejecución. */
    private ArrayList<Usuario> usuarios;
    /** Próximo número para construir un código interno. */
    private int siguienteCodigo;
    /** Explicación de la última operación rechazada. */
    private String mensajeError;

    /** Crea la colección y una cuenta ficticia de encargado para pruebas académicas. */
    public GestorUsuarios() {
        usuarios = new ArrayList<Usuario>();
        siguienteCodigo = 1;
        mensajeError = "";
        usuarios.add(new Usuario("U-ENC-001", "Encargado de demostración", "DEMO-ENC-001",
                "encargado@reencuentra.edu", "0000000000", "Encargado123", "ENCARGADO", "ACTIVA"));
    }

    /** Registra un MIEMBRO si los datos requeridos y los identificadores son válidos; devuelve null si falla. */
    public Usuario registrarUsuario(String nombreCompleto, String idBanner, String correo, String telefono, String contrasena) {
        mensajeError = "";
        if (vacio(nombreCompleto)) mensajeError = "El nombre completo es obligatorio.";
        else if (vacio(idBanner)) mensajeError = "El ID Banner es obligatorio.";
        else if (vacio(correo)) mensajeError = "El correo electrónico es obligatorio.";
        else if (vacio(telefono)) mensajeError = "El teléfono es obligatorio.";
        else if (vacio(contrasena)) mensajeError = "La contraseña es obligatoria.";
        else if (contrasena.length() < 8) mensajeError = "La contraseña debe tener al menos 8 caracteres.";
        else if (buscarPorCorreo(correo) != null) mensajeError = "Ese correo electrónico ya está registrado.";
        else if (buscarPorIdBanner(idBanner) != null) mensajeError = "Ese ID Banner ya está registrado.";

        if (!mensajeError.isEmpty()) return null;
        Usuario usuario = new Usuario("U-" + siguienteCodigo, nombreCompleto.trim(), idBanner.trim(),
                correo.trim(), telefono.trim(), contrasena, "MIEMBRO", "ACTIVA");
        siguienteCodigo++;
        usuarios.add(usuario);
        return usuario;
    }

    /** Busca credenciales válidas; devuelve null si el correo, contraseña o estado no coinciden. */
    public Usuario iniciarSesion(String correo, String contrasena) {
        Usuario usuario = buscarPorCorreo(correo);
        if (usuario != null && usuario.getEstadoCuenta().equals("ACTIVA")
                && usuario.getContrasena().equals(contrasena)) return usuario;
        return null;
    }

    /** Busca un usuario por correo sin distinguir mayúsculas ni espacios externos. */
    public Usuario buscarPorCorreo(String correo) {
        if (correo == null) return null;
        for (Usuario usuario : usuarios) {
            if (usuario.getCorreo().trim().equalsIgnoreCase(correo.trim())) return usuario;
        }
        return null;
    }

    /** Busca un usuario por ID Banner sin distinguir mayúsculas ni espacios externos. */
    public Usuario buscarPorIdBanner(String idBanner) {
        if (idBanner == null) return null;
        for (Usuario usuario : usuarios) {
            if (usuario.getIdBanner().trim().equalsIgnoreCase(idBanner.trim())) return usuario;
        }
        return null;
    }

    /** Actualiza nombre, correo y teléfono; no cambia el ID Banner ni el rol. */
    public boolean editarPerfil(Usuario usuario, String nombreCompleto, String correo, String telefono) {
        mensajeError = "";
        if (usuario == null || !usuarios.contains(usuario)) mensajeError = "La cuenta no existe.";
        else if (vacio(nombreCompleto)) mensajeError = "El nombre completo es obligatorio.";
        else if (vacio(correo)) mensajeError = "El correo electrónico es obligatorio.";
        else if (vacio(telefono)) mensajeError = "El teléfono es obligatorio.";
        else {
            Usuario dueñoCorreo = buscarPorCorreo(correo);
            if (dueñoCorreo != null && dueñoCorreo != usuario) mensajeError = "Ese correo pertenece a otra cuenta.";
        }
        if (!mensajeError.isEmpty()) return false;
        usuario.setNombreCompleto(nombreCompleto.trim());
        usuario.setCorreo(correo.trim());
        usuario.setTelefono(telefono.trim());
        return true;
    }

    /** Indica si un campo no se ingresó o solo contiene espacios. */
    private boolean vacio(String texto) { return texto == null || texto.trim().isEmpty(); }
    /** Devuelve la explicación de la última validación fallida. */
    public String getMensajeError() { return mensajeError; }
    /** Devuelve las cuentas de la sesión actual. */
    /** Devuelve una copia de las cuentas, sin exponer la colección interna del gestor. */
    public ArrayList<Usuario> getUsuarios() { return new ArrayList<Usuario>(usuarios); }
}
