package yan.candaes.gmagro.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import yan.candaes.gmagro.R;
import yan.candaes.gmagro.beans.Intervention;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private List<Intervention> list;

    public CustomAdapter(Context context, List<Intervention> l) {
        this.list = l;
        this.context = context;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Intervention getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_inter, parent, false);
        }
        Intervention i = list.get(position);
        TextView tvId = convertView.findViewById(R.id.ocntinueInterAddInterName);
        TextView tvNom = convertView.findViewById(R.id.continueInterTvInterTime) ;
//        TextView tvLC = convertView.findViewById(R.id.tvLC) ;


        tvId.setText(i.getId() + "");
        tvNom.setText("Machine :" +i.getMachine_code());
//        tvLC.setText("Nanos: "+i.getT().getNano());

        return convertView;
    }
}
