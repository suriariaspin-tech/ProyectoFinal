package modelo;

/** Guarda la similitud calculada entre un reporte de pérdida y uno de hallazgo. */
public class Coincidencia {
    private String codigoCoincidencia; //Código que identifica el resultado de comparación.
    private ReportePerdida reportePerdida; //Reporte de pérdida que se comparó.
    private ReporteHallazgo reporteHallazgo; //Reporte de hallazgo que se comparó.
    private double puntaje; //Puntaje de similitud comprendido entre 0 y 100.
    /** Datos que coincidieron y explican el puntaje obtenido. */
    private String criteriosCoincidentes;

    /** Inicializa el resultado de comparar los dos reportes. */
    public Coincidencia(String codigoCoincidencia, ReportePerdida reportePerdida,
                        ReporteHallazgo reporteHallazgo, double puntaje, String criteriosCoincidentes) {
        this.codigoCoincidencia = codigoCoincidencia;
        this.reportePerdida = reportePerdida;
        this.reporteHallazgo = reporteHallazgo;
        this.puntaje = puntaje;
        this.criteriosCoincidentes = criteriosCoincidentes;
    }
    public String getCodigoCoincidencia() { return codigoCoincidencia; }
    public void setCodigoCoincidencia(String codigoCoincidencia) { this.codigoCoincidencia = codigoCoincidencia; }
    public ReportePerdida getReportePerdida() { return reportePerdida; }
    public void setReportePerdida(ReportePerdida reportePerdida) { this.reportePerdida = reportePerdida; }
    public ReporteHallazgo getReporteHallazgo() { return reporteHallazgo; }
    public void setReporteHallazgo(ReporteHallazgo reporteHallazgo) { this.reporteHallazgo = reporteHallazgo; }
    public double getPuntaje() { return puntaje; }
    public void setPuntaje(double puntaje) { this.puntaje = puntaje; }
    public String getCriteriosCoincidentes() { return criteriosCoincidentes; }
    public void setCriteriosCoincidentes(String criteriosCoincidentes) { this.criteriosCoincidentes = criteriosCoincidentes; }
}
