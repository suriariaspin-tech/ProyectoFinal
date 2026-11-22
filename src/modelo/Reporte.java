package modelo;

import java.time.LocalDateTime;

/**
 * Clase base abstracta que concentra los datos comunes de pérdidas y hallazgos.
 * abstract evita crear un reporte genérico: siempre se registra una pérdida o un hallazgo.
 */
public abstract class Reporte {
    private String codigo; //Código único que identifica el reporte.
    private String categoria; //Categoría general del objeto reportado.
    private String marca; //Marca del objeto, cuando se conoce.
    private String color; //Color principal del objeto.
    private String descripcion; //Descripción general aportada por quien reporta.
    private String caracteristicas; //Características particulares que ayudan a reconocer el objeto.
    private LocalDateTime fechaRegistro; //Fecha y hora en que el reporte fue ingresado al sistema.
    private String estado; //Estado general del reporte.
    private String fotografia; //Ruta opcional de fotografía solicitada por los requisitos funcionales.

    /** Inicializa los datos comunes; las fechas del suceso pertenecen a cada subclase. */
    protected Reporte(String codigo, String categoria, String marca, String color, String descripcion,
                      String caracteristicas, LocalDateTime fechaRegistro, String estado, String fotografia) {
        this.codigo = codigo;
        this.categoria = categoria;
        this.marca = marca;
        this.color = color;
        this.descripcion = descripcion;
        this.caracteristicas = caracteristicas;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.fotografia = fotografia;
    }

    /** Comprueba que los datos comunes imprescindibles del reporte estén completos. */
    public boolean validarDatos() {
        return codigo != null && !codigo.trim().isEmpty()
                && categoria != null && !categoria.trim().isEmpty()
                && color != null && !color.trim().isEmpty()
                && descripcion != null && !descripcion.trim().isEmpty()
                && fechaRegistro != null && estado != null && !estado.trim().isEmpty();
    }

    public String consultarEstado(){
        return estado; } //Devuelve el estado general actual del reporte.
    public String getCodigo(){
        return codigo; } //Devuelve el código del reporte.
    public void setCodigo(String codigo){
        this.codigo = codigo; } //Actualiza el código del reporte.
    public String getCategoria(){
        return categoria; } //Devuelve la categoría del objeto.
    public void setCategoria(String categoria){
        this.categoria = categoria; } //Actualiza la categoría del objeto.
    public String getMarca(){
        return marca; } //Devuelve la marca informada o texto vacío si se desconoce.
    public void setMarca(String marca){
        this.marca = marca; } //Actualiza la marca del objeto.
    public String getColor(){
        return color; } //Devuelve el color del objeto.
    public void setColor(String color){
        this.color = color; } //Actualiza el color del objeto.
    public String getDescripcion(){
        return descripcion; } //Devuelve la descripción aportada.
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion; } //Actualiza la descripción aportada.
    public String getCaracteristicas(){
        return caracteristicas; } //Devuelve las características distintivas.
    public void setCaracteristicas(String caracteristicas){
        this.caracteristicas = caracteristicas; } //Actualiza las características distintivas.
    public LocalDateTime getFechaRegistro(){
        return fechaRegistro; } //Devuelve cuándo se ingresó el reporte al sistema.
    public void setFechaRegistro(LocalDateTime fechaRegistro){
        this.fechaRegistro = fechaRegistro; } //Actualiza la fecha de registro.
    public String getEstado(){
        return estado; } //Devuelve el estado general del reporte.
    public void setEstado(String estado){
        this.estado = estado; } //Actualiza el estado general del reporte.
    public String getFotografia(){
        return fotografia; } //Devuelve la ruta opcional de la fotografía.
    public void setFotografia(String fotografia){
        this.fotografia = fotografia; } //Actualiza la ruta opcional de la fotografía.
}
