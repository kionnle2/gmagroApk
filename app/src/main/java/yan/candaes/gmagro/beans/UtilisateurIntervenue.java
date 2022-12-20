package yan.candaes.gmagro.beans;

public class UtilisateurIntervenue extends Utilisateur{
    private int time;
    public UtilisateurIntervenue(String login, String nom, String prenom, String uai, int time) {
        super(login, nom, prenom, uai);
        this.setTime(time);
    }

    public UtilisateurIntervenue(Utilisateur u, int time) {
        super(u.getLogin(), u.getNom(), u.getPrenom(), u.getUai());
        this.time = time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

}
