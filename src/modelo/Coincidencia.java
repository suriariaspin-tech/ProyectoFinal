package modelo;

/** Guarda la similitud calculada entre un reporte de pérdida y uno de hallazgo. */
public class Coincidencia {
    private String codigoCoincidencia; //Código que identifica el resultado de comparación.
    private ReportePerdida reportePerdida; //Reporte de pérdida que se comparó.
    private ReporteHallazgo reporteHallazgo; //Reporte de hallazgo que se comparó.
    private double puntaje; //Puntaje de similitud comprendido entre 0 y 100.
    private String criteriosCoincidentes; //Datos que coincidieron y explican el puntaje obtenido.

    /** Inicializa el resultado de comparar los dos reportes. */
    public Coincidencia(String codigoCoincidencia, ReportePerdida reportePerdida,
                        ReporteHallazgo reporteHallazgo, double puntaje, String criteriosCoincidentes) {
        this.codigoCoincidencia = codigoCoincidencia;
        this.reportePerdida = reportePerdida;
        this.reporteHallazgo = reporteHallazgo;
        this.puntaje = puntaje;
        this.criteriosCoincidentes = criteriosCoincidentes;
    }

    public String getCodigoCoincidencia(){
        return codigoCoincidencia; } //Devuelve el código único del resultado.
    public void setCodigoCoincidencia(String codigoCoincidencia){
        this.codigoCoincidencia = codigoCoincidencia; } //Actualiza el código del resultado.
    public ReportePerdida getReportePerdida(){
        return reportePerdida; } //Devuelve el reporte de pérdida comparado.
    public void setReportePerdida(ReportePerdida reportePerdida){
        this.reportePerdida = reportePerdida; } //Actualiza el reporte de pérdida asociado.
    public ReporteHallazgo getReporteHallazgo(){
        return reporteHallazgo; } //Devuelve el reporte de hallazgo comparado.
    public void setReporteHallazgo(ReporteHallazgo reporteHallazgo){
        this.reporteHallazgo = reporteHallazgo; } //Actualiza el reporte de hallazgo asociado.
    public double getPuntaje(){
        return puntaje; } //Devuelve el puntaje de similitud calculado.
    public void setPuntaje(double puntaje){
        this.puntaje = puntaje; } //Actualiza el puntaje de similitud.
    public String getCriteriosCoincidentes(){
        return criteriosCoincidentes; } //Devuelve los criterios que aportaron puntos.
    public void setCriteriosCoincidentes(String criteriosCoincidentes){
        this.criteriosCoincidentes = criteriosCoincidentes; } //Actualiza el resumen de criterios coincidentes.
}
