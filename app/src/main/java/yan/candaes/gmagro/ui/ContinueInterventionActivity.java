package yan.candaes.gmagro.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
        DaoIntervention.getInstance().getAscod(new Delegate() {
            @Override
            public void WSRequestIsDone(Object result) {
            }
        });


        ////////////////////////////////////    DECONNEXION   ////////////////////////////////////////
        findViewById(R.id.continueInterventionBtnDeco).setOnClickListener(v -> deconnexion());
        ////////////////////////////////////    GET INTENT   ////////////////////////////////////////
        Intervention intervention = (Intervention) this.getIntent().getSerializableExtra("inter");

        ////////////////////////////////////    IMAGE   //////////////////////////////////////////
        //get image by typeMachine
        // Récupérer la machine correspondant à l'intervention
        Machine machine = null;
        for (Machine m : DaoIntervention.getInstance().getLesMachines()) {
            if (m.getCode().equals(intervention.getMachine_code())) {
                machine = m;
            }
        }

// Charger l'image de la machine dans l'ImageView
        if (machine != null) {
            ImageView ivBasicImage = findViewById(R.id.continueInterventionImageView);
            Machine finalMachine = machine;
            Picasso.get().load("http://sio.jbdelasalle.com/~ycandaes/gmagrowww/images/photos/" + machine.getPhoto())
                    .resize(300, 300)
                    .centerCrop()
                    .into(ivBasicImage);
        }


        ////////////////////////////////////    LISTVIEW   //////////////////////////////////////////


        adaInter = new CustomAdapterContinueInter(this, lvlInterInterList);
        lvInterInter = findViewById(R.id.listViewCustomEditInter);
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
        ////////////////////////////////////    SPINNER   //////////////////////////////////////////
        /////////////////////////////    SPINNER  ANCIEN INTER   ///////////////////////////////////
        //retire les intervenant déjà présent

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
        ////////////////////////////////  SPINNER NEW INTER   //////////////////////////////////////

        Spinner tempInterSpin = (Spinner) findViewById(R.id.continueInterventionSpinnerTimeByInter);
        Spinner arretMachine = (Spinner) findViewById(R.id.continueInterventionSpinnerTempArret);


        //un adapter pour arret machine et ajout intervenant
        ArrayList<String> arraySpinnerI = new ArrayList<>();
        arraySpinnerI = Tools.fillTimeList(arraySpinnerI, false);
        ArrayAdapter<String> timeAdapterI = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinnerI);
        timeAdapterI.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tempInterSpin.setAdapter(timeAdapterI);

        ArrayList<String> arraySpinnerA = new ArrayList<>();
        arraySpinnerA = Tools.fillTimeList(arraySpinnerA, true);
        ArrayAdapter<String> timeAdapterA = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinnerA);
        timeAdapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arretMachine.setAdapter(timeAdapterA);


        /////////////////////////     DEPLACEMENT INTERVENENT LISTVIEW     /////////////////////////
        ///////////////////////////     AJOUTE INTERVENENT LISTVIEW      ///////////////////////////

        ((Button) findViewById(R.id.ContinueInterventionAddIntervenantBtnListIntervention)).setOnClickListener(v -> {
            if (spinIntervenant.getCount() != 0) {
                String newTime = (String) ((Spinner) findViewById(R.id.continueInterventionSpinnerTimeByInter)).getSelectedItem();
                int newPos = ((Spinner) findViewById(R.id.continueInterventionSpinnerTimeByInter)).getSelectedItemPosition();

                Utilisateur u = (Utilisateur) spinIntervenant.getSelectedItem();
                UtilisateurIntervenue ui = new UtilisateurIntervenue(u, "0:0");
                ui.setNouveauTemp(newTime);
                ui.setNouveauTempPosition(newPos + 1);
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

        // TEXTVIEW TOP
        String stringTop = intervention.getMachine_code() + "/" + typeMachineCodeToLibelle(intervention.getMachine_code()) +
                "\nde " + intervention.getDh_debut() + "\na   " + intervention.getDh_derniere_maj();
        ((TextView) findViewById(R.id.continueInterTVNomInter)).setText(stringTop);
        ((TextView) findViewById(R.id.continueInterventionTVSO)).setText(intervention.getSymptome_objet_code());
        ((TextView) findViewById(R.id.continueInterventionTVSD)).setText(intervention.getSymptome_defaut_code());
        ((TextView) findViewById(R.id.continueInterventionTVCO)).setText(intervention.getCause_objet_code());
        ((TextView) findViewById(R.id.continueInterventionTVCD)).setText(intervention.getCause_defaut_code());
        ((TextView) findViewById(R.id.continueInterventionTVCommentaire)).setText(intervention.getCommentaire());

        //CHECKBOX
        if (intervention.getPerte()) {
            ((CheckBox) findViewById(R.id.continueInterventionCBPerte)).setChecked(true);
            findViewById(R.id.continueInterventionCBPerte).setEnabled(false);
        }

        if (intervention.getChangement_organe()) {
            ((CheckBox) findViewById(R.id.continueInterventionCBChangementOrgane)).setChecked(true);
            findViewById(R.id.continueInterventionCBChangementOrgane).setEnabled(false);
        }

        CheckBox isArret = findViewById(R.id.continueInterventoinCBMachineArretee);
        if (!intervention.getTemp_arret().equals("00:00:00")) {
            isArret.setChecked(true);
            findViewById(R.id.continueInterventoinCBMachineArretee).setEnabled(false);
            arretMachine.setVisibility(View.VISIBLE);
            isArret.setText(isArret.getText().toString() + " : " + intervention.getTemp_arret() + "min");

        }
        // affiche le choix de l'heure de fin et du temp d'arret si la case est coché
        TextView heureF = findViewById(R.id.continueInterventionBtnInterFin);
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

        heureF.setOnClickListener(v -> {
            DateAndTimePicker dateTimePick = new DateAndTimePicker(this, heureF);
            dateTimePick.dateTimeAsk();
        });


        isArret.setOnClickListener(v -> {
            if (isArret.isChecked()) {
                arretMachine.setVisibility(View.VISIBLE);
            } else {
                arretMachine.setVisibility(View.INVISIBLE);
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
            String tempAr = "0:0";
            try {
                // j'execute tout ce qui peux lever une exception enssemble pour arreter l'action en cours en une seul fois (action: post Update intervention)
                if (boolFin) {
                    hFin = ((TextView) findViewById(R.id.continueInterventionBtnInterFin)).getText().toString();
                }
                if (boolArr) {
                    tempAr = arretMachine.getSelectedItem().toString();
                }
                DaoIntervention.getInstance().updateUneInterventions(intervention.getId(), intervention.getCommentaire() + " " + comm, boolOrg, boolPer, hFin, tempAr, intervention.getTemp_arret(), lvlInterInterList,

                        new Delegate() {
                            @Override
                            public void WSRequestIsDone(Object result) {
                                if ((boolean) result) {
                                    Toast.makeText(getApplicationContext(), "Update Réussie", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "échec de la mise à jour, vérifiez la cohérence de vos données.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                finishActivity(2);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                Log.e("TAGTAG", "onCreate: JsonProcessingException ");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("TAGTAG", "onCreate: JSONException ");
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

    private String typeMachineCodeToLibelle(String code) {
        String libReturn = "ERROR";

        for (Machine m : DaoIntervention.getInstance().getLesMachines()) {
            if (m.getCode().equals(code)) {
                libReturn = m.getNumero_serie();
            }
        }

        return libReturn;
    }
}
