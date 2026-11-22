package modelo;

import java.time.LocalDateTime;

/** Reporte de un objeto perdido. extends hereda aquí los atributos y métodos comunes de Reporte. */
public class ReportePerdida extends Reporte {
    private Usuario propietario; //Usuario dueño del reporte de pérdida.
    private LocalDateTime fechaHoraPerdida; //Fecha y hora aproximada en que ocurrió la pérdida.
    private String lugarPerdida; //Lugar del campus donde ocurrió la pérdida.

    /** Inicializa los datos comunes y la información específica de una pérdida. */
    public ReportePerdida(String codigo, Usuario propietario, String categoria, String marca, String color,
                          String descripcion, String caracteristicas, LocalDateTime fechaHoraPerdida,
                          String lugarPerdida, String fotografia, String estado) {
        // super() inicializa la parte compartida del reporte antes de guardar sus datos de pérdida.
        super(codigo, categoria, marca, color, descripcion, caracteristicas, LocalDateTime.now(), estado, fotografia);
        this.propietario = propietario;
        this.fechaHoraPerdida = fechaHoraPerdida;
        this.lugarPerdida = lugarPerdida;
    }

    /** Devuelve la fecha de pérdida usada en las comparaciones de coincidencia. */
    public LocalDateTime getFechaHoraPerdida(){
        return fechaHoraPerdida; } //Devuelve la fecha y hora aproximada de la pérdida.
    public void setFechaHoraPerdida(LocalDateTime fecha){
        fechaHoraPerdida = fecha; } //Actualiza la fecha y hora de pérdida durante una edición validada.
    public String getLugarPerdida(){
        return lugarPerdida; } //Devuelve el lugar donde se perdió el objeto.
    public void setLugarPerdida(String lugar){
        lugarPerdida = lugar; } //Actualiza el lugar de pérdida durante una edición validada.
    public Usuario getPropietario(){
        return propietario; } //Devuelve el usuario propietario del reporte.
    public void setPropietario(Usuario propietario){
        this.propietario = propietario; } //Actualiza el propietario asociado.
}
