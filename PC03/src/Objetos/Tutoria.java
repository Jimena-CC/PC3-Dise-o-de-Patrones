package Objetos;

import Interface.Observador;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Tutoria {
    
    private TutorObservador tutor;
    private EstudianteObservador estudiante;
    private EstadoTutoria estado;
    private LocalDateTime fechaHora;
    private List<Observador> observadores = new ArrayList<>();
    
    /*En la clase Tutoria, al estar destinada a crear diversos objetos, se decidió 
    utilizar el patron Builder. Este patrón creacional permite construir objetos 
    complejos paso a paso, separando la construcción de la representación final.*/ 
    private Tutoria(Builder builder) {
        this.tutor = builder.tutor;
        this.estudiante = builder.estudiante;
        this.estado = builder.estado;

        if (tutor != null) agregarObservador(tutor);
        if (estudiante != null) agregarObservador(estudiante);
    }

    public void agregarObservador(Observador o) {
        observadores.add(o);
    }

    public void notificarObservadores(String mensaje) {
        for (Observador o : observadores) {
            o.actualizar(mensaje);
        }
    }

    public void cambiarEstado(EstadoTutoria nuevoEstado) {
        this.estado = nuevoEstado;
        notificarObservadores("Estado cambiado a: " + nuevoEstado);
    }

    public TutorObservador getTutor() {
        return tutor;
    }

    public void setTutor(TutorObservador tutor) {
        this.tutor = tutor;
        notificarObservadores("Tutor asignado: " + tutor.getNombre());
    }

    public EstudianteObservador getEstudiante() {
        return estudiante;
    }

    public EstadoTutoria getEstado() {
        return estado;
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
        notificarObservadores("La tutoría ha sido reprogramada para: " + fechaHora.toString());
    }

    public static class Builder {
        private TutorObservador tutor;
        private EstudianteObservador estudiante;
        private EstadoTutoria estado = EstadoTutoria.SOLICITADA;

        public Builder setTutor(TutorObservador tutor) {
            this.tutor = tutor;
            return this;
        }

        public Builder setEstudiante(EstudianteObservador estudiante) {
            this.estudiante = estudiante;
            return this;
        }

        public Builder setEstado(EstadoTutoria estado) {
            this.estado = estado;
            return this;
        }

        public Tutoria build() {
            return new Tutoria(this);
        }
    }

    @Override
    public String toString() {
        return "Tutoria{tutor=" + (tutor != null ? tutor.getNombre() : "null") +
                ", estudiante=" + (estudiante != null ? estudiante.getNombre() : "null") +
                ", estado=" + estado + "}";
    }
}