package yan.candaes.gmagro.dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yan.candaes.gmagro.beans.Utilisateur;
import yan.candaes.gmagro.beans.UtilisateurIntervenue;
import yan.candaes.gmagro.net.WSConnexionHTTPS;
import yan.candaes.gmagro.ui.MainActivity;


public class DaoUtilisateur {
    private static DaoUtilisateur instance = null;
    private final List<Utilisateur> lesIntervenents;
    private final List<UtilisateurIntervenue> lesIntersInter;
//    private final ObjectMapper mapper = new ObjectMapper();


    private DaoUtilisateur() {
        lesIntervenents = new ArrayList<>();
        lesIntersInter = new ArrayList<>();
    }

    public static DaoUtilisateur getInstance() {
        if (instance == null) {
            instance = new DaoUtilisateur();
        }
        return instance;
    }

    public List<Utilisateur> getLesIntervenantsLoc() {
        return lesIntervenents;
    }

    //list des Intervenants qui sont intervenue sur l'intervention cible
    // Soit l'intervention que l'on créé soit l'intervention que l'on continue
    public List<UtilisateurIntervenue> getLocalIntervenantIntervention() {
        return lesIntersInter;
    }

    public void simpleRequest(String request, Delegate delegate) {
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
        ws.execute(request);
    }


    public void connexionAccount(String request, Delegate delegate) {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            public void onPostExecute(String s) {
                Utilisateur wsRetour = null;
                if (s != null) {
                    try {
                        JSONObject jo = new JSONObject(s);

                        jo = jo.getJSONObject("response");
                        wsRetour = new Utilisateur(
                                jo.getInt("id"),
                                jo.getString("mail"),
                                jo.getString("nom"),
                                jo.getString("prenom"),
                                jo.getString("site_uai"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                delegate.WSRequestIsDone(wsRetour);

            }
        };
        ws.execute(request);
    }

    public void getLesIntervenantsBBD(Delegate delegate) {

        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            public void onPostExecute(String s) {
                boolean wsRetour = false;
                if (s != null) {
                    try {

                        JSONObject jo = new JSONObject(s);
                        JSONArray ja = jo.getJSONArray("response");
                        lesIntervenents.clear();
                        for (int i = 0; i < ja.length(); i++) {
                            jo = ja.getJSONObject(i);
                            lesIntervenents.add(new Utilisateur(
                                    jo.getInt("id"),
                                    jo.getString("mail"),
                                    jo.getString("nom"),
                                    jo.getString("prenom"),
                                    ""
                            ));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                delegate.WSRequestIsDone(wsRetour);

            }
        };
        ws.execute("controller=user&action=getBy&ua=" + MainActivity.logU.getSite_uai());
    }

    /**
     * @param id       id de l'interventions à qui on souhaite récup les intervenants qui on participé
     * @param delegate permet d'actualiser la listeView des interventions (notifydatasetchage)
     */
    public void getIntervenantsByIntervenstionId(int id, Delegate delegate) {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            public void onPostExecute(String s) {
                boolean wsRetour = false;
                if (s != null) {


                    JSONObject jo = null;
                    try {
                        jo = new JSONObject(s);
                        wsRetour = jo.getBoolean("success");

                        if (wsRetour) {
                            JSONArray ja = jo.getJSONArray("response");
                            lesIntersInter.clear();
                            for (int i = 0; i < ja.length(); i++) {
                                jo = ja.getJSONObject(i);
                                //transform time dd:hh:mm to mm
                                String time = jo.getString("tps_passe");


                                //lesIntersInter est une liste d'interInter
                                //un interInter c'est un id d'intervenant/utilisateur, un id d'intervention et un temp passé
                                // pour chaque id intervenant je recup l'objet entier dans la bdd
                                lesIntersInter.add(new UtilisateurIntervenue(
                                        new Utilisateur((long) jo.getInt("intervenant_id"), "", jo.getString("nom"), jo.getString("prenom"), ""), time));
                            }
                            jo = new JSONObject(s);
                            wsRetour = jo.getBoolean("success");
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

                delegate.WSRequestIsDone(wsRetour);
            }
        };
        ws.execute("controller=user&action=getByInterId&id=" + id);
    }
}