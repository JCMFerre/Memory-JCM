package com.exemple.profedam.memory.controllers;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.exemple.profedam.memory.R;
import com.exemple.profedam.memory.model.Partida;

public class ActivityJuego extends AppCompatActivity {

    private GridView gv;
    private Partida partida;
    private ImageAdapter adapter;
    private CountDownTimer timer;


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
        gv = (GridView) findViewById(R.id.gridViewMemory);
        //TODO este 12 hay que calcularlo de alguna manera
        partida = new Partida(12);
        // timer = new Timer(2000, (TextView) findViewById(R.id.textTimeLeft));

        adapter = new ImageAdapter(this, partida);
        GeneralListener listener = new GeneralListener(this);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(listener);

        /*gv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(TaulerActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });*/
    }
    public void refrescarTablero ()
    {
        gv.setAdapter(adapter);
        gv.refreshDrawableState();
    }
}
