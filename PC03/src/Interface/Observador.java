package Interface;

/*Usamos Observer para notificar automáticamente a tutor y estudiante cuando cambia 
el estado de la tutoría. Este interface define el contrato para recibir notificaciones.*/

public interface Observador {
    void actualizar(String mensaje);
}