package modelo;

import java.time.LocalDateTime;

/** Representa el aviso de que alguien encontró un objeto, antes de su recepción física. */
public class ReporteHallazgo {
    /** Código único del reporte. */
    private String codigo;
    /** Usuario que registró el hallazgo. */
    private Usuario usuarioReportante;
    /** Tipo general del objeto encontrado. */
    private String categoria;
    /** Marca del objeto, opcional. */
    private String marca;
    /** Color principal del objeto. */
    private String color;
    /** Descripción general del objeto. */
    private String descripcion;
    /** Rasgos particulares que ayudan a reconocerlo. */
    private String caracteristicas;
    /** Fecha y hora del hallazgo. */
    private LocalDateTime fechaHallazgo;
    /** Lugar donde se encontró. */
    private String lugar;
    /** Ruta o referencia a una fotografía opcional. */
    private String fotografia;
    /** Estado: PENDIENTE_RECEPCION o RECIBIDO. */
    private String estado;
    /** Objeto físico asociado solo después de confirmar su recepción. */
    private ObjetoEncontrado objetoEncontrado;

    /** Inicializa el aviso; registrar el aviso no implica recepción del objeto. */
    public ReporteHallazgo(String codigo, Usuario usuarioReportante, String categoria, String marca,
                           String color, String descripcion, String caracteristicas,
                           LocalDateTime fechaHallazgo, String lugar, String fotografia, String estado) {
        this.codigo = codigo; this.usuarioReportante = usuarioReportante; this.categoria = categoria;
        this.marca = marca; this.color = color; this.descripcion = descripcion;
        this.caracteristicas = caracteristicas; this.fechaHallazgo = fechaHallazgo;
        this.lugar = lugar; this.fotografia = fotografia; this.estado = estado;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public Usuario getUsuarioReportante() { return usuarioReportante; }
    public void setUsuarioReportante(Usuario usuarioReportante) { this.usuarioReportante = usuarioReportante; }
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
    public LocalDateTime getFechaHallazgo() { return fechaHallazgo; }
    public void setFechaHallazgo(LocalDateTime fechaHallazgo) { this.fechaHallazgo = fechaHallazgo; }
    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    public String getFotografia() { return fotografia; }
    public void setFotografia(String fotografia) { this.fotografia = fotografia; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public ObjetoEncontrado getObjetoEncontrado() { return objetoEncontrado; }
    public void setObjetoEncontrado(ObjetoEncontrado objetoEncontrado) { this.objetoEncontrado = objetoEncontrado; }
}
