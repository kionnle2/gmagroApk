package yan.candaes.gmagro.dao;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yan.candaes.gmagro.beans.Intervention;
import yan.candaes.gmagro.net.WSConnexionHTTPS;

public class DaoIntervention {


    private static DaoIntervention instance = null;
    private final List<Intervention> lesInterventions;

    public DaoIntervention() {
        lesInterventions = new ArrayList<>();
    }

    public static DaoIntervention getInstance() {
        if (instance == null) {
            instance = new DaoIntervention();
        }
        return instance;
    }


    public List<Intervention> getLesInterventionsLOC() {
        return lesInterventions;
    }

    public void getLesInterventionsBDD(Delegate delegate) {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            public void onPostExecute(String s) {
                boolean wsRetour = false;
                if (s != null) {
                try {

                    JSONObject jo = new JSONObject(s);
                    JSONArray ja = jo.getJSONArray("response");

                    lesInterventions.clear();
                    for (int i = 0; i < ja.length(); i++) {
                        //soirees.add(mapper.readValue((DataInput) ja.getJSONObject(i), Soiree.class));
                        jo = ja.getJSONObject(i);

                        lesInterventions.add(
                                new Intervention(
                                        jo.getInt("id")
                                )
                        );
                    }
                    jo = new JSONObject(s);
                    wsRetour = jo.getBoolean("success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }}
                delegate.WSRequestIsDone(wsRetour);
            }
        };
        ws.execute("controller=inter&action=getA");
    }

    public void deco(Delegate delegate) {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            public void onPostExecute(String s) {
                boolean wsRetour = false;
                if (s != null) {
                    wsRetour=true;

                    }
                delegate.WSRequestIsDone(wsRetour);
            }
        };
        ws.execute("controller=deconnexion");
    }
}
