package org.example.gayathri.a20180403_gg_nycschools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
 class SchoolListAdapter extends ArrayAdapter<SchoolData> {

    private ArrayList<SchoolData> items;
    private LayoutInflater inflator;

    public SchoolListAdapter(Context context, int resource, ArrayList<SchoolData> items){
        super(context,resource,items);
        this.items = items;
        inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    private static class ViewHolder{
        private TextView txtSchoolName;
        private TextView txtDBN;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        try{
            if(convertView == null){
                convertView = inflator.inflate(R.layout.school_list_item, parent, false);
                viewHolder = new ViewHolder();

                viewHolder.txtSchoolName = convertView.findViewById(R.id.tvSchoolName);
                viewHolder.txtDBN = convertView.findViewById(R.id.tvDBN);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.txtSchoolName.setText(items.get(position).school_name);
            viewHolder.txtDBN.setText(items.get(position).dbn);
        } catch(Exception e){
            e.printStackTrace();
        }


        return convertView;
    }

    @Nullable
    @Override
    public SchoolData getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
