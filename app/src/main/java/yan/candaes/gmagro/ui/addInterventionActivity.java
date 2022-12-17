package yan.candaes.gmagro.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import yan.candaes.gmagro.R;
import yan.candaes.gmagro.beans.Utilisateur;
import yan.candaes.gmagro.dao.DaoIntervention;
import yan.candaes.gmagro.dao.DaoUtilisateur;
import yan.candaes.gmagro.dao.Delegate;
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
    ArrayAdapter adaLesIntervenants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_intervention);

        innit();


        heureD = ((TextView) findViewById(R.id.addInterBtnHeureDebut));
        heureD.setOnClickListener(Tools.TimePicker(addInterventionActivity.this, heureD));
        heureF = ((TextView) findViewById(R.id.addInterBtnHeureFin));
        heureF.setOnClickListener(Tools.TimePicker(addInterventionActivity.this, heureF));
        CheckBox isTerminee = ((CheckBox) findViewById(R.id.addInterCbInterventionTerminee));
        isTerminee.setOnClickListener(v -> {
            if (isTerminee.isChecked()) {
                    heureF.setVisibility(View.VISIBLE);
                } else {
                    heureF.setVisibility(View.INVISIBLE);
                }
            }
        );



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
        adaLesIntervenants = new ArrayAdapter(this, android.R.layout.simple_list_item_1, DaoUtilisateur.getInstance().getLesIntervenantsLoc());
        ((Spinner) findViewById(R.id.addInterSpinActivite)).setAdapter(adaActivite);
        ((Spinner) findViewById(R.id.addInterSpinSO)).setAdapter(adaSO);
        ((Spinner) findViewById(R.id.addInterSpinSD)).setAdapter(adaSD);
        ((Spinner) findViewById(R.id.addInterSpinCO)).setAdapter(adaCO);
        ((Spinner) findViewById(R.id.addInterSpinCD)).setAdapter(adaCD);
        ((Spinner) findViewById(R.id.addInterSpinMachine)).setAdapter(adaMachine);
        ((Spinner) findViewById(R.id.addInterSpinnerLesInter)).setAdapter(adaLesIntervenants);



        isInnit = true;

    }
}