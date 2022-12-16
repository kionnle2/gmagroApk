package yan.candaes.gmagro.dao;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yan.candaes.gmagro.beans.Ascod;
import yan.candaes.gmagro.beans.Intervention;
import yan.candaes.gmagro.beans.Machine;
import yan.candaes.gmagro.net.WSConnexionHTTPS;

public class DaoIntervention {

    private static DaoIntervention instance = null;
    private final List<Intervention> lesInterventions;
    private final List<Ascod> lesActivites;
    private final List<Ascod> lesCO;
    private final List<Ascod> lesCD;
    private final List<Ascod> lesSO;
    private final List<Ascod> lesSD;
    private final List<Machine> lesMachines;
    // private final ObjectMapper mapper = new ObjectMapper();

    public DaoIntervention() {
        lesInterventions = new ArrayList<>();
        lesActivites = new ArrayList<>();
        lesCO = new ArrayList<>();
        lesSO = new ArrayList<>();
        lesCD = new ArrayList<>();
        lesSD = new ArrayList<>();
        lesMachines = new ArrayList<>();

    }

    public static DaoIntervention getInstance() {
        if (instance == null) {
            instance = new DaoIntervention();
        }
        return instance;
    }

    public List<Ascod> getLesActivites() {
        return lesActivites;
    }

    public List<Ascod> getLesCO() {
        return lesCO;
    }

    public List<Ascod> getLesCD() {
        return lesCD;
    }

    public List<Ascod> getLesSO() {
        return lesSO;
    }

    public List<Ascod> getLesSD() {
        return lesSD;
    }

    public List<Machine> getLesMachines() {
        return lesMachines;
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
                    }
                }
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
                    wsRetour = true;

                }
                delegate.WSRequestIsDone(wsRetour);
            }
        };
        ws.execute("controller=deconnexion");
    }

    public void getAscod(Delegate delegate) {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            public void onPostExecute(String s) {
                boolean wsRetour = false;
                if (s != null) {
                    try {

                        JSONObject jo = new JSONObject(s);
                        jo = jo.getJSONObject("response");
                        JSONArray jascod = jo.getJSONArray("Activite");
                        JSONObject ji;
                        lesActivites.clear();
                        for (int i = 0; i < jascod.length(); i++) {
                            ji = jascod.getJSONObject(i);
                            lesActivites.add(new Ascod(ji.getString("code"), ji.getString("libelle")));
                        }
                        jascod = jo.getJSONArray("SO");
                        lesSO.clear();
                        for (int i = 0; i < jascod.length(); i++) {
                            ji = jascod.getJSONObject(i);
                            lesSO.add(new Ascod(ji.getString("code"), ji.getString("libelle")));
                        }
                        jascod = jo.getJSONArray("SD");
                        lesSD.clear();
                        for (int i = 0; i < jascod.length(); i++) {
                            ji = jascod.getJSONObject(i);
                            lesSD.add(new Ascod(ji.getString("code"), ji.getString("libelle")));
                        }
                        jascod = jo.getJSONArray("CO");
                        lesCO.clear();
                        for (int i = 0; i < jascod.length(); i++) {
                            ji = jascod.getJSONObject(i);
                            lesCO.add(new Ascod(ji.getString("code"), ji.getString("libelle")));
                        }
                        jascod = jo.getJSONArray("CD");
                        lesCD.clear();
                        for (int i = 0; i < jascod.length(); i++) {
                            ji = jascod.getJSONObject(i);
                            lesCD.add(new Ascod(ji.getString("code"), ji.getString("libelle")));
                        }

                        jo = new JSONObject(s);
                        wsRetour = jo.getBoolean("success");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                delegate.WSRequestIsDone(wsRetour);
            }
        };
        ws.execute("controller=ascod&action=getALL");
    }

    public void getMachines(Delegate delegate) {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            public void onPostExecute(String s) {
                boolean wsRetour = false;
                if (s != null) {
                    try {

                        JSONObject jo = new JSONObject(s);
                        JSONArray ja = jo.getJSONArray("response");

                        lesMachines.clear();
                        //  lesMachines.add( mapper.readValue(ja,);
                        for (int i = 0; i < ja.length(); i++) {
                            jo = ja.getJSONObject(i);
                            lesMachines.add(new Machine(
                                    jo.getString("code"),
                                    jo.getString("libelle"),
                                    jo.getString("numero_serie"),
                                    jo.getString("uai"),
                                    jo.getString("type_machine_code")
                            ));
                        }
                        jo = new JSONObject(s);
                        wsRetour = jo.getBoolean("success");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                delegate.WSRequestIsDone(wsRetour);
            }
        };
        ws.execute("controller=machine&action=getA");
    }


}
