package yan.candaes.gmagro.beans;


public class UtilisateurIntervenue {
    private int time;
    private Utilisateur inter;

    public UtilisateurIntervenue(Utilisateur u, int time) {
       this.inter=u;
        this.time = time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Utilisateur getInter() {
        return inter;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return inter.toString();
    }
}
