package ups.poo.modelo;

import java.io.Serializable;

public class Partido implements Serializable {
    private static final long serialVersionUID = 1L;
    private Equipo local;
    private Equipo visitante;
    private String etapa;
    private Equipo ganador;

    public Partido(Equipo local, Equipo visitante, String etapa) {
        this.local = local;
        this.visitante = visitante;
        this.etapa = etapa;
        this.ganador = null;
    }

    public Equipo getLocal() {
        return local;
    }

    public Equipo getVisitante() {
        return visitante;
    }

    public String getEtapa() {
        return etapa;
    }

    public Equipo getGanador() {
        return ganador;
    }

    public void setGanador(Equipo ganador) {
        this.ganador = ganador;
    }

    @Override
    public String toString() {
        String infoGanador = (ganador != null) ? " (Gana: " + ganador.getNombre() + ")" : "";
        return local.getNombre() + " vs " + visitante.getNombre() + infoGanador;
    }
}