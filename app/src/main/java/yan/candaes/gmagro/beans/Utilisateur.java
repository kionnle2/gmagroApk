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

    public String getLogin() {
        return login;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    @Override
    public String toString() {
        return nom + " " + prenom;
    }

    public String getUai() {
        return uai;
    }
}
