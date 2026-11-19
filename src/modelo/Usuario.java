package modelo;

import java.util.ArrayList;

/** Representa a un miembro de la comunidad o a un encargado autorizado. */
public class Usuario {
    private String idUsuario; //código interno único de la cuenta.
    private String nombreCompleto; //Nombres y apellidos de la persona.
    private String idBanner; //Identificador académico o institucional, sin posibilidad de edición posterior.
    private String correo; //Correo empleado para iniciar sesión; debe ser único.
    private String telefono; //Número de contacto de la persona.
    private String contrasena; //Contraseña de acceso de esta versión educativa en memoria.
    private String rol; //Permisos de la cuenta: MIEMBRO o ENCARGADO.
    private String estadoCuenta; //Estado de la cuenta: ACTIVA o INACTIVA.
    private ArrayList<ReportePerdida> reportesPerdida; //Reportes de pérdida cuyo propietario es esta cuenta.
    private ArrayList<ReporteHallazgo> reportesHallazgo; //Reportes de hallazgo registrados por esta cuenta.

    /** Recibe e inicializa todos los datos de una cuenta. */
    public Usuario(String codigo, String nombres, String idBanner, String correo,
                   String telefono, String contrasena, String rol, String estado) {
        this.idUsuario = codigo;
        this.nombreCompleto = nombres;
        this.idBanner = idBanner;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.rol = rol;
        this.estadoCuenta = estado;
        this.reportesPerdida = new ArrayList<ReportePerdida>();
        this.reportesHallazgo = new ArrayList<ReporteHallazgo>();
    }

    /** Agrega el mismo reporte de pérdida a la colección relacionada con su propietario. */
    public void agregarReportePerdida(ReportePerdida reporte) { reportesPerdida.add(reporte); }

    /** Agrega el mismo hallazgo a la colección relacionada con su reportante. */
    public void agregarReporteHallazgo(ReporteHallazgo reporte) { reportesHallazgo.add(reporte); }
    public ArrayList<ReportePerdida> getReportesPerdida() { return reportesPerdida; }
    public ArrayList<ReporteHallazgo> getReportesHallazgo() { return reportesHallazgo; }
    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    /** Perfil conservado para las interfaces existentes. */
    public String getCodigo() { return idUsuario; }
    public void setCodigo(String codigo) { this.idUsuario = codigo; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    /** Perfil conservado para las interfaces existentes. */
    public String getNombres() { return nombreCompleto; }
    public void setNombres(String nombres) { this.nombreCompleto = nombres; }
    public String getIdBanner() { return idBanner; }
    public void setIdBanner(String idBanner) { this.idBanner = idBanner; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getEstadoCuenta() { return estadoCuenta; }
    public void setEstadoCuenta(String estadoCuenta) { this.estadoCuenta = estadoCuenta; }

    /** Perfil conservado para las interfaces existentes. */
    public String getEstado() { return estadoCuenta; }
    public void setEstado(String estado) { this.estadoCuenta = estado; }

    /** Devuelve los datos visibles del perfil sin exponer la contraseña. */
    @Override
    public String toString() {
        return "Código: " + idUsuario + "\nNombre: " + nombreCompleto + "\nIdentificador: "
                + idBanner + "\nCorreo: " + correo + "\nContacto: "
                + telefono + "\nRol: " + rol + "\nEstado: " + estadoCuenta;
    }
}
