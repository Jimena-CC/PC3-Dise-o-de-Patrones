package Objetos;

public class UsuarioAdmin extends Usuario {
    public UsuarioAdmin(String nombre, String email, String password) {
        super(nombre, email, password, Rol.ADMIN);
    }
}