package yan.candaes.gmagro.dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yan.candaes.gmagro.beans.Utilisateur;
import yan.candaes.gmagro.net.WSConnexionHTTPS;


public class DaoUtilisateur {
    private static DaoUtilisateur instance = null;
    private final List<Utilisateur> lesIntervenants;
    public static Utilisateur logUtilisateur;
//    private final ObjectMapper mapper = new ObjectMapper();


    private DaoUtilisateur() {
        lesIntervenants = new ArrayList<>();
    }

    public static DaoUtilisateur getInstance() {
        if (instance == null) {
            instance = new DaoUtilisateur();
        }
        return instance;
    }

    public List<Utilisateur> getLesIntervenantsLoc() {
        return lesIntervenants;
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
                boolean wsRetour = false;
                if (s != null) {
                    try {
                        JSONObject jo = new JSONObject(s);

                        jo = jo.getJSONObject("response");
                        DaoUtilisateur.logUtilisateur = new Utilisateur(
                                jo.getString("login"),
                                jo.getString("nom"),
                                jo.getString("prenom"),
                                jo.getString("uai"));
                        jo = new JSONObject(s);
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

    public void getLesIntervenantsBBD(Delegate delegate) {

        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            public void onPostExecute(String s) {
                boolean wsRetour = false;
                if (s != null) {
                    try {

                        JSONObject jo = new JSONObject(s);
                        JSONArray ja = jo.getJSONArray("response");
                        lesIntervenants.clear();
                        for (int i = 0; i < ja.length(); i++) {
                            jo = ja.getJSONObject(i);
                            lesIntervenants.add(new Utilisateur(
                                    jo.getString("login"),
                                    jo.getString("nom"),
                                    jo.getString("prenom"),
                                    jo.getString("uai")
                            ));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                delegate.WSRequestIsDone(wsRetour);
            }
        };
        ws.execute("controller=user&action=getBy&ua="+DaoUtilisateur.logUtilisateur.getUai());
    }
}