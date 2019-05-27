package com.nguyen.tam.ghichunhanh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class DanhSach extends AppCompatActivity {
    final String DATABASE_NAME = "dbnotenow.sqlite";
    SQLiteDatabase database;

    ListView listViewDanhSachNote;
    ArrayList<GhiChu> list;
    GhiChuAdapter adapter;
    int id = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_danh_sach);
        listViewDanhSachNote = findViewById(R.id.lvDanhSach);
        addControls();
        readData();
        addEvents();

        listViewDanhSachNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mh_show = new Intent(DanhSach.this, Xem.class);
                mh_show.putExtra("noteItem", (Serializable) list.get(position));
                startActivity(mh_show);
                //finish();
            }
        });
       listViewDanhSachNote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               final int idNote = list.get(position).getId();
               AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DanhSach.this);
               alertDialogBuilder.setTitle("Note....!");
               alertDialogBuilder.setMessage(R.string.xoa_item);
               alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       xoa(idNote);
                       //cập nhật lại listview
                       adapter.notifyDataSetChanged();
                   }
               });
               alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       //không làm gì
                   }
               });
               alertDialogBuilder.show();
               return true;
           }
       });
    }
    private void addEvents() {
    }
    private void xoa(int idNote) {
        SQLiteDatabase database = Database.initDatabase(DanhSach.this, "dbnotenow.sqlite");
        database.delete("DBNOTENOW",  "id = ?", new String[]{idNote + ""});
        list.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM DBNOTENOW", null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String note = cursor.getString(1);
            String date = cursor.getString(2);
            String time = cursor.getString(3);

            list.add(new GhiChu(id,note,date,time));
        }

        adapter.notifyDataSetChanged();
    }
    private void readData() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM DBNOTENOW",null);
        //list.clear();
        list.clear();
        for (int i=0; i<cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id=cursor.getInt(0);
            String note=cursor.getString(1);
            String date=cursor.getString(2);
            String time = cursor.getString(3);
            // byte[] anh = cursor.getBlob(5);

            //list.add(new Note(id,note,date,time));
            list.add(new GhiChu(id,note,date,time));
        }
        // adapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
    }

    private void addControls() {
        listViewDanhSachNote= (ListView) findViewById(R.id.lvDanhSach);
        list=new ArrayList<>();
        adapter=new GhiChuAdapter(this,list);
        listViewDanhSachNote.setAdapter(adapter);

    }
}
