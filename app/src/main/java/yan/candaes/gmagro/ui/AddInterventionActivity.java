package yan.candaes.gmagro.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import yan.candaes.gmagro.beans.Machine;
import yan.candaes.gmagro.beans.Utilisateur;
import yan.candaes.gmagro.beans.UtilisateurIntervenue;
import yan.candaes.gmagro.dao.DaoIntervention;
import yan.candaes.gmagro.dao.DaoUtilisateur;
import yan.candaes.gmagro.dao.Delegate;
import yan.candaes.gmagro.tools.CustomAdapterAddInter;
import yan.candaes.gmagro.tools.DateAndTimePicker;

public class AddInterventionActivity extends AppCompatActivity {
    static Boolean isInnit = false;
    ArrayAdapter adaActivite;
    ArrayAdapter adaSO;
    ArrayAdapter adaSD;
    ArrayAdapter adaCO;
    ArrayAdapter adaCD;
    ArrayAdapter adaMachine;
    TextView heureD;
    TextView heureF;
    Spinner tempP;
    Spinner tempInter;
    ;

    //pour choisir la date et l'heure
    DateAndTimePicker dateTimePick;


    ArrayAdapter adaLesIntervenants;
    CustomAdapterAddInter adaLvInter;

    ArrayList<Utilisateur> interSpinList = new ArrayList<>();
    ArrayList<UtilisateurIntervenue> interLvList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_intervention);

        heureD = ((TextView) findViewById(R.id.addInterBtnHeureDebut));
        dateTimePick = new DateAndTimePicker(this, heureD);
        dateTimePick.dateTimeAsk();
        heureD.setOnClickListener(v -> {
            dateTimePick = new DateAndTimePicker(this, heureD);
            dateTimePick.dateTimeAsk();
        });
        heureF = ((TextView) findViewById(R.id.addInterBtnHeureFin));
        heureF.setOnClickListener(v -> {
            dateTimePick = new DateAndTimePicker(this, heureF);
            dateTimePick.dateTimeAsk();
        });
        // innit recupere les listes acsod et intervenants et atache les adapteurs
        innit();

//deconnexion
        findViewById(R.id.addInterBtnDeco).setOnClickListener(v -> deconnexion());
