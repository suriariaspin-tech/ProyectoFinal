# Reencuentra

Sistema de gestión y ubicación de objetos perdidos y encontrados en el campus universitario. Esta primera versión implementa los cuatro módulos de usuarios, reportes, coincidencias y custodia. No incluye reclamos, verificación de propiedad ni devolución.

## Requisitos y ejecución

- Java 8 o superior, con soporte para Swing.
- No se requieren bibliotecas externas ni base de datos.
- En IntelliJ IDEA, abra el proyecto y ejecute `interfaz.Main` desde `src/interfaz/Main.java`.
- Desde una terminal ubicada en la raíz, con el directorio `bin` del JDK agregado a `PATH`, puede compilar y ejecutar con:

```text
javac -encoding UTF-8 -d out src\modelo\*.java src\negocio\*.java src\interfaz\*.java
java -cp out interfaz.Main
```

Los datos se guardan en memoria durante la ejecución y se pierden al cerrar el programa.

## Cuenta de demostración

- Correo: `encargado@reencuentra.edu`
- Contraseña: `Encargado123`

Es una cuenta ficticia creada exclusivamente para pruebas. El registro público siempre crea cuentas `MIEMBRO`; los encargados no pueden autoasignarse ese rol.

## Paquetes y estructura

```text
ProyectoFinal/
├── README.md
└── src/
    ├── interfaz/
    │   ├── InterfazCoincidencias.java
    │   ├── InterfazCustodia.java
    │   ├── InterfazReportes.java
    │   ├── InterfazUsuarios.java
    │   ├── Main.java
    │   ├── MenuEncargado.java
    │   ├── MenuUsuario.java
    │   └── VentanaPrincipal.java
    ├── modelo/
    │   ├── Coincidencia.java
    │   ├── MovimientoCustodia.java
    │   ├── ObjetoEncontrado.java
    │   ├── ReporteHallazgo.java
    │   ├── ReportePerdida.java
    │   ├── Ubicacion.java
    │   └── Usuario.java
    └── negocio/
        ├── GestorCoincidencias.java
        ├── GestorCustodia.java
        ├── GestorReportesHallazgo.java
        ├── GestorReportesPerdida.java
        └── GestorUsuarios.java
```

### Responsabilidad de las clases

**modelo:** `Usuario` conserva perfil, credenciales, rol y estado; `ReportePerdida` y `ReporteHallazgo` representan avisos separados; `ObjetoEncontrado` representa el objeto recibido físicamente; `Ubicacion` registra capacidad, categorías admitidas y ocupación; `MovimientoCustodia` conserva cada recepción o traslado; `Coincidencia` relaciona dos reportes y su puntaje.

**negocio:** `GestorUsuarios` valida registro, inicio de sesión y perfil; `GestorReportesPerdida` administra las pérdidas; `GestorReportesHallazgo` administra avisos de hallazgo que permanecen pendientes hasta ser recibidos; `GestorCoincidencias` aplica las reglas de puntuación y ordenamiento; `GestorCustodia` administra ubicaciones, recepciones, capacidad y movimientos históricos.

**interfaz:** `Main` inicia Swing; `VentanaPrincipal` conserva instancias compartidas de los gestores y dirige la navegación; `MenuUsuario` y `MenuEncargado` restringen opciones por rol; `InterfazUsuarios`, `InterfazReportes`, `InterfazCoincidencias` e `InterfazCustodia` solicitan y muestran información con `JOptionPane`.

## Módulos implementados

1. **Usuarios:** registro de miembros, validación de correo e identificador únicos, contraseña mínima de ocho caracteres, inicio de sesión y consulta/edición limitada de perfil.
2. **Reportes:** creación independiente de pérdidas abiertas y hallazgos pendientes de recepción; validación de datos y fechas; edición del reporte propio según su estado.
3. **Coincidencias:** comparación por categoría y cronología; suma de marca (40), color (40) y lugar (20); consulta de resultados de 40 puntos o más en orden descendente. Un puntaje no confirma propiedad.
4. **Recepción y custodia:** recepción física por encargado en Garita, Biblioteca o Recepción, consulta de capacidad y categorías, traslado validado y conservación de historial.

## Limitaciones de esta versión

- No hay persistencia: usuarios, reportes y movimientos se reinician al cerrar la aplicación.
- La contraseña de demostración y las contraseñas registradas se conservan en memoria sin cifrado; esto es solo para una práctica académica, no para producción.
- Las capacidades iniciales de demostración (30 objetos por ubicación) están centralizadas en `GestorCustodia` y no representan capacidades oficiales de la UDLA.
- Las categorías iniciales admitidas son Accesorios, Electrónica, Ropa, Documentos y Otros.
- La fotografía es opcional y se almacena como una referencia/ruta de texto; no se carga ni almacena el archivo de imagen.
- No se implementa el módulo de reclamos, verificación de propiedad ni devolución.
