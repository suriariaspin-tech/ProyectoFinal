package modelo;

import java.util.ArrayList;

/** Representa el objeto físico recibido y su ubicación de custodia actual. */
public class ObjetoEncontrado {
    /** Código del reporte de hallazgo que originó el objeto físico. */
    private String codigoObjeto;
    /** Reporte con la descripción original del objeto. */
    private ReporteHallazgo reporteHallazgo;
    /** Ubicación física actual; es nula antes de la recepción. */
    private Ubicacion ubicacionActual;
    /** Estado físico: EN_CUSTODIA o ENTREGADO. */
    private String estadoCustodia;
    /** Historial de traslados asociados a este objeto. */
    private ArrayList<MovimientoCustodia> movimientos;

    /** Inicializa un objeto recibido y registra su ubicación actual. */
    public ObjetoEncontrado(String codigo, ReporteHallazgo reporteHallazgo, Ubicacion ubicacionActual, String estadoCustodia) {
        this.codigoObjeto = codigo; this.reporteHallazgo = reporteHallazgo;
        this.ubicacionActual = ubicacionActual; this.estadoCustodia = estadoCustodia;
        this.movimientos = new ArrayList<MovimientoCustodia>();
    }
    /** Actualiza la referencia única a la ubicación física actual. */
    public void actualizarUbicacion(Ubicacion ubicacion) { this.ubicacionActual = ubicacion; }
    /** Devuelve la ubicación actual, o null si aún no hay ubicación asociada. */
    public Ubicacion consultarUbicacion() { return ubicacionActual; }
    /** Conserva cada movimiento en el historial propio del objeto. */
    public void agregarMovimiento(MovimientoCustodia movimiento) { movimientos.add(movimiento); }
    public ArrayList<MovimientoCustodia> getMovimientos() { return movimientos; }
    public String getCodigoObjeto() { return codigoObjeto; }
    /** Alias conservado para las interfaces y gestores existentes. */
    public String getCodigo() { return codigoObjeto; }
    public void setCodigoObjeto(String codigoObjeto) { this.codigoObjeto = codigoObjeto; }
    public void setCodigo(String codigo) { this.codigoObjeto = codigo; }
    public ReporteHallazgo getReporteHallazgo() { return reporteHallazgo; }
    public void setReporteHallazgo(ReporteHallazgo reporteHallazgo) { this.reporteHallazgo = reporteHallazgo; }
    public Ubicacion getUbicacionActual() { return ubicacionActual; }
    public void setUbicacionActual(Ubicacion ubicacionActual) { actualizarUbicacion(ubicacionActual); }
    public String getEstadoCustodia() { return estadoCustodia; }
    public void setEstadoCustodia(String estadoCustodia) { this.estadoCustodia = estadoCustodia; }
}
