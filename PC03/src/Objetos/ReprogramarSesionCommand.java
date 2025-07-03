package Objetos;

import Interface.Command;
import java.time.LocalDateTime;

public class ReprogramarSesionCommand implements Command {
    private Tutoria tutoria;
    private LocalDateTime nuevaFechaHora;
    private LocalDateTime fechaHoraAnterior;

    public ReprogramarSesionCommand(Tutoria tutoria, LocalDateTime nuevaFechaHora) {
        this.tutoria = tutoria;
        this.nuevaFechaHora = nuevaFechaHora;
        this.fechaHoraAnterior = tutoria.getFechaHora();
    }

    @Override
    public void ejecutar() {
        tutoria.setFechaHora(nuevaFechaHora);
        tutoria.notificarObservadores("Sesión reprogramada para: " + nuevaFechaHora.toString());
    }

    @Override
    public void deshacer() {
        tutoria.setFechaHora(fechaHoraAnterior);
        tutoria.notificarObservadores("Se revirtió la reprogramación, fecha anterior: " + fechaHoraAnterior.toString());
    }
}