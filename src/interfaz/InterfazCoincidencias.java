package interfaz;

import modelo.Coincidencia;
import modelo.ReporteHallazgo;
import modelo.Usuario;
import negocio.GestorCoincidencias;
import negocio.GestorReportesHallazgo;
import negocio.GestorReportesPerdida;
import javax.swing.JOptionPane;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/** Actualiza y presenta posibles relaciones entre reportes, sin confirmar propiedad. */
public class InterfazCoincidencias {
    /** Gestor que calcula los puntajes. */
    private GestorCoincidencias gestorCoincidencias;
    /** Fuente de reportes de pérdida para recalcular resultados. */
    private GestorReportesPerdida gestorPerdidas;
    /** Fuente de reportes de hallazgo para recalcular resultados. */
    private GestorReportesHallazgo gestorHallazgos;

    /** Recibe los tres gestores usados durante toda la sesión. */
    public InterfazCoincidencias(GestorCoincidencias coincidencias, GestorReportesPerdida perdidas, GestorReportesHallazgo hallazgos) {
        gestorCoincidencias = coincidencias; gestorPerdidas = perdidas; gestorHallazgos = hallazgos;
    }

    /** Recalcula y muestra coincidencias propias con puntaje mínimo de 40. */
    public void consultar(Usuario usuario) {
        gestorCoincidencias.recalcular(gestorPerdidas.getReportes(), gestorHallazgos.getReportes());
        ArrayList<Coincidencia> lista = gestorCoincidencias.consultarPorUsuario(usuario);
        if (lista.isEmpty()) { JOptionPane.showMessageDialog(null, "No hay posibles coincidencias de 40 puntos o más."); return; }
        StringBuilder texto = new StringBuilder("Las coincidencias son similitudes y no demuestran propiedad.\n\n");
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Coincidencia coincidencia : lista) {
            ReporteHallazgo hallazgo = coincidencia.getReporteHallazgo();
            texto.append("Hallazgo: ").append(hallazgo.getCodigo()).append(" | ").append(hallazgo.getCategoria())
                    .append(" | ").append(hallazgo.getDescripcion()).append(" | Color: ").append(hallazgo.getColor())
                    .append(" | Marca: ").append(hallazgo.getMarca().isEmpty() ? "No informada" : hallazgo.getMarca())
                    .append(" | Fecha: ").append(hallazgo.getFechaHallazgo().format(formato)).append(" | Lugar: ").append(hallazgo.getLugar())
                    .append(" | Puntaje: ").append((int) coincidencia.getPuntaje())
                    .append(" | Estado de recepción: ").append(hallazgo.getEstadoRecepcion());
            if (hallazgo.getObjetoEncontrado() != null) texto.append(" | Ubicación actual: ").append(hallazgo.getObjetoEncontrado().getUbicacionActual().getNombre());
            else texto.append(" | Objeto todavía no recibido físicamente");
            texto.append("\n\n");
        }
        JOptionPane.showMessageDialog(null, texto.toString(), "Posibles coincidencias", JOptionPane.INFORMATION_MESSAGE);
    }
}
