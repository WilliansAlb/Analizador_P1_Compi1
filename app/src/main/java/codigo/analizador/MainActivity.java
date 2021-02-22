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

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import java_cup.Lexer;
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
                LexerCup lexi = new LexerCup(new StringReader(ST));
                parser s = new parser(new LexerCup(new StringReader(ST)));
                try {
                    s.parse();
                    if (s.getListado_errores().size()==0) {
                        listado_operadores = s.getListado_operadores();
                        //Intent intent = new Intent(MainActivity.this ,Reportes.class);
                        Intent intent = new Intent(MainActivity.this, Resultado.class);
                        //Intent intent = new Intent(MainActivity.this ,Animado.class);
                        intent.putExtra("hay_errores",false);
                        intent.putExtra("miLista", s.getTemp());
                        intent.putExtra("list_ocur", devuelta(lexi));
                        intent.putExtra("operadores", listado_operadores);
                        intent.putExtra("colores", s.getListado_usos());
                        intent.putExtra("texto_ingresado",ST);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this ,Reportes.class);
                        intent.putExtra("hay_errores",true);
                        intent.putExtra("lista_errores", s.getListado_errores());
                        intent.putExtra("texto_ingresado",ST);
                        System.out.println("aca");
                        startActivity(intent);
                    }
                } catch (Exception ex) {
                    System.out.println("error por " + ex);
                }
            } else {
                toast.show();
            }
        });
    }

    public ArrayList<String[]> devuelta(LexerCup lexi){
        ArrayList<Symbol> simbolos = new ArrayList<>();
        ArrayList<String[]> ocurrencias = new ArrayList<>();
        while(true){
            Symbol sym = new Symbol(-1);
            try {
                sym = lexi.next_token();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (sym!=null && sym.value !=null){
                simbolos.add(sym);
            } else {
                break;
            }
        }
        for (int i = 0; i < simbolos.size(); i++) {
            Symbol temp = simbolos.get(i);
            if (temp.value.toString().equalsIgnoreCase("+") || temp.value.toString().equalsIgnoreCase("-")|| temp.value.toString().equalsIgnoreCase("*")|| temp.value.toString().equalsIgnoreCase("/")){
                String alapar = simbolos.get(i-1).value.toString()+temp.value.toString()+simbolos.get(i+1).value.toString();
                String[] te = new String[5];
                switch(temp.value.toString()){
                    case "+":
                        String[] te2 = {"SUMA",(temp.right+1)+"",(temp.left+1)+"",alapar};
                        te = te2;
                        break;
                    case "-":
                        String[] te3 = {"RESTA",(temp.right+1)+"",(temp.left+1)+"",alapar};
                        te = te3;
                        break;
                    case "*":
                        String[] te4 = {"MULTIPLICACION",(temp.right+1)+"",(temp.left+1)+"",alapar};
                        te = te4;
                        break;
                    case "/":
                        String[] te5 = {"DIVISION",(temp.right+1)+"",(temp.left+1)+"",alapar};
                        te = te5;
                        break;
                    default:
                        break;
                }
                ocurrencias.add(te);
            }
        }
        return ocurrencias;
    }
}