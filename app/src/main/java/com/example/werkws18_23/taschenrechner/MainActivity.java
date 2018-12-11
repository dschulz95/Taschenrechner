package com.example.werkws18_23.taschenrechner;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    int anzahlKonnektor;
    int anzahlZahl;
    String konnektor;
    Button nullButton;
    Button einsButton;
    Button andButton;
    Button orButton;
    Button xorButton;
    Button ergebnisButton;
    Button backButton;
    Button loeschenButton;
    Button dezimalButton;
    boolean dez;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView berechnungsFeld = findViewById(R.id.berechnungsFeld);
        final TextView ergebnisFeld = findViewById(R.id.ergebnisFeld);
        nullButton=findViewById(R.id.nullButton);
        nullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                berechnungsFeld.setText ( berechnungsFeld.getText()+"0");
                anzahlZahl++;
            }
        });
        einsButton=findViewById(R.id.einsButton);
        einsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                berechnungsFeld.setText ( berechnungsFeld.getText()+"1");
                anzahlZahl++;
            }
        });

        andButton=findViewById(R.id.andButton);
        andButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(anzahlKonnektor > 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Es darf nur ein Konnektor in Berechnung verwendet werden", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(anzahlZahl < 1){
                    Toast toast = Toast.makeText(getApplicationContext(), "Es muss zuerst eine Zahl eingegeben werden", Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                   anzahlKonnektor = 1;
                   konnektor = "UND";
                   berechnungsFeld.setText ( berechnungsFeld.getText()+"&");
               }
            }
        });

        orButton=findViewById(R.id.orButton);
        orButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(anzahlKonnektor > 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Es darf nur ein Konnektor in Berechnung verwendet werden", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(anzahlZahl < 1){
                    Toast toast = Toast.makeText(getApplicationContext(), "Es muss zuerst eine Zahl eingegeben werden", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    anzahlKonnektor = 1;
                    konnektor = "ODER";
                    berechnungsFeld.setText ( berechnungsFeld.getText()+"|");
                }
            }
        });

        xorButton=findViewById(R.id.xorButton);
        xorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(anzahlKonnektor > 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Es darf nur ein Konnektor in Berechnung verwendet werden", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(anzahlZahl < 1){
                    Toast toast = Toast.makeText(getApplicationContext(), "Es muss zuerst eine Zahl eingegeben werden", Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    anzahlKonnektor = 1;
                    konnektor = "XOR";
                    berechnungsFeld.setText ( berechnungsFeld.getText()+"^");
                }
            }
        });

        //Ergebnis entsprechend ausrechnen
        ergebnisButton=findViewById(R.id.ergebnisButton);
        ergebnisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean setDisabled = false;
                if(ergebnisFeld.getText().toString().isEmpty()) {
                    if (anzahlKonnektor == 0) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Es muss ein Konnektor angegeben werden", Toast.LENGTH_SHORT);
                        toast.show();
                    } else if (konnektor.equals("UND")) {
                        //man muss hier Pattern.quote verwenden, weil er sonst das & als regulären Ausdruck interpretiert
                        String[] parts = (berechnungsFeld.getText().toString()).split(Pattern.quote("&"));
                        if (parts.length < 2) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Es muss nach dem Konnektor eine Zahl stehen", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            int a = Integer.parseInt(parts[0], 2);
                            int b = Integer.parseInt(parts[1], 2);
                            int temp = (a & b);
                            String c = Integer.toBinaryString(temp);
                            ergebnisFeld.setText(c);
                            setDisabled = true;
                        }
                    } else if (konnektor.equals("ODER")) {
                        //man muss hier Pattern.quote verwenden, weil er sonst das | als regulären Ausdruck interpretiert
                        String[] parts = (berechnungsFeld.getText().toString()).split(Pattern.quote("|"));
                        Log.d("tag", parts[0]);
                        if (parts.length < 2) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Es muss nach dem Konnektor eine Zahl stehen", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {

                            int a = Integer.parseInt(parts[0], 2);
                            int b = Integer.parseInt(parts[1], 2);
                            int temp = (a | b);
                            String c = Integer.toBinaryString(temp);
                            ergebnisFeld.setText(c);
                            setDisabled = true;
                        }
                    } else {
                        //man muss hier Pattern.quote verwenden, weil er sonst das ^ als regulären Ausdruck interpretiert
                        String[] parts = (berechnungsFeld.getText().toString()).split(Pattern.quote("^"));
                        if (parts.length < 2) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Es muss nach dem Konnektor eine Zahl stehen", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            int a = Integer.parseInt(parts[0], 2);
                            int b = Integer.parseInt(parts[1], 2);
                            int c = a ^ b;
                            ergebnisFeld.setText(String.valueOf(c));
                            setDisabled = true;
                        }
                    }
                    if (setDisabled) {
                        nullButton.setEnabled(false);
                        einsButton.setEnabled(false);
                        andButton.setEnabled(false);
                        orButton.setEnabled(false);
                        xorButton.setEnabled(false);
                        backButton.setEnabled(false);
                        Toast toast = Toast.makeText(getApplicationContext(), "Für neue Berechnung bitte den Lösch-Button drücken", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }else{
                    //in Binärzahl umwandeln, falls es eine Dezimlazhal ist
                    if(dez == true){
                        int i = Integer.parseInt(ergebnisFeld.getText().toString());
                        String c = Integer.toBinaryString(i);
                        ergebnisFeld.setText(c);
                        dez = false;
                    }
                }
            }
        });

        //letztes eingegebenes Zeichen löschen
        backButton = findViewById((R.id.backButton));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = berechnungsFeld.getText().toString();
                //letztes Zeichen löschen vom String, wenn Zeichen vorhanden sind
                if(s.length()>0){
                    berechnungsFeld.setText(s.substring(0,s.length()-1));
                    char lastChar =s.charAt(s.length()-1);
                    if(lastChar=='|'|| lastChar=='&'||lastChar =='^'){
                        anzahlKonnektor = 0;
                        anzahlZahl=(anzahlZahl-1)>0?anzahlZahl-1:0;
                    }
                }
            }
        });

        //Ergebnisfeld leeren
        loeschenButton=findViewById(R.id.loeschenButton);
        loeschenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                berechnungsFeld.setText ("");
                anzahlKonnektor=0;
                anzahlZahl=0;
                ergebnisFeld.setText ("");
                nullButton.setEnabled(true);
                einsButton.setEnabled(true);
                andButton.setEnabled(true);
                orButton.setEnabled(true);
                xorButton.setEnabled(true);
                backButton.setEnabled(true);
            }
        });

        //in Dezimal umrechnen
        dezimalButton = findViewById(R.id.dezimalButton);
        dezimalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ergebnisFeld.getText().toString().isEmpty()){
                    if(dez == false) {
                        int dezZahl = Integer.parseInt(ergebnisFeld.getText().toString(), 2);
                        ergebnisFeld.setText(String.valueOf(dezZahl));
                        dez = true;
                    }
                }
            }
        });


    }
}
