package Objetos;

import Interface.Observador;

/*La clase TutorObservador implementa Observador para recibir 
mensajes respecto a su estado.*/
public class TutorObservador extends Usuario implements Observador {
    public TutorObservador(String nombre, String email, String password) {
        super(nombre, email, password, Rol.TUTOR);
    }

    @Override
    public void actualizar(String mensaje) {
        System.out.println("[Notificación para Tutor " + nombre + "]: " + mensaje);
    }
}
