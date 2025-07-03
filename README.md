Actividades (Etapas y Evidencias)
1. Identifica 5 clases principales del sistema y relaciónalas con al menos 5 
patrones.

Sistema
Clase central que gestiona usuarios, tutorías y estado de sesión
Singleton - asegura única instancia global 

Tutoria
Modela una tutoría con su estado, tutor, estudiante y fecha
Builder, Observer

TutorObservador 
Subclase de usuario que observa cambios en tutoría
Observer

EstudianteObservador
Subclase de usuario que observa cambios en tutoría
Observer
Command y sus derivados (`AsignarTutorCommand`, `ReprogramarSesionCommand`)
Encapsulan operaciones administrativas que ejecuta el administrador
Command                                    |

2. Justifica por qué aplicaste cada patrón.

Builder
Permite crear objetos Tutoria complejos de forma flexible, asignando opcionalmente propiedades (estudiante, tutor) antes de construir la instancia final.
Evita constructores con demasiados parámetros.

Singleton
La clase Sistema controla todo el estado global (usuarios, tutorías).
Solo debe existir una instancia, accesible desde todo el programa.

Observer
TutorObservador y EstudianteObservador reciben notificaciones automáticas cuando cambia el estado de la tutoría.
Favorece el desacoplamiento entre la clase Tutoria y sus observadores.

Command
Las acciones administrativas como Asignar Tutor o Reprogramar Sesión se encapsulan en objetos comando (AsignarTutorCommand, ReprogramarSesionCommand).
Permite registrar, deshacer o reejecutar comandos en un futuro.

3. Detecta y describe dos antipatrones que podrían presentarse en una mala 
implementación del sistema.

God Object (Objeto Dios)
Ocurre si la clase Sistema acumula demasiadas responsabilidades: usuarios, tutorías, notificaciones, historial, validaciones, etc.
Esto genera código difícil de mantener y de probar.

Spaghetti Code
Si no se separan bien los comandos, estados y observadores, se termina con llamadas encadenadas y dependencias circulares.
Hace que los cambios en una parte del sistema rompan otras inesperadamente.
