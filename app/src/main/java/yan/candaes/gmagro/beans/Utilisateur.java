package yan.candaes.gmagro.beans;

import java.io.Serializable;

public class Utilisateur implements Serializable {
    String login;
    String nom;
    String prenom;
    String uai;

    public Utilisateur(String login, String nom, String prenom, String uai) {
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
        this.uai = uai;
    }


    @Override
    public String toString() {
        return nom + " " + prenom;
    }

    public String getUai() {
        return uai;
    }
}
