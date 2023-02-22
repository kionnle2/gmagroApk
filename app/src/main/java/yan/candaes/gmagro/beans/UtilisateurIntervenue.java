package yan.candaes.gmagro.beans;

// CONFuSION : UTILISATEUR = INTERVENANT
// REPRESENTE UTILISATEUR + TEMP PASSE
// UTILE POUR REMPLIR LA TABLE INTERVENANT_INTERVENTION DE LA BDD
// GRACE A LID UTILISATEUR, LE TEMP ET L'ID INTERVENTION
public class UtilisateurIntervenue {
    private String time;
    private Utilisateur inter;
    // nouveau utilisé uniquement dans ContinueIntervention lors de l'ajoue d'un intervenent dans la liste vieuw,
    //l'objectif et d'afficher le temp passé dans le text "Temp passé:X min" et le nouveau temp dan le PlainText si l'inter viens du spinner
    private int nouveauTempPosition = 0;
    public UtilisateurIntervenue(Utilisateur u, String time) {
        this.inter=u;
        this.time = time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Utilisateur getInter() {
        return inter;
    }

    public String getTime() {
        return time;
    }

    public void setNouveauTempPosition(int t){
        this.nouveauTempPosition=t;
    }
    public int getNouveauTempPosition() {
        return nouveauTempPosition;
    }

    @Override
    public String toString() {
        return inter.toString() ;
    }
}
