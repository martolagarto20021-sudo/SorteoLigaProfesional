package ups.poo.vista;

import ups.poo.modelo.Partido;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsolaVista {
    private Scanner scanner;

    public ConsolaVista() {
        this.scanner = new Scanner(System.in);
    }

    public int mostrarMenu() {
        System.out.println("   SORTEO DE FÚTBOL   ");
        System.out.println("1. Inscribir 16 equipos de la liga");
        System.out.println("2. Sortear Octavos de Final (16 equipos)");
        System.out.println("3. Avanzar y Sortear Cuartos de Final (8 equipos)");
        System.out.println("4. Avanzar y Sortear Semifinales (4 equipos)");
        System.out.println("5. Avanzar y Sortear Final (2 equipos)");
        System.out.println("6. Guardar estado del torneo");
        System.out.println("7. Cargar estado del torneo");
        System.out.println("8. Mostrar enfrentamientos actuales");
        System.out.println("9. Probar excepción (se intenta sortear 5 equipos)");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");

        try {
            int opcion = scanner.nextInt();
            scanner.nextLine();
            return opcion;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -1;
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println("\n[INFO] " + mensaje);
    }

    public void mostrarError(String error) {
        System.err.println("\n[ERROR] " + error);
    }

    public void mostrarEnfrentamientos(List<Partido> partidos, String etapa) {
        System.out.println("\n--- " + etapa + " ---");
        if (partidos == null || partidos.isEmpty()) {
            System.out.println("Todavia no hay partidos sorteados en esta fase.");
            return;
        }
        for (int i = 0; i < partidos.size(); i++) {
            System.out.println((i + 1) + ". " + partidos.get(i));
        }
        System.out.println("-------------------------");
    }

    public void cerrarScanner() {
        scanner.close();
    }
}