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
    /** Devuelve el código único del resultado. */
    public String getCodigoCoincidencia() { return codigoCoincidencia; }
    /** Actualiza el código del resultado. */
    public void setCodigoCoincidencia(String codigoCoincidencia) { this.codigoCoincidencia = codigoCoincidencia; }
    /** Devuelve el reporte de pérdida comparado. */
    public ReportePerdida getReportePerdida() { return reportePerdida; }
    /** Actualiza el reporte de pérdida asociado. */
    public void setReportePerdida(ReportePerdida reportePerdida) { this.reportePerdida = reportePerdida; }
    /** Devuelve el reporte de hallazgo comparado. */
    public ReporteHallazgo getReporteHallazgo() { return reporteHallazgo; }
    /** Actualiza el reporte de hallazgo asociado. */
    public void setReporteHallazgo(ReporteHallazgo reporteHallazgo) { this.reporteHallazgo = reporteHallazgo; }
    /** Devuelve el puntaje de similitud calculado. */
    public double getPuntaje() { return puntaje; }
    /** Actualiza el puntaje de similitud. */
    public void setPuntaje(double puntaje) { this.puntaje = puntaje; }
    /** Devuelve los criterios que aportaron puntos. */
    public String getCriteriosCoincidentes() { return criteriosCoincidentes; }
    /** Actualiza el resumen de criterios coincidentes. */
    public void setCriteriosCoincidentes(String criteriosCoincidentes) { this.criteriosCoincidentes = criteriosCoincidentes; }
}
