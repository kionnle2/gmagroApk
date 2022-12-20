package yan.candaes.gmagro.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import yan.candaes.gmagro.R;
import yan.candaes.gmagro.beans.Utilisateur;
import yan.candaes.gmagro.beans.UtilisateurIntervenue;
import yan.candaes.gmagro.dao.DaoIntervention;
import yan.candaes.gmagro.dao.DaoUtilisateur;
import yan.candaes.gmagro.dao.Delegate;
import yan.candaes.gmagro.tools.CustomAdapterAddInter;
import yan.candaes.gmagro.tools.Tools;

public class addInterventionActivity extends AppCompatActivity {
    static Boolean isInnit = false;
    ArrayAdapter adaActivite;
    ArrayAdapter adaSO;
    ArrayAdapter adaSD;
    ArrayAdapter adaCO;
    ArrayAdapter adaCD;
    ArrayAdapter adaMachine;
    TextView heureD;
    TextView heureF;
//    TextView tempP;

    ArrayAdapter adaLesIntervenants;
    CustomAdapterAddInter adaLvInter;

    ArrayList<Utilisateur> interSpinList = new ArrayList<>();
    ArrayList<UtilisateurIntervenue> interLvList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_intervention);

        innit();


        heureD = ((TextView) findViewById(R.id.addInterBtnHeureDebut));
        heureD.setOnClickListener(Tools.TimePicker(addInterventionActivity.this, heureD));
        heureF = ((TextView) findViewById(R.id.addInterBtnHeureFin));
        heureF.setOnClickListener(Tools.TimePicker(addInterventionActivity.this, heureF));
//        tempP = ((TextView) findViewById(R.id.addInterBtnTempsPasse));
//        tempP.setOnClickListener(Tools.TimePicker(addInterventionActivity.this, tempP));

        CheckBox isTerminee = ((CheckBox) findViewById(R.id.addInterCbInterventionTerminee));
        isTerminee.setOnClickListener(v -> {
            if (isTerminee.isChecked()) {
                heureF.setVisibility(View.VISIBLE);
            } else {
                heureF.setVisibility(View.INVISIBLE);
            }
        });

        //ajout d'untervenant
        Spinner spinIntervenant = ((Spinner) findViewById(R.id.addInterSpinnerLesInter));
        spinIntervenant.setAdapter(adaLesIntervenants);
        ListView lvIntervenant = ((ListView) findViewById(R.id.addInterListViewInter));
        lvIntervenant.setAdapter(adaLvInter);
        ((Button) findViewById(R.id.addInterBtnAjouterIntervenent)).setOnClickListener(v ->
        {
            Boolean isTimeOk=false;

            if (spinIntervenant.getCount() != 0 ) {
                int t = Integer.parseInt(((TextView)findViewById(R.id.addInterTvTime)).getText().toString());
                Utilisateur u = (Utilisateur) spinIntervenant.getSelectedItem();
                UtilisateurIntervenue ui=new UtilisateurIntervenue(u,t);
                interLvList.add(ui);

                int i = spinIntervenant.getSelectedItemPosition();
                interSpinList.remove(i);

                adaLesIntervenants.notifyDataSetChanged();
                adaLvInter.notifyDataSetChanged();
            }
        });
        lvIntervenant.setOnItemLongClickListener((parent, view, position, id) ->
        {

            Utilisateur u = (Utilisateur) lvIntervenant.getItemAtPosition(position);
            interSpinList.add(u);
            interLvList.remove(position);

            adaLesIntervenants.notifyDataSetChanged();
            adaLvInter.notifyDataSetChanged();
            return true;
        });




       /* private void deconnexion() {
            DaoIntervention.getInstance().deco(new Delegate() {
                @Override
                public void WSRequestIsDone(Object result) {
                    if ((Boolean) result) {

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class) ;
                      //  intent.addFlags(MainActivity.);
                    }
                }
            });
        }*/


    }


    private void innit() {
        if (!isInnit) {
            DaoIntervention.getInstance().getAscod(new Delegate() {
                @Override
                public void WSRequestIsDone(Object result) {
                    adaActivite.notifyDataSetChanged();
                    adaCD.notifyDataSetChanged();
                    adaCO.notifyDataSetChanged();
                    adaSD.notifyDataSetChanged();
                    adaSO.notifyDataSetChanged();
                }
            });
            DaoIntervention.getInstance().getMachines(new Delegate() {
                @Override
                public void WSRequestIsDone(Object result) {
                    adaMachine.notifyDataSetChanged();
                }
            });
            DaoUtilisateur.getInstance().getLesIntervenantsBBD(new Delegate() {
                @Override
                public void WSRequestIsDone(Object result) {

                    interSpinList.addAll(DaoUtilisateur.getInstance().getLesIntervenantsLoc());

                    adaLesIntervenants.notifyDataSetChanged();
                }
            });
        }
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


        isInnit = true;

    }
}