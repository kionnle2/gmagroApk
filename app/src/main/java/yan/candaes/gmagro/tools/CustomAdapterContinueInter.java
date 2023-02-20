package yan.candaes.gmagro.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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

        UtilisateurIntervenue i = list.get(position);
        TextView tvName = convertView.findViewById(R.id.addInterName);
        TextView tvTime = convertView.findViewById(R.id.addInterTime);
        TextView addTime = convertView.findViewById(R.id.continueInterTextAddTime);
        addTime.setOnEditorActionListener((v, actionId, event) -> {
                    v.setText(v.getText().toString() + " min");
                    return true;
                }
        );
        addTime.setOnClickListener(v ->
        {
            if (addTime.getText().toString().contains(" min"))
                addTime.setText(addTime.getText().toString().replace(" min", ""));

        });
        tvName.setText(i.toString());
        tvTime.setText("Temps passé: " + i.getTime() + " min. Ajouter :");
        if (i.getNewTime() != 0) {
            addTime.setText("" + i.getNewTime()+" min");
        }
        return convertView;
    }
}

