package com.rtech.wallifymanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
Button update_btn;
Button add_btn;
EditText category,page;
CheckBox flag;
ProgressBar progressBar;
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
        AndroidNetworking.initialize(this);
        String api="https://wallify-ebon.vercel.app/";
        update_btn=findViewById(R.id.update_btn);
        add_btn=findViewById(R.id.addLinks_btn);
        category=findViewById(R.id.category);
        flag=findViewById(R.id.flag);
        page=findViewById(R.id.page);
        progressBar=findViewById(R.id.spinner);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q=category.getText().toString().toLowerCase();
                String Flag;
                if(flag.isChecked()){
                    Flag="1";
                }else{
                    Flag="0";
                }
                String Page=Integer.toString(Integer.parseInt(page.getText().toString())<1?1:Integer.parseInt(page.getText().toString()));

                if(!q.isEmpty()){
                    String query=api+"update/addData"+"?q="+q+"&flag="+Flag+"&page="+Page;
                    progressBar.setVisibility(View.VISIBLE);
                    AndroidNetworking.get(query).setPriority(Priority.HIGH).build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Toast.makeText(MainActivity.this, response.getString("result"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            } catch (JSONException e) {
                                e.printStackTrace();

                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    });


                }else{
                    Toast.makeText(MainActivity.this, "please enter a category", Toast.LENGTH_SHORT).show();
                }

            }
        });
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query=api+"update/refreshAllData";
                progressBar.setVisibility(View.VISIBLE);
                AndroidNetworking.get(query).setPriority(Priority.HIGH).build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    Toast.makeText(MainActivity.this, response.getString("result"), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        });

    }
}