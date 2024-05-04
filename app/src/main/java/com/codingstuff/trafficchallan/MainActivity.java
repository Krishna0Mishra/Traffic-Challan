package com.codingstuff.trafficchallan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText contact,vechile;
    AutoCompleteTextView location;
    String[] place={"White Field","Yelahanka","Gandhi Nagar","Indra Nagar","Jainagar"};
    String selectedCity;
    Button submit,display,paid;
    RadioGroup radioGroup;
    RadioButton radioButton;
    DBHandler db;
    TextView result;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        contact=findViewById(R.id.mobileNumber);
        vechile=findViewById(R.id.vehicleNumber);
        location=findViewById(R.id.location);
        submit=findViewById(R.id.submit);
        display=findViewById(R.id.display);
        paid=findViewById(R.id.paid);
        radioGroup=findViewById(R.id.radiogroup);
        result=findViewById(R.id.result);
        db=new DBHandler(getApplicationContext(),"Traffic",null,1);

        ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,place);
        location.setAdapter(adapter);
        location.setThreshold(1);
        selectedCity=location.getText().toString();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton=findViewById(radioGroup.getCheckedRadioButtonId());
                String type=radioButton.getText().toString();
                String mob=contact.getText().toString();
                String veh=vechile.getText().toString();

                db.insertRecord(mob,veh,selectedCity,type);
                Toast.makeText(MainActivity.this, "New Challan Registered!", Toast.LENGTH_SHORT).show();
            }
        });
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res=db.displayRecord();
                result.setText(res);
            }
        });
        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vec=vechile.getText().toString();
                db.challanPaid(vec);
                Toast.makeText(MainActivity.this, "Challan Paid!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}