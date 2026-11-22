package modelo;

import java.time.LocalDateTime;

/** Conserva los datos inmutables de una recepción inicial o un traslado confirmado. */
public class MovimientoCustodia {
    private final String codigoMovimiento; //Código único del movimiento histórico.
    private final ObjetoEncontrado objeto; //Objeto físico recibido o trasladado.
    private final Ubicacion ubicacionOrigen; //Ubicación desde la que salió el objeto.
    private final Ubicacion ubicacionDestino; //Ubicación a la que llegó el objeto.
    private final LocalDateTime fechaHora; //Fecha y hora en que se confirmó el traslado.
    private final String motivo; //Motivo escrito para justificar el traslado.
    private final String observacion; //Observaciones adicionales sobre el traslado.
    private final Usuario encargado; //Encargado responsable de realizar el traslado.

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

    public String getCodigoMovimiento(){
        return codigoMovimiento; } //Devuelve el código único del movimiento.
    public ObjetoEncontrado getObjeto(){
        return objeto; } //Devuelve el objeto trasladado.
    public Ubicacion getUbicacionOrigen(){
        return ubicacionOrigen; } //Devuelve la ubicación desde la que salió el objeto.
    public Ubicacion getUbicacionDestino(){
        return ubicacionDestino; } //Devuelve la ubicación a la que llegó el objeto.
    public LocalDateTime getFechaHora(){
        return fechaHora; } //Devuelve la fecha y hora del movimiento.
    public String getMotivo(){
        return motivo; } //Devuelve el motivo del traslado.
    public String getObservacion(){
        return observacion; } //Devuelve las observaciones asociadas.
    public Usuario getEncargado(){
        return encargado; } //Devuelve el encargado que realizó el movimiento.
}
