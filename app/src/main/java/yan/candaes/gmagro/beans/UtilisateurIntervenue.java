package yan.candaes.gmagro.beans;

// CONFUSION : UTILISATEUR = INTERVENANT
// REPRESENTE UTILISATEUR + TEMP PASSE
// UTILE POUR REMPLIR LA TABLE INTERVENANT_INTERVENTION DE LA BDD
// GRACE A LID UTILISATEUR, LE TEMP ET L'ID INTERVENTION

// nouveauTempPosition utilisé uniquement dans ContinueIntervention lors de l'ajoue d'un intervenent dans la liste view,
//l'objectif et d'afficher le temp passé dans le text "Temp passé:X min" et le nouveau temp dans le PlainText si l'inter viens du spinner
public class UtilisateurIntervenue {

    private String time;
    private Utilisateur inter;

    private String nouveauTemp = "0:0";
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



    public void setNouveauTemp(String t){
        this.nouveauTemp=t;
    }

    public String getNouveauTemp(){
       return this.nouveauTemp;
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
