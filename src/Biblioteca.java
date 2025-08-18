package biblioteca;

import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {
    // ArrayList básicos para almacenar datos
    private ArrayList<Libro> libros;
    private ArrayList<Usuario> usuarios;
    private Scanner scanner;

    // Constructor
    public Biblioteca() {
        this.libros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        cargarDatosPrueba();
    }

    // Método principal
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.ejecutar();
    }

    // Ejecutar el sistema
    public void ejecutar() {
        mostrarBienvenida();

        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 0);

        System.out.println("👋 ¡Gracias por usar la Biblioteca!");
        scanner.close();
    }

    // Mostrar bienvenida
    private void mostrarBienvenida() {
        System.out.println("========================================");
        System.out.println("      📚 SISTEMA DE BIBLIOTECA 📚     ");
        System.out.println("========================================");
    }

    // Mostrar menú
    private void mostrarMenu() {
        System.out.println("\n=========== MENÚ PRINCIPAL ===========");
        System.out.println("1. Agregar libro");
        System.out.println("2. Agregar usuario");
        System.out.println("3. Prestar libro");
        System.out.println("4. Devolver libro");
        System.out.println("5. Mostrar libros");
        System.out.println("6. Mostrar usuarios");
        System.out.println("7. Buscar libro");
        System.out.println("8. Buscar usuario");
        System.out.println("0. Salir");
        System.out.println("=====================================");
        System.out.print("👉 Elige una opción: ");
    }

    // Leer opción
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Procesar opción
    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                agregarLibro();
                break;
            case 2:
                agregarUsuario();
                break;
            case 3:
                prestarLibro();
                break;
            case 4:
                devolverLibro();
                break;
            case 5:
                mostrarLibros();
                break;
            case 6:
                mostrarUsuarios();
                break;
            case 7:
                buscarLibro();
                break;
            case 8:
                buscarUsuario();
                break;
            case 0:
                System.out.println("Cerrando sistema...");
                break;
            default:
                System.out.println("❌ Opción no válida");
        }
    }

    // 1. Agregar libro
    private void agregarLibro() {
        System.out.println("\n--- AGREGAR LIBRO ---");

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Autor: ");
        String autor = scanner.nextLine();

        System.out.print("Código: ");
        String codigo = scanner.nextLine();

        // Verificar que no exista el código
        if (buscarLibroPorCodigo(codigo) != null) {
            System.out.println("❌ Ya existe un libro con ese código");
            return;
        }

        // Agregar libro al ArrayList
        Libro nuevoLibro = new Libro(titulo, autor, codigo);
        libros.add(nuevoLibro);

        System.out.println("✅ Libro agregado exitosamente");
    }

    // 2. Agregar usuario
    private void agregarUsuario() {
        System.out.println("\n--- AGREGAR USUARIO ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Código: ");
        String codigo = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        // Verificar que no exista el código
        if (buscarUsuarioPorCodigo(codigo) != null) {
            System.out.println("❌ Ya existe un usuario con ese código");
            return;
        }

        // Agregar usuario al ArrayList
        Usuario nuevoUsuario = new Usuario(nombre, codigo, email);
        usuarios.add(nuevoUsuario);

        System.out.println("✅ Usuario agregado exitosamente");
    }

    // 3. Prestar libro
    private void prestarLibro() {
        System.out.println("\n--- PRESTAR LIBRO ---");

        System.out.print("Código del usuario: ");
        String codigoUsuario = scanner.nextLine();

        System.out.print("Código del libro: ");
        String codigoLibro = scanner.nextLine();

        // Buscar usuario y libro
        Usuario usuario = buscarUsuarioPorCodigo(codigoUsuario);
        Libro libro = buscarLibroPorCodigo(codigoLibro);

        // Verificaciones
        if (usuario == null) {
            System.out.println("❌ Usuario no encontrado");
            return;
        }

        if (libro == null) {
            System.out.println("❌ Libro no encontrado");
            return;
        }

        if (!libro.isDisponible()) {
            System.out.println("❌ El libro no está disponible");
            return;
        }

        if (!usuario.puedePrestarLibro()) {
            System.out.println("❌ El usuario ya tiene 3 libros prestados");
            return;
        }

        // Realizar préstamo
        libro.setDisponible(false);
        usuario.agregarLibro(codigoLibro);

        System.out.println("✅ Préstamo realizado exitosamente");
        System.out.println("📚 " + libro.getTitulo() + " prestado a " + usuario.getNombre());
    }

    // 4. Devolver libro
    private void devolverLibro() {
        System.out.println("\n--- DEVOLVER LIBRO ---");

        System.out.print("Código del usuario: ");
        String codigoUsuario = scanner.nextLine();

        System.out.print("Código del libro: ");
        String codigoLibro = scanner.nextLine();

        // Buscar usuario y libro
        Usuario usuario = buscarUsuarioPorCodigo(codigoUsuario);
        Libro libro = buscarLibroPorCodigo(codigoLibro);

        // Verificaciones
        if (usuario == null) {
            System.out.println("❌ Usuario no encontrado");
            return;
        }

        if (libro == null) {
            System.out.println("❌ Libro no encontrado");
            return;
        }

        if (libro.isDisponible()) {
            System.out.println("❌ El libro no está prestado");
            return;
        }

        if (!usuario.getLibrosActivos().contains(codigoLibro)) {
            System.out.println("❌ El usuario no tiene prestado este libro");
            return;
        }

        // Realizar devolución
        libro.setDisponible(true);
        usuario.devolverLibro(codigoLibro);

        System.out.println("✅ Libro devuelto exitosamente");
        System.out.println("📚 " + libro.getTitulo() + " devuelto por " + usuario.getNombre());
    }

    // 5. Mostrar libros
    private void mostrarLibros() {
        System.out.println("\n--- LISTA DE LIBROS ---");

        if (libros.isEmpty()) {
            System.out.println("📚 No hay libros registrados");
            return;
        }

        for (int i = 0; i < libros.size(); i++) {
            System.out.println((i + 1) + ". " + libros.get(i));
        }
    }

    // 6. Mostrar usuarios
    private void mostrarUsuarios() {
        System.out.println("\n--- LISTA DE USUARIOS ---");

        if (usuarios.isEmpty()) {
            System.out.println("👤 No hay usuarios registrados");
            return;
        }

        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.get(i);
            System.out.println((i + 1) + ". " + usuario);

            // Mostrar libros prestados
            if (!usuario.getLibrosActivos().isEmpty()) {
                System.out.print("    Libros prestados: ");
                for (String codigo : usuario.getLibrosActivos()) {
                    Libro libro = buscarLibroPorCodigo(codigo);
                    if (libro != null) {
                        System.out.print(libro.getTitulo() + " ");
                    }
                }
                System.out.println();
            }
        }
    }

    // 7. Buscar libro
    private void buscarLibro() {
        System.out.println("\n--- BUSCAR LIBRO ---");
        System.out.print("Ingrese código o título: ");
        String criterio = scanner.nextLine().toLowerCase();

        boolean encontrado = false;
        for (Libro libro : libros) {
            if (libro.getCodigo().toLowerCase().contains(criterio) ||
                    libro.getTitulo().toLowerCase().contains(criterio)) {
                System.out.println("📚 " + libro);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("❌ No se encontraron libros");
        }
    }

    // 8. Buscar usuario
    private void buscarUsuario() {
        System.out.println("\n--- BUSCAR USUARIO ---");
        System.out.print("Ingrese código o nombre: ");
        String criterio = scanner.nextLine().toLowerCase();

        boolean encontrado = false;
        for (Usuario usuario : usuarios) {
            if (usuario.getCodigo().toLowerCase().contains(criterio) ||
                    usuario.getNombre().toLowerCase().contains(criterio)) {
                System.out.println("👤 " + usuario);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("❌ No se encontraron usuarios");
        }
    }

    // Método auxiliar: Buscar libro por código
    private Libro buscarLibroPorCodigo(String codigo) {
        for (Libro libro : libros) {
            if (libro.getCodigo().equals(codigo)) {
                return libro;
            }
        }
        return null;
    }

    // Método auxiliar: Buscar usuario por código
    private Usuario buscarUsuarioPorCodigo(String codigo) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCodigo().equals(codigo)) {
                return usuario;
            }
        }
        return null;
    }

    // Cargar datos de prueba
    private void cargarDatosPrueba() {
        // Libros de prueba
        libros.add(new Libro("Java para Principiantes", "Oracle", "LIB001"));
        libros.add(new Libro("Programación Básica", "Deitel", "LIB002"));
        libros.add(new Libro("Algoritmos Simples", "Cormen", "LIB003"));

        // Usuarios de prueba
        usuarios.add(new Usuario("Ana García", "USR001", "ana@email.com"));
        usuarios.add(new Usuario("Carlos López", "USR002", "carlos@email.com"));

        System.out.println("✅ Datos cargados: 3 libros, 2 usuarios");
    }
}
