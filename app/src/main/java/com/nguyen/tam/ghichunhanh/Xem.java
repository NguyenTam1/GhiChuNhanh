package com.nguyen.tam.ghichunhanh;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Xem extends AppCompatActivity {

    final String DATABASE_NAME = "dbnotenow.sqlite";
    SQLiteDatabase database;

    ListView listViewDanhSachNote;
    ArrayList<GhiChu> dsGhiChu;
    GhiChuAdapter ghiChuAdapter;
    EditText edtUpdate;
    int id = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_xem);
        edtUpdate = findViewById(R.id.edtUpdate);
        getData();
       // readData();

    }
    public void getData() {
        Intent mh_show=getIntent();
        GhiChu noteItem = (GhiChu) mh_show.getSerializableExtra("noteItem");
        String note = noteItem.getNote();
        id = noteItem.getId();
        edtUpdate.setText(note);
    }
    private void readData() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM DBNOTENOW",null);
        //list.clear();
        //dsGhiChu.clear();
        for (int i=0; i<cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id=cursor.getInt(0);
            String note=cursor.getString(1);
            String date=cursor.getString(2);
            String time = cursor.getString(3);
            // byte[] anh = cursor.getBlob(5);

            //list.add(new Note(id,note,date,time));
            dsGhiChu.add(new GhiChu(id,note,date,time));
        }
        // adapter.notifyDataSetChanged();
        ghiChuAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                // action save
                upDate();
              Intent intent = new Intent(Xem.this,DanhSach.class);
              startActivity(intent);
              finish();
                return true;
            case R.id.delete:
               xacNhanXoa();
               return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void xacNhanXoa() {
        getData();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Xem.this);
        alertDialogBuilder.setTitle("Note....!");
        alertDialogBuilder.setMessage(R.string.xoa_item);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                xoa(id);
                Intent intent1 = new Intent(Xem.this,DanhSach.class);
                startActivity(intent1);
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //không làm gì
            }
        });
        alertDialogBuilder.show();
    }

    private void luu() {

        String update = edtUpdate.getText().toString();

        if (update==null || update.trim().isEmpty()){
            Toast.makeText(Xem.this,"Ghi chu rong",Toast.LENGTH_LONG).show();
        }
        else {
            update=edtUpdate.getText().toString();

            ContentValues contentValues = new ContentValues();
            contentValues.put("note", update);
            SQLiteDatabase database = Database.initDatabase(this, "dbnotenow.sqlite");
            database.insert("DBNOTENOW", null, contentValues);
            Cursor cursor = database.rawQuery("SELECT * FROM DBNOTENOW", null);

        }

    }
    public void upDate() {
        String upDateNote= edtUpdate.getText().toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put("note", upDateNote);

        SQLiteDatabase database = Database.initDatabase(this, "dbnotenow.sqlite");
        database.update("DBNOTENOW", contentValues,"id = ?", new String[]{id +""});

        Cursor cursor = database.rawQuery("SELECT * FROM DBNOTENOW", null);

    }
    private void xoa(int idNote) {
        SQLiteDatabase database = Database.initDatabase(Xem.this, "dbnotenow.sqlite");
        database.delete("DBNOTENOW",  "id = ?", new String[]{idNote + ""});
    }

}
