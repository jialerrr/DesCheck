package rp.edu.sg.c346.id20021576.descheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etDesc, etPrice;
    Button btnInsert, btnShowList;
    RatingBar rtBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_main));

        etTitle = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        etPrice = findViewById(R.id.etPrice);
        btnInsert = findViewById(R.id.btnInsertHardware);
        btnShowList = findViewById(R.id.btnShowList);
        rtBar = findViewById(R.id.ratingBarList);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                if (title.length() == 0 || desc.length() == 0){
                    Toast.makeText(MainActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                    return;
                }

                String price_str = etPrice.getText().toString().trim();
                float price = Float.parseFloat(price_str);
                float stars = getStars();

                DBHelper dbh = new DBHelper(MainActivity.this);
                long result = dbh.insertHardware(title, desc, price, stars);

                if (result != -1) {
                    Toast.makeText(MainActivity.this, "Hardware inserted", Toast.LENGTH_LONG).show();
                    etTitle.setText("");
                    etDesc.setText("");
                    etPrice.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Insert failed", Toast.LENGTH_LONG).show();
                }


            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });

    }

    private float getStars() {

        float stars = rtBar.getRating();
        return stars;
    }

}