package yan.candaes.gmagro.dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yan.candaes.gmagro.beans.Utilisateur;
import yan.candaes.gmagro.net.WSConnexionHTTPS;


public class DaoUtilisateur {
    private static DaoUtilisateur instance = null;
    private final List<Utilisateur> Utilisateurs;
    private Utilisateur logUtilisateur;
//    private final List<Soiree> soirees;
//    private final ObjectMapper mapper = new ObjectMapper();


    private DaoUtilisateur() {
//        soirees = new ArrayList<>();
        Utilisateurs = new ArrayList<>();
    }

    public static DaoUtilisateur getInstance() {
        if (instance == null) {
            instance = new DaoUtilisateur();
        }
        return instance;
    }

    public Utilisateur getLogUtilisateur() {
        return logUtilisateur;
    }
//
//    public List<Soiree> getLocalSoirees() {
//        return soirees;
//    }

//    public List<Utilisateur> getLocalUtilisateurs() {
//        return Utilisateurs;
//    }

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
                        wsRetour = jo.getBoolean("success");
                        jo = jo.getJSONObject("response");
                        logUtilisateur = new Utilisateur(
                                jo.getString("login"),
                                jo.getString("nom"),
                                jo.getString("prenom")
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                delegate.WSRequestIsDone(wsRetour);
            }
        };
        ws.execute(request);
    }

//    public void getLesSoirees(String request, Delegate delegate) {
//        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
//            @Override
//            public void onPostExecute(String s) {
//                boolean wsRetour = false;
//                if (s != null) {
//                try {
//
//                    JSONObject jo = new JSONObject(s);
//                    JSONArray ja = jo.getJSONArray("response");
//         /*             soirees =  mapper.readValue(ja.toString(),  new TypeReference<List<Soiree>>(){});
//                      soirees =  mapper.readValue(ja.toString(), List<Soiree>.class);*/
//                    soirees.clear();
//                    for (int i = 0; i < ja.length(); i++) {
//                        //soirees.add(mapper.readValue((DataInput) ja.getJSONObject(i), Soiree.class));
//                        jo = ja.getJSONObject(i);
//
//                        soirees.add(
//                                new Soiree(
//                                        jo.getString("id"),
//                                        jo.getString("libelleCourt"),
//                                        jo.getString("descriptif"),
//                                        jo.getString("dateDebut"),
//                                        jo.getString("heureDebut"),
//                                        jo.getString("adresse"),
//                                        jo.getString("latitude"),
//                                        jo.getString("longitude"),
//                                        jo.getString("login")
//                                )
//                        );
//                    }
//                    jo = new JSONObject(s);
//                    wsRetour = jo.getBoolean("success");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }}
//                delegate.WSRequestIsDone(wsRetour);
//            }
//        };
//        ws.execute(request);
//    }


//    public void getLesUtilisateurs(String request, Delegate delegate) {
//        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
//            @Override
//            public void onPostExecute(String s) {
//                boolean wsRetour = false;
//                if (s != null) {
//                try {
//
//                    JSONObject jo = new JSONObject(s);
//                    JSONArray ja = jo.getJSONArray("response");
//                    /*  Utilisateurs =  mapper.readValue(ja.toString(),  new TypeReference<List<Utilisateur>>(){});
//                      Utilisateurs =  mapper.readValue(ja.toString(), List<Soiree>.class);*/
//                    Utilisateurs.clear();
//                    for (int i = 0; i < ja.length(); i++) {
//                        //Utilisateurs.add(mapper.readValue((DataInput) ja.getJSONObject(i), Utilisateur.class));
//                        jo = ja.getJSONObject(i);
//
//                        Utilisateurs.add(
//                                new Utilisateur(
//                                        jo.getString("login"),
//                                        jo.getString("nom"),
//                                        jo.getString("prenom"),
//                                        jo.getString("ddn"),
//                                        jo.getString("mail")
//                                )
//                        );
//                    }
//                    jo = new JSONObject(s);
//                    wsRetour = jo.getBoolean("success");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }}
//                delegate.WSRequestIsDone(wsRetour);
//            }
//        };
//        ws.execute(request);
//
//    }
}