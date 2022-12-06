package yan.candaes.gmagro.beans;

public class Utilisateur {
    String login;
    String nom;
    String prenom;

    public Utilisateur(String login,String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }
}
