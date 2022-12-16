package yan.candaes.gmagro.beans;

public class Ascod {
    private String code;
    private String libelle;

    public Ascod(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return  libelle ;
    }
}
