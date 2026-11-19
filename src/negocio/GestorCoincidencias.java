package negocio;

import modelo.Coincidencia;
import modelo.ReporteHallazgo;
import modelo.ReportePerdida;
import modelo.Usuario;
import java.util.ArrayList;
import java.util.Locale;

/** Calcula y consulta similitudes entre avisos de pérdida y de hallazgo. */
public class GestorCoincidencias {
    /** Resultados de la última comparación completa. */
    private ArrayList<Coincidencia> coincidencias;
    /** Secuencia usada para diferenciar los códigos de coincidencia. */
    private int siguienteCodigo;

    /** Crea una colección vacía de resultados. */
    public GestorCoincidencias() { coincidencias = new ArrayList<Coincidencia>(); siguienteCodigo = 1; }

    /** Vuelve a comparar todos los pares y ordena los resultados de mayor a menor puntaje. */
    public void recalcular(ArrayList<ReportePerdida> perdidas, ArrayList<ReporteHallazgo> hallazgos) {
        coincidencias.clear();
        for (ReportePerdida perdida : perdidas) {
            for (ReporteHallazgo hallazgo : hallazgos) {
                int puntaje = calcularPuntaje(perdida, hallazgo);
                if (puntaje >= 40) {
                    coincidencias.add(new Coincidencia("COI-" + siguienteCodigo++, perdida, hallazgo,
                            puntaje, consultarCriterios(perdida, hallazgo)));
                }
            }
        }
        ordenarPorPuntaje();
    }

    /** Aplica las reglas de categoría, cronología, marca, color y lugar (máximo 100). */
    public int calcularPuntaje(ReportePerdida perdida, ReporteHallazgo hallazgo) {
        if (!normalizar(perdida.getCategoria()).equals(normalizar(hallazgo.getCategoria()))) return 0;
        if (hallazgo.getFechaHallazgo().isBefore(perdida.getFechaPerdida())) return 0;
        int puntaje = 0;
        if (coincideDato(perdida.getMarca(), hallazgo.getMarca())) puntaje += 40;
        if (coincideDato(perdida.getColor(), hallazgo.getColor())) puntaje += 40;
        if (coincideDato(perdida.getLugar(), hallazgo.getLugar())) puntaje += 20;
        return puntaje;
    }

    /** Entrega las coincidencias que pertenecen a los reportes del usuario. */
    public ArrayList<Coincidencia> consultarPorUsuario(Usuario usuario) {
        ArrayList<Coincidencia> propias = new ArrayList<Coincidencia>();
        for (Coincidencia coincidencia : coincidencias) {
            if (coincidencia.getReportePerdida().getPropietario() == usuario) propias.add(coincidencia);
        }
        return propias;
    }

    /** Compara dos datos informados tras normalizarlos, sin premiar datos desconocidos. */
    private boolean coincideDato(String primero, String segundo) {
        String datoPrimero = normalizar(primero);
        String datoSegundo = normalizar(segundo);
        return !datoPrimero.isEmpty() && !datoSegundo.isEmpty() && datoPrimero.equals(datoSegundo);
    }
    /** Elimina espacios externos y diferencias entre mayúsculas y minúsculas. */
    private String normalizar(String texto) { return texto == null ? "" : texto.trim().toLowerCase(Locale.ROOT); }
    /** Describe los datos que aportaron puntos al resultado. */
    private String consultarCriterios(ReportePerdida perdida, ReporteHallazgo hallazgo) {
        StringBuilder criterios = new StringBuilder();
        if (coincideDato(perdida.getMarca(), hallazgo.getMarca())) criterios.append("marca, ");
        if (coincideDato(perdida.getColor(), hallazgo.getColor())) criterios.append("color, ");
        if (coincideDato(perdida.getLugar(), hallazgo.getLugar())) criterios.append("lugar, ");
        return criterios.length() == 0 ? "categoría" : "categoría, " + criterios.substring(0, criterios.length() - 2);
    }
    /** Ordena resultados sin usar características posteriores a Java 8. */
    private void ordenarPorPuntaje() {
        for (int i = 0; i < coincidencias.size(); i++) {
            for (int j = i + 1; j < coincidencias.size(); j++) {
                if (coincidencias.get(j).getPuntaje() > coincidencias.get(i).getPuntaje()) {
                    Coincidencia temporal = coincidencias.get(i);
                    coincidencias.set(i, coincidencias.get(j)); coincidencias.set(j, temporal);
                }
            }
        }
    }
    public ArrayList<Coincidencia> getCoincidencias() { return coincidencias; }
}
