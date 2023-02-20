package yan.candaes.gmagro.beans;

import java.io.Serializable;

public class Machine implements Serializable {
    private String code;
    private String date_fab;
    private String numero_serie;
    private String uai;
    private String type_machine_code;

    public Machine(String code, String date_fab, String numero_serie, String uai, String type_machine_code) {
        this.code = code;
        this.date_fab = date_fab;
        this.numero_serie = numero_serie;
        this.uai = uai;
        this.type_machine_code = type_machine_code;
    }

    public String getCode() {
        return code;
    }

    public String getType_machine_code() {
        return type_machine_code;
    }

    @Override
    public String toString() {
        return code;
    }
}
