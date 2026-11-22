import interfaz.Main;
import interfaz.InterfazUsuarios;
import interfaz.Validacion;
import modelo.Coincidencia;
import modelo.ObjetoEncontrado;
import modelo.ReporteHallazgo;
import modelo.ReportePerdida;
import modelo.Ubicacion;
import modelo.Usuario;
import negocio.GestorCoincidencias;
import negocio.GestorCustodia;
import negocio.GestorReportesHallazgo;
import negocio.GestorReportesPerdida;
import negocio.GestorUsuarios;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Pruebas sencillas de los cuatro módulos, ejecutables sin bibliotecas externas. */
public class ReencuentraPruebas {
    private static int pruebasEjecutadas;

    public static void main(String[] args) throws Exception {
        String entradas = "0\n1\n2\n3\n4\n5\n"
                + "2\nEncargado Rechazado\nUI-ENC-BAD\nrechazado@reencuentra.edu\n0000000000\n"
                + "Encargado123\nCODIGO-INCORRECTO\n"
                + "1\nMiembro Interfaz\nUI-MEM-001\nui-member@reencuentra.edu\n1111111111\nMiembro123\n"
                + "2\nEncargado Interfaz\nUI-ENC-001\nui-enc@reencuentra.edu\n2222222222\n"
                + "Encargado123\nREENCUENTRA-ENC-2026\n"
                + "1\n1\nMiembro Menú\nMENU-MEM-001\nmenu-member@reencuentra.edu\n3333333333\nMiembro123\n"
                + "1\n2\nEncargado Menú\nMENU-ENC-001\nmenu-enc@reencuentra.edu\n4444444444\n"
                + "Encargado123\nREENCUENTRA-ENC-2026\n"
                + "2\nmenu-member@reencuentra.edu\nMiembro123\n"
                + "1\n1\nBolso\nNegro\nBolso perdido\n\n2020-01-01 10:00\nBiblioteca\n\n"
                + "2\n1\nBolso\nNegro\nBolso hallado\n\n2020-01-02 10:00\nBiblioteca\n\n"
                + "3\n4\nPER-1\n1\nBolso\nNegro\nBolso perdido actualizado\n\n"
                + "2020-01-01 10:00\nBiblioteca\n\n5\n6\n7\nMiembro Menú Actualizado\n"
                + "menu-member-actualizado@reencuentra.edu\n3333333334\n8\n"
                + "9\nHAL-1\n1\nBolso\nNegro\nBolso hallado actualizado\n\n"
                + "2020-01-02 10:00\nBiblioteca\n\n10\n"
                + "2\nmenu-enc@reencuentra.edu\nEncargado123\n5\n1\nHAL-1\n2\n2\n"
                + "4\nHAL-1\n3\nHAL-1\n1\nTraslado de prueba\n4\nHAL-1\n6\n"
                + "2\nencargado@reencuentra.edu\nEncargado123\n6\n3\n";
        System.setIn(new ByteArrayInputStream(entradas.getBytes("UTF-8")));
        PrintStream salidaOriginal = System.out;
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        System.setOut(new PrintStream(salida, true, "UTF-8"));

        try {
            probarSeleccionCategorias();
            probarRegistroInterfaz();
            probarGestores();
            Main.main(new String[0]);

            String textoSalida = salida.toString("UTF-8");
            comprobar(textoSalida.contains("El código de autorización del encargado es incorrecto."),
                    "La interfaz explica el rechazo del código incorrecto.");
            comprobar(textoSalida.contains("Menú de miembro: Miembro Menú"),
                    "El inicio de sesión lleva al menú de miembro.");
            comprobar(textoSalida.contains("Menú de encargado: Encargado Menú"),
                    "El inicio de sesión lleva al menú de encargado.");
            comprobar(textoSalida.contains("Menú de encargado: Encargado de demostración"),
                    "La cuenta de encargado de demostración conserva su acceso.");
            comprobar(textoSalida.contains("Puntaje: 100"),
                    "El flujo por menú muestra la coincidencia calculada.");
            comprobar(textoSalida.contains("no demuestran propiedad"),
                    "La coincidencia no se presenta como confirmación de propiedad.");
            comprobar(textoSalida.contains("Recepción confirmada."),
                    "El flujo por menú permite confirmar una recepción.");
            comprobar(textoSalida.contains("Traslado registrado correctamente."),
                    "El flujo por menú permite trasladar el objeto.");
            comprobar(textoSalida.contains("Motivo: Traslado de prueba"),
                    "El flujo por menú conserva el motivo en el historial.");
        } finally {
            System.setOut(salidaOriginal);
        }
        System.out.println("PRUEBAS OK: " + pruebasEjecutadas);
    }

