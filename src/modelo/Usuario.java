package modelo;

import java.util.ArrayList;

/** Representa una cuenta del sistema y guarda referencias a los reportes de esa persona. */
public class Usuario {
    /** Código interno único de la cuenta. */
    private String idUsuario;
    /** Nombres y apellidos de la persona. */
    private String nombreCompleto;
    /** ID Banner único de la persona; no se modifica después del registro. */
    private String idBanner;
    /** Correo empleado para iniciar sesión. */
    private String correo;
    /** Número de contacto de la persona. */
    private String telefono;
    /** Contraseña de acceso de esta versión educativa en memoria. */
    private String contrasena;
    /** Rol que define los permisos: MIEMBRO o ENCARGADO. */
    private String rol;
    /** Estado de acceso de la cuenta: ACTIVA o INACTIVA. */
    private String estadoCuenta;
    /** Reportes de pérdida cuyo propietario es esta cuenta. */
    private ArrayList<ReportePerdida> reportesPerdida;
    /** Reportes de hallazgo registrados por esta cuenta. */
    private ArrayList<ReporteHallazgo> reportesHallazgo;

    /** Recibe e inicializa el perfil y las colecciones vacías de reportes. */
    public Usuario(String idUsuario, String nombreCompleto, String idBanner, String correo,
                   String telefono, String contrasena, String rol, String estadoCuenta) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.idBanner = idBanner;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.rol = rol;
        this.estadoCuenta = estadoCuenta;
        reportesPerdida = new ArrayList<ReportePerdida>();
        reportesHallazgo = new ArrayList<ReporteHallazgo>();
    }

    /** Relaciona un reporte de pérdida con su propietario. */
    public void agregarReportePerdida(ReportePerdida reporte) { if (reporte != null) reportesPerdida.add(reporte); }
    /** Relaciona un reporte de hallazgo con quien lo registró. */
    public void agregarReporteHallazgo(ReporteHallazgo reporte) { if (reporte != null) reportesHallazgo.add(reporte); }
    /** Devuelve una copia de los reportes de pérdida propios. */
    public ArrayList<ReportePerdida> getReportesPerdida() { return new ArrayList<ReportePerdida>(reportesPerdida); }
    /** Devuelve una copia de los reportes de hallazgo propios. */
    public ArrayList<ReporteHallazgo> getReportesHallazgo() { return new ArrayList<ReporteHallazgo>(reportesHallazgo); }
    /** Devuelve el código interno único. */
    public String getIdUsuario() { return idUsuario; }
    /** Actualiza el código interno de la cuenta. */
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
    /** Devuelve el nombre completo. */
    public String getNombreCompleto() { return nombreCompleto; }
    /** Actualiza el nombre completo. */
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    /** Devuelve el ID Banner, que permanece fijo después del registro. */
    public String getIdBanner() { return idBanner; }
    /** Devuelve el correo de inicio de sesión. */
    public String getCorreo() { return correo; }
    /** Actualiza el correo después de que el gestor valide su unicidad. */
    public void setCorreo(String correo) { this.correo = correo; }
    /** Devuelve el número de contacto. */
    public String getTelefono() { return telefono; }
    /** Actualiza el número de contacto. */
    public void setTelefono(String telefono) { this.telefono = telefono; }
    /** Devuelve la contraseña para que el gestor pueda autenticar la cuenta. */
    public String getContrasena() { return contrasena; }
    /** Actualiza la contraseña de la cuenta. */
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    /** Devuelve el rol autorizado. */
    public String getRol() { return rol; }
    /** Devuelve el estado de acceso de la cuenta. */
    public String getEstadoCuenta() { return estadoCuenta; }
    /** Actualiza el estado de la cuenta. */
    public void setEstadoCuenta(String estadoCuenta) { this.estadoCuenta = estadoCuenta; }

    /** Presenta el perfil sin mostrar la contraseña ni permitir cambiar el ID Banner. */
    @Override
    public String toString() {
        return "Código: " + idUsuario + "\nNombre: " + nombreCompleto + "\nID Banner: "
                + idBanner + "\nCorreo: " + correo + "\nContacto: " + telefono
                + "\nRol: " + rol + "\nEstado: " + estadoCuenta;
    }
}
