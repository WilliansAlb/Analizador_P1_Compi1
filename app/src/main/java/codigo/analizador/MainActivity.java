package codigo.analizador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
        final TextView label2 = (TextView) findViewById(R.id.area_resultado);
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
            label2.setText("");
            editText.setText("");
        });

        ConstraintLayout layout1 = (ConstraintLayout) findViewById(R.id.constraint);
        Lienzo fondo = new Lienzo(this);
        layout1.addView(fondo);

        button2.setOnClickListener(v -> {
            String ST = label.getText().toString();
            parser s = new parser(new LexerCup(new StringReader(ST)));
            try {
                s.parse();
                String resultado = "Analisis realizado correctamente";
                System.out.println(s.getTemp().size());
                fondo.dibujandoFiguras(s.getTemp());
                label2.setText(resultado);
            } catch (Exception ex) {
                label2.setText("Error");
                Symbol sym = s.getS();
                label2.setText("Error de sintaxis. Linea: " + (sym.right + 1) + " Columna: " + (sym.left + 1) + ", Texto: \"" + sym.value + "\"");

            }
        });
    }
}