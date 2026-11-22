package modelo;

import java.util.ArrayList;

/** Representa una cuenta del sistema y guarda referencias a los reportes de esa persona. */
public class Usuario {
    private String idUsuario; //Código interno único de la cuenta.
    private String nombreCompleto; //Nombres y apellidos de la persona.
    private String idBanner; //ID Banner único de la persona; no se modifica después del registro.
    private String correo; //Correo empleado para iniciar sesión.
    private String telefono; //Número de contacto de la persona.
    private String contrasena; //Contraseña de acceso de esta versión educativa en memoria.
    private String rol; //Rol que define los permisos: MIEMBRO o ENCARGADO.
    private String estadoCuenta; //Estado de acceso de la cuenta: ACTIVA o INACTIVA.
    private ArrayList<ReportePerdida> reportesPerdida; //Reportes de pérdida cuyo propietario es esta cuenta.
    private ArrayList<ReporteHallazgo> reportesHallazgo; //Reportes de hallazgo registrados por esta cuenta.

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

    public void agregarReportePerdida(ReportePerdida reporte){
        if (reporte != null) reportesPerdida.add(reporte); } //Relaciona un reporte de pérdida con su propietario.
    public void agregarReporteHallazgo(ReporteHallazgo reporte){
        if (reporte != null) reportesHallazgo.add(reporte); } //Relaciona un reporte de hallazgo con quien lo registró.
    public ArrayList<ReportePerdida> getReportesPerdida(){
        return new ArrayList<ReportePerdida>(reportesPerdida); } //Devuelve una copia de los reportes de pérdida propios.
    public ArrayList<ReporteHallazgo> getReportesHallazgo(){
        return new ArrayList<ReporteHallazgo>(reportesHallazgo); } //Devuelve una copia de los reportes de hallazgo propios.
    public String getIdUsuario(){
        return idUsuario; } //Devuelve el código interno único.
    public void setIdUsuario(String idUsuario){
        this.idUsuario = idUsuario; } //Actualiza el código interno de la cuenta.
    public String getNombreCompleto(){
        return nombreCompleto; } //Devuelve el nombre completo.
    public void setNombreCompleto(String nombreCompleto){
        this.nombreCompleto = nombreCompleto; } //Actualiza el nombre completo.
    public String getIdBanner(){
        return idBanner; } //Devuelve el ID Banner, que permanece fijo después del registro.
    public String getCorreo(){
        return correo; } //Devuelve el correo de inicio de sesión.
    public void setCorreo(String correo){
        this.correo = correo; } //Actualiza el correo después de que el gestor valide su unicidad.
    public String getTelefono(){
        return telefono; } //Devuelve el número de contacto.
    public void setTelefono(String telefono){
        this.telefono = telefono; } //Actualiza el número de contacto.
    public String getContrasena(){
        return contrasena; } //Devuelve la contraseña para que el gestor pueda autenticar la cuenta.
    public void setContrasena(String contrasena){
        this.contrasena = contrasena; } //Actualiza la contraseña de la cuenta.
    public String getRol(){
        return rol; } //Devuelve el rol autorizado.
    public String getEstadoCuenta(){
        return estadoCuenta; } //Devuelve el estado de acceso de la cuenta.
    public void setEstadoCuenta(String estadoCuenta){
        this.estadoCuenta = estadoCuenta; } //Actualiza el estado de la cuenta.

    /** Presenta el perfil sin mostrar la contraseña ni permitir cambiar el ID Banner. */
    @Override
    public String toString() {
        return "Código: " + idUsuario + "\nNombre: " + nombreCompleto + "\nID Banner: "
                + idBanner + "\nCorreo: " + correo + "\nContacto: " + telefono
                + "\nRol: " + rol + "\nEstado: " + estadoCuenta;
    }
}
