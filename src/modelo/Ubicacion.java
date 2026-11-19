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
        this.codigoUbicacion = codigo; this.nombre = nombre; this.capacidadMaxima = capacidadMaxima;
        this.ocupacionActual = 0;
        this.categoriasAdmitidas = categoriasAdmitidas; this.habilitada = true;
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
    public void registrarEntrada(ObjetoEncontrado objeto) {
        validarEntrada(objeto);
        objetos.add(objeto);
        ocupacionActual++;
    }

    /** Comprueba una entrada sin cambiar ocupación ni colecciones. */
    public void validarEntrada(ObjetoEncontrado objeto) {
        if (objeto == null) throw new IllegalArgumentException("El objeto es obligatorio.");
        if (!tieneEspacio()) throw new IllegalStateException("La ubicación alcanzó su capacidad máxima.");
        if (!admiteCategoria(objeto.getReporteHallazgo().getCategoria()))
            throw new IllegalArgumentException("La ubicación no admite la categoría del objeto.");
        if (objetos.contains(objeto)) throw new IllegalStateException("El objeto ya está registrado en esta ubicación.");
    }

    /** Registra la salida física del objeto sin modificar el historial de movimientos. */
    public void registrarSalida(ObjetoEncontrado objeto) {
        validarSalida(objeto);
        objetos.remove(objeto);
        ocupacionActual--;
    }

    /** Comprueba una salida sin cambiar ocupación ni colecciones. */
    public void validarSalida(ObjetoEncontrado objeto) {
        if (objeto == null || !objetos.contains(objeto))
            throw new IllegalStateException("El objeto no está registrado en esta ubicación.");
    }

    public String getCodigoUbicacion() { return codigoUbicacion; }
    public void setCodigoUbicacion(String codigoUbicacion) { this.codigoUbicacion = codigoUbicacion; }
    /** Alias conservado para las interfaces y gestores existentes. */
    public String getCodigo() { return codigoUbicacion; }
    public void setCodigo(String codigo) { this.codigoUbicacion = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(int capacidadMaxima) {
        if (capacidadMaxima < ocupacionActual) throw new IllegalArgumentException("La capacidad no puede ser menor que la ocupación actual.");
        this.capacidadMaxima = capacidadMaxima;
    }
    public ArrayList<String> getCategoriasAdmitidas() { return categoriasAdmitidas; }
    public void setCategoriasAdmitidas(ArrayList<String> categoriasAdmitidas) { this.categoriasAdmitidas = categoriasAdmitidas; }
    public boolean isHabilitada() { return habilitada; }
    public void setHabilitada(boolean habilitada) { this.habilitada = habilitada; }
    /** Devuelve una copia para impedir que se modifique la ocupación sin registrar una entrada o salida. */
    public ArrayList<ObjetoEncontrado> getObjetos() { return new ArrayList<ObjetoEncontrado>(objetos); }
}
