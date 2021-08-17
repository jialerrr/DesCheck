package rp.edu.sg.c346.id20021576.descheck;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Hardware> HardwareList;
    CustomAdapter adapter;
    String moduleCode;
    int requestCode = 9;
    Button btn5Stars, btnaddHard;
    Spinner spnPrice;
    ArrayList<String> spnAl;
    ArrayAdapter<String> spnAa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setTitle(getTitle().toString() + " ~ " +  getResources().getText(R.string.title_activity_second));

        lv = (ListView) this.findViewById(R.id.lv);
        btn5Stars = (Button) this.findViewById(R.id.btnShow5Stars);
        spnPrice = (Spinner) this.findViewById(R.id.spnArea);
        btnaddHard = (Button) this.findViewById(R.id.buttonaddHard);

        DBHelper dbh = new DBHelper(this);
        HardwareList = dbh.getAllHardwares();
        dbh.close();

        adapter = new CustomAdapter(this, R.layout.row, HardwareList);
        lv.setAdapter(adapter);


        spnAl = new ArrayList<String>();
        spnAa = new ArrayAdapter<>(SecondActivity.this, R.layout.spinner_item, spnAl);
        spnPrice.setAdapter(spnAa);
        spnAa.add("Filter Price");
        for (int i = 0; i < HardwareList.size(); i++) {
            if(HardwareList.get(i).getPrice() < 30){
                spnAa.add("Hardware below $30");
            } else if(HardwareList.get(i).getPrice() < 50){
                spnAa.add("Hardware below $50");
            } else if(HardwareList.get(i).getPrice() < 100){
                spnAa.add("Hardware below $100");
            }
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("Hardware", HardwareList.get(position));
                startActivityForResult(i, requestCode);
            }
        });

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                HardwareList.clear();
                HardwareList.addAll(dbh.getAllHardwaresByStars(5));
                adapter.notifyDataSetChanged();
            }
        });

        spnPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Hardware> alFilt;
                alFilt = new ArrayList<Hardware>();
                float priceFind = 0;

                if(spnPrice.getSelectedItemPosition() == 0) {
                    lv.setAdapter(adapter);
                } else {
                    if (spnPrice.getSelectedItem().toString().length() == 19) {
                        priceFind = Float.parseFloat(spnPrice.getSelectedItem().toString().substring(16, 19));
                        for (int i = 0; i < HardwareList.size(); i++) {
                            if (HardwareList.get(i).getPrice() < priceFind) {
                                alFilt.add(HardwareList.get(i));
                            }
                        }
                    } else {
                        priceFind = Float.parseFloat(spnPrice.getSelectedItem().toString().substring(16, 18));
                        for (int i = 0; i < HardwareList.size(); i++) {
                            if (HardwareList.get(i).getPrice() < priceFind) {
                                alFilt.add(HardwareList.get(i));
                            }
                        }
                    }
                    CustomAdapter aaFilt = new CustomAdapter(SecondActivity.this, R.layout.row, alFilt);
                    lv.setAdapter(aaFilt);


                    aaFilt.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        btnaddHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(SecondActivity.this);
        HardwareList.clear();
        HardwareList.addAll(dbh.getAllHardwares());
        adapter.notifyDataSetChanged();
    }
}