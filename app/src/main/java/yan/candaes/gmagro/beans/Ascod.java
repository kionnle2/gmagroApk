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

    public String getCode() {
        return code;
    }
}

/* TODO passable, get scod  with union
(select code lib "co"
uso=ion
select code lib "cd" ...)
 */