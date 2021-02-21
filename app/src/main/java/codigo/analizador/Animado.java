package codigo.analizador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

public class Animado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animado);
        ArrayList<Instruccion> lista = (ArrayList<Instruccion>) getIntent().getExtras().get("lista");
        Prueba_A n1 = new Prueba_A(this,lista);
        n1.setLayoutParams( new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.lay);
        layout1.addView(n1,0);
    }
}