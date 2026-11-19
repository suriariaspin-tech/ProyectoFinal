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
    /** Devuelve el código único del movimiento. */
    public String getCodigoMovimiento() { return codigoMovimiento; }
    /** Actualiza el código del movimiento. */
    public void setCodigoMovimiento(String codigoMovimiento) { this.codigoMovimiento = codigoMovimiento; }
    /** Devuelve el objeto trasladado. */
    public ObjetoEncontrado getObjeto() { return objeto; }
    /** Actualiza el objeto asociado al movimiento. */
    public void setObjeto(ObjetoEncontrado objeto) { this.objeto = objeto; }
    /** Devuelve la ubicación desde la que salió el objeto. */
    public Ubicacion getUbicacionOrigen() { return ubicacionOrigen; }
    /** Actualiza la ubicación de origen. */
    public void setUbicacionOrigen(Ubicacion ubicacionOrigen) { this.ubicacionOrigen = ubicacionOrigen; }
    /** Devuelve la ubicación a la que llegó el objeto. */
    public Ubicacion getUbicacionDestino() { return ubicacionDestino; }
    /** Actualiza la ubicación de destino. */
    public void setUbicacionDestino(Ubicacion ubicacionDestino) { this.ubicacionDestino = ubicacionDestino; }
    /** Devuelve la fecha y hora del movimiento. */
    public LocalDateTime getFechaHora() { return fechaHora; }
    /** Actualiza la fecha y hora del movimiento. */
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    /** Devuelve el motivo del traslado. */
    public String getMotivo() { return motivo; }
    /** Actualiza el motivo del traslado. */
    public void setMotivo(String motivo) { this.motivo = motivo; }
    /** Devuelve las observaciones asociadas. */
    public String getObservacion() { return observacion; }
    /** Actualiza las observaciones del movimiento. */
    public void setObservacion(String observacion) { this.observacion = observacion; }
    /** Devuelve el encargado que realizó el movimiento. */
    public Usuario getEncargado() { return encargado; }
    /** Actualiza la persona responsable registrada. */
    public void setEncargado(Usuario encargado) { this.encargado = encargado; }
}
