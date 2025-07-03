package Objetos;

public abstract class Usuario {
    protected String nombre;
    protected String email;
    protected String password;
    protected Rol rol;

    public Usuario(String nombre, String email, String password, Rol rol) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public Rol getRol() { return rol; }
    public boolean verificarPassword(String pwd) {
        return password.equals(pwd);
    }
}
