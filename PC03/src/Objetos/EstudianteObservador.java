package Objetos;

import Interface.Observador;

/*La clase EstudianteObservador implementa Observador para recibir 
mensajes respecto a su estado.*/
public class EstudianteObservador extends Usuario implements Observador {
    public EstudianteObservador(String nombre, String email, String password) {
        super(nombre, email, password, Rol.ESTUDIANTE);
    }

    @Override
    public void actualizar(String mensaje) {
        System.out.println("[Notificación para Estudiante " + nombre + "]: " + mensaje);
    }
}