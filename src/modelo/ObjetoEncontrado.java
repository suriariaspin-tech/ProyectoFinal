package modelo;

import java.util.ArrayList;

/** Representa el objeto físico recibido y su ubicación de custodia actual. */
public class ObjetoEncontrado {
    private String codigoObjeto; //Código del reporte de hallazgo que originó el objeto físico.
    private ReporteHallazgo reporteHallazgo; //Reporte con la descripción original del objeto.
    private Ubicacion ubicacionActual; //Ubicación física actual; es nula antes de la recepción.
    private String estadoCustodia; //Estado físico: EN_CUSTODIA o ENTREGADO.
    private ArrayList<MovimientoCustodia> movimientos; //Historial de traslados asociados a este objeto.

    /** Inicializa un objeto recibido y registra su ubicación actual. */
    public ObjetoEncontrado(String codigo, ReporteHallazgo reporteHallazgo, Ubicacion ubicacionActual, String estadoCustodia) {
        this.codigoObjeto = codigo; this.reporteHallazgo = reporteHallazgo;
        this.ubicacionActual = ubicacionActual; this.estadoCustodia = estadoCustodia;
        this.movimientos = new ArrayList<MovimientoCustodia>();
    }

    public void actualizarUbicacion(Ubicacion ubicacion){
        this.ubicacionActual = ubicacion; } //Actualiza la referencia única a la ubicación física actual.
    public Ubicacion consultarUbicacion(){
        return ubicacionActual; } //Devuelve la ubicación actual, o null si aún no hay ubicación asociada.
    public void agregarMovimiento(MovimientoCustodia movimiento){
        movimientos.add(movimiento); } //Conserva cada movimiento en el historial propio del objeto.
    public ArrayList<MovimientoCustodia> getMovimientos(){
        return new ArrayList<MovimientoCustodia>(movimientos); } //Devuelve una copia de los movimientos históricos.
    public String getCodigoObjeto(){
        return codigoObjeto; } //Devuelve el código asignado al objeto físico.
    public void setCodigoObjeto(String codigoObjeto){
        this.codigoObjeto = codigoObjeto; } //Actualiza el código del objeto.
    public ReporteHallazgo getReporteHallazgo(){
        return reporteHallazgo; } //Devuelve el reporte de hallazgo que originó este objeto.
    public void setReporteHallazgo(ReporteHallazgo reporteHallazgo){
        this.reporteHallazgo = reporteHallazgo; } //Actualiza el reporte relacionado.
    public Ubicacion getUbicacionActual(){
        return ubicacionActual; } //Devuelve la única ubicación actual del objeto.
    public String getEstadoCustodia(){
        return estadoCustodia; } //Devuelve el estado físico actual.
    public void setEstadoCustodia(String estadoCustodia){
        this.estadoCustodia = estadoCustodia; } //Actualiza el estado físico usado por las reglas de custodia.
}
