package com.exemple.profedam.memory.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;

import com.exemple.profedam.memory.R;
import com.exemple.profedam.memory.model.Partida;

import java.util.Timer;

public class MainActivity extends Activity {
    private GridView gv;
    private Partida partida;
    private ImageAdapter adapter;
    private CountDownTimer  timer;


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

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Intent intent = getIntent();
        gv = (GridView) findViewById(R.id.gridViewMemory);
        //TODO este 12 hay que calcularlo de alguna manera
        partida = new Partida (12);
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


