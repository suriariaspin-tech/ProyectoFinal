package modelo;

import java.time.LocalDateTime;

/** Reporte de un objeto que su propietario perdió; hereda los datos comunes de Reporte. */
public class ReportePerdida extends Reporte {
    /** Usuario dueño del reporte de pérdida. */
    private Usuario propietario;
    /** Fecha y hora aproximada en que ocurrió la pérdida. */
    private LocalDateTime fechaHoraPerdida;
    /** Lugar del campus donde ocurrió la pérdida. */
    private String lugarPerdida;

    /** Inicializa los datos comunes y la información específica de una pérdida. */
    public ReportePerdida(String codigo, Usuario propietario, String categoria, String marca, String color,
                          String descripcion, String caracteristicas, LocalDateTime fechaHoraPerdida,
                          String lugarPerdida, String fotografia, String estado) {
        super(codigo, categoria, marca, color, descripcion, caracteristicas, LocalDateTime.now(), estado, fotografia);
        this.propietario = propietario;
        this.fechaHoraPerdida = fechaHoraPerdida;
        this.lugarPerdida = lugarPerdida;
    }

    /** Devuelve la fecha de pérdida usada en las comparaciones de coincidencia. */
    public LocalDateTime getFechaHoraPerdida() { return fechaHoraPerdida; }
    /** Conserva compatibilidad con el nombre empleado por las interfaces y gestores. */
    public LocalDateTime getFechaPerdida() { return fechaHoraPerdida; }
    public void setFechaPerdida(LocalDateTime fecha) { fechaHoraPerdida = fecha; }
    public void setFechaHoraPerdida(LocalDateTime fecha) { fechaHoraPerdida = fecha; }
    public String getLugarPerdida() { return lugarPerdida; }
    /** Conserva compatibilidad con el nombre empleado por las interfaces y gestores. */
    public String getLugar() { return lugarPerdida; }
    public void setLugar(String lugar) { lugarPerdida = lugar; }
    public void setLugarPerdida(String lugar) { lugarPerdida = lugar; }
    public Usuario getPropietario() { return propietario; }
    public void setPropietario(Usuario propietario) { this.propietario = propietario; }
}
