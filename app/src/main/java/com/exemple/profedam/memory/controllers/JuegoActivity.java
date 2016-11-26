package com.exemple.profedam.memory.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.exemple.profedam.memory.R;
import com.exemple.profedam.memory.model.Partida;

public class JuegoActivity extends AppCompatActivity {

    private GridView gv;
    private Partida partida;
    private ImageAdapter adapter;
    private Contador timer;
    private int segundos;
    private final int SEGUNDO_EN_MILIS = 1000;


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
            gv = (GridView) findViewById(R.id.gridViewMemory);
            partida = new Partida(12);
            adapter = new ImageAdapter(this, partida);
            // CAMBIAR
            segundos = 20;
            gv.setAdapter(adapter);
            gv.setOnItemClickListener(new GeneralListener(this));
        }
    }

    public void iniciarContador() {
        timer = new Contador(this, segundos * SEGUNDO_EN_MILIS, SEGUNDO_EN_MILIS);
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
