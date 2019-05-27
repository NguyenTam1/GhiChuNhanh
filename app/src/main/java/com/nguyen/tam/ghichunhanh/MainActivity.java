package com.nguyen.tam.ghichunhanh;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button btnDanhSach, btnLuu;
    EditText edtGhiChu;
    TextView txtvNgay, txtvGio;
    final String DATABASE_NAME = "dbnotenow.sqlite";
    SQLiteDatabase database;
    public static String curDate = "";
    public static String curTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        time();
        date();

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luu();
                edtGhiChu.getText().clear();
                Toast.makeText(MainActivity.this,R.string.luu_thanh_cong,Toast.LENGTH_LONG).show();

            }
        });
        btnDanhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DanhSach.class);
                startActivity(intent);
               // finish();
            }
        });
    }

    private void anhxa() {
        btnDanhSach = findViewById(R.id.btnDanhSach);
        btnLuu = findViewById(R.id.btnLuu);
        edtGhiChu  =findViewById(R.id.edtNhapGhiChu);
        txtvNgay = findViewById(R.id.txtvNgay);
        txtvGio = findViewById(R.id.txtvGio);
    }

    private void time() {
        long msTime = System.currentTimeMillis();
        Time cuTime = new Time(msTime);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        curTime = format.format(cuTime);

        txtvGio.setText(""+curTime);
    }
    private void date() {
        long msTime = System.currentTimeMillis();
        Date curDateTime = new Date(msTime);

        SimpleDateFormat formatter = new SimpleDateFormat("dd'/'MM'/'yyyy");
        curDate = formatter.format(curDateTime);
        txtvNgay.setText(""+curDate);
    }
    private void luu() {

        String date = txtvNgay.getText().toString();
        String note = edtGhiChu.getText().toString();
        String time = txtvGio.getText().toString();
        //byte[] anh = getByteArrayFromImageView(imdAnh);

        if (note==null || note.trim().isEmpty()){
            Toast.makeText(MainActivity.this,R.string.ghi_chu_rong,Toast.LENGTH_LONG).show();
        }
        else {
            note=edtGhiChu.getText().toString();
            date = txtvNgay.getText().toString();
            time = txtvGio.getText().toString();
            // anh = getByteArrayFromImageView(imdAnh);

            ContentValues contentValues = new ContentValues();
            contentValues.put("note", note);
            contentValues.put("day",date);
            contentValues.put("time",time);
            // contentValues.put("abc",anh);

            SQLiteDatabase database = Database.initDatabase(this, "dbnotenow.sqlite");
            database.insert("DBNOTENOW", null, contentValues);


            Cursor cursor = database.rawQuery("SELECT * FROM DBNOTENOW", null);

        }

    }
}
