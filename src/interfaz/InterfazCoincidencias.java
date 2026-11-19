package interfaz;

import modelo.Coincidencia;
import modelo.ReporteHallazgo;
import modelo.Usuario;
import negocio.GestorCoincidencias;
import negocio.GestorReportesHallazgo;
import negocio.GestorReportesPerdida;
import java.util.ArrayList;

/** Calcula y muestra por consola posibles coincidencias sin confirmar propiedad. */
public class InterfazCoincidencias {
    /** Gestor compartido para comparar y ordenar reportes. */
    private GestorCoincidencias gestorCoincidencias;
    /** Reportes de pérdida que se comparan. */
    private GestorReportesPerdida gestorPerdidas;
    /** Reportes de hallazgo que se comparan. */
    private GestorReportesHallazgo gestorHallazgos;

    /** Recibe los tres gestores que comparten los datos de la ejecución. */
    public InterfazCoincidencias(GestorCoincidencias coincidencias,
                                 GestorReportesPerdida perdidas, GestorReportesHallazgo hallazgos) {
        gestorCoincidencias = coincidencias;
        gestorPerdidas = perdidas;
        gestorHallazgos = hallazgos;
    }

    /** Recalcula y presenta coincidencias propias con puntaje mínimo de 40. */
    public void consultar(Usuario usuario) {
        gestorCoincidencias.recalcular(gestorPerdidas.getReportes(), gestorHallazgos.getReportes());
        ArrayList<Coincidencia> lista = gestorCoincidencias.consultarPorUsuario(usuario);
        if (lista.isEmpty()) {
            System.out.println("No hay posibles coincidencias de 40 puntos o más.");
            return;
        }
        System.out.println("Las coincidencias representan similitud y no demuestran propiedad.");
        for (Coincidencia coincidencia : lista) {
            ReporteHallazgo hallazgo = coincidencia.getReporteHallazgo();
            System.out.println("Hallazgo: " + hallazgo.getCodigo() + " | " + hallazgo.getCategoria()
                    + " | " + hallazgo.getDescripcion() + " | Color: " + hallazgo.getColor()
                    + " | Marca: " + textoMarca(hallazgo.getMarca())
                    + " | Fecha: " + hallazgo.getFechaHoraHallazgo()
                    + " | Lugar: " + hallazgo.getLugarHallazgo()
                    + " | Puntaje: " + (int) coincidencia.getPuntaje()
                    + " | Criterios: " + coincidencia.getCriteriosCoincidentes());
            if (hallazgo.getObjetoEncontrado() == null) {
                System.out.println("Estado: PENDIENTE_RECEPCION; objeto aún no recibido físicamente.");
            } else {
                System.out.println("Estado: EN_CUSTODIA | Ubicación actual: "
                        + hallazgo.getObjetoEncontrado().getUbicacionActual().getNombre());
            }
        }
    }

    /** Indica que una marca vacía no fue informada. */
    private String textoMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) return "No informada";
        return marca;
    }
}
