package xyz.shaneneary.laundryview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import xyz.shaneneary.laundryview.R;
import xyz.shaneneary.laundryview.objects.DormMachines;

import java.util.List;

public class BaseAdapterDorm extends BaseAdapter {

    private List<DormMachines> list;
    private LayoutInflater layoutInflater;

    public BaseAdapterDorm(List<DormMachines> list, Context context) {
        this.list = list;
        this.layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        DormViewHolder dormViewHolder;

        if(view == null) {
            view = layoutInflater.inflate(R.layout.listview_dorm_item, parent, false);

            dormViewHolder = new DormViewHolder();

            dormViewHolder.textViewDormName = (TextView) view.findViewById(R.id.list_item_dorm_item_name);
            dormViewHolder.textViewWasherAvail = (TextView) view.findViewById(R.id.list_item_washers_avail);
            dormViewHolder.textViewWasherOccupied = (TextView) view.findViewById(R.id.list_item_washer_occupied);
            dormViewHolder.textViewDryersAvail = (TextView) view.findViewById(R.id.list_item_dryers_avail);
            dormViewHolder.textViewDryersOccupied = (TextView) view.findViewById(R.id.list_item_dryers_occupied);

            dormViewHolder.textViewDormName.setText(list.get(position).dormName);
            dormViewHolder.textViewWasherAvail.setText(""+list.get(position).washersFree);
            dormViewHolder.textViewWasherOccupied.setText(""+list.get(position).washersInUse);
            dormViewHolder.textViewDryersAvail.setText(""+list.get(position).dryersFree);
            dormViewHolder.textViewDryersOccupied.setText(""+list.get(position).dryersInUse);
        }
        return view;
    }

    static class DormViewHolder {
        TextView textViewDormName;
        TextView textViewWasherAvail;
        TextView textViewWasherOccupied;
        TextView textViewDryersAvail;
        TextView textViewDryersOccupied;
    }
}
