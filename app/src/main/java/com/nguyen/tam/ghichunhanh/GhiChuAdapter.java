package com.nguyen.tam.ghichunhanh;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GhiChuAdapter extends BaseAdapter {
    Activity context;
    ArrayList<GhiChu>dsGhiChu;

    public GhiChuAdapter(Activity context, ArrayList<GhiChu> dsGhiChu) {
        this.context = context;
        this.dsGhiChu = dsGhiChu;
    }

    @Override
    public int getCount() {
        return dsGhiChu.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.item_danhsach, null);
        TextView txtvNote= (TextView) row.findViewById(R.id.txtvItemNoiDungGhiChu);
        TextView txtDate=(TextView) row.findViewById(R.id.txtvItemNgay);
        TextView txtvTime = (TextView) row.findViewById(R.id.txtvItemGio);
        final GhiChu ghiChu = dsGhiChu.get(position);

        txtvNote.setText(ghiChu.note);
        txtDate.setText(ghiChu.date);
        txtvTime.setText(ghiChu.time);
        return row;
    }
}
