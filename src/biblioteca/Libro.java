package biblioteca;

public class Libro {
    // Atributos básicos
    private String titulo;
    private String autor;
    private String codigo;
    private boolean disponible;

    // Constructor
    public Libro(String titulo, String autor, String codigo) {
        this.titulo = titulo;
        this.autor = autor;
        this.codigo = codigo;
        this.disponible = true; // Siempre empieza disponible
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // Método toString para mostrar información
    @Override
    public String toString() {
        String estado = disponible ? "DISPONIBLE" : "PRESTADO";
        return "📚 " + titulo + " - " + autor + " (Código: " + codigo + ") - " + estado;
    }
}