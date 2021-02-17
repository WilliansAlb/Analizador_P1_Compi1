package codigo.analizador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
        final Button button = (Button) findViewById(R.id.btn_agregar);
        final Button button2 = (Button) findViewById(R.id.btn_compilar);
        final Button limpiar = (Button) findViewById(R.id.btn_limpiar);
        button.setOnClickListener(v -> {
            String texto = label.getText()+"\n"+editText.getText();
            label.setText(texto);
            editText.setText("");
        });
        limpiar.setOnClickListener(v -> {
            label.setText("");
            editText.setText("");
        });

        button2.setOnClickListener(v -> {
            String ST = label.getText().toString();
            parser s = new parser(new LexerCup(new StringReader(ST)));
            try {
                s.parse();
                Intent intent = new Intent(MainActivity.this ,Resultado.class);
                intent.putExtra("miLista", s.getTemp());
                startActivity(intent);
            } catch (Exception ex) {
                System.out.println("error por "+ex);
            }
        });
    }
}