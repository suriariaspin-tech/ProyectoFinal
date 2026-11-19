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
    /** Devuelve una copia de los movimientos históricos. */
    public ArrayList<MovimientoCustodia> getMovimientos() { return new ArrayList<MovimientoCustodia>(movimientos); }
    /** Devuelve el código asignado al objeto físico. */
    public String getCodigoObjeto() { return codigoObjeto; }
    /** Actualiza el código del objeto. */
    public void setCodigoObjeto(String codigoObjeto) { this.codigoObjeto = codigoObjeto; }
    /** Devuelve el reporte de hallazgo que originó este objeto. */
    public ReporteHallazgo getReporteHallazgo() { return reporteHallazgo; }
    /** Actualiza el reporte relacionado. */
    public void setReporteHallazgo(ReporteHallazgo reporteHallazgo) { this.reporteHallazgo = reporteHallazgo; }
    /** Devuelve la única ubicación actual del objeto. */
    public Ubicacion getUbicacionActual() { return ubicacionActual; }
    /** Devuelve el estado físico actual. */
    public String getEstadoCustodia() { return estadoCustodia; }
    /** Actualiza el estado físico usado por las reglas de custodia. */
    public void setEstadoCustodia(String estadoCustodia) { this.estadoCustodia = estadoCustodia; }
}
