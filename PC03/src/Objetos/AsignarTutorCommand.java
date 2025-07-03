package Objetos;

import Interface.Command;

public class AsignarTutorCommand implements Command {
    private Tutoria tutoria;
    private TutorObservador nuevoTutor;
    private TutorObservador tutorAnterior;

    public AsignarTutorCommand(Tutoria tutoria, TutorObservador nuevoTutor) {
        this.tutoria = tutoria;
        this.nuevoTutor = nuevoTutor;
        this.tutorAnterior = tutoria.getTutor();
    }

    @Override
    public void ejecutar() {
        tutoria.setTutor(nuevoTutor);
        tutoria.notificarObservadores("Tutor asignado: " + nuevoTutor.getNombre());
    }

    @Override
    public void deshacer() {
        tutoria.setTutor(tutorAnterior);
        tutoria.notificarObservadores("Se revirtió asignación, tutor anterior: " + tutorAnterior.getNombre());
    }
}
