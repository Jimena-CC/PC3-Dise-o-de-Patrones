package Main;

import Objetos.AsignarTutorCommand;
import Objetos.EstadoTutoria;
import Objetos.EstudianteObservador;
import Objetos.ReprogramarSesionCommand;
import Objetos.Rol;
import Objetos.Sistema;
import Objetos.TutorObservador;
import Objetos.Tutoria;
import Objetos.Usuario;
import Objetos.UsuarioAdmin;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static Sistema sistema = Sistema.getInstancia();

    public static void main(String[] args) {
        // Crear admin inicial (para pruebas)
        sistema.registrarUsuario(new UsuarioAdmin("admin", "admin@utp.edu", "admin123"));

        boolean salir = false;
        while (!salir) {
            if (sistema.getUsuarioLogueado() == null) {
                menuSinLogin();
            } else {
                menuConLogin();
            }
        }
        System.out.println("Gracias por usar el sistema.");
    }

    static void menuSinLogin() {
        System.out.println("\n--- Menú Principal (No logueado) ---");
        System.out.println("1. Login");
        System.out.println("2. Registrar Tutor");
        System.out.println("3. Registrar Estudiante");
        System.out.println("4. Salir");
        System.out.print("Seleccione opción: ");
        String op = sc.nextLine();

        switch (op) {
            case "1":
                login();
                break;
            case "2":
                registrarTutor();
                break;
            case "3":
                registrarEstudiante();
                break;
            case "4":
                System.exit(0);
            default:
                System.out.println("Opción inválida.");
        }
    }

    static void menuConLogin() {
        Usuario u = sistema.getUsuarioLogueado();
        System.out.println("\n--- Menú Principal (Usuario: " + u.getNombre() + ", Rol: " + u.getRol() + ") ---");
        System.out.println("1. Crear tutoría");
        System.out.println("2. Ver tutorías");
        System.out.println("3. Cambiar estado tutoría");
        if (u.getRol() == Rol.ADMIN) {
            System.out.println("4. Ejecutar comandos administrativos (pendiente)");
        }
        System.out.println("0. Logout");
        System.out.print("Seleccione opción: ");
        String op = sc.nextLine();

        switch (op) {
            case "1":
                crearTutoria();
                break;
            case "2":
                verTutorias();
                break;
            case "3":
                cambiarEstadoTutoria();
                break;
            case "4":
                if (u.getRol() == Rol.ADMIN) {
                    menuComandosAdministrativos();
                } else {
                    System.out.println("Opción inválida.");
                }
                break;
            case "0":
                sistema.logout();
                System.out.println("Sesión cerrada.");
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }

    // Métodos para login y registro
    static void login() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String pwd = sc.nextLine();

        Usuario u = sistema.login(email, pwd);
        if (u != null) {
            System.out.println("Bienvenido, " + u.getNombre() + "! Rol: " + u.getRol());
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }

    static void registrarTutor() {
        System.out.print("Nombre tutor: ");
        String nombre = sc.nextLine();
        System.out.print("Email tutor: ");
        String email = sc.nextLine();
        System.out.print("Password tutor: ");
        String pwd = sc.nextLine();

        TutorObservador tutor = new TutorObservador(nombre, email, pwd);
        if (sistema.registrarUsuario(tutor)) {
            System.out.println("Tutor registrado con éxito.");
        } else {
            System.out.println("Email ya registrado.");
        }
    }

    static void registrarEstudiante() {
        System.out.print("Nombre estudiante: ");
        String nombre = sc.nextLine();
        System.out.print("Email estudiante: ");
        String email = sc.nextLine();
        System.out.print("Password estudiante: ");
        String pwd = sc.nextLine();

        EstudianteObservador estudiante = new EstudianteObservador(nombre, email, pwd);
        if (sistema.registrarUsuario(estudiante)) {
            System.out.println("Estudiante registrado con éxito.");
        } else {
            System.out.println("Email ya registrado.");
        }
    }

    static void crearTutoria() {
        System.out.println("Seleccionar estudiante (ingrese email): ");
        String emailEst = sc.nextLine();
        EstudianteObservador estudiante = null;
        for (Usuario u : sistema.getUsuarios()) {
            if (u.getEmail().equalsIgnoreCase(emailEst) && u.getRol() == Rol.ESTUDIANTE) {
                estudiante = (EstudianteObservador) u;
                break;
            }
        }
        if (estudiante == null) {
            System.out.println("Estudiante no encontrado.");
            return;
        }

        System.out.println("Seleccionar tutor (ingrese email): ");
        String emailTutor = sc.nextLine();
        TutorObservador tutor = null;
        for (Usuario u : sistema.getUsuarios()) {
            if (u.getEmail().equalsIgnoreCase(emailTutor) && u.getRol() == Rol.TUTOR) {
                tutor = (TutorObservador) u;
                break;
            }
        }
        if (tutor == null) {
            System.out.println("Tutor no encontrado.");
            return;
        }

        Tutoria tutoria = new Tutoria.Builder()
                .setEstudiante(estudiante)
                .setTutor(tutor)
                .build();

        sistema.agregarTutoria(tutoria);
        System.out.println("Tutoría creada: " + tutoria);
    }

    static void verTutorias() {
        List<Tutoria> tutorias = sistema.getTutorias();
        if (tutorias.isEmpty()) {
            System.out.println("No hay tutorías registradas.");
        } else {
            for (int i = 0; i < tutorias.size(); i++) {
                System.out.println(i + ": " + tutorias.get(i));
            }
        }
    }

    static void cambiarEstadoTutoria() {
        verTutorias();
        System.out.print("Ingrese índice de tutoría a modificar: ");
        try {
            int idx = Integer.parseInt(sc.nextLine());
            List<Tutoria> tutorias = sistema.getTutorias();
            if (idx < 0 || idx >= tutorias.size()) {
                System.out.println("Índice inválido.");
                return;
            }
            Tutoria tutoria = tutorias.get(idx);

            System.out.print("Nuevo estado (SOLICITADA, ACEPTADA, RECHAZADA, COMPLETADA): ");
            String nuevoEstadoStr = sc.nextLine().toUpperCase();
            EstadoTutoria nuevoEstado = EstadoTutoria.valueOf(nuevoEstadoStr);

            tutoria.cambiarEstado(nuevoEstado);
        } catch (Exception e) {
            System.out.println("Entrada inválida.");
        }
    }
    
    static void menuComandosAdministrativos() {
        System.out.println("\n--- Comandos Administrativos ---");
        System.out.println("1. Asignar tutor (pendiente implementación)");
        System.out.println("2. Reprogramar sesión");
        System.out.println("3. Suspender tutoría (pendiente implementación)");
        System.out.println("0. Volver");
        System.out.print("Seleccione opción: ");
        String op = sc.nextLine();

        switch (op) {
            case "1":
                asignarTutor();
            break;
            case "2":
                reprogramarSesion();
                break;
            case "3":
                System.out.println("Funcionalidad de suspender tutoría aún no implementada.");
                break;
            case "0":
                // volver al menú anterior
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }

    static void reprogramarSesion() {
        verTutorias();
        System.out.print("Ingrese índice de tutoría a reprogramar: ");
        try {
            int idx = Integer.parseInt(sc.nextLine());
            List<Tutoria> tutorias = sistema.getTutorias();
            if (idx < 0 || idx >= tutorias.size()) {
                System.out.println("Índice inválido.");
                return;
            }
            Tutoria tutoria = tutorias.get(idx);

            System.out.print("Ingrese nueva fecha y hora (formato yyyy-MM-ddTHH:mm): ");
            String fechaStr = sc.nextLine();
            LocalDateTime nuevaFecha;
            try {
                nuevaFecha = LocalDateTime.parse(fechaStr);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido.");
                return;
            }

            // Ejecutar comando ReprogramarSesionCommand
            ReprogramarSesionCommand cmd = new ReprogramarSesionCommand(tutoria, nuevaFecha);
            cmd.ejecutar();

            // Aquí podrías agregar el comando a un historial para undo, si lo implementas luego
            System.out.println("Sesión reprogramada correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }
    
    static void asignarTutor() {
        verTutorias();
        System.out.print("Ingrese índice de tutoría para asignar tutor: ");
        try {
            int idx = Integer.parseInt(sc.nextLine());
            List<Tutoria> tutorias = sistema.getTutorias();
            if (idx < 0 || idx >= tutorias.size()) {
                System.out.println("Índice inválido.");
                return;
            }
            Tutoria tutoria = tutorias.get(idx);

            System.out.print("Ingrese email del nuevo tutor: ");
            String emailTutor = sc.nextLine();
            TutorObservador nuevoTutor = null;
            for (Usuario u : sistema.getUsuarios()) {
                if (u.getEmail().equalsIgnoreCase(emailTutor) && u.getRol() == Rol.TUTOR) {
                    nuevoTutor = (TutorObservador) u;
                    break;
                }
            }
            if (nuevoTutor == null) {
                System.out.println("Tutor no encontrado.");
                return;
            }

            AsignarTutorCommand cmd = new AsignarTutorCommand(tutoria, nuevoTutor);
            cmd.ejecutar();

            // Aquí podrías guardar el comando para undo
            System.out.println("Tutor asignado correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }
}