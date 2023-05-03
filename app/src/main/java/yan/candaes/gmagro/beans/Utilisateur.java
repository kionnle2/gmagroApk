package yan.candaes.gmagro.beans;

import java.io.Serializable;
import java.util.Objects;

// CONFuSION : UTILISATEUR = INTERVENANT
public class Utilisateur implements Serializable {
    long id;
    String login;
    String nom;
    String prenom;
    String site_uai;

    public Utilisateur(long id, String login, String nom, String prenom, String site_uai) {
        this.id = id;
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
        this.site_uai = site_uai;
    }

    public Utilisateur(String login, String nom, String prenom, String site_uai) {
    }
    public Utilisateur( long id,String nom, String prenom) {
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

    public String getSite_uai() {
        return site_uai;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
