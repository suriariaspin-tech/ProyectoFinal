package modelo;

import java.util.ArrayList;

/** Representa un punto físico del campus donde pueden guardarse objetos recibidos. */
public class Ubicacion {
    /** Código único de la ubicación. */
    private String codigoUbicacion;
    /** Nombre visible: GARITA, BIBLIOTECA o RECEPCION. */
    private String nombre;
    /** Límite máximo configurable de objetos en este punto. */
    private int capacidadMaxima;
    /** Número de objetos que ocupan físicamente esta ubicación. */
    private int ocupacionActual;
    /** Categorías de objetos aceptadas por el punto. */
    private ArrayList<String> categoriasAdmitidas;
    /** Disponibilidad operativa de la ubicación. */
    private boolean habilitada;
    /** Objetos que físicamente están guardados aquí. */
    private ArrayList<ObjetoEncontrado> objetos;

    /** Crea una ubicación vacía con su capacidad y categorías permitidas. */
    public Ubicacion(String codigo, String nombre, int capacidadMaxima, ArrayList<String> categoriasAdmitidas) {
        this.codigoUbicacion = codigo; this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima > 0 ? capacidadMaxima : 1;
        this.ocupacionActual = 0;
        this.categoriasAdmitidas = categoriasAdmitidas == null
                ? new ArrayList<String>() : new ArrayList<String>(categoriasAdmitidas);
        this.habilitada = true;
        this.objetos = new ArrayList<ObjetoEncontrado>();
    }

    /** Devuelve la ocupación actual, actualizada al registrar entradas y salidas. */
    public int getOcupacionActual() { return ocupacionActual; }
    /** Calcula cuántos espacios quedan sin reservar. */
    public int getEspaciosDisponibles() { return capacidadMaxima - ocupacionActual; }
    /** Indica si queda al menos un espacio sin exceder la capacidad configurada. */
    public boolean tieneEspacio() { return ocupacionActual < capacidadMaxima; }
    /** Indica si la ubicación admite una categoría, ignorando mayúsculas y espacios externos. */
    public boolean admiteCategoria(String categoria) {
        if (categoria == null) return false;
        for (String admitida : categoriasAdmitidas) {
            if (admitida.trim().equalsIgnoreCase(categoria.trim())) return true;
        }
        return false;
    }

    /** Registra la entrada física de un objeto después de verificar capacidad y categoría. */
    public boolean registrarEntrada(ObjetoEncontrado objeto) {
        if (!validarEntrada(objeto)) return false;
        // No se agrega el objeto a dos ubicaciones si todavía apunta a otra ubicación física.
        if (objeto.getUbicacionActual() != null && objeto.getUbicacionActual() != this) return false;
        objetos.add(objeto);
        ocupacionActual++;
        return true;
    }

    /** Comprueba una entrada sin cambiar ocupación ni colecciones. */
    public boolean validarEntrada(ObjetoEncontrado objeto) {
        if (objeto == null || objeto.getReporteHallazgo() == null) return false;
        if (!habilitada) return false;
        if (!tieneEspacio()) return false;
        if (!admiteCategoria(objeto.getReporteHallazgo().getCategoria())) return false;
        if (objetos.contains(objeto)) return false;
        return true;
    }

    /** Registra la salida física del objeto sin modificar el historial de movimientos. */
    public boolean registrarSalida(ObjetoEncontrado objeto) {
        if (!validarSalida(objeto)) return false;
        objetos.remove(objeto);
        ocupacionActual--;
        objeto.actualizarUbicacion(null);
        return true;
    }

    /** Comprueba una salida sin cambiar ocupación ni colecciones. */
    public boolean validarSalida(ObjetoEncontrado objeto) {
        return objeto != null && objetos.contains(objeto);
    }

    /** Devuelve el código único de la ubicación. */
    public String getCodigoUbicacion() { return codigoUbicacion; }
    /** Actualiza el código único de la ubicación. */
    public void setCodigoUbicacion(String codigoUbicacion) { this.codigoUbicacion = codigoUbicacion; }
    /** Devuelve el nombre de la ubicación. */
    public String getNombre() { return nombre; }
    /** Actualiza el nombre de la ubicación. */
    public void setNombre(String nombre) { this.nombre = nombre; }
    /** Devuelve el máximo de objetos permitido. */
    public int getCapacidadMaxima() { return capacidadMaxima; }
    /** Cambia la capacidad solo si no queda por debajo de la ocupación actual. */
    public void setCapacidadMaxima(int capacidadMaxima) {
        if (capacidadMaxima >= ocupacionActual && capacidadMaxima > 0) this.capacidadMaxima = capacidadMaxima;
    }
    /** Devuelve una copia de las categorías que acepta la ubicación. */
    public ArrayList<String> getCategoriasAdmitidas() { return new ArrayList<String>(categoriasAdmitidas); }
    /** Actualiza las categorías aceptadas usando una copia de la lista recibida. */
    public void setCategoriasAdmitidas(ArrayList<String> categoriasAdmitidas) {
        if (categoriasAdmitidas != null) this.categoriasAdmitidas = new ArrayList<String>(categoriasAdmitidas);
    }
    /** Devuelve si la ubicación está disponible para operaciones. */
    public boolean isHabilitada() { return habilitada; }
    /** Cambia la disponibilidad de la ubicación. */
    public void setHabilitada(boolean habilitada) { this.habilitada = habilitada; }
    /** Devuelve una copia para impedir que se modifique la ocupación sin registrar una entrada o salida. */
    public ArrayList<ObjetoEncontrado> getObjetos() { return new ArrayList<ObjetoEncontrado>(objetos); }
}
