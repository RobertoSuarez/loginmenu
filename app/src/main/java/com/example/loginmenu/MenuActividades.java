package com.example.loginmenu;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginmenu.databinding.ActivityMenuActividadesBinding;

public class MenuActividades extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuActividadesBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuActividadesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenuActividades.toolbar);
        binding.appBarMenuActividades.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "No existen mensajes", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;


        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                 R.id.nav_home,R.id.nav_gallery, R.id.nav_slideshow, R.id.listaUsuario)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_actividades);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        // si es normal se quitan varias opciones, caso contrario no pasa nada.
        String modo = getIntent().getExtras().getString("MODO");
        String username = getIntent().getExtras().getString("USERNAME");



        System.out.println(modo + " : " + username);

        if (modo.equals("normal")) {
            navigationView.getMenu().removeItem(R.id.nav_gallery);
            navigationView.getMenu().removeItem(R.id.listaUsuario);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String modo = getIntent().getExtras().getString("MODO");
        String username = getIntent().getExtras().getString("USERNAME");

        TextView txtUsuario = (TextView)findViewById(R.id.usuario);
        TextView txtModo = (TextView)findViewById(R.id.lbmodo);

        txtUsuario.setText(username);
        txtModo.setText(modo);

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actividades, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_actividades);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}