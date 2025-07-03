package Objetos;

import java.util.ArrayList;
import java.util.List;

/*En este caso, como se requiere un sistema de inicio de sesión, se utilizó el patrón 
Singleton. Esto nos asegura que la clase Sistema solo tenga una única instancia a lo largo
de toda la ejecución del programa. En este contexto, esta unica instancia se va a asegurar de
guardar las listas de usuarios y tutorías, controlar quién está logueado, etc*/

public class Sistema {
    private static Sistema instancia;
    private List<Usuario> usuarios;
    private List<Tutoria> tutorias;
    private Usuario usuarioLogueado;

    private Sistema() {
        usuarios = new ArrayList<>();
        tutorias = new ArrayList<>();
        usuarioLogueado = null;
    }

    public static Sistema getInstancia() {
        if (instancia == null) {
            instancia = new Sistema();
        }
        return instancia;
    }

    public boolean registrarUsuario(Usuario u) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(u.getEmail())) {
                return false;
            }
        }
        usuarios.add(u);
        return true;
    }

    public Usuario login(String email, String password) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email) && u.verificarPassword(password)) {
                usuarioLogueado = u;
                return u;
            }
        }
        return null;
    }

    public void logout() {
        usuarioLogueado = null;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void agregarTutoria(Tutoria t) {
        tutorias.add(t);
    }

    public List<Tutoria> getTutorias() {
        return tutorias;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}