package yan.candaes.gmagro.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import yan.candaes.gmagro.R;
import yan.candaes.gmagro.dao.DaoIntervention;
import yan.candaes.gmagro.dao.Delegate;
import yan.candaes.gmagro.tools.CustomAdapter;

public class InterventionsActivity extends AppCompatActivity {
    CustomAdapter adaInter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interventions);

        adaInter = new CustomAdapter(this, DaoIntervention.getInstance().getLesInterventionsLOC());
        ((ListView) findViewById(R.id.interListView)).setAdapter(adaInter);

        DaoIntervention.getInstance().getLesInterventionsBDD(new Delegate() {
            @Override
            public void WSRequestIsDone(Object result) {
                adaInter.notifyDataSetChanged();
            }
        });


        ((Button) findViewById(R.id.interBtnDeco)).setOnClickListener(v -> deconnexion());
        ((Button) findViewById(R.id.interBtnAdd)).setOnClickListener(v ->
        {
            startActivity(new Intent(this, addInterventionActivity.class));
        });

    }

    private void deconnexion() {
        DaoIntervention.getInstance().deco(new Delegate() {
            @Override
            public void WSRequestIsDone(Object result) {
                if ((Boolean) result) finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DaoIntervention.getInstance().deco(new Delegate() {
            @Override
            public void WSRequestIsDone(Object result) {
                if ((Boolean) result) finish();
            }
        });
    }
}



