package interfaz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/** Centraliza la lectura por consola para que toda la aplicación comparta un Scanner. */
public class Validacion {
    /** Único Scanner del programa, conectado a la entrada de teclado. */
    private static final Scanner ENTRADA = new Scanner(System.in);
    /** Formato simple para escribir fechas y horas: año-mes-día hora:minuto. */
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /** Evita crear objetos de lectura, ya que las operaciones son compartidas. */
    private Validacion() { }

    /** Lee una línea obligatoria y repite la pregunta si queda vacía. */
    public static String leerTextoObligatorio(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = ENTRADA.nextLine().trim();
            if (texto.isEmpty()) System.out.println("Este campo es obligatorio. Intente nuevamente.");
        } while (texto.isEmpty());
        return texto;
    }

    /** Lee una línea opcional; una entrada vacía se representa con texto vacío. */
    public static String leerTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        return ENTRADA.nextLine().trim();
    }

    /** Lee un entero y repite la pregunta cuando el texto no representa un número. */
    public static int leerEntero(String mensaje) {
        int numero = 0;
        boolean valido = false;
        while (!valido) {
            System.out.print(mensaje);
            if (ENTRADA.hasNextInt()) {
                numero = ENTRADA.nextInt();
                ENTRADA.nextLine();
                valido = true;
            } else {
                ENTRADA.nextLine();
                System.out.println("Ingrese un número entero válido.");
            }
        }
        return numero;
    }

    /** Lee una opción de menú y comprueba que esté entre el mínimo y el máximo indicados. */
    public static int leerOpcion(String mensaje, int minimo, int maximo) {
        int opcion;
        do {
            opcion = leerEntero(mensaje);
            if (opcion < minimo || opcion > maximo) {
                System.out.println("Seleccione una opción entre " + minimo + " y " + maximo + ".");
            }
        } while (opcion < minimo || opcion > maximo);
        return opcion;
    }

    /** Lee un número decimal, validando que la persona escriba un valor numérico. */
    public static double leerNumero(String mensaje) {
        double numero = 0;
        boolean valido = false;
        while (!valido) {
            System.out.print(mensaje);
            if (ENTRADA.hasNextDouble()) {
                numero = ENTRADA.nextDouble();
                ENTRADA.nextLine();
                valido = true;
            } else {
                ENTRADA.nextLine();
                System.out.println("Ingrese un número válido.");
            }
        }
        return numero;
    }

    /**
     * Lee una fecha pasada o presente. LocalDateTime permite comparar cronológicamente
     * pérdida y hallazgo; DateTimeFormatter define su formato. DateTimeParseException
     * se captura únicamente para volver a pedir fechas con formato o calendario inválido.
     */
    public static LocalDateTime leerFechaNoFutura(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (AAAA-MM-DD HH:MM): ");
            String texto = ENTRADA.nextLine().trim();
            try {
                LocalDateTime fecha = LocalDateTime.parse(texto, FORMATO_FECHA);
                // La comparación detecta fechas de calendario inválidas que el formato podría normalizar.
                if (!fecha.format(FORMATO_FECHA).equals(texto)) {
                    System.out.println("La fecha escrita no existe en el calendario.");
                    continue;
                }
                if (!fecha.isAfter(LocalDateTime.now())) return fecha;
                System.out.println("La fecha no puede estar en el futuro.");
            } catch (DateTimeParseException error) {
                System.out.println("Formato incorrecto. Ejemplo: 2026-03-15 14:30.");
            }
        }
    }
}
