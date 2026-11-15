package ups.poo.controlador;

import ups.poo.modelo.Equipo;
import ups.poo.modelo.NumeroEquiposInvalidoException;
import ups.poo.modelo.Partido;
import ups.poo.modelo.SorteoInvalidoException;
import ups.poo.modelo.Torneo;
import ups.poo.vista.ConsolaVista;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TorneoControlador {
    private Torneo torneo;
    private ConsolaVista vista;
    private static final String ARCHIVO_SERIALIZADO = "torneo.ser";

    public TorneoControlador() {
        this.torneo = new Torneo();
        this.vista = new ConsolaVista();
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            int opcion = vista.mostrarMenu();

            try {
                switch (opcion) {
                    case 1:
                        torneo.cargar16EquiposLiga();
                        vista.mostrarMensaje("Se han registrado los 16 equipos de la Liga Profesional.");
                        break;

                    case 2:
                        if (torneo.getEquiposInscritos().isEmpty()) {
                            vista.mostrarError("Primero debe inscribir los 16 equipos (Opción 1).");
                            break;
                        }
                        torneo.iniciarSorteoEtapa(torneo.getEquiposInscritos(), "Octavos de Final");
                        vista.mostrarEnfrentamientos(torneo.getPartidosEtapaActual(), torneo.getEtapaActual());
                        break;

                    case 3:
                        avanzarYSortearSiguienteEtapa("Octavos de Final", "Cuartos de Final");
                        break;

                    case 4:
                        avanzarYSortearSiguienteEtapa("Cuartos de Final", "Semifinales");
                        break;

                    case 5:
                        avanzarYSortearSiguienteEtapa("Semifinales", "Gran Final");
                        break;

                    case 6:
                        torneo.guardarTorneo(ARCHIVO_SERIALIZADO);
                        vista.mostrarMensaje("Torneo serializado correctamente en '" + ARCHIVO_SERIALIZADO + "'.");
                        break;

                    case 7:
                        this.torneo = Torneo.cargarTorneo(ARCHIVO_SERIALIZADO);
                        vista.mostrarMensaje("Torneo deserializado desde '" + ARCHIVO_SERIALIZADO + "'.");
                        vista.mostrarEnfrentamientos(torneo.getPartidosEtapaActual(), torneo.getEtapaActual());
                        break;

                    case 8:
                        vista.mostrarEnfrentamientos(torneo.getPartidosEtapaActual(), torneo.getEtapaActual());
                        break;

                    case 9:
                        List<Equipo> listaInvalida = new ArrayList<>();
                        for (int i = 1; i <= 5; i++) {
                            listaInvalida.add(new Equipo("Equipo Prueba " + i));
                        }
                        torneo.iniciarSorteoEtapa(listaInvalida, "Etapa Inválida");
                        break;

                    case 0:
                        continuar = false;
                        vista.cerrarScanner();
                        vista.mostrarMensaje("Saliendo del sistema de sorteo...");
                        break;

                    default:
                        vista.mostrarError("Opción no válida. Por favor ingrese un número del 0 al 9.");
                }
            } catch (NumeroEquiposInvalidoException e) {
                vista.mostrarError("Excepción de Cantidad de Equipos: " + e.getMessage());
            } catch (SorteoInvalidoException e) {
                vista.mostrarError("Excepción en el Sorteo Recursivo: " + e.getMessage());
            } catch (IOException e) {
                vista.mostrarError("Error de Entrada/Salida con el archivo '" + ARCHIVO_SERIALIZADO + "': " + e.getMessage());
            } catch (ClassNotFoundException e) {
                vista.mostrarError("Error de compatibilidad de clases al deserializar: " + e.getMessage());
            } catch (Exception e) {
                vista.mostrarError("Error inesperado: " + e.getMessage());
            }
        }
    }

    private void avanzarYSortearSiguienteEtapa(String etapaEsperada, String etapaNueva)
            throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        if (!torneo.getEtapaActual().equals(etapaEsperada)) {
            throw new SorteoInvalidoException(
                "Flujo incorrecto: Actualmente se encuentra en '" + torneo.getEtapaActual() + 
                "', debe completar '" + etapaEsperada + "' primero."
            );
        }

        List<Equipo> clasificados = torneo.obtenerClasificadosSiguienteEtapa();
        vista.mostrarMensaje("Resultados simulados de " + etapaEsperada + ":");
        for (Partido p : torneo.getPartidosEtapaActual()) {
            System.out.println("  -> " + p);
        }

        torneo.iniciarSorteoEtapa(clasificados, etapaNueva);
        vista.mostrarEnfrentamientos(torneo.getPartidosEtapaActual(), etapaNueva);
    }
}