    private static void probarSeleccionCategorias() {
        String[] categorias = {"Accesorios", "Electrónica", "Ropa", "Documentos", "Otros"};
        for (String categoria : categorias) {
            comprobar(categoria.equals(Validacion.seleccionarCategoria()),
                    "La selección devuelve la categoría oficial " + categoria + ".");
        }
    }

    private static void probarRegistroInterfaz() {
        GestorUsuarios gestor = new GestorUsuarios();
        InterfazUsuarios interfaz = new InterfazUsuarios(gestor);
        int cuentasIniciales = gestor.getUsuarios().size();
        interfaz.registrar();
        comprobar(gestor.getUsuarios().size() == cuentasIniciales,
                "Un código incorrecto no crea una cuenta de encargado.");
        interfaz.registrar();
        comprobar("MIEMBRO".equals(gestor.buscarPorCorreo("ui-member@reencuentra.edu").getRol()),
                "La interfaz registra una cuenta como miembro.");
        interfaz.registrar();
        comprobar("ENCARGADO".equals(gestor.buscarPorCorreo("ui-enc@reencuentra.edu").getRol()),
                "La interfaz registra un encargado autorizado.");
    }

    private static void probarGestores() {
        GestorUsuarios usuarios = new GestorUsuarios();
        Usuario miembro = usuarios.registrarUsuario("Persona Miembro", "BANNER-100",
                "miembro@reencuentra.edu", "5550001000", "Miembro123");
        comprobar(miembro != null && "MIEMBRO".equals(miembro.getRol()), "Se registra un miembro.");
        comprobar(usuarios.iniciarSesion("miembro@reencuentra.edu", "Miembro123") == miembro,
                "El miembro puede iniciar sesión.");
        comprobar(usuarios.iniciarSesion("miembro@reencuentra.edu", "incorrecta") == null,
                "Se rechaza una contraseña incorrecta.");

        int cuentasIniciales = usuarios.getUsuarios().size();
        Usuario noAutorizado = usuarios.registrarUsuario("Encargado No Autorizado", "BANNER-101",
                "no-autorizado@reencuentra.edu", "5550001001", "Encargado123",
                "ENCARGADO", "incorrecto");
        comprobar(noAutorizado == null && usuarios.getUsuarios().size() == cuentasIniciales,
                "El gestor rechaza el código incorrecto sin crear una cuenta.");
        comprobar(usuarios.registrarUsuario("Rol inválido", "BANNER-102", "rol@reencuentra.edu",
                "5550001002", "Password123", "ADMIN", "REENCUENTRA-ENC-2026") == null,
                "El gestor rechaza roles diferentes de MIEMBRO y ENCARGADO.");
        Usuario encargado = usuarios.registrarUsuario("Persona Encargada", "BANNER-103",
                "encargado-prueba@reencuentra.edu", "5550001003", "Encargado123",
                "ENCARGADO", "REENCUENTRA-ENC-2026");
        comprobar(encargado != null && "ENCARGADO".equals(encargado.getRol()),
                "El gestor registra un encargado con autorización.");
        comprobar(usuarios.iniciarSesion("encargado-prueba@reencuentra.edu", "Encargado123") == encargado,
                "El encargado autorizado puede iniciar sesión.");
        comprobar(usuarios.iniciarSesion("encargado@reencuentra.edu", "Encargado123") != null,
                "La cuenta de encargado demostrativa conserva sus credenciales.");
        comprobar(usuarios.registrarUsuario("Correo repetido", "BANNER-104",
                "MIEMBRO@REENCUENTRA.EDU", "5550001004", "Password123") == null,
                "Se rechaza el correo duplicado sin distinguir mayúsculas.");
        comprobar(usuarios.registrarUsuario("ID repetido", "banner-100",
                "otro@reencuentra.edu", "5550001005", "Password123") == null,
                "Se rechaza el ID Banner duplicado sin distinguir mayúsculas.");
        comprobar(usuarios.registrarUsuario("", "BANNER-105", "vacio@reencuentra.edu",
                "5550001006", "Password123") == null, "Se rechazan los datos personales obligatorios vacíos.");
        comprobar(usuarios.registrarUsuario("Contraseña corta", "BANNER-106",
                "corta@reencuentra.edu", "5550001007", "corta") == null,
                "Se rechaza una contraseña menor de ocho caracteres.");
        String idBannerAnterior = miembro.getIdBanner();
        String rolAnterior = miembro.getRol();
        comprobar(usuarios.editarPerfil(miembro, "Nombre Actualizado",
                "miembro-actualizado@reencuentra.edu", "5550001999"),
                "Se pueden editar los datos permitidos del perfil.");
        comprobar(idBannerAnterior.equals(miembro.getIdBanner()) && rolAnterior.equals(miembro.getRol()),
                "La edición de perfil no cambia el ID Banner ni el rol.");
        comprobar(!usuarios.editarPerfil(miembro, "Nombre", "encargado@reencuentra.edu", "5550001998"),
                "Se rechaza usar el correo de otra cuenta.");

        probarReportesYCoincidencias(miembro, encargado, usuarios);
        probarCustodia(miembro, encargado);
    }

