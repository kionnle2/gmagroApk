package yan.candaes.gmagro.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

import yan.candaes.gmagro.R;
import yan.candaes.gmagro.dao.DaoUtilisateur;
import yan.candaes.gmagro.dao.Delegate;
import yan.candaes.gmagro.net.Tools;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); ((Button) findViewById(R.id.mainBtnConnexion)).setOnClickListener(view -> {

            String request = null;

                request = ("controller=connexion&action=U" +
                        "&login=" + ((TextView) findViewById(R.id.mainTxtLogin)).getText().toString() +
                        "&mdp=" + Tools.md5(((TextView) findViewById(R.id.mainTxtPass)).getText().toString()));


            DaoUtilisateur.getInstance().connexionAccount(request, new Delegate() {
                @Override
                public void WSRequestIsDone(Object result) {
                    // result boolean, reussi ou non
                    if ((boolean) result) {
                        Toast.makeText(getApplicationContext(), "connexion réussie", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),InterventionsActivity.class);
                        startActivity(intent);
                        /* DaoParticipant.getInstance().*/
                    } else
                        Toast.makeText(getApplicationContext(), "connexion échoué", Toast.LENGTH_SHORT).show();
                }

            });
        });


    }
}