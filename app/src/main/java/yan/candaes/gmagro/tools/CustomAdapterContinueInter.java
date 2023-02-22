package yan.candaes.gmagro.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yan.candaes.gmagro.R;
import yan.candaes.gmagro.beans.UtilisateurIntervenue;

// UTILE DANS AddInterventionActivity pour afficher les intervenants de la liste avec plus qu'un simple textview
public class CustomAdapterContinueInter extends BaseAdapter {

    private Context context;
    private List<UtilisateurIntervenue> list;

    public CustomAdapterContinueInter(Context context, List<UtilisateurIntervenue> l) {
        this.list = l;
        this.context = context;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public UtilisateurIntervenue getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getInter().getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_continueinter, parent, false);
        }
        //fill spinner
        Spinner spinAddTime = (Spinner) convertView.findViewById(R.id.continueInterterSpinnerAddTime);
        ArrayList<String> arraySpinner = new ArrayList<>();

        int minute = 0;
        int heure = 0;
        arraySpinner.add("0:0");
        for (int i = 0; i < 32; i++) {
            minute += 15;
            if (minute == 60) {
                minute = 0;
                heure++;
            }

            arraySpinner.add(heure + ":" + minute);
        }
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,arraySpinner);

        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAddTime.setAdapter(timeAdapter);



        UtilisateurIntervenue inter = list.get(position);
        TextView tvName = convertView.findViewById(R.id.ocntinueInterAddInterName);
        TextView tvTime = convertView.findViewById(R.id.continueInterTvInterTime);
        spinAddTime.setSelection(inter.getNouveauTempPosition());

        tvName.setText(inter.toString());
        tvTime.setText("Temps passé: " + inter.getTime() + " min.");


        return convertView;

    }
}

