# Tarea Unidad 3 - Sorteo de Liga Profesional (POO)

Proyecto desarrollado en Java aplicando el patrón arquitectónico MVC, excepciones personalizadas, algoritmo recursivo para el sorteo y serialización de datos.

## Descripción
Aplicación de consola para gestionar el sorteo de eliminación directa de un torneo de fútbol de 16 equipos (Octavos, Cuartos, Semifinales y Gran Final), asegurando que no se repitan partidos ni enfrentamientos inválidos.

## Estructura del Código (MVC)
El proyecto está organizado en los siguientes paquetes dentro de src/ups/poo/:
- **modelo**: Contiene las clases Equipo, Partido, Torneo (con la lógica recursiva y serialización), y las excepciones NumeroEquiposInvalidoException y SorteoInvalidoException.
- **vista**: Contiene la clase ConsolaVista encargada de la interacción con el usuario mediante la consola.
- **controlador**: Contiene TorneoControlador, que maneja el flujo del menú y conecta el modelo con la vista.
- **principal**: Contiene la clase Main para iniciar la aplicación.

## Instrucciones de Ejecución
1. Importar el proyecto en Eclipse IDE.
2. Navegar hasta src/ups/poo/principal/Main.java.
3. Ejecutar la clase como **Java Application**.
4. Usar el menú interactivo en la consola para:
   - Inscribir los 16 equipos.
   - Ejecutar los sorteos recursivos de cada fase.
   - Guardar y cargar el estado del torneo mediante el archivo torneo.ser.
   - Probar las excepciones ingresando cantidades no válidas.
