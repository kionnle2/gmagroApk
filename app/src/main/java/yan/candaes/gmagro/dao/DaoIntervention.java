package yan.candaes.gmagro.dao;


import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yan.candaes.gmagro.beans.Ascod;
import yan.candaes.gmagro.beans.Intervention;
import yan.candaes.gmagro.beans.Machine;
import yan.candaes.gmagro.beans.Utilisateur;
import yan.candaes.gmagro.beans.UtilisateurIntervenue;
import yan.candaes.gmagro.net.WSConnexionHTTPS;
import yan.candaes.gmagro.tools.Tools;
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
                            boolean perte = false;

                            changeOrgane = jo.getString("changement_organe").toString().equals("1");

                            perte = jo.getString("perte").equals("1");
                            Log.i("TAGTAG", "onPostExecute: " + perte + " " + jo.getString("perte"));
                            lesInterventions.add(new Intervention(jo.getInt("id"),
                                    jo.getString("dh_debut"),
                                    jo.getString("dh_fin"),
                                    jo.getString("commentaire"),
                                    jo.getString("temps_arret"),
                                    changeOrgane,
                                    perte,
                                    jo.getString("dh_creation"),
                                    jo.getString("dh_derniere_maj"),
                                    jo.getLong("intervenant_id"),
                                    jo.getString("activite_code").charAt(0),
                                    jo.getString("machine_code"),
                                    jo.getString("cause_defaut_code"),
                                    jo.getString("cause_objet_code"),
                                    jo.getString("symptome_defaut_code"),
                                    jo.getString("symptome_objet_code"),
                                    jo.getString("type_machine")));
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
                        lesActivites.add(new Ascod("", "Activité"));
                        for (int i = 0; i < jascod.length(); i++) {
                            ji = jascod.getJSONObject(i);
                            lesActivites.add(new Ascod(ji.getString("code"), ji.getString("libelle")));
                        }
                        jascod = jo.getJSONArray("SO");
                        lesSO.clear();
                        lesSO.add(new Ascod("", "Symptom Object"));
                        for (int i = 0; i < jascod.length(); i++) {
                            ji = jascod.getJSONObject(i);
                            lesSO.add(new Ascod(ji.getString("code"), ji.getString("libelle")));
                        }
                        jascod = jo.getJSONArray("SD");
                        lesSD.clear();
                        lesSD.add(new Ascod("", "Symptom Default"));
                        for (int i = 0; i < jascod.length(); i++) {
                            ji = jascod.getJSONObject(i);
                            lesSD.add(new Ascod(ji.getString("code"), ji.getString("libelle")));
                        }
                        jascod = jo.getJSONArray("CO");
                        lesCO.clear();
                        lesCO.add(new Ascod("", "Cause Object"));

                        for (int i = 0; i < jascod.length(); i++) {
                            ji = jascod.getJSONObject(i);
                            lesCO.add(new Ascod(ji.getString("code"), ji.getString("libelle")));
                        }
                        jascod = jo.getJSONArray("CD");
                        lesCD.clear();
                        lesCD.add(new Ascod("", "Cause Default"));

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
                        lesMachines.add(new Machine("les Machines","","",""));
                        //  lesMachines.add( mapper.readValue(ja,);
                        for (int i = 0; i < ja.length(); i++) {
                            jo = ja.getJSONObject(i);
                            lesMachines.add(new Machine(jo.getString("code"), jo.getString("numero_serie"), jo.getString("type_machine_code"), jo.getString("photo")));
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

    public void insertUneInterventions(String hDeb, String hFin, String comm, String tArr, Boolean org, Boolean per, String acti, String mach, String cd, String co, String sd, String so,
                                       ArrayList<UtilisateurIntervenue> interLvList, Delegate delegate) throws IOException, JSONException, ParseException {
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

        Date convDhDebut = Tools.sdfFR.parse(hDeb);
        hDeb = Tools.sdfEN.format(convDhDebut);
        if (hFin!=null) {
            Date convDhFin = Tools.sdfFR.parse(hFin);
            hFin = Tools.sdfEN.format(convDhFin);
        }

        Map<String, Object> intervention = new HashMap<>();
        intervention.put("dh_debut", hDeb);
        intervention.put("dh_fin", hFin);
        intervention.put("commentaire", comm);
        intervention.put("temps_arret", tArr);
        intervention.put("changement_organe", org);
        intervention.put("perte", per);
        intervention.put("intervenant_id", MainActivity.logU.getId());
        intervention.put("activite_code", acti);
        intervention.put("machine_code", mach);
        intervention.put("cause_defaut_code", cd);
        intervention.put("cause_objet_code", co);
        intervention.put("symptome_defaut_code", sd);
        intervention.put("symptome_objet_code", so);
        Log.d("TAGTAGTAGTAGTAGTAG", "addUneInterventions: " + intervention);

        ObjectMapper mapper = new ObjectMapper();
        JSONObject jSendI = new JSONObject(intervention);
        Log.d("jSendI.toString()", "addUneInterventions: " + jSendI);

        JSONArray jSendII = new JSONArray(mapper.writeValueAsString(interLvList));
        ws.execute("controller=inter&action=add", jSendI.toString(), jSendII.toString());
    }


    public void updateUneInterventions(int id, String commentaire, Boolean boolOrg, Boolean boolPer, String hFin, String ntempAr,String atempAr, ArrayList<UtilisateurIntervenue> interInterList, Delegate delegate) throws JsonProcessingException, JSONException, ParseException {

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
        if (hFin!=null) {
            Date convDhDebut = Tools.sdfFR.parse(hFin);
            hFin = Tools.sdfEN.format(convDhDebut);
        }

        Map<String, Object> intervention = new HashMap<>();
        intervention.put("id", id);
        intervention.put("commentaire", commentaire);
        intervention.put("changement_organe", boolOrg);
        intervention.put("perte", boolPer);
        intervention.put("dh_fin", hFin);
        intervention.put("nTemps_arret", ntempAr);
        intervention.put("aTemps_arret", atempAr);
        Log.d("TAGTAGTAGTAGTAGTAG", "updateUneInterventions: " + intervention);

        ObjectMapper mapper = new ObjectMapper();
        JSONObject jSendI = new JSONObject(intervention);
        Log.d("jSendI.toString()", "updateUneInterventions: " + jSendI);


        JSONArray jSendII = new JSONArray(mapper.writeValueAsString(interInterList));
        Log.d("jSendII.toString()", "updateUneInterventions: " + jSendII);

        ws.execute("controller=inter&action=update&id=" + id, jSendI.toString(), jSendII.toString());
    }
}