    private static void probarReportesYCoincidencias(Usuario miembro, Usuario encargado,
                                                      GestorUsuarios usuarios) {
        GestorReportesPerdida perdidas = new GestorReportesPerdida();
        GestorReportesHallazgo hallazgos = new GestorReportesHallazgo();
        LocalDateTime fechaPerdida = LocalDateTime.now().minusDays(2);
        ReportePerdida perdida = perdidas.registrar(miembro, " ACCESORIOS ", "MochilaCo",
                "Negro", "Mochila negra", "", fechaPerdida, "Biblioteca", "");
        comprobar(perdida != null && "Accesorios".equals(perdida.getCategoria()),
                "La pérdida se registra con categoría oficial normalizada.");
        comprobar(perdidas.registrar(encargado, "Accesorios", "Marca", "Negro",
                "No permitido", "", fechaPerdida, "Biblioteca", "") == null,
                "Un encargado no puede registrar una pérdida como miembro.");
        comprobar(perdidas.registrar(miembro, "Muebles", "Marca", "Negro",
                "Categoría inválida", "", fechaPerdida, "Biblioteca", "") == null,
                "Se rechaza una categoría inválida en pérdidas.");
        comprobar(perdidas.registrar(miembro, "Ropa", "Marca", "Azul",
                "Fecha futura", "", LocalDateTime.now().plusDays(1), "Biblioteca", "") == null,
                "Se rechaza una fecha futura en pérdidas.");
        comprobar(perdidas.consultarPorPropietario(miembro).size() == 1
                        && perdidas.consultarPorPropietario(encargado).isEmpty(),
                "La consulta de pérdidas limita los resultados al propietario.");
        comprobar(!perdidas.editar(perdida.getCodigo(), usuarios.buscarPorCorreo("encargado-prueba@reencuentra.edu"),
                        "Ropa", "", "Negro", "Edición ajena", "", fechaPerdida, "Biblioteca", ""),
                "Se rechaza editar la pérdida de otra cuenta.");
        comprobar(!perdidas.editar(perdida.getCodigo(), miembro, "Muebles", "", "Negro",
                        "Edición inválida", "", fechaPerdida, "Biblioteca", ""),
                "Se rechaza una categoría inválida al editar una pérdida.");
        comprobar("Accesorios".equals(perdida.getCategoria()),
                "La edición rechazada no cambia la categoría guardada.");
        comprobar(perdidas.editar(perdida.getCodigo(), miembro, " accesorios ", "MochilaCo",
                        "Negro", "Mochila negra", "", fechaPerdida, "Biblioteca", "")
                        && "Accesorios".equals(perdida.getCategoria()),
                "La edición normaliza una categoría válida.");

        ReporteHallazgo exacto = registrarHallazgo(hallazgos, miembro, "ACCESORIOS", "MochilaCo",
                "Negro", "Mochila hallada", fechaPerdida.plusHours(1), "Biblioteca");
        ReporteHallazgo soloMarca = registrarHallazgo(hallazgos, miembro, "Accesorios", "MochilaCo",
                "Rojo", "Coincide marca", fechaPerdida.plusHours(2), "Garita");
        ReporteHallazgo soloColor = registrarHallazgo(hallazgos, miembro, "Accesorios", "",
                "Negro", "Marca desconocida", fechaPerdida.plusHours(3), "Garita");
        ReporteHallazgo soloLugar = registrarHallazgo(hallazgos, miembro, "Accesorios", "",
                "Rojo", "Coincide lugar", fechaPerdida.plusHours(4), "Biblioteca");
        ReporteHallazgo anterior = registrarHallazgo(hallazgos, miembro, "Accesorios", "MochilaCo",
                "Negro", "Hallazgo anterior", fechaPerdida.minusHours(1), "Biblioteca");
        ReporteHallazgo otraCategoria = registrarHallazgo(hallazgos, miembro, "Ropa", "MochilaCo",
                "Negro", "Categoría diferente", fechaPerdida.plusHours(5), "Biblioteca");
        comprobar(registrarHallazgo(hallazgos, encargado, "Accesorios", "", "Negro",
                        "Encargado sin permiso", fechaPerdida.plusHours(1), "Biblioteca") == null,
                "Un encargado no puede registrar hallazgos como miembro.");
        comprobar(hallazgos.registrar(miembro, "Inválida", "", "Negro", "Categoría inválida",
                        "", fechaPerdida.plusHours(1), "Biblioteca", "") == null,
                "Se rechaza una categoría inválida en hallazgos.");
        comprobar(hallazgos.registrar(miembro, "Otros", "", "Negro", "Fecha futura",
                        "", LocalDateTime.now().plusDays(1), "Biblioteca", "") == null,
                "Se rechaza una fecha futura en hallazgos.");
        comprobar(hallazgos.editar(exacto.getCodigo(), miembro, "Inválida", "", "Negro",
                        "Edición inválida", "", fechaPerdida.plusHours(1), "Biblioteca", "") == false,
                "Se rechaza una categoría inválida al editar un hallazgo.");
        comprobar(hallazgos.editar(exacto.getCodigo(), miembro, " accesorios ", "MochilaCo",
                        "Negro", "Mochila hallada", "", fechaPerdida.plusHours(1), "Biblioteca", "")
                        && "Accesorios".equals(exacto.getCategoria()),
                "La edición normaliza una categoría válida en un hallazgo.");
        comprobar(miembro.getReportesHallazgo().size() == 6
                        && encargado.getReportesHallazgo().isEmpty(),
                "La consulta de hallazgos limita los resultados al reportante.");

        GestorCoincidencias coincidencias = new GestorCoincidencias();
        comprobar(coincidencias.calcularPuntaje(perdida, exacto) == 100,
                "La coincidencia suma 40 puntos por marca, 40 por color y 20 por lugar.");
        comprobar(coincidencias.calcularPuntaje(perdida, soloMarca) == 40,
                "La coincidencia de marca aporta 40 puntos.");
        comprobar(coincidencias.calcularPuntaje(perdida, soloColor) == 40,
                "Una marca desconocida no suma puntos y el color aporta 40.");
        comprobar(coincidencias.calcularPuntaje(perdida, soloLugar) == 20,
                "La coincidencia de lugar aporta 20 puntos.");
        comprobar(coincidencias.calcularPuntaje(perdida, anterior) == 0,
                "Se descartan hallazgos anteriores a la pérdida.");
        comprobar(coincidencias.calcularPuntaje(perdida, otraCategoria) == 0,
                "Se exigen categorías iguales.");
        ArrayList<ReportePerdida> listaPerdidas = perdidas.getReportes();
        ArrayList<ReporteHallazgo> listaHallazgos = hallazgos.getReportes();
        coincidencias.recalcular(listaPerdidas, listaHallazgos);
        ArrayList<Coincidencia> listaCoincidencias = coincidencias.getCoincidencias();
        comprobar(listaCoincidencias.size() == 3, "Solo se presentan coincidencias desde 40 puntos.");
        comprobar(listaCoincidencias.get(0).getPuntaje() == 100
                        && listaCoincidencias.get(1).getPuntaje() == 40
                        && listaCoincidencias.get(2).getPuntaje() == 40,
                "Las coincidencias se ordenan de mayor a menor puntaje.");
        ReportePerdida cerrada = perdidas.registrar(miembro, "Ropa", "", "Azul",
                "Pérdida cerrada", "", fechaPerdida, "Garita", "");
        cerrada.setEstado("CERRADO");
        comprobar(!perdidas.editar(cerrada.getCodigo(), miembro, "Ropa", "", "Azul",
                        "Edición cerrada", "", fechaPerdida, "Garita", ""),
                "No se puede editar una pérdida cerrada.");
    }

