package yan.candaes.gmagro.dao;


import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yan.candaes.gmagro.beans.Ascod;
import yan.candaes.gmagro.beans.Intervention;
import yan.candaes.gmagro.beans.Machine;
import yan.candaes.gmagro.beans.Utilisateur;
import yan.candaes.gmagro.beans.UtilisateurIntervenue;
import yan.candaes.gmagro.net.WSConnexionHTTPS;
import yan.candaes.gmagro.ui.MainActivity;

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
                            boolean changeOrgane = false;
                            try{
                                changeOrgane=jo.getInt("changement_organe")==1;
                            }catch (JSONException e){}
                            boolean perte = false;
                            try{
                                perte=jo.getInt("perte")==1;
                            }catch (JSONException e){}
                            lesInterventions.add(new Intervention(jo.getInt("id"), jo.getString("dh_debut"), jo.getString("dh_fin"), jo.getString("commentaire"), jo.getInt("temp_arret"), changeOrgane,
                                    perte,jo.getString("dh_creation"), jo.getString("dh_derniere_maj"), jo.getLong("intervenant_id"), jo.getString("activite_code").charAt(0), jo.getString("machine_code"), jo.getString("cause_defaut_code"), jo.getString("cause_objet_code"), jo.getString("symptome_defaut_code"), jo.getString("symptome_objet_code")));
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
        ws.execute("controller=ascod&action=getAll");
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
                            lesMachines.add(new Machine(jo.getString("code"), jo.getString("libelle"), jo.getString("numero_serie"), jo.getString("uai"), jo.getString("type_machine_code")));
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
        ws.execute("controller=machine&action=getAll");
    }

    public void insertUneInterventions(String hDeb, String hFin, String comm,int tArr, Boolean org, Boolean per,String acti,String mach, String cd,String co,String sd, String so,
                                       ArrayList<UtilisateurIntervenue> interLvList, Delegate delegate) throws IOException, JSONException {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            public void onPostExecute(String s) {
                boolean wsRetour = false;
                if (s != null) {
                    try {
                        JSONObject jo = new JSONObject(s);
                        wsRetour = jo.getBoolean("success");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                delegate.WSRequestIsDone(wsRetour);
            }
        };

        ObjectMapper mapper = new ObjectMapper();

        Intervention inter = new Intervention(1, hDeb, hFin, comm, tArr, org, per, "now", "now", MainActivity.logU.getId(), acti.charAt(0), mach, cd, co, sd, so);

        JSONObject jSendI = new JSONObject(mapper.writeValueAsString(inter));
        Log.d("jSendI.toString()", "insertUneInterventions: " + jSendI);
        JSONArray jSendII = new JSONArray(mapper.writeValueAsString(interLvList));


        ws.execute("controller=inter&action=add", jSendI.toString(), jSendII.toString());
    }


    public void getInterventionsById(int id, Delegate delegate) {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            public void onPostExecute(String s) {
                boolean wsRetour = false;
                if (s != null) {
                    try {

                        JSONObject jo = new JSONObject(s);
                        jo = jo.getJSONObject("response");
                        new Intervention(jo.getInt("id"),
                                jo.getString("dh_debut"),
                                jo.getString("dh_fin"),
                                jo.getString("commentaire"),
                                jo.getInt("temp_arret"), jo.getBoolean("changement_organe"), jo.getBoolean("perte"), jo.getString("dh_creation"), jo.getString("dh_derniere_maj"), jo.getLong("intervenant_id"), jo.getString("activite_code").charAt(0), jo.getString("machine_code"), jo.getString("cause_defaut_code"), jo.getString("cause_objet_code"), jo.getString("symptome_defaut_code"), jo.getString("symptome_objet_code"));


                        jo = new JSONObject(s);
                        wsRetour = jo.getBoolean("success");

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

                delegate.WSRequestIsDone(wsRetour);

            }
        };
        ws.execute("controller=inter&action=getById");
    }


    public void updateUneInterventions(int id, String commentaire, Boolean boolFin, Boolean boolArr, Boolean boolOrg, Boolean boolPer, String hFin, int tempAr, ArrayList<UtilisateurIntervenue> interInterList, Delegate delegate) throws JsonProcessingException, JSONException {

        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            public void onPostExecute(String s) {
                boolean wsRetour = false;
                if (s != null) {
                    try {
                        JSONObject jo = new JSONObject(s);
                        wsRetour = jo.getBoolean("success");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                delegate.WSRequestIsDone(wsRetour);
            }
        };
        Map<String,Object>intervention=new HashMap<>();
        intervention.put("id",id);
        intervention.put("commentaire",commentaire);
        intervention.put("boolFin",boolFin);
        intervention.put("boolArr",boolArr);
        intervention.put("boolOrg",boolOrg);
        intervention.put("boolPer",boolPer);
        intervention.put("hFin",hFin);
        intervention.put("tempAr",tempAr);

        ObjectMapper mapper = new ObjectMapper();
        JSONObject jSendI = new JSONObject(intervention);
        Log.d("jSendI.toString()", "updateUneInterventions: " + jSendI);
        JSONArray jSendII = new JSONArray(mapper.writeValueAsString(interInterList));
        Log.d("jSendII.toString()", "updateUneInterventions: " + jSendII);

        ws.execute("controller=inter&action=update&id="+id, jSendI.toString(), jSendII.toString());
    }
}
