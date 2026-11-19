package modelo;

import java.time.LocalDateTime;

/** Conserva los datos históricos de un traslado confirmado entre ubicaciones. */
public class MovimientoCustodia {
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
    /** Encargado responsable de realizar el traslado. */
    private Usuario encargado;

    /** Inicializa un movimiento histórico ya confirmado. */
    public MovimientoCustodia(ObjetoEncontrado objeto, Ubicacion ubicacionOrigen, Ubicacion ubicacionDestino,
                              LocalDateTime fechaHora, String motivo, Usuario encargado) {
        this.objeto = objeto; this.ubicacionOrigen = ubicacionOrigen; this.ubicacionDestino = ubicacionDestino;
        this.fechaHora = fechaHora; this.motivo = motivo; this.encargado = encargado;
    }
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
    public Usuario getEncargado() { return encargado; }
    public void setEncargado(Usuario encargado) { this.encargado = encargado; }
}
