package fr.unice.polytech.restaurant;

public class ScheduleDTO {
    private String jour;
    private String horaireOuverture;
    private String horaireFermeture;

    public ScheduleDTO(String jour, String horaireOuverture, String horaireFermeture) {
        this.jour = jour;
        this.horaireOuverture = horaireOuverture;
        this.horaireFermeture = horaireFermeture;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getHoraireOuverture() {
        return horaireOuverture;
    }

    public void setHoraireOuverture(String horaireOuverture) {
        this.horaireOuverture = horaireOuverture;
    }

    public String getHoraireFermeture() {
        return horaireFermeture;
    }

    public void setHoraireFermeture(String horaireFermeture) {
        this.horaireFermeture = horaireFermeture;
    }
}
