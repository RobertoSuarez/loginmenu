package com.example.loginmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // Request
    RequestQueue queue;
    final String BASEURL = "https://my-json-server.typicode.com/RobertoSuarez/loginmenu/users";

    // Controles
    public TextInputLayout txtUsername;
    public TextInputLayout txtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);

        queue = Volley.newRequestQueue(this);

    }

    public void btnLogin(View view) {
        String username = txtUsername.getEditText().getText().toString().trim();
        String password = txtPassword.getEditText().getText().toString().trim();

        this.checkUser(username, password, this);
    }

//                Toast.makeText(this, "Fatal, parace que no funca tu usuario o contraseña", Toast.LENGTH_SHORT).show();

    public void SendTableMenu(String modo, String usuario) {
        System.out.println("Modo: " + modo);
        Intent intent = new Intent(this, MenuActividades.class);
        intent.putExtra("MODO", modo);
        intent.putExtra("USERNAME", usuario);
        startActivity(intent);
    }

    public void checkUser(String username, String password, MainActivity activity)  {
        JsonArrayRequest usuariosReques = new JsonArrayRequest(
                Request.Method.GET,
                BASEURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject usuario = response.getJSONObject(i);
                                // Revisamos si es el usuario
                                if (usuario.getString("username").equals(username) && usuario.getString("password").equals(password)) {
                                    activity.SendTableMenu(usuario.getString("modo"), usuario.getString("username"));
                                    return;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Toast.makeText(activity, "Fatal, parace que no funca tu usuario o contraseña", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "Fatal, parace que no funca el server", Toast.LENGTH_LONG).show();
                    }
                }
        );

        queue.add(usuariosReques);
    }

    final class StatusUser {
        private final boolean ok;
        private final String modo;

        public StatusUser(boolean ok, String modo) {
            this.ok = ok;
            this.modo = modo;
        }

        public boolean isOk() {
            return ok;
        }

        public String getModo() {
            return modo;
        }
    }
}