package negocio;

import modelo.Usuario;
import java.util.ArrayList;

/** Contiene el registro, autenticación, consulta y edición de cuentas. */
public class GestorUsuarios {
    /** Cuentas disponibles durante la ejecución actual. */
    private ArrayList<Usuario> usuarios;
    /** Secuencia sencilla para generar códigos internos únicos. */
    private int siguienteCodigo;

    /** Crea el registro en memoria y una cuenta ficticia para probar funciones administrativas. */
    public GestorUsuarios() {
        usuarios = new ArrayList<Usuario>();
        siguienteCodigo = 1;
        usuarios.add(new Usuario("U-ENC-001", "Encargado de demostración", "DEMO-ENC-001",
                "encargado@reencuentra.edu", "0000000000", "Encargado123", "ENCARGADO", "ACTIVA"));
    }

    /** Registra una cuenta pública y siempre le asigna el rol MIEMBRO. */
    public Usuario registrarUsuario(String nombres, String identificador, String correo, String telefono, String contrasena) {
        validarObligatorio(nombres, "Los nombres y apellidos son obligatorios.");
        validarObligatorio(identificador, "El identificador institucional es obligatorio.");
        validarObligatorio(correo, "El correo electrónico es obligatorio.");
        validarObligatorio(telefono, "El número de contacto es obligatorio.");
        validarObligatorio(contrasena, "La contraseña es obligatoria.");
        if (contrasena.length() < 8) throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        if (buscarPorCorreo(correo) != null) throw new IllegalArgumentException("Ese correo electrónico ya está registrado.");
        if (buscarPorIdentificador(identificador) != null) throw new IllegalArgumentException("Ese identificador institucional ya está registrado.");
        Usuario usuario = new Usuario("U-" + siguienteCodigo++, nombres.trim(), identificador.trim(),
                correo.trim(), telefono.trim(), contrasena, "MIEMBRO", "ACTIVA");
        usuarios.add(usuario);
        return usuario;
    }

    /** Verifica correo, contraseña y estado; devuelve null si las credenciales no son válidas. */
    public Usuario iniciarSesion(String correo, String contrasena) {
        Usuario usuario = buscarPorCorreo(correo);
        if (usuario != null && usuario.getEstado().equals("ACTIVA") && usuario.getContrasena().equals(contrasena)) return usuario;
        return null;
    }

    /** Busca una cuenta por su correo sin distinguir mayúsculas ni espacios externos. */
    public Usuario buscarPorCorreo(String correo) {
        if (correo == null) return null;
        for (Usuario usuario : usuarios) if (usuario.getCorreo().trim().equalsIgnoreCase(correo.trim())) return usuario;
        return null;
    }

    /** Busca una cuenta por su identificador institucional. */
    public Usuario buscarPorIdentificador(String identificador) {
        if (identificador == null) return null;
        for (Usuario usuario : usuarios) if (usuario.getIdentificadorInstitucional().trim().equalsIgnoreCase(identificador.trim())) return usuario;
        return null;
    }

    /** Actualiza únicamente nombre, correo y contacto, conservando el identificador y el rol. */
    public void editarPerfil(Usuario usuario, String nombres, String correo, String telefono) {
        if (usuario == null || !usuarios.contains(usuario)) throw new IllegalArgumentException("La cuenta no existe.");
        validarObligatorio(nombres, "Los nombres y apellidos son obligatorios.");
        validarObligatorio(correo, "El correo electrónico es obligatorio.");
        validarObligatorio(telefono, "El número de contacto es obligatorio.");
        Usuario cuentaConCorreo = buscarPorCorreo(correo);
        if (cuentaConCorreo != null && cuentaConCorreo != usuario) throw new IllegalArgumentException("Ese correo ya pertenece a otra cuenta.");
        usuario.setNombres(nombres.trim()); usuario.setCorreo(correo.trim()); usuario.setTelefono(telefono.trim());
    }

    /** Rechaza los datos vacíos antes de almacenar una cuenta. */
    private void validarObligatorio(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) throw new IllegalArgumentException(mensaje);
    }
    public ArrayList<Usuario> getUsuarios() { return usuarios; }
}