//spinner temp machine arret
        tempP = (Spinner) findViewById(R.id.addInterSpinnerTempArret);
        ArrayList<String> arraySpinner = new ArrayList<>();
        int minute = 0;
        int heure = 0;
        for (int i = 0; i < 32; i++) {
            minute += 15;
            if (minute == 60) {
                minute = 0;
                heure++;
            }
            arraySpinner.add(heure + ":" + minute);
        }
        //aussi utiliser pour tempInter
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tempP.setAdapter(timeAdapter);

        //CheckBox et Heure Fin inter

        CheckBox isTerminee = ((CheckBox) findViewById(R.id.addInterCbInterventionTerminee));
        isTerminee.setOnClickListener(v -> {
            if (isTerminee.isChecked()) {
                heureF.setVisibility(View.VISIBLE);
                dateTimePick = new DateAndTimePicker(this, heureF);
                dateTimePick.dateTimeAsk();
            } else {
                heureF.setVisibility(View.INVISIBLE);
            }
        });
        CheckBox isPause = ((CheckBox) findViewById(R.id.addInterCbMachineArretee));
        isPause.setOnClickListener(v -> {
            if (isPause.isChecked()) {
                findViewById(R.id.addInterSpinnerTempArret).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.addInterSpinnerTempArret).setVisibility(View.INVISIBLE);
            }
        });
        //renome BTN Ajout avec l'inter selectionné et le temp
        TextView btnAdd = (TextView) findViewById(R.id.addInterBtnAjouterIntervenent);
        Spinner interAdd = ((Spinner) findViewById(R.id.addInterSpinnerLesInter));
        tempInter = (Spinner) findViewById(R.id.addInterSpinnerInterTime);
   /*     interAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                btnAdd.setText("Ajouter: "+interAdd.getSelectedItem().toString()+ " "+interAdd.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        tempInter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                btnAdd.setText("Ajouter: "+interAdd.getSelectedItem().toString()+ " "+interAdd.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/
        //ajout d'intervenant

        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tempInter.setAdapter(timeAdapter);
        Spinner spinIntervenant = ((Spinner) findViewById(R.id.addInterSpinnerLesInter));
        spinIntervenant.setAdapter(adaLesIntervenants);
        ListView lvIntervenant = ((ListView) findViewById(R.id.addInterListViewInter));
        lvIntervenant.setAdapter(adaLvInter);
        ((Button) findViewById(R.id.addInterBtnAjouterIntervenent)).setOnClickListener(v -> {
            Boolean isTimeOk = false;

            if (spinIntervenant.getCount() != 0) {
                String t = (tempInter.getSelectedItem().toString());
                Utilisateur u = (Utilisateur) spinIntervenant.getSelectedItem();
                UtilisateurIntervenue ui = new UtilisateurIntervenue(u, t);
                interLvList.add(ui);

                int i = spinIntervenant.getSelectedItemPosition();
                interSpinList.remove(i);

                adaLesIntervenants.notifyDataSetChanged();
                adaLvInter.notifyDataSetChanged();
            }
        });
        lvIntervenant.setOnItemLongClickListener((parent, view, position, id) -> {

            Utilisateur u = ((UtilisateurIntervenue) lvIntervenant.getItemAtPosition(position)).getInter();
            interSpinList.add(u);
            interLvList.remove(position);

            adaLesIntervenants.notifyDataSetChanged();
            adaLvInter.notifyDataSetChanged();
            return true;
        });


        ((Button) findViewById(R.id.addInterBtnEnvoyerIntervention)).setOnClickListener(v -> {
            String acti = ((Ascod) ((Spinner) findViewById(R.id.addInterSpinActivite)).getSelectedItem()).getCode();
            String so = ((Ascod) ((Spinner) findViewById(R.id.addInterSpinSO)).getSelectedItem()).getCode();
            String sd = ((Ascod) ((Spinner) findViewById(R.id.addInterSpinSD)).getSelectedItem()).getCode();
            String co = ((Ascod) ((Spinner) findViewById(R.id.addInterSpinCO)).getSelectedItem()).getCode();
            String cd = ((Ascod) ((Spinner) findViewById(R.id.addInterSpinCD)).getSelectedItem()).getCode();
            String mach = ((Machine) ((Spinner) findViewById(R.id.addInterSpinMachine)).getSelectedItem()).getCode();
            String comm = ((TextView) findViewById(R.id.addInterTvCommentaire)).getText().toString();
            Boolean fin = ((CheckBox) findViewById(R.id.addInterCbInterventionTerminee)).isChecked();
            Boolean arr = ((CheckBox) findViewById(R.id.addInterCbMachineArretee)).isChecked();
            Boolean org = ((CheckBox) findViewById(R.id.addInterCbChangementOrgane)).isChecked();
            Boolean per = ((CheckBox) findViewById(R.id.addInterCbPerte)).isChecked();
            String hDeb = ((TextView) findViewById(R.id.addInterBtnHeureDebut)).getText().toString();
            String hFin = null;
            String tempAr = (String) tempP.getSelectedItem();
            boolean isOk = true;
            try {
                // j'execute tout ce qui peux lever une exception enssemble pour arreter l'action en cours en une seul fois (action: post intervention)
                if (fin) {
                    hFin = ((TextView) findViewById(R.id.addInterBtnHeureFin)).getText().toString();
                }
                if (acti == "" || so == "" || sd == "" || co == "" || cd == "" || mach == "") {
                    isOk = false;
                }
                DaoIntervention.getInstance().insertUneInterventions(hDeb, hFin, comm, tempAr, org, per, acti, mach, cd, co, sd, so, interLvList,

                        new Delegate() {
                            @Override
                            public void WSRequestIsDone(Object result) {
                                Toast.makeText(getApplicationContext(), "Ajout Réussie", Toast.LENGTH_SHORT).show();
                            }
                        });
                finish();
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
                Toast.makeText(this, "veiller à bien entrer un nombre dans le temp d'arret", Toast.LENGTH_SHORT).show();
            }

        });

    }


    private void innit() {


        DaoUtilisateur.getInstance().getLesIntervenantsBBD(new Delegate() {
            @Override
            public void WSRequestIsDone(Object result) {

                interSpinList.addAll(DaoUtilisateur.getInstance().getLesIntervenantsLoc());

                adaLesIntervenants.notifyDataSetChanged();
            }
        });

        adaActivite = new ArrayAdapter(this, android.R.layout.simple_list_item_1, DaoIntervention.getInstance().getLesActivites());
        adaSO = new ArrayAdapter(this, android.R.layout.simple_list_item_1, DaoIntervention.getInstance().getLesSO());
        adaSD = new ArrayAdapter(this, android.R.layout.simple_list_item_1, DaoIntervention.getInstance().getLesSD());
        adaCO = new ArrayAdapter(this, android.R.layout.simple_list_item_1, DaoIntervention.getInstance().getLesCO());
        adaCD = new ArrayAdapter(this, android.R.layout.simple_list_item_1, DaoIntervention.getInstance().getLesCD());
        adaMachine = new ArrayAdapter(this, android.R.layout.simple_list_item_1, DaoIntervention.getInstance().getLesMachines());
        adaLesIntervenants = new ArrayAdapter(this, android.R.layout.simple_list_item_1, interSpinList);
        adaLvInter = new CustomAdapterAddInter(this, interLvList);

        ((Spinner) findViewById(R.id.addInterSpinActivite)).setAdapter(adaActivite);
        ((Spinner) findViewById(R.id.addInterSpinSO)).setAdapter(adaSO);
        ((Spinner) findViewById(R.id.addInterSpinSD)).setAdapter(adaSD);
        ((Spinner) findViewById(R.id.addInterSpinCO)).setAdapter(adaCO);
        ((Spinner) findViewById(R.id.addInterSpinCD)).setAdapter(adaCD);
        ((Spinner) findViewById(R.id.addInterSpinMachine)).setAdapter(adaMachine);


        if (!isInnit) {
            isInnit = true;
            adaActivite.addAll(DaoIntervention.getInstance().getLesActivites());
            adaCD.addAll(DaoIntervention.getInstance().getLesCD());
            adaCO.addAll(DaoIntervention.getInstance().getLesCO());
            adaSD.addAll(DaoIntervention.getInstance().getLesSD());
            adaSO.addAll(DaoIntervention.getInstance().getLesSO());
            adaMachine.addAll(DaoIntervention.getInstance().getLesMachines());
        }


        adaActivite.notifyDataSetChanged();
        adaCD.notifyDataSetChanged();
        adaCO.notifyDataSetChanged();
        adaSD.notifyDataSetChanged();
        adaSO.notifyDataSetChanged();
        adaMachine.notifyDataSetChanged();
        isInnit = true;
    }

    /*
        TODO spinner Machine arret
        TODO spinner temp inter
        TODO btn ajout rename info inter
        TODO btn annuler

 */

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
}