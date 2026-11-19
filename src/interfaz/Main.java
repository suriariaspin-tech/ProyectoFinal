package interfaz;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/** Punto de entrada: crea una sola instancia de cada gestor para compartir datos entre módulos. */
public class Main {
    /** Inicia la interfaz gráfica en el hilo de eventos de Swing. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new VentanaPrincipal().iniciar();
                } catch (RuntimeException error) {
                    JOptionPane.showMessageDialog(null, "No se pudo iniciar Reencuentra: " + error.getMessage(),
                            "Error de inicio", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
