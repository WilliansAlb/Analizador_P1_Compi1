package codigo.analizador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

public class Resultado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        ArrayList<Instruccion> lista = (ArrayList<Instruccion>) getIntent().getExtras().get("miLista");
        ArrayList<Operadores> lista2 = (ArrayList<Operadores>) getIntent().getExtras().get("operadores");
        ArrayList<String[]> ocur = (ArrayList<String[]>) getIntent().getExtras().get("list_ocur");
        ColorObjeto creando = (ColorObjeto) getIntent().getExtras().get("colores");
        String ingresado = (String) getIntent().getExtras().get("texto_ingresado");
        ScrollView layout1 = (ScrollView) findViewById(R.id.scroll2);
        Lienzo fondo = new Lienzo(this,lista);
        fondo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        layout1.addView(fondo);
        final Button button = (Button) findViewById(R.id.btn_reporte);
        final Button button2 = (Button) findViewById(R.id.btn_animar);
        Instruccion temporal;
        int animaciones = 0;
        for (int i = 1; i<lista.size(); i++) {
             temporal = lista.get(i);
            if (!temporal.isGraficar()) {
                Instruccion b = lista.get(i - 1);
                if (b.isGraficar()) {
                    animaciones++;
                }
            }
        }
        if (animaciones==0){
            button2.setVisibility(View.GONE);
        } else {
            String numero = "VER ANIMACIONES ("+animaciones+")";
            button2.setText(numero);
        }
        button.setOnClickListener(v -> {
            Intent intent = new Intent(Resultado.this ,Reportes.class);
            intent.putExtra("opera",lista2);
            intent.putExtra("color",creando);
            intent.putExtra("list_ocur",ocur);
            intent.putExtra("hay_errores",false);
            intent.putExtra("texto_ingresado",ingresado);
            startActivity(intent);
        });
        button2.setOnClickListener(v -> {
            //Intent intent = new Intent(Resultado.this ,Animado.class);
            //intent.putExtra("lista",lista);
            //startActivity(intent);
            fondo.invalidate();
            fondo.setAnimados(true);
            fondo.postInvalidateDelayed(1000);
        });
    }
}