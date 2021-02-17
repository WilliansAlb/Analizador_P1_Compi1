package codigo.analizador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

public class Resultado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        ArrayList<Instruccion> lista = (ArrayList<Instruccion>) getIntent().getExtras().get("miLista");
        ScrollView layout1 = (ScrollView) findViewById(R.id.scroll2);
        Lienzo fondo = new Lienzo(this,lista);
        fondo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        layout1.addView(fondo);
    }
}