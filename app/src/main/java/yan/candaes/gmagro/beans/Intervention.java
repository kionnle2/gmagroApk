package yan.candaes.gmagro.beans;

import java.io.Serializable;

public class Intervention implements Serializable {

    int id;
    String dh_debut;
    String dh_fin;
    String commentaire;
    String temp_arret;
    Boolean changement_organe;
    Boolean perte;
    String dh_creation;
    String dh_derniere_maj;
    long intervenant_id;
    char activite_code;
    String machine_code;
    String cause_defaut_code;
    String cause_objet_code;
    String symptome_defaut_code;
    String symptome_objet_code;
    String type_machine;

    public Intervention(int id, String dh_debut, String dh_fin, String commentaire, String temp_arret, Boolean changement_organe, Boolean perte, String dh_creation, String dh_derniere_maj, long intervenant_id, char activite_code, String machine_code, String cause_defaut_code, String cause_objet_code, String symptome_defaut_code, String symptome_objet_code, String type_machine) {
        this.id = id;
        this.dh_debut = dh_debut;
        this.dh_fin = dh_fin;
        this.commentaire = commentaire;
        this.temp_arret = temp_arret;
        this.changement_organe = changement_organe;
        this.perte = perte;
        this.dh_creation = dh_creation;
        this.dh_derniere_maj = dh_derniere_maj;
        this.intervenant_id = intervenant_id;
        this.activite_code = activite_code;
        this.machine_code = machine_code;
        this.cause_defaut_code = cause_defaut_code;
        this.cause_objet_code = cause_objet_code;
        this.symptome_defaut_code = symptome_defaut_code;
        this.symptome_objet_code = symptome_objet_code;
        this.type_machine = type_machine;
    }

    public int getId() {
        return id;
    }

    public String getDh_debut() {
        return dh_debut;
    }


    public String getCommentaire() {
        return commentaire;
    }

    public String getTemp_arret() {
        return temp_arret;
    }

    public Boolean getChangement_organe() {
        return changement_organe;
    }

    public Boolean getPerte() {
        return perte;
    }

    public String getDh_derniere_maj() {
        return dh_derniere_maj;
    }


    public String getMachine_code() {
        return machine_code;
    }

    public String getCause_defaut_code() {
        return cause_defaut_code;
    }

    public String getCause_objet_code() {
        return cause_objet_code;
    }

    public String getSymptome_defaut_code() {
        return symptome_defaut_code;
    }

    public String getSymptome_objet_code() {
        return symptome_objet_code;
    }


    @Override
    public String toString() {
        return "Intervention{" + "id=" + id + ", dh_debut='" + dh_debut + '\'' + ", dh_fin='" + dh_fin + '\'' + ", commentaire='" + commentaire + '\'' + ", temp_arret=" + temp_arret + ", changement_organe=" + changement_organe + ", perte=" + perte + ", dh_creation='" + dh_creation + '\'' + ", dh_derniere_maj='" + dh_derniere_maj + '\'' + ", intervenant_id=" + intervenant_id + ", activite_code=" + activite_code + ", machine_code='" + machine_code + '\'' + ", cause_defaut_code='" + cause_defaut_code + '\'' + ", cause_objet_code='" + cause_objet_code + '\'' + ", symptome_defaut_code='" + symptome_defaut_code + '\'' + ", symptome_objet_code='" + symptome_objet_code + '\'' + '}';
    }
}
