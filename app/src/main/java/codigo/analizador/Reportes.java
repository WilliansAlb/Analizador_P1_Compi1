package codigo.analizador;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;

import java.util.ArrayList;

public class Reportes extends AppCompatActivity {
    private String[] header = {"OPERADOR","LINEA","COLUMNA","OCURRENCIA"};
    private String[] header2 = {"COLOR","# USOS"};
    private String[] header3 = {"OBJETO","# USOS"};
    private String[] header4 = {"ANIMACION","# USOS"};
    private ArrayList<String[]> rows = new ArrayList<>();
    private ArrayList<String[]> rows2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        TableLayout tabla = (TableLayout)findViewById(R.id.rep_operadores);
        TableLayout tabla2 = (TableLayout)findViewById(R.id.rep_colores);
        TableLayout tabla3 = (TableLayout)findViewById(R.id.rep_objetos);
        TableLayout tabla4 = (TableLayout)findViewById(R.id.rep_animaciones);

        Tabla tab = new Tabla(tabla,getApplicationContext());
        tab.addHeader(header);
        tab.agregarDatos(getDatos());
        tab.colorHeader(Color.WHITE);
        tab.colorDatos(Color.GRAY,Color.LTGRAY);

        Tabla tab2 = new Tabla(tabla2,getApplicationContext());
        tab2.addHeader(header2);
        tab2.agregarDatos(getDatos2());
        tab2.colorHeader(Color.WHITE);
        tab2.colorDatos(Color.GRAY,Color.LTGRAY);

        Tabla tab3 = new Tabla(tabla3,getApplicationContext());
        tab3.addHeader(header3);
        tab3.agregarDatos(getDatos2());
        tab3.colorHeader(Color.WHITE);
        tab3.colorDatos(Color.GRAY,Color.LTGRAY);

        Tabla tab4 = new Tabla(tabla4,getApplicationContext());
        tab4.addHeader(header4);
        tab4.agregarDatos(getDatos2());
        tab4.colorHeader(Color.WHITE);
        tab4.colorDatos(Color.GRAY,Color.LTGRAY);
    }

    private ArrayList<String[]> getDatos(){
        rows.add(new String[]{"SUMA","1","15","12+2"});
        rows.add(new String[]{"RESTA","1","28",")-25"});
        rows.add(new String[]{"SUMA","1","15","8+("});
        rows.add(new String[]{"MULTIPLICACION","1","15","5*44"});
        return rows;
    }

    private ArrayList<String[]> getDatos2(){
        rows2.add(new String[]{"AMARILLO","2"});
        rows2.add(new String[]{"NEGRO","15"});
        return rows2;
    }
}