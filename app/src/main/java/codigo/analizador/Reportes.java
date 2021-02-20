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
    private ArrayList<String[]> datos_reporte1 = new ArrayList<>();
    private ArrayList<String[]> datos_reporte2 = new ArrayList<>();
    private ArrayList<String[]> datos_reporte3 = new ArrayList<>();
    private ArrayList<String[]> datos_reporte4 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        ArrayList<Operadores> lista = (ArrayList<Operadores>) getIntent().getExtras().get("operadores");
        for (int i = 0; i < lista.size(); i++){
            Operadores temporal = lista.get(i);
            String[] temp = {temporal.getOperacion(),temporal.getFila(),temporal.getColumna(),temporal.getOcurrencia()};
            datos_reporte1.add(temp);
        }
        ColorObjeto creando = (ColorObjeto) getIntent().getExtras().get("colores");
        agregandoColores(creando);
        agregandoFiguras(creando);
        agregandoAnimaciones(creando);
        TableLayout tabla = (TableLayout)findViewById(R.id.rep_operadores);
        TableLayout tabla2 = (TableLayout)findViewById(R.id.rep_colores);
        TableLayout tabla3 = (TableLayout)findViewById(R.id.rep_objetos);
        TableLayout tabla4 = (TableLayout)findViewById(R.id.rep_animaciones);

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
}