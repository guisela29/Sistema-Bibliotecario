package biblioteca;

import java.util.ArrayList;

public class Usuario {
    // Atributos básicos
    private String nombre;
    private String codigo;
    private String email;
    private ArrayList<String> librosActivos; // Solo guardamos códigos de libros

    // Constructor
    public Usuario(String nombre, String codigo, String email) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.email = email;
        this.librosActivos = new ArrayList<>();
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getLibrosActivos() {
        return librosActivos;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Métodos para manejar libros
    public boolean puedePrestarLibro() {
        return librosActivos.size() < 3; // Máximo 3 libros
    }

    public void agregarLibro(String codigoLibro) {
        if (puedePrestarLibro()) {
            librosActivos.add(codigoLibro);
        }
    }

    public void devolverLibro(String codigoLibro) {
        librosActivos.remove(codigoLibro);
    }

    public int getCantidadLibros() {
        return librosActivos.size();
    }

    // Método toString para mostrar información
    @Override
    public String toString() {
        return "👤 " + nombre + " (Código: " + codigo + ") - Email: " + email +
                " - Libros activos: " + librosActivos.size() + "/3";
    }
}