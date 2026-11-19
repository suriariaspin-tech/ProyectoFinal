package modelo;

import java.time.LocalDateTime;

/** Reporte de un objeto encontrado; la recepción física se registra en un paso separado. */
public class ReporteHallazgo extends Reporte {
    /** Usuario que comunicó el hallazgo. */
    private Usuario usuarioReportante;
    /** Fecha y hora en que fue encontrado el objeto. */
    private LocalDateTime fechaHoraHallazgo;
    /** Lugar donde se encontró el objeto. */
    private String lugarHallazgo;
    /** Estado de recepción, independiente del estado general heredado del reporte. */
    private String estadoRecepcion;
    /** Objeto físico relacionado al confirmar su recepción; nulo antes de ese momento. */
    private ObjetoEncontrado objetoEncontrado;

    /** Inicializa los datos comunes y mantiene el estado de recepción separado del reporte. */
    public ReporteHallazgo(String codigo, Usuario usuarioReportante, String categoria, String marca,
                           String color, String descripcion, String caracteristicas,
                           LocalDateTime fechaHoraHallazgo, String lugarHallazgo, String fotografia,
                           String estadoRecepcion) {
        // El estado general queda ABIERTO; la recepción se controla en su atributo independiente.
        super(codigo, categoria, marca, color, descripcion, caracteristicas, LocalDateTime.now(), "ABIERTO", fotografia);
        this.usuarioReportante = usuarioReportante;
        this.fechaHoraHallazgo = fechaHoraHallazgo;
        this.lugarHallazgo = lugarHallazgo;
        this.estadoRecepcion = estadoRecepcion;
    }

    /** Devuelve la fecha del hallazgo usada por la búsqueda de coincidencias. */
    public LocalDateTime getFechaHoraHallazgo() { return fechaHoraHallazgo; }
    public void setFechaHoraHallazgo(LocalDateTime fecha) { fechaHoraHallazgo = fecha; }
    public String getLugarHallazgo() { return lugarHallazgo; }
    public void setLugarHallazgo(String lugar) { lugarHallazgo = lugar; }
    public String getEstadoRecepcion() { return estadoRecepcion; }
    public Usuario getUsuarioReportante() { return usuarioReportante; }
    public void setUsuarioReportante(Usuario usuarioReportante) { this.usuarioReportante = usuarioReportante; }
    public ObjetoEncontrado getObjetoEncontrado() { return objetoEncontrado; }

    /** Vincula el objeto físico y actualiza el estado de recepción en una sola operación. */
    public void confirmarRecepcion(ObjetoEncontrado objetoEncontrado) {
        if (objetoEncontrado == null || this.objetoEncontrado != null) return;
        this.objetoEncontrado = objetoEncontrado;
        this.estadoRecepcion = "RECIBIDO";
    }

    /** Permite al gestor consultar la relación, pero evita separarla después de la recepción. */
    public void setObjetoEncontrado(ObjetoEncontrado objetoEncontrado) {
        if (objetoEncontrado != null) confirmarRecepcion(objetoEncontrado);
    }
}
