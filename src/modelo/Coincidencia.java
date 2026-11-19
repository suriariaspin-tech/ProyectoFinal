package modelo;

/** Guarda la similitud calculada entre un reporte de pérdida y uno de hallazgo. */
public class Coincidencia {
    /** Reporte de pérdida que se comparó. */
    private ReportePerdida reportePerdida;
    /** Reporte de hallazgo que se comparó. */
    private ReporteHallazgo reporteHallazgo;
    /** Puntaje de similitud comprendido entre 0 y 100. */
    private int puntaje;

    /** Inicializa el resultado de comparar los dos reportes. */
    public Coincidencia(ReportePerdida reportePerdida, ReporteHallazgo reporteHallazgo, int puntaje) {
        this.reportePerdida = reportePerdida; this.reporteHallazgo = reporteHallazgo; this.puntaje = puntaje;
    }
    public ReportePerdida getReportePerdida() { return reportePerdida; }
    public void setReportePerdida(ReportePerdida reportePerdida) { this.reportePerdida = reportePerdida; }
    public ReporteHallazgo getReporteHallazgo() { return reporteHallazgo; }
    public void setReporteHallazgo(ReporteHallazgo reporteHallazgo) { this.reporteHallazgo = reporteHallazgo; }
    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }
}
