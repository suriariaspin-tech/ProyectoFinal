package modelo;

/** Representa a un miembro de la comunidad o a un encargado autorizado. */
public class Usuario {
    /** Código interno único de la cuenta. */
    private String codigo;
    /** Nombres y apellidos de la persona. */
    private String nombres;
    /** Identificador académico o institucional, sin posibilidad de edición posterior. */
    private String identificadorInstitucional;
    /** Correo empleado para iniciar sesión; debe ser único. */
    private String correo;
    /** Número de contacto de la persona. */
    private String telefono;
    /** Contraseña de acceso de esta versión educativa en memoria. */
    private String contrasena;
    /** Permisos de la cuenta: MIEMBRO o ENCARGADO. */
    private String rol;
    /** Estado de la cuenta: ACTIVA o INACTIVA. */
    private String estado;

    /** Recibe e inicializa todos los datos de una cuenta. */
    public Usuario(String codigo, String nombres, String identificadorInstitucional, String correo,
                   String telefono, String contrasena, String rol, String estado) {
        this.codigo = codigo;
        this.nombres = nombres;
        this.identificadorInstitucional = identificadorInstitucional;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.rol = rol;
        this.estado = estado;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getIdentificadorInstitucional() { return identificadorInstitucional; }
    public void setIdentificadorInstitucional(String identificadorInstitucional) { this.identificadorInstitucional = identificadorInstitucional; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    /** Devuelve los datos visibles del perfil sin exponer la contraseña. */
    @Override
    public String toString() {
        return "Código: " + codigo + "\nNombre: " + nombres + "\nIdentificador: "
                + identificadorInstitucional + "\nCorreo: " + correo + "\nContacto: "
                + telefono + "\nRol: " + rol + "\nEstado: " + estado;
    }
}
