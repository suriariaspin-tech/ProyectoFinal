package modelo;

import java.time.LocalDateTime;

/** Guarda la información proporcionada por una persona sobre un objeto perdido. */
public class ReportePerdida {
    /** Código único del reporte. */
    private String codigo;
    /** Usuario dueño del reporte. */
    private Usuario propietario;
    /** Tipo general del objeto. */
    private String categoria;
    /** Marca del objeto, opcional. */
    private String marca;
    /** Color principal del objeto. */
    private String color;
    /** Descripción general del objeto. */
    private String descripcion;
    /** Rasgos particulares que ayudan a reconocerlo. */
    private String caracteristicas;
    /** Fecha y hora aproximada en que se perdió. */
    private LocalDateTime fechaPerdida;
    /** Lugar donde se perdió. */
    private String lugar;
    /** Ruta o referencia a una fotografía opcional. */
    private String fotografia;
    /** Estado del reporte: ABIERTO o CERRADO. */
    private String estado;

    /** Recibe e inicializa los datos del reporte; los gestores asignan su estado inicial. */
    public ReportePerdida(String codigo, Usuario propietario, String categoria, String marca, String color,
                          String descripcion, String caracteristicas, LocalDateTime fechaPerdida,
                          String lugar, String fotografia, String estado) {
        this.codigo = codigo; this.propietario = propietario; this.categoria = categoria;
        this.marca = marca; this.color = color; this.descripcion = descripcion;
        this.caracteristicas = caracteristicas; this.fechaPerdida = fechaPerdida;
        this.lugar = lugar; this.fotografia = fotografia; this.estado = estado;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public Usuario getPropietario() { return propietario; }
    public void setPropietario(Usuario propietario) { this.propietario = propietario; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getCaracteristicas() { return caracteristicas; }
    public void setCaracteristicas(String caracteristicas) { this.caracteristicas = caracteristicas; }
    public LocalDateTime getFechaPerdida() { return fechaPerdida; }
    public void setFechaPerdida(LocalDateTime fechaPerdida) { this.fechaPerdida = fechaPerdida; }
    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    public String getFotografia() { return fotografia; }
    public void setFotografia(String fotografia) { this.fotografia = fotografia; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
