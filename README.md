# Reencuentra

## Descripción y objetivo

Reencuentra es una aplicación de consola académica para registrar objetos perdidos y encontrados en el campus, identificar posibles coincidencias y llevar el control de los objetos recibidos físicamente y bajo custodia. Busca facilitar el encuentro entre los reportes de una pérdida y los hallazgos comunicados.

El problema que atiende es la falta de un registro compartido que permita consultar reportes, detectar similitudes y conocer dónde se resguarda un objeto encontrado.

## Módulos implementados

1. **Registro y gestión de usuarios:** crea cuentas de miembros o encargados, autentica sesiones y permite editar algunos datos del perfil.
2. **Reportes de pérdida y hallazgo:** registra, consulta y edita los reportes propios mientras estén habilitados para edición.
3. **Coincidencias:** compara categorías, fechas, marca, color y lugar; muestra similitudes desde 40 puntos, ordenadas por puntaje. Una coincidencia es orientativa y no confirma la propiedad de un objeto.
4. **Recepción y custodia:** permite a un encargado activo confirmar recepciones, consultar ubicaciones y trasladar objetos con motivo e historial de movimientos.

El módulo de reclamos y devoluciones no forma parte de esta versión.

## Roles

- **MIEMBRO:** puede reportar objetos perdidos o encontrados, consultar y editar sus propios reportes pendientes y consultar posibles coincidencias.
- **ENCARGADO:** puede consultar los reportes y, si su cuenta está activa, confirmar recepciones y trasladar objetos bajo custodia.

## Categorías y ubicaciones

Las categorías válidas son **Accesorios**, **Electrónica**, **Ropa**, **Documentos** y **Otros**. Se eligen desde un menú y los gestores también las validan y almacenan con esos nombres.

Las ubicaciones configuradas son **Garita**, **Biblioteca** y **Recepción**. Sus capacidades y categorías aceptadas están configuradas para demostración.

## Requisitos

- JDK 8 o posterior.
- IntelliJ IDEA o una consola con `javac` y `java` disponibles.

## Abrir y ejecutar en IntelliJ IDEA

1. Abre IntelliJ IDEA y selecciona **Open** para abrir la carpeta raíz del proyecto.
2. Configura un JDK 8 o posterior en **Project Structure > Project SDK**.
3. Abre `src/interfaz/Main.java` y ejecuta el método `main`.

## Compilar y ejecutar desde consola

Desde PowerShell, en la carpeta raíz del proyecto:

```powershell
New-Item -ItemType Directory -Force out
$fuentes = Get-ChildItem -Recurse src -Filter *.java | ForEach-Object FullName
javac -encoding UTF-8 -d out $fuentes
java -cp out interfaz.Main
```

Para compilar y ejecutar las pruebas automatizadas incluidas:

```powershell
New-Item -ItemType Directory -Force out
$fuentes = Get-ChildItem -Recurse src -Filter *.java | ForEach-Object FullName
$pruebas = Get-ChildItem -Recurse test -Filter *.java | ForEach-Object FullName
javac -encoding UTF-8 -d out $fuentes $pruebas
java -cp out ReencuentraPruebas
```

## Credenciales de demostración

- **Encargado:** `encargado@reencuentra.edu`
- **Contraseña:** `Encargado123`
- **Código de autorización para registrar encargados:** `REENCUENTRA-ENC-2026`

El código de autorización y las contraseñas en texto son mecanismos exclusivamente académicos y de demostración; no son seguros para producción.

## Funcionalidades principales

- Registro de miembros y registro de encargados sujeto al código de autorización.
- Validación de ID Banner y correo únicos, datos personales obligatorios y contraseña de al menos ocho caracteres.
- Inicio de sesión y acceso al menú según el rol.
- Registro y edición de reportes con categoría y fecha validadas.
- Comparación por categoría y orden de fechas; puntajes de 40 por marca, 40 por color y 20 por lugar. Los datos desconocidos no aportan puntos.
- Recepción física, control de capacidad y estado de las ubicaciones, traslados e historial.

## Limitaciones actuales

- Los datos se almacenan temporalmente en memoria mediante `ArrayList`; se pierden al cerrar el programa.
- No existe una base de datos.
- Las fotografías se manejan actualmente como rutas de texto; el sistema no carga ni almacena archivos de imagen.
- Las capacidades de las ubicaciones son valores de demostración y no representan capacidades oficiales de la universidad.
- El código de autorización y las contraseñas son mecanismos de demostración académica, no controles de seguridad para producción.
- Las contraseñas se conservan en texto durante la ejecución; no se implementa persistencia ni gestión segura de credenciales.
