package negocio;

import modelo.Usuario;
import java.util.ArrayList;

/** Registra cuentas, verifica sesiones y edita los datos personales permitidos. */
public class GestorUsuarios {
    // Código fijo solo para la demostración académica; no es un mecanismo de seguridad para producción.
    private static final String CODIGO_AUTORIZACION_DEMO = "REENCUENTRA-ENC-2026";
    private ArrayList<Usuario> usuarios; //Cuentas disponibles durante la ejecución.
    private int siguienteCodigo; //Próximo número para construir un código interno.
    private String mensajeError; //Explicación de la última operación rechazada.

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
        return registrarUsuario(nombreCompleto, idBanner, correo, telefono, contrasena, "MIEMBRO", "");
    }

    /**
     * Registra un rol permitido y comprueba la autorización del encargado antes de crear la cuenta.
     * El código fijo es únicamente una medida de demostración académica, no seguridad real.
     */
    public Usuario registrarUsuario(String nombreCompleto, String idBanner, String correo, String telefono,
                                    String contrasena, String rol, String codigoAutorizacion) {
        mensajeError = "";
        if (!"MIEMBRO".equals(rol) && !"ENCARGADO".equals(rol)) {
            mensajeError = "El rol debe ser MIEMBRO o ENCARGADO.";
        } else if ("ENCARGADO".equals(rol)
                && !CODIGO_AUTORIZACION_DEMO.equals(codigoAutorizacion)) {
            mensajeError = "El código de autorización del encargado es incorrecto.";
        } else if (vacio(nombreCompleto)) mensajeError = "El nombre completo es obligatorio.";
        else if (vacio(idBanner)) mensajeError = "El ID Banner es obligatorio.";
        else if (vacio(correo)) mensajeError = "El correo electrónico es obligatorio.";
        else if (vacio(telefono)) mensajeError = "El teléfono es obligatorio.";
        else if (vacio(contrasena)) mensajeError = "La contraseña es obligatoria.";
        else if (contrasena.length() < 8) mensajeError = "La contraseña debe tener al menos 8 caracteres.";
        else if (buscarPorCorreo(correo) != null) mensajeError = "Ese correo electrónico ya está registrado.";
        else if (buscarPorIdBanner(idBanner) != null) mensajeError = "Ese ID Banner ya está registrado.";

        if (!mensajeError.isEmpty()) return null;
        Usuario usuario = new Usuario("U-" + siguienteCodigo, nombreCompleto.trim(), idBanner.trim(),
                correo.trim(), telefono.trim(), contrasena, rol, "ACTIVA");
        siguienteCodigo++;
        usuarios.add(usuario);
        return usuario;
    }

    /** Busca credenciales válidas; devuelve null si el correo, contraseña o estado no coinciden. */
    public Usuario iniciarSesion(String correo, String contrasena) {
        Usuario usuario = buscarPorCorreo(correo);
        if (usuario != null && "ACTIVA".equals(usuario.getEstadoCuenta())
                && usuario.getContrasena() != null && usuario.getContrasena().equals(contrasena)) return usuario;
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

    private boolean vacio(String texto){ //Indica si un campo no se ingresó o solo contiene espacios.
        return texto == null || texto.trim().isEmpty(); }

    public String getMensajeError(){ //Devuelve la explicación de la última validación fallida.
        return mensajeError; }

    /** Devuelve las cuentas de la sesión actual. */
    /** Devuelve una copia de las cuentas, sin exponer la colección interna del gestor. */
    public ArrayList<Usuario> getUsuarios() { return new ArrayList<Usuario>(usuarios); }
}
