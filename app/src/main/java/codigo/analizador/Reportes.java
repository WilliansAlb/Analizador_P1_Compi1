package codigo.analizador;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Reportes extends AppCompatActivity {
    private String[] header = {"OPERADOR","LINEA","COLUMNA","OCURRENCIA"};
    private String[] header2 = {"COLOR","# USOS"};
    private String[] header3 = {"OBJETO","# USOS"};
    private String[] header4 = {"ANIMACION","# USOS"};
    private String[] header5 = {"LEXEMA","LINEA","COLUMNA","TIPO","DESCRIPCION"};
    private ArrayList<String[]> datos_reporte1 = new ArrayList<>();
    private ArrayList<String[]> datos_reporte2 = new ArrayList<>();
    private ArrayList<String[]> datos_reporte3 = new ArrayList<>();
    private ArrayList<String[]> datos_reporte4 = new ArrayList<>();
    private ArrayList<String[]> datos_reporte5 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        boolean hay_errores = (boolean) getIntent().getExtras().get("hay_errores");
        String ingresado = (String) getIntent().getExtras().get("texto_ingresado");
        TableLayout tabla = (TableLayout)findViewById(R.id.rep_operadores);
        TableLayout tabla2 = (TableLayout)findViewById(R.id.rep_colores);
        TableLayout tabla3 = (TableLayout)findViewById(R.id.rep_objetos);
        TableLayout tabla4 = (TableLayout)findViewById(R.id.rep_animaciones);
        TableLayout tabla5 = (TableLayout)findViewById(R.id.rep_errores);
        final TextView report1 = (TextView) findViewById(R.id.reporte_1);
        final TextView report2 = (TextView) findViewById(R.id.reporte_2);
        final TextView report3 = (TextView) findViewById(R.id.reporte_3);
        final TextView report4 = (TextView) findViewById(R.id.reporte_4);
        final TextView report5 = (TextView) findViewById(R.id.reporte_5);
        if (hay_errores){
            tabla.setVisibility(View.GONE);
            tabla2.setVisibility(View.GONE);
            tabla3.setVisibility(View.GONE);
            tabla4.setVisibility(View.GONE);
            report1.setVisibility(View.GONE);
            report2.setVisibility(View.GONE);
            report3.setVisibility(View.GONE);
            report4.setVisibility(View.GONE);
            ArrayList<Errores> lista = (ArrayList<Errores>) getIntent().getExtras().get("lista_errores");
            for (int i = 0; i < lista.size(); i++){
                Errores temp = lista.get(i);
                datos_reporte5.add(new String[]{temp.getLexema(),temp.getLinea(),temp.getColumna(),temp.getTipo(),temp.getDescripcion()});
            }
            Tabla tab2 = new Tabla(tabla5,getApplicationContext());
            tab2.addHeader(header5);
            tab2.agregarDatos(datos_reporte5);
            tab2.colorHeader(Color.WHITE);
            tab2.colorDatos(Color.GRAY,Color.LTGRAY);

        } else {
            ArrayList<Operadores> lista = (ArrayList<Operadores>) getIntent().getExtras().get("opera");
            datos_reporte1 = (ArrayList<String[]>) getIntent().getExtras().get("list_ocur");
            ColorObjeto creando = (ColorObjeto) getIntent().getExtras().get("color");
            agregandoColores(creando);
            agregandoFiguras(creando);
            agregandoAnimaciones(creando);
            if (datos_reporte1.size()==0){
                datos_reporte1.add(new String[]{"NO","HAY","OPERADORES","MATEMATICOS"});
            }
            if (datos_reporte4.size()==0){
                datos_reporte4.add(new String[]{"NO HAY","ANIMACIONES"});
            }
            Tabla tab = new Tabla(tabla,getApplicationContext());
            tab.addHeader(header);
            tab.agregarDatos(datos_reporte1);
            tab.colorHeader(Color.WHITE);
            tab.colorDatos(Color.GRAY,Color.LTGRAY);

            Tabla tab2 = new Tabla(tabla2,getApplicationContext());
            tab2.addHeader(header2);
            tab2.agregarDatos(datos_reporte2);
            tab2.colorHeader(Color.WHITE);
            tab2.colorDatos(Color.GRAY,Color.LTGRAY);

            Tabla tab3 = new Tabla(tabla3,getApplicationContext());
            tab3.addHeader(header3);
            tab3.agregarDatos(datos_reporte3);
            tab3.colorHeader(Color.WHITE);
            tab3.colorDatos(Color.GRAY,Color.LTGRAY);

            Tabla tab4 = new Tabla(tabla4,getApplicationContext());
            tab4.addHeader(header4);
            tab4.agregarDatos(datos_reporte4);
            tab4.colorHeader(Color.WHITE);
            tab4.colorDatos(Color.GRAY,Color.LTGRAY);
            report5.setVisibility(View.GONE);
            tabla5.setVisibility(View.GONE);
        }
    }

    public void agregandoColores(ColorObjeto temp){
        String[] nombres_colores = {"rojo","azul","verde","amarillo","cafe","negro","morado","naranja"};
        int[] veces = {temp.getRojo(),temp.getAzul(),temp.getVerde(),temp.getAmarillo(),temp.getCafe(),temp.getNegro(),temp.getMorado(),temp.getNaranja()};
        agregando(nombres_colores,veces,1);
    }
    public void agregandoFiguras(ColorObjeto temp){
        String[] nombres = {"circulo","rectangulo","cuadrado","linea","poligono"};
        int[] veces = {temp.getCirculo(),temp.getRectangulo(),temp.getCuadrado(),temp.getLinea_figura(),temp.getPoligono()};
        agregando(nombres,veces,2);
    }
    public void agregandoAnimaciones(ColorObjeto temp){
        String[] nombres = {"linea","curva"};
        int[] veces = {temp.getLinea(),temp.getCurva()};
        agregando(nombres,veces,3);
    }
    public void agregando(String[] nombres, int[] veces, int opcion){
        for (int i = 0; i < veces.length; i++) {
            if (veces[i] != 0) {
                if (opcion==1){
                    datos_reporte2.add(new String[]{nombres[i], veces[i] + ""});
                } else if (opcion==2){
                    datos_reporte3.add(new String[]{nombres[i], veces[i] + ""});
                } else if (opcion==3){
                    datos_reporte4.add(new String[]{nombres[i], veces[i] + ""});
                }
            }
        }
    }
    public void tablas(TableLayout tabla_temp, String[] head, ArrayList<String[]> data){
        Tabla tab = new Tabla(tabla_temp,getApplicationContext());
        tab.addHeader(head);
        tab.agregarDatos(data);
        tab.colorHeader(Color.WHITE);
        tab.colorDatos(Color.GRAY,Color.LTGRAY);
    }
}