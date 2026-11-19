package modelo;

import java.time.LocalDateTime;

/**
 * Clase base abstracta que concentra los datos comunes de pérdidas y hallazgos.
 * abstract evita crear un reporte genérico: siempre se registra una pérdida o un hallazgo.
 */
public abstract class Reporte {
    /** Código único que identifica el reporte. */
    private String codigo;
    /** Categoría general del objeto reportado. */
    private String categoria;
    /** Marca del objeto, cuando se conoce. */
    private String marca;
    /** Color principal del objeto. */
    private String color;
    /** Descripción general aportada por quien reporta. */
    private String descripcion;
    /** Características particulares que ayudan a reconocer el objeto. */
    private String caracteristicas;
    /** Fecha y hora en que el reporte fue ingresado al sistema. */
    private LocalDateTime fechaRegistro;
    /** Estado general del reporte. */
    private String estado;
    /** Ruta opcional de fotografía solicitada por los requisitos funcionales. */
    private String fotografia;

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

    /** Devuelve el estado general actual del reporte. */
    public String consultarEstado() { return estado; }
    /** Devuelve el código del reporte. */
    public String getCodigo() { return codigo; }
    /** Actualiza el código del reporte. */
    public void setCodigo(String codigo) { this.codigo = codigo; }
    /** Devuelve la categoría del objeto. */
    public String getCategoria() { return categoria; }
    /** Actualiza la categoría del objeto. */
    public void setCategoria(String categoria) { this.categoria = categoria; }
    /** Devuelve la marca informada o texto vacío si se desconoce. */
    public String getMarca() { return marca; }
    /** Actualiza la marca del objeto. */
    public void setMarca(String marca) { this.marca = marca; }
    /** Devuelve el color del objeto. */
    public String getColor() { return color; }
    /** Actualiza el color del objeto. */
    public void setColor(String color) { this.color = color; }
    /** Devuelve la descripción aportada. */
    public String getDescripcion() { return descripcion; }
    /** Actualiza la descripción aportada. */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    /** Devuelve las características distintivas. */
    public String getCaracteristicas() { return caracteristicas; }
    /** Actualiza las características distintivas. */
    public void setCaracteristicas(String caracteristicas) { this.caracteristicas = caracteristicas; }
    /** Devuelve cuándo se ingresó el reporte al sistema. */
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    /** Actualiza la fecha de registro. */
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    /** Devuelve el estado general del reporte. */
    public String getEstado() { return estado; }
    /** Actualiza el estado general del reporte. */
    public void setEstado(String estado) { this.estado = estado; }
    /** Devuelve la ruta opcional de la fotografía. */
    public String getFotografia() { return fotografia; }
    /** Actualiza la ruta opcional de la fotografía. */
    public void setFotografia(String fotografia) { this.fotografia = fotografia; }
}
