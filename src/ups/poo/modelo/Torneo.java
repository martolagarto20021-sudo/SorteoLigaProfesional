package ups.poo.modelo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Torneo implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Equipo> equiposInscritos;
    private List<Partido> partidosEtapaActual;
    private String etapaActual;

    public Torneo() {
        this.equiposInscritos = new ArrayList<>();
        this.partidosEtapaActual = new ArrayList<>();
        this.etapaActual = "Sin iniciar";
    }

    // Cargar equipos
    public void cargar16EquiposLiga() {
        equiposInscritos.clear();
        partidosEtapaActual.clear();
        String[] nombres = {
            "Liga de Quito", "Barcelona SC", "Independiente del Valle", "Emelec",
            "Deportivo Cuenca", "El Nacional", "Universidad Católica", "Aucas",
            "Macará", "Técnico Universitario", "Delfín SC", "Orense SC",
            "Mushuc Runa", "Cumbayá FC", "Libertad FC", "Imbabura SC"
        };
        for (String nombre : nombres) {
            equiposInscritos.add(new Equipo(nombre));
        }
        this.etapaActual = "Octavos de Final (Listo para sortear)";
    }

    // Revisar que la cantidad equipos
    public void validarPotenciaDeDos(int cantidad) throws NumeroEquiposInvalidoException {
        if (cantidad < 2 || (cantidad & (cantidad - 1)) != 0) {
            throw new NumeroEquiposInvalidoException(
                "Cantidad inválida de equipos (" + cantidad + "). Tienen que ser 16, 8, 4 o 2 para las llaves."
            );
        }
    }

    // Preparar la lista y empezar
    public void iniciarSorteoEtapa(List<Equipo> equiposParticipantes, String nombreEtapa)
            throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        
        validarPotenciaDeDos(equiposParticipantes.size());

        List<Equipo> bolsaAleatoria = new ArrayList<>(equiposParticipantes);
        Collections.shuffle(bolsaAleatoria); 

        List<Partido> nuevosPartidos = new ArrayList<>();
        emparejarRecursivo(bolsaAleatoria, nuevosPartidos, nombreEtapa);

        this.partidosEtapaActual = nuevosPartidos;
        this.etapaActual = nombreEtapa;
    }

    // armando los cruces
    private void emparejarRecursivo(List<Equipo> disponibles, List<Partido> partidosGenerados, String etapa)
            throws SorteoInvalidoException {
        
        // Caso: si ya se vació la lista
        if (disponibles.isEmpty()) {
            return;
        }

        // error si queda uno volando
        if (disponibles.size() == 1) {
            throw new SorteoInvalidoException(
                "Error en el sorteo: Se quedó un equipo sin rival."
            );
        }

        // sacar los 2 primeros de la lista
        Equipo local = disponibles.remove(0);
        Equipo visitante = disponibles.remove(0);

        // Validación de seguridad 
        if (local.equals(visitante)) {
            throw new SorteoInvalidoException(
                "Error grave: El equipo " + local.getNombre() + " salió emparejado contra sí mismo."
            );
        }

        // armar partido y enviar
        partidosGenerados.add(new Partido(local, visitante, etapa));


        emparejarRecursivo(disponibles, partidosGenerados, etapa);
    }

    // sacar resultados y avanzar
    public List<Equipo> obtenerClasificadosSiguienteEtapa() throws SorteoInvalidoException {
        if (partidosEtapaActual.isEmpty()) {
            throw new SorteoInvalidoException("No hay partidos en esta fase para poder simular.");
        }
        List<Equipo> ganadores = new ArrayList<>();
        for (Partido p : partidosEtapaActual) {
            Equipo ganador = (Math.random() < 0.5) ? p.getLocal() : p.getVisitante();
            p.setGanador(ganador);
            ganadores.add(ganador);
        }
        return ganadores;
    }

    // Guardar el objeto Torneo completo en un archivo binario
    public void guardarTorneo(String nombreArchivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(this);
        }
    }

    // Recuperar el objeto Torneo desde el archivo binario
    public static Torneo cargarTorneo(String nombreArchivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            return (Torneo) ois.readObject();
        }
    }

    public List<Equipo> getEquiposInscritos() {
        return equiposInscritos;
    }

    public List<Partido> getPartidosEtapaActual() {
        return partidosEtapaActual;
    }

    public String getEtapaActual() {
        return etapaActual;
    }
}