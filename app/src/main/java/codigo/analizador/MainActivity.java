package codigo.analizador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.StringReader;
import java.util.ArrayList;

import java_cup.runtime.Symbol;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = (EditText) findViewById(R.id.txt_ingresar);
        final TextView label = (TextView) findViewById(R.id.area_txt);
        final Button button2 = (Button) findViewById(R.id.btn_compilar);
        String mensaje = "Debes de ingresar una instruccion primero!";
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        button2.setOnClickListener(v -> {
            ArrayList<Operadores> listado_operadores = new ArrayList<>();
            String ST = editText.getText().toString();
            if (!ST.isEmpty()) {
                parser s = new parser(new LexerCup(new StringReader(ST)));
                try {
                    s.parse();
                    listado_operadores = s.getListado_operadores();
                    //Intent intent = new Intent(MainActivity.this ,Reportes.class);
                    Intent intent = new Intent(MainActivity.this, Resultado.class);
                    //Intent intent = new Intent(MainActivity.this ,Animado.class);
                    intent.putExtra("miLista", s.getTemp());
                    intent.putExtra("operadores", listado_operadores);
                    intent.putExtra("colores", s.getListado_usos());
                    startActivity(intent);
                } catch (Exception ex) {
                    System.out.println("error por " + ex);
                }
            } else {
                toast.show();
            }
        });
    }
}