package yan.candaes.gmagro.beans;

import java.io.Serializable;

public class Utilisateur implements Serializable {
    long id;
    String login;
    String nom;
    String prenom;
    String uai;

    public Utilisateur(long id,String login, String nom, String prenom, String uai) {
        this.id=id;
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
        this.uai = uai;
    }

    public Utilisateur(String login, String nom, String prenom, String uai) {
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

    public long getId() {
return id;
    }
}