    private static ReporteHallazgo registrarHallazgo(GestorReportesHallazgo gestor, Usuario usuario,
                                                      String categoria, String marca, String color,
                                                      String descripcion, LocalDateTime fecha, String lugar) {
        return gestor.registrar(usuario, categoria, marca, color, descripcion, "",
                fecha, lugar, "");
    }

    private static void probarCustodia(Usuario miembro, Usuario encargado) {
        GestorReportesHallazgo hallazgos = new GestorReportesHallazgo();
        GestorCustodia custodia = new GestorCustodia();
        LocalDateTime fecha = LocalDateTime.now().minusHours(1);
        ReporteHallazgo inicial = registrarHallazgo(hallazgos, miembro, "Documentos", "",
                "Azul", "Documento encontrado", fecha, "Biblioteca");
        ReporteHallazgo otro = registrarHallazgo(hallazgos, miembro, "Documentos", "",
                "Rojo", "Otro documento", fecha, "Biblioteca");
        ReporteHallazgo categoriaNoAdmitida = registrarHallazgo(hallazgos, miembro, "Ropa", "",
                "Verde", "Ropa encontrada", fecha, "Garita");
        ReporteHallazgo ubicacionDeshabilitada = registrarHallazgo(hallazgos, miembro, "Documentos", "",
                "Negro", "Objeto para ubicación deshabilitada", fecha, "Garita");

        comprobar(custodia.registrarRecepcion(inicial.getCodigo(), "GARITA", miembro, hallazgos) == null,
                "Un miembro no puede confirmar una recepción.");
        encargado.setEstadoCuenta("INACTIVA");
        comprobar(custodia.registrarRecepcion(inicial.getCodigo(), "GARITA", encargado, hallazgos) == null,
                "Un encargado inactivo no puede confirmar una recepción.");
        encargado.setEstadoCuenta("ACTIVA");

        Ubicacion garita = custodia.buscarUbicacion("GARITA");
        Ubicacion biblioteca = custodia.buscarUbicacion("BIBLIOTECA");
        Ubicacion recepcion = custodia.buscarUbicacion("RECEPCION");
        biblioteca.setCapacidadMaxima(1);
        comprobar(custodia.registrarRecepcion(otro.getCodigo(), "BIBLIOTECA", encargado, hallazgos) != null,
                "Un encargado activo puede confirmar una recepción física.");
        comprobar(otro.getObjetoEncontrado() != null
                        && "RECIBIDO".equals(otro.getEstadoRecepcion())
                        && otro.getObjetoEncontrado().getUbicacionActual() == biblioteca,
                "La recepción actualiza el hallazgo y la ubicación del objeto.");
        comprobar(!hallazgos.editar(otro.getCodigo(), miembro, "Documentos", "", "Rojo",
                        "Edición posterior a recepción", "", fecha, "Biblioteca", ""),
                "No se puede editar un hallazgo después de recibirlo físicamente.");
        comprobar(custodia.consultarHistorial(otro.getCodigo()).size() == 1
                        && custodia.consultarHistorial(otro.getCodigo()).get(0).getUbicacionOrigen() == null,
                "La recepción inicial se conserva en el historial.");
        comprobar(custodia.registrarRecepcion(otro.getCodigo(), "GARITA", encargado, hallazgos) == null,
                "No se recibe dos veces el mismo objeto.");

        comprobar(custodia.registrarRecepcion(inicial.getCodigo(), "BIBLIOTECA", encargado, hallazgos) == null,
                "No se recibe un objeto en una ubicación llena.");
        biblioteca.setCapacidadMaxima(30);
        biblioteca.setHabilitada(false);
        comprobar(custodia.registrarRecepcion(inicial.getCodigo(), "BIBLIOTECA", encargado, hallazgos) == null,
                "No se recibe un objeto en una ubicación inhabilitada.");
        comprobar(!custodia.trasladar(otro.getCodigo(), "BIBLIOTECA", "Mismo lugar", encargado),
                "No se traslada un objeto a su ubicación actual.");
        comprobar(!custodia.trasladar(otro.getCodigo(), "RECEPCION", "   ", encargado),
                "Todo traslado exige un motivo.");
        comprobar(!custodia.trasladar(otro.getCodigo(), "RECEPCION", "Sin permisos", miembro),
                "Un miembro no puede trasladar objetos.");
        encargado.setEstadoCuenta("INACTIVA");
        comprobar(!custodia.trasladar(otro.getCodigo(), "RECEPCION", "Encargado inactivo", encargado),
                "Un encargado inactivo no puede trasladar objetos.");
        encargado.setEstadoCuenta("ACTIVA");
        garita.setHabilitada(false);
        comprobar(!custodia.trasladar(otro.getCodigo(), "GARITA", "Destino deshabilitado", encargado),
                "No se traslada a una ubicación inhabilitada.");
        comprobar(biblioteca.getOcupacionActual() == 1 && recepcion.getOcupacionActual() == 0
                        && otro.getObjetoEncontrado().getUbicacionActual() == biblioteca
                        && custodia.consultarHistorial(otro.getCodigo()).size() == 1,
                "Los traslados rechazados conservan ubicación, ocupación e historial.");
        garita.setHabilitada(true);
        biblioteca.setHabilitada(true);

        recepcion.setCategoriasAdmitidas(new ArrayList<String>());
        comprobar(custodia.registrarRecepcion(categoriaNoAdmitida.getCodigo(), "RECEPCION",
                        encargado, hallazgos) == null,
                "No se recibe una categoría que la ubicación no admite.");
        comprobar(!custodia.trasladar(otro.getCodigo(), "RECEPCION", "Categoría no admitida", encargado),
                "No se traslada a una ubicación que no admite la categoría.");
        recepcion.setCategoriasAdmitidas(categoriasValidas());

        comprobar(custodia.registrarRecepcion(inicial.getCodigo(), "GARITA", encargado, hallazgos) != null,
                "Se puede recibir en una ubicación habilitada y compatible.");
        garita.setCapacidadMaxima(1);
        comprobar(custodia.registrarRecepcion(ubicacionDeshabilitada.getCodigo(), "GARITA",
                        encargado, hallazgos) == null,
                "No se recibe un objeto en una ubicación llena.");
        garita.setCapacidadMaxima(30);
        biblioteca.setCapacidadMaxima(1);
        comprobar(!custodia.trasladar(inicial.getCodigo(), "BIBLIOTECA", "Destino lleno", encargado),
                "No se traslada a una ubicación llena.");
        comprobar(custodia.trasladar(inicial.getCodigo(), "RECEPCION", "Cambio de ubicación", encargado),
                "Un encargado activo puede trasladar a una ubicación compatible.");
        comprobar(inicial.getObjetoEncontrado().getUbicacionActual() == recepcion
                        && garita.getOcupacionActual() == 0 && recepcion.getOcupacionActual() == 1,
                "El traslado actualiza ubicación y ocupación de origen y destino.");
        comprobar(custodia.consultarHistorial(inicial.getCodigo()).size() == 2
                        && custodia.getMovimientos().size() == 3
                        && "Cambio de ubicación".equals(custodia.consultarHistorial(inicial.getCodigo())
                        .get(1).getMotivo()),
                "El historial conserva la recepción inicial y el motivo del traslado.");
        comprobar(!custodia.trasladar("NO-EXISTE", "GARITA", "Objeto inexistente", encargado),
                "Se rechaza trasladar un objeto inexistente.");
    }

    private static ArrayList<String> categoriasValidas() {
        ArrayList<String> categorias = new ArrayList<String>();
        categorias.add("Accesorios");
        categorias.add("Electrónica");
        categorias.add("Ropa");
        categorias.add("Documentos");
        categorias.add("Otros");
        return categorias;
    }

    private static void comprobar(boolean condicion, String mensaje) {
        pruebasEjecutadas++;
        if (!condicion) throw new AssertionError(mensaje);
    }
}
