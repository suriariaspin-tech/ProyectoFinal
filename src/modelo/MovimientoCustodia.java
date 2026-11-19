package modelo;

import java.time.LocalDateTime;

/** Conserva los datos históricos de un traslado confirmado entre ubicaciones. */
public class MovimientoCustodia {
    /** Código único del movimiento histórico. */
    private String codigoMovimiento;
    /** Objeto físico que fue trasladado. */
    private ObjetoEncontrado objeto;
    /** Ubicación desde la que salió el objeto. */
    private Ubicacion ubicacionOrigen;
    /** Ubicación a la que llegó el objeto. */
    private Ubicacion ubicacionDestino;
    /** Fecha y hora en que se confirmó el traslado. */
    private LocalDateTime fechaHora;
    /** Motivo escrito para justificar el traslado. */
    private String motivo;
    /** Observaciones adicionales sobre el traslado. */
    private String observacion;
    /** Encargado responsable de realizar el traslado. */
    private Usuario encargado;

    /** Inicializa un movimiento histórico ya confirmado. */
    public MovimientoCustodia(String codigoMovimiento, ObjetoEncontrado objeto, Ubicacion ubicacionOrigen,
                              Ubicacion ubicacionDestino, LocalDateTime fechaHora, String motivo,
                              String observacion, Usuario encargado) {
        this.codigoMovimiento = codigoMovimiento;
        this.objeto = objeto; this.ubicacionOrigen = ubicacionOrigen; this.ubicacionDestino = ubicacionDestino;
        this.fechaHora = fechaHora; this.motivo = motivo; this.observacion = observacion; this.encargado = encargado;
    }
    public String getCodigoMovimiento() { return codigoMovimiento; }
    public void setCodigoMovimiento(String codigoMovimiento) { this.codigoMovimiento = codigoMovimiento; }
    public ObjetoEncontrado getObjeto() { return objeto; }
    public void setObjeto(ObjetoEncontrado objeto) { this.objeto = objeto; }
    public Ubicacion getUbicacionOrigen() { return ubicacionOrigen; }
    public void setUbicacionOrigen(Ubicacion ubicacionOrigen) { this.ubicacionOrigen = ubicacionOrigen; }
    public Ubicacion getUbicacionDestino() { return ubicacionDestino; }
    public void setUbicacionDestino(Ubicacion ubicacionDestino) { this.ubicacionDestino = ubicacionDestino; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
    public Usuario getEncargado() { return encargado; }
    public void setEncargado(Usuario encargado) { this.encargado = encargado; }
}
