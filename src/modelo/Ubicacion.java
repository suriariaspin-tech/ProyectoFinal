package modelo;

import java.util.ArrayList;

/** Representa un punto físico del campus donde pueden guardarse objetos recibidos. */
public class Ubicacion {
    /** Código único de la ubicación. */
    private String codigo;
    /** Nombre visible: GARITA, BIBLIOTECA o RECEPCION. */
    private String nombre;
    /** Límite máximo configurable de objetos en este punto. */
    private int capacidadMaxima;
    /** Categorías de objetos aceptadas por el punto. */
    private ArrayList<String> categoriasAdmitidas;
    /** Disponibilidad operativa de la ubicación. */
    private boolean habilitada;
    /** Objetos que físicamente están guardados aquí. */
    private ArrayList<ObjetoEncontrado> objetos;

    /** Crea una ubicación vacía con su capacidad y categorías permitidas. */
    public Ubicacion(String codigo, String nombre, int capacidadMaxima, ArrayList<String> categoriasAdmitidas) {
        this.codigo = codigo; this.nombre = nombre; this.capacidadMaxima = capacidadMaxima;
        this.categoriasAdmitidas = categoriasAdmitidas; this.habilitada = true;
        this.objetos = new ArrayList<ObjetoEncontrado>();
    }

    /** Calcula la ocupación a partir de los objetos realmente asociados a esta ubicación. */
    public int getOcupacionActual() { return objetos.size(); }
    /** Calcula cuántos espacios quedan sin reservar. */
    public int getEspaciosDisponibles() { return capacidadMaxima - objetos.size(); }
    /** Indica si la ubicación admite una categoría, ignorando mayúsculas y espacios externos. */
    public boolean admiteCategoria(String categoria) {
        if (categoria == null) return false;
        for (String admitida : categoriasAdmitidas) {
            if (admitida.trim().equalsIgnoreCase(categoria.trim())) return true;
        }
        return false;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(int capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }
    public ArrayList<String> getCategoriasAdmitidas() { return categoriasAdmitidas; }
    public void setCategoriasAdmitidas(ArrayList<String> categoriasAdmitidas) { this.categoriasAdmitidas = categoriasAdmitidas; }
    public boolean isHabilitada() { return habilitada; }
    public void setHabilitada(boolean habilitada) { this.habilitada = habilitada; }
    public ArrayList<ObjetoEncontrado> getObjetos() { return objetos; }
    public void setObjetos(ArrayList<ObjetoEncontrado> objetos) { this.objetos = objetos; }
}
