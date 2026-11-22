package negocio;

import modelo.Coincidencia;
import modelo.ReporteHallazgo;
import modelo.ReportePerdida;
import modelo.Usuario;
import java.util.ArrayList;

/** Compara reportes para mostrar posibles similitudes, sin decidir quién es dueño de un objeto. */
public class GestorCoincidencias {
    private ArrayList<Coincidencia> coincidencias; //Coincidencias calculadas más recientemente.

    public GestorCoincidencias(){
        coincidencias = new ArrayList<Coincidencia>(); } //Inicializa la lista de resultados de comparación.

    /** Compara cada pérdida con cada hallazgo y guarda resultados de 40 puntos o más en orden descendente. */
    public void recalcular(ArrayList<ReportePerdida> perdidas, ArrayList<ReporteHallazgo> hallazgos) {
        coincidencias.clear();
        int siguienteCodigo = 1;
        for (ReportePerdida perdida : perdidas) {
            for (ReporteHallazgo hallazgo : hallazgos) {
                int puntaje = calcularPuntaje(perdida, hallazgo);
                if (puntaje >= 40) {
                    Coincidencia coincidencia = new Coincidencia("COI-" + siguienteCodigo,
                            perdida, hallazgo, puntaje, consultarCriterios(perdida, hallazgo));
                    coincidencias.add(coincidencia);
                    siguienteCodigo++;
                }
            }
        }
        ordenarPorPuntaje();
    }

    /** Devuelve un puntaje entre 0 y 100; la categoría y el orden de fechas son requisitos. */
    public int calcularPuntaje(ReportePerdida perdida, ReporteHallazgo hallazgo) {
        if (perdida == null || hallazgo == null) return 0;
        if (perdida.getFechaHoraPerdida() == null || hallazgo.getFechaHoraHallazgo() == null) return 0;
        if (!iguales(perdida.getCategoria(), hallazgo.getCategoria())) return 0;
        if (hallazgo.getFechaHoraHallazgo().isBefore(perdida.getFechaHoraPerdida())) return 0;
        int puntaje = 0;
        if (coincideDato(perdida.getMarca(), hallazgo.getMarca())) puntaje += 40;
        if (coincideDato(perdida.getColor(), hallazgo.getColor())) puntaje += 40;
        if (coincideDato(perdida.getLugarPerdida(), hallazgo.getLugarHallazgo())) puntaje += 20;
        return puntaje;
    }

    /** Devuelve solo las coincidencias que corresponden a los reportes de una persona. */
    public ArrayList<Coincidencia> consultarPorUsuario(Usuario usuario) {
        ArrayList<Coincidencia> propias = new ArrayList<Coincidencia>();
        for (Coincidencia coincidencia : coincidencias) {
            if (coincidencia.getReportePerdida().getPropietario() == usuario) propias.add(coincidencia);
        }
        return propias;
    }

    /** Compara valores conocidos e ignora diferencias de mayúsculas y espacios externos. */
    private boolean coincideDato(String datoPerdida, String datoHallazgo) {
        if (datoPerdida == null || datoHallazgo == null) return false;
        if (datoPerdida.trim().isEmpty() || datoHallazgo.trim().isEmpty()) return false;
        return datoPerdida.trim().equalsIgnoreCase(datoHallazgo.trim());
    }

    /** Compara categorías sin distinguir mayúsculas ni espacios externos. */
    private boolean iguales(String primero, String segundo) {
        if (primero == null || segundo == null) return false;
        return primero.trim().equalsIgnoreCase(segundo.trim());
    }

    /** Construye un texto sencillo con los criterios que aportaron puntos. */
    private String consultarCriterios(ReportePerdida perdida, ReporteHallazgo hallazgo) {
        String criterios = "categoría";
        if (coincideDato(perdida.getMarca(), hallazgo.getMarca())) criterios += ", marca";
        if (coincideDato(perdida.getColor(), hallazgo.getColor())) criterios += ", color";
        if (coincideDato(perdida.getLugarPerdida(), hallazgo.getLugarHallazgo())) criterios += ", lugar";
        return criterios;
    }

    /** Ordena la lista usando intercambios sencillos para ubicar primero el puntaje mayor. */
    private void ordenarPorPuntaje() {
        for (int i = 0; i < coincidencias.size(); i++) {
            for (int j = i + 1; j < coincidencias.size(); j++) {
                if (coincidencias.get(j).getPuntaje() > coincidencias.get(i).getPuntaje()) {
                    Coincidencia temporal = coincidencias.get(i);
                    coincidencias.set(i, coincidencias.get(j));
                    coincidencias.set(j, temporal);
                }
            }
        }
    }

    /** Devuelve los resultados generados en el último cálculo. */
    /** Devuelve una copia de las coincidencias creadas en el último cálculo. */
    public ArrayList<Coincidencia> getCoincidencias(){
        return new ArrayList<Coincidencia>(coincidencias); }
}
