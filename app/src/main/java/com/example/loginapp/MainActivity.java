package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 10;

    ArrayList<String> passwordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.btnLogin);

        Info.setText("No of attempts remaining: 10");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // validate(Name.getText().toString(), Password.getText().toString());
                get_json();
            }
        });


    }

    public void get_json(){
        String json;
        try{
            InputStream is = getAssets().open("a.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for(int i = 0; i<jsonArray.length(); i++){

                JSONObject obj = jsonArray.getJSONObject(i);
                if(obj.getString("Username").equals("Admin")) {
                    passwordList.add(obj.getString("Password"));
                }
            }

            Toast.makeText(getApplicationContext(), passwordList.toString(), Toast.LENGTH_SHORT).show();

        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch(JSONException e){
            e.printStackTrace();
        }

    }




    private void validate(String userName, String userPassword) {
        if ((userName.equals("Admin")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        } else {
            counter--;

            Info.setText("No of attemps remaining: " + String.valueOf(counter));

            if (counter == 0) {
                Login.setEnabled(false);
            }
        }
    }




}