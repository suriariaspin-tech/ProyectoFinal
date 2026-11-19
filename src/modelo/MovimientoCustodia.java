package modelo;

import java.time.LocalDateTime;

/** Conserva los datos inmutables de una recepción inicial o un traslado confirmado. */
public class MovimientoCustodia {
    /** Código único del movimiento histórico. */
    private final String codigoMovimiento;
    /** Objeto físico recibido o trasladado. */
    private final ObjetoEncontrado objeto;
    /** Ubicación desde la que salió el objeto. */
    private final Ubicacion ubicacionOrigen;
    /** Ubicación a la que llegó el objeto. */
    private final Ubicacion ubicacionDestino;
    /** Fecha y hora en que se confirmó el traslado. */
    private final LocalDateTime fechaHora;
    /** Motivo escrito para justificar el traslado. */
    private final String motivo;
    /** Observaciones adicionales sobre el traslado. */
    private final String observacion;
    /** Encargado responsable de realizar el traslado. */
    private final Usuario encargado;

    /** Inicializa un movimiento histórico ya confirmado. */
    public MovimientoCustodia(String codigoMovimiento, ObjetoEncontrado objeto, Ubicacion ubicacionOrigen,
                              Ubicacion ubicacionDestino, LocalDateTime fechaHora, String motivo,
                              String observacion, Usuario encargado) {
        this.codigoMovimiento = codigoMovimiento;
        this.objeto = objeto;
        this.ubicacionOrigen = ubicacionOrigen;
        this.ubicacionDestino = ubicacionDestino;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.observacion = observacion;
        this.encargado = encargado;
    }
    /** Devuelve el código único del movimiento. */
    public String getCodigoMovimiento() { return codigoMovimiento; }
    /** Devuelve el objeto trasladado. */
    public ObjetoEncontrado getObjeto() { return objeto; }
    /** Devuelve la ubicación desde la que salió el objeto. */
    public Ubicacion getUbicacionOrigen() { return ubicacionOrigen; }
    /** Devuelve la ubicación a la que llegó el objeto. */
    public Ubicacion getUbicacionDestino() { return ubicacionDestino; }
    /** Devuelve la fecha y hora del movimiento. */
    public LocalDateTime getFechaHora() { return fechaHora; }
    /** Devuelve el motivo del traslado. */
    public String getMotivo() { return motivo; }
    /** Devuelve las observaciones asociadas. */
    public String getObservacion() { return observacion; }
    /** Devuelve el encargado que realizó el movimiento. */
    public Usuario getEncargado() { return encargado; }
}
