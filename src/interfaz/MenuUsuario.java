package interfaz;

import modelo.Usuario;
import negocio.GestorCoincidencias;
import negocio.GestorReportesHallazgo;
import negocio.GestorReportesPerdida;
import negocio.GestorUsuarios;

/** Presenta las opciones permitidas a un usuario con rol MIEMBRO. */
public class MenuUsuario {
    private Usuario usuario; //Usuario autenticado durante este menú.
    private InterfazUsuarios interfazUsuarios; //Interfaces de consola conectadas a los gestores compartidos.
    private InterfazReportes interfazReportes; //Interfaces de consola conectadas a los gestores compartidos.
    private InterfazCoincidencias interfazCoincidencias; //Interfaces de consola conectadas a los gestores compartidos.

    /** Recibe el usuario y los gestores que continúan activos durante toda la sesión. */
    public MenuUsuario(Usuario usuario, GestorUsuarios usuarios, GestorReportesPerdida perdidas,
                       GestorReportesHallazgo hallazgos, GestorCoincidencias coincidencias) {
        this.usuario = usuario;
        interfazUsuarios = new InterfazUsuarios(usuarios);
        interfazReportes = new InterfazReportes(perdidas, hallazgos, coincidencias);
        interfazCoincidencias = new InterfazCoincidencias(coincidencias, perdidas, hallazgos);
    }

    /** Repite las opciones del miembro hasta cerrar sesión. */
    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n=== Menú de miembro: " + usuario.getNombreCompleto() + " ===");
            System.out.println("1. Registrar objeto perdido");
            System.out.println("2. Registrar objeto encontrado");
            System.out.println("3. Consultar mis reportes de pérdida y estado");
            System.out.println("4. Editar reporte de pérdida");
            System.out.println("5. Consultar posibles coincidencias");
            System.out.println("6. Consultar perfil");
            System.out.println("7. Editar perfil");
            System.out.println("8. Consultar mis reportes de hallazgo");
            System.out.println("9. Editar reporte de hallazgo pendiente");
            System.out.println("10. Cerrar sesión");
            opcion = Validacion.leerOpcion("Seleccione una opción: ", 1, 10);
            switch (opcion) {
                case 1: interfazReportes.registrarPerdida(usuario); break;
                case 2: interfazReportes.registrarHallazgo(usuario); break;
                case 3: interfazReportes.consultarPerdidas(usuario); break;
                case 4: interfazReportes.editarPerdida(usuario); break;
                case 5: interfazCoincidencias.consultar(usuario); break;
                case 6: interfazUsuarios.consultarPerfil(usuario); break;
                case 7: interfazUsuarios.editarPerfil(usuario); break;
                case 8: interfazReportes.consultarHallazgos(usuario); break;
                case 9: interfazReportes.editarHallazgo(usuario); break;
                case 10: System.out.println("Sesión cerrada."); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 10);
    }
}
