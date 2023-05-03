package yan.candaes.gmagro.beans;

import java.io.Serializable;

public class Machine implements Serializable {
    private String code;
    private String numero_serie;
    private String type_machine_code;
    private String photo;


    public Machine(String code, String numero_serie, String type_machine_code,String photo) {
        this.code = code;
        this.numero_serie = numero_serie;
        this.type_machine_code = type_machine_code;
        this.photo=photo;
    }

    public String getNumero_serie() {
        return numero_serie;
    }

    public String getCode() {
        return code;
    }

    public String getType_machine_code() {
        return type_machine_code;
    }

    public String getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        return code;
    }
}
