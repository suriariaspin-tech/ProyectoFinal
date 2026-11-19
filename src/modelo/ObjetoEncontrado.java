package modelo;

/** Representa el objeto físico recibido y su ubicación de custodia actual. */
public class ObjetoEncontrado {
    /** Código del reporte de hallazgo que originó el objeto físico. */
    private String codigo;
    /** Reporte con la descripción original del objeto. */
    private ReporteHallazgo reporteHallazgo;
    /** Ubicación física actual; es nula antes de la recepción. */
    private Ubicacion ubicacionActual;
    /** Estado físico: EN_CUSTODIA o ENTREGADO. */
    private String estadoCustodia;

    /** Inicializa un objeto recibido y registra su ubicación actual. */
    public ObjetoEncontrado(String codigo, ReporteHallazgo reporteHallazgo, Ubicacion ubicacionActual, String estadoCustodia) {
        this.codigo = codigo; this.reporteHallazgo = reporteHallazgo;
        this.ubicacionActual = ubicacionActual; this.estadoCustodia = estadoCustodia;
    }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public ReporteHallazgo getReporteHallazgo() { return reporteHallazgo; }
    public void setReporteHallazgo(ReporteHallazgo reporteHallazgo) { this.reporteHallazgo = reporteHallazgo; }
    public Ubicacion getUbicacionActual() { return ubicacionActual; }
    public void setUbicacionActual(Ubicacion ubicacionActual) { this.ubicacionActual = ubicacionActual; }
    public String getEstadoCustodia() { return estadoCustodia; }
    public void setEstadoCustodia(String estadoCustodia) { this.estadoCustodia = estadoCustodia; }
}
