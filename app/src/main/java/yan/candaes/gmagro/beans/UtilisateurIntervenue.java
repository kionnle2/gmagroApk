package yan.candaes.gmagro.beans;

// CONFuSION : UTILISATEUR = INTERVENANT
// REPRESENTE UTILISATEUR + TEMP PASSE
// UTILE POUR REMPLIR LA TABLE INTERVENANT_INTERVENTION DE LA BDD
// GRACE A LID UTILISATEUR, LE TEMP ET L'ID INTERVENTION
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
