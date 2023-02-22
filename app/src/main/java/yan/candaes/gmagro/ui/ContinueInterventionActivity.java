package yan.candaes.gmagro.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import yan.candaes.gmagro.R;
import yan.candaes.gmagro.beans.Ascod;
import yan.candaes.gmagro.beans.Intervention;
import yan.candaes.gmagro.beans.Machine;
import yan.candaes.gmagro.beans.Utilisateur;
import yan.candaes.gmagro.beans.UtilisateurIntervenue;
import yan.candaes.gmagro.dao.DaoIntervention;
import yan.candaes.gmagro.dao.DaoUtilisateur;
import yan.candaes.gmagro.dao.Delegate;
import yan.candaes.gmagro.tools.CustomAdapterContinueInter;
import yan.candaes.gmagro.tools.DateAndTimePicker;
import yan.candaes.gmagro.tools.Tools;

public class ContinueInterventionActivity extends AppCompatActivity {
    //pour le spinner, j'ai la liste des intervenents possible et l'adapter
    //je dois encore remplis la liste spinInterList sans les inter qui ont déjà participé

    //adapter spinner
    ArrayAdapter adaLesIntervenants;
    //adapter ListView
    CustomAdapterContinueInter adaInter;

    //liste pour spinner
    ArrayList<Utilisateur> spinInterList = new ArrayList<>();
    //liste pour listView
    ArrayList<UtilisateurIntervenue> lvlInterInterList = new ArrayList<>();
    //ListVIew des intervenentIntervenue
    ListView lvInterInter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_intervention);


        ////////////////////////////////////    DECONNEXION   ////////////////////////////////////////
        findViewById(R.id.continueInterventionBtnDeco).setOnClickListener(v -> deconnexion());
        ////////////////////////////////////    GET INTENT   ////////////////////////////////////////
        Intervention intervention = (Intervention) this.getIntent().getSerializableExtra("id");

        ////////////////////////////////////    IMAGE   //////////////////////////////////////////
        //get image by typeMachine
        ////////////////////////////////////    LISTVIEW   //////////////////////////////////////////


        adaInter = new CustomAdapterContinueInter(this, lvlInterInterList);
        lvInterInter = ((ListView) findViewById(R.id.listViewCustomEditInter));
        lvInterInter.setAdapter(adaInter);

        DaoUtilisateur.getInstance().getIntervenantsByIntervenstionId(intervention.getId(), new Delegate() {
            @Override
            public void WSRequestIsDone(Object result) {

                if ((boolean) result) {
                    lvlInterInterList.clear();
                    lvlInterInterList.addAll(DaoUtilisateur.getInstance().getLocalIntervenantIntervention());
                    adaInter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "aucun Intervenant", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ////////////////////////////////////    SPINNER   ///////////////////////////////////////////
        adaLesIntervenants = new ArrayAdapter(this, android.R.layout.simple_list_item_1, spinInterList);
        Spinner spinIntervenant = ((Spinner) findViewById(R.id.ContinueInterventionSpinInter));
        spinIntervenant.setAdapter(adaLesIntervenants);
        DaoUtilisateur.getInstance().getLesIntervenantsBBD(new Delegate() {
            @Override
            public void WSRequestIsDone(Object result) {
                spinInterList.addAll(DaoUtilisateur.getInstance().getLesIntervenantsLoc());
                for (UtilisateurIntervenue ui : lvlInterInterList) {
                    for (int i = 0; i < spinInterList.size(); i++) {
                        if (ui.getInter().getId() == spinInterList.get(i).getId())
                            spinInterList.remove(i);
                    }
                }


                adaLesIntervenants.notifyDataSetChanged();
            }
        });
        /////////////////////////     DEPLACEMENT INTERVENENT LISTVIEW     /////////////////////////
        ///////////////////////////     AJOUTE INTERVENENT LISTVIEW      ///////////////////////////

        ((Button) findViewById(R.id.ContinueInterventionAddIntervenantBtnListIntervention)).setOnClickListener(v -> {
            if (spinIntervenant.getCount() != 0) {
                int newTime = Integer.parseInt(((TextView) findViewById(R.id.ContinueInterventionTimeByInter)).getText().toString());
                Utilisateur u = (Utilisateur) spinIntervenant.getSelectedItem();
                UtilisateurIntervenue ui = new UtilisateurIntervenue(u, "0:0");
                ui.setNewTime(newTime);
                lvlInterInterList.add(ui);

                int i = spinIntervenant.getSelectedItemPosition();
                spinInterList.remove(i);

                adaLesIntervenants.notifyDataSetChanged();
                adaInter.notifyDataSetChanged();
            }
        });
        /////////////////////////      SUPPRIME INTERVENENT LISTVIEW      //////////////////////////

        lvInterInter.setOnItemLongClickListener((parent, view, position, id) -> {

            UtilisateurIntervenue i = (UtilisateurIntervenue) lvInterInter.getItemAtPosition(position);
            if (i.getTime() == "0:0") {
                Utilisateur u = i.getInter();
                spinInterList.add(u);
                lvlInterInterList.remove(position);

                adaLesIntervenants.notifyDataSetChanged();
                adaInter.notifyDataSetChanged();
            }
            return true;
        });
        ////////////////////////////    INNIT   CHECKBOX / TEXTVIEW     ////////////////////////////
        /* TODO on peut remplacer les listes d'ascod par des dictionnaires ou mettre les libelle au lieu des codes dans le constructeur de intervention*/

        // code machine, type machine, date heure début, la photo du type de machine.
        String stringTop = intervention.getMachine_code() + " "
                + ascodAndTypeMachineCodeToLibelle(intervention.getMachine_code(), "m") + " "
                + intervention.getDh_debut() + " "
                + intervention.getDh_derniere_maj();
        ((TextView) findViewById(R.id.continueInterTVNomInter)).setText(stringTop);
        ((TextView) findViewById(R.id.continueInterventionTVSO)).setText(ascodAndTypeMachineCodeToLibelle(intervention.getSymptome_objet_code(), "so"));
        ((TextView) findViewById(R.id.continueInterventionTVSD)).setText(ascodAndTypeMachineCodeToLibelle(intervention.getSymptome_defaut_code(), "sd"));
        ((TextView) findViewById(R.id.continueInterventionTVCO)).setText(ascodAndTypeMachineCodeToLibelle(intervention.getCause_objet_code(), "co"));
        ((TextView) findViewById(R.id.continueInterventionTVCD)).setText(ascodAndTypeMachineCodeToLibelle(intervention.getCause_defaut_code(), "cd"));
        ((TextView) findViewById(R.id.continueInterventionTVCommentaire)).setText(intervention.getCommentaire());
        if (intervention.getPerte()) {
            ((CheckBox) findViewById(R.id.continueInterventionCBPerte)).setChecked(true);
            findViewById(R.id.continueInterventionCBPerte).setEnabled(false);
        }
        if (intervention.getChangement_organe()) {
            ((CheckBox) findViewById(R.id.continueInterventionCBChangementOrgane)).setChecked(true);
            findViewById(R.id.continueInterventionCBChangementOrgane).setEnabled(false);
        }
        if (intervention.getTemp_arret() != "0" && intervention.getTemp_arret() != "0:0") {
            CheckBox cbArret = findViewById(R.id.continueInterventoinCBMachineArretee);
            cbArret.setChecked(true);
            findViewById(R.id.continueInterventoinCBMachineArretee).setEnabled(false);
            findViewById(R.id.continueInterventionTVTempArret).setVisibility(View.VISIBLE);
            Log.e("TAGTAGTAG", "onCreate: " + intervention.toString());
            cbArret.setText(cbArret.getText().toString() + " : " + intervention.getTemp_arret() + "min");

        }
        // affiche le choix de l'heure de fin et du temp d'arret si la case est coché
        TextView heureF = findViewById(R.id.continueInterventionBtnInterFin);
        heureF.setOnClickListener(v -> {
            DateAndTimePicker dateTimePick = new DateAndTimePicker(this, heureF);
            dateTimePick.dateTimeAsk();
        });
        CheckBox isTerminee = findViewById(R.id.continueInterventionCBInterventionTerminee);
        isTerminee.setOnClickListener(v -> {
            if (isTerminee.isChecked()) {
                heureF.setVisibility(View.VISIBLE);
                DateAndTimePicker dateTimePick = new DateAndTimePicker(this, heureF);
                dateTimePick.dateTimeAsk();
            } else {
                heureF.setVisibility(View.INVISIBLE);
            }
        });

        CheckBox isPause = findViewById(R.id.continueInterventoinCBMachineArretee);
        isPause.setOnClickListener(v -> {
            if (isPause.isChecked()) {
                findViewById(R.id.continueInterventionTVTempArret).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.continueInterventionTVTempArret).setVisibility(View.INVISIBLE);
            }
        });

/////////////////////////////////    BTN UPDATE INTERVENTION    ////////////////////////////////////
        ((Button) findViewById(R.id.continueInterventionUpdateIntervention)).setOnClickListener(v -> {
            String comm = ((TextView) findViewById(R.id.continueInterventionTVNewCommentaire)).getText().toString();
            Boolean boolFin = ((CheckBox) findViewById(R.id.continueInterventionCBInterventionTerminee)).isChecked();
            Boolean boolArr = ((CheckBox) findViewById(R.id.continueInterventoinCBMachineArretee)).isChecked();
            Boolean boolOrg = ((CheckBox) findViewById(R.id.continueInterventionCBChangementOrgane)).isChecked();
            Boolean boolPer = ((CheckBox) findViewById(R.id.continueInterventionCBPerte)).isChecked();
            String hFin = null;
            int tempAr = 0;
            try {
                // j'execute tout ce qui peux lever une exception enssemble pour arreter l'action en cours en une seul fois (action: post Update intervention)
                if (boolFin) {
                    hFin = ((TextView) findViewById(R.id.continueInterventionBtnInterFin)).getText().toString();
                }
                if (boolArr) {
                    tempAr = Integer.parseInt(((TextView) findViewById(R.id.continueInterventionTVTempArret)).getText().toString());
                }
                DaoIntervention.getInstance().updateUneInterventions(intervention.getId(), comm, boolFin, boolArr, boolOrg, boolPer, hFin, tempAr, lvlInterInterList,

                        new Delegate() {
                            @Override
                            public void WSRequestIsDone(Object result) {
                                Toast.makeText(getApplicationContext(), "Update Réussie", Toast.LENGTH_SHORT).show();
                            }
                        });
                finishActivity(2);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                Log.e("TAGTAG", "onCreate: JsonProcessingException ");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("TAGTAG", "onCreate: JSONException ");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("TAGTAG", "onCreate: IOException ");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TAGTAG", "onCreate: Exception ");
                Toast.makeText(this, "ERREUR, Veuiller verifier la coherence de vos données.", Toast.LENGTH_SHORT).show();
            }

        });


    }

    private void deconnexion() {
        DaoIntervention.getInstance().deco(new Delegate() {
            @Override
            public void WSRequestIsDone(Object result) {
                if ((Boolean) result) {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }

    private String ascodAndTypeMachineCodeToLibelle(String code, String type) {
        String libReturn = "ERROR";
        switch (type) {
            case "a":
                for (Ascod a : DaoIntervention.getInstance().getLesActivites()) {
                    if (a.getCode().equals(code)) {
                        libReturn = a.toString();
                    }
                }
                break;
            case "co":
                for (Ascod co : DaoIntervention.getInstance().getLesCO()) {
                    if (co.getCode().equals(code)) {
                        libReturn = co.toString();
                    }
                }
                break;
            case "cd":
                for (Ascod cd : DaoIntervention.getInstance().getLesCD()) {
                    if (cd.getCode().equals(code)) {
                        libReturn = cd.toString();
                    }
                }
                break;
            case "so":
                for (Ascod so : DaoIntervention.getInstance().getLesSO()) {
                    if (so.getCode().equals(code)) {
                        libReturn = so.toString();
                    }
                }
                break;
            case "sd":
                for (Ascod sd : DaoIntervention.getInstance().getLesSD()) {
                    if (sd.getCode().equals(code)) {
                        libReturn = sd.toString();
                    }
                }
                break;
            case "m":
                for (Machine m : DaoIntervention.getInstance().getLesMachines()) {
                    if (m.getCode().equals(code)) {
                        libReturn = m.getType_machine_code();
                    }
                }
                break;
        }
        return libReturn;
    }
}
/* TODO */