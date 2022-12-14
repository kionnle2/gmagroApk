package yan.candaes.gmagro.ui;

import android.os.Bundle;
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
    }
}


