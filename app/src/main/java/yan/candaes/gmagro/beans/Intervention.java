package yan.candaes.gmagro.beans;

public class Intervention {

    int id;
    String dh_debut;
    String dh_fin;
    String commentaire;
    String temp_arret;
    String changement_organe;
    String perte;
    String dh_creation;
    String dh_derniere_maj;
    String intervenant_id;
    char activite_code;
    String machine_code;
    String cause_defaut_code;
    String cause_objet_code;
    String symptome_defaut_code;
    String symptome_objet_code;

    public int getId() {
        return id;
    }

    public Intervention(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Intervention "+id;
    }
}
