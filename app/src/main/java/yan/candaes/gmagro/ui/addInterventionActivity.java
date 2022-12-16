package yan.candaes.gmagro.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import yan.candaes.gmagro.R;
import yan.candaes.gmagro.dao.DaoIntervention;
import yan.candaes.gmagro.dao.Delegate;

public class addInterventionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_intervention);

        DaoIntervention.getInstance().getAscod(new Delegate() {
            @Override
            public void WSRequestIsDone(Object result) {
            }
        });
        DaoIntervention.getInstance().getMachines(new Delegate() {
            @Override
            public void WSRequestIsDone(Object result) {
            }
        });

            ArrayAdapter adaActivite = new ArrayAdapter(this, android.R.layout.simple_list_item_1, DaoIntervention.getInstance().getLesActivites());
            ArrayAdapter adaSO = new ArrayAdapter(this, android.R.layout.simple_list_item_1, DaoIntervention.getInstance().getLesSO());
            ArrayAdapter adaSD = new ArrayAdapter(this, android.R.layout.simple_list_item_1, DaoIntervention.getInstance().getLesSD());
            ArrayAdapter adaCO = new ArrayAdapter(this, android.R.layout.simple_list_item_1, DaoIntervention.getInstance().getLesCO());
            ArrayAdapter adaCD = new ArrayAdapter(this, android.R.layout.simple_list_item_1, DaoIntervention.getInstance().getLesCD());
            ArrayAdapter adaMachine = new ArrayAdapter(this, android.R.layout.simple_list_item_1, DaoIntervention.getInstance().getLesMachines());
            ((Spinner) findViewById(R.id.addInterSpinActivite)).setAdapter(adaActivite);
            ((Spinner) findViewById(R.id.addInterSpinSO)).setAdapter(adaSO);
            ((Spinner) findViewById(R.id.addInterSpinSD)).setAdapter(adaSD);
            ((Spinner) findViewById(R.id.addInterSpinCO)).setAdapter(adaCO);
            ((Spinner) findViewById(R.id.addInterSpinCD)).setAdapter(adaCD);
            ((Spinner) findViewById(R.id.addInterSpinMachine)).setAdapter(adaMachine);






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
}