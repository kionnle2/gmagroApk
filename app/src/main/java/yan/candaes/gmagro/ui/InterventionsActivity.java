package yan.candaes.gmagro.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import yan.candaes.gmagro.R;
import yan.candaes.gmagro.dao.DaoIntervention;
import yan.candaes.gmagro.dao.Delegate;
import yan.candaes.gmagro.tools.CustomAdapter;

public class InterventionsActivity extends AppCompatActivity {
    CustomAdapter adaInter;
    final int AJOUT_FAIT = 2;

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
            startActivityForResult(new Intent(this, AddInterventionActivity.class), AJOUT_FAIT);
        });
        ((ListView) findViewById(R.id.interListView)).setOnItemClickListener((parent, view, position, id) ->
                {
                    Intent i = new Intent(this, ContinueInterventionActivity.class);
                    i.putExtra("id", adaInter.getItem(position));
                    startActivity(i);
                }
        );

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
    /* TODO add new inter dans liste intervention au lieu de prendre dans la bdd*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DaoIntervention.getInstance().getLesInterventionsBDD(new Delegate() {
            @Override
            public void WSRequestIsDone(Object result) {
                adaInter.notifyDataSetChanged();
            }
        });

    }
}



