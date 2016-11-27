package com.exemple.profedam.memory.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.Toast;

import com.exemple.profedam.memory.R;
import com.exemple.profedam.memory.model.Configuracion;
import com.exemple.profedam.memory.model.Partida;

public class JuegoActivity extends AppCompatActivity {

    private GridView gv;
    private Partida partida;
    private ImageAdapter adapter;
    private Contador timer;
    public static final int SEGUNDO_EN_MILIS = 1000;
    private Configuracion configJuego;

    public Configuracion getConfigJuego() {
        return configJuego;
    }

    public GridView getGv() {
        return gv;
    }

    public void setGv(GridView gv) {
        this.gv = gv;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        if (savedInstanceState == null) {
            configJuego = (Configuracion) getIntent().getSerializableExtra("config");
            gv = (GridView) findViewById(R.id.gridViewMemory);
            partida = new Partida(configJuego.getNumCartas(), configJuego.getCartaBack());
            adapter = new ImageAdapter(this, partida);
            gv.setAdapter(adapter);
            gv.setOnItemClickListener(new GeneralListener(this));
        }
    }

    public void iniciarContador() {
        timer = new Contador(this, configJuego.getTiempoPartidaMilis(), Configuracion.SEGUNDO_EN_MILIS);
        timer.start();
    }

    public void cancelarContador() {
        timer.cancel();
    }

    public void refrescarTablero() {
        gv.setAdapter(adapter);
        gv.refreshDrawableState();
    }
}
