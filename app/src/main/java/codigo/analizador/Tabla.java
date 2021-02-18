package codigo.analizador;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class Tabla {
    private TableLayout tabla;
    private Context contexto;
    private String[] header;
    private ArrayList<String[]> datos;
    private TableRow row;
    private TextView cell;
    private int index_C;
    private int index_R;
    private boolean multicolor = false;

    public Tabla(TableLayout tabla, Context contexto){
        this.tabla = tabla;
        this.contexto = contexto;
    }

    public void addHeader(String[] header){
        this.header = header;
        crearHeader();
    }

    public void agregarDatos(ArrayList<String[]> datos){
        this.datos = datos;
        crearTablaDatos();
    }

    private void newRow(){
        row = new TableRow(contexto);
    }
    private void newCell(){
        cell = new TextView(contexto);
        cell.setGravity(Gravity.CENTER);
        cell.setTextSize(15);
    }

    private void crearHeader(){
        index_C = 0;
        newRow();
        while(index_C<header.length){
            newCell();
            cell.setText(header[index_C++]);
            row.addView(cell,newTableRowParams());
        }
        tabla.addView(row);
    }
    private void crearTablaDatos(){
        String info;
        for (index_R=1; index_R <= datos.size(); index_R++){
            newRow();
            for(index_C = 0; index_C < header.length; index_C++){
                newCell();
                String[] filas = datos.get(index_R-1);
                info = (index_C<filas.length)?filas[index_C]:"";
                cell.setText(info);
                row.addView(cell,newTableRowParams());
            }
            tabla.addView(row);
        }
    }

    private TableRow.LayoutParams newTableRowParams(){
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(1,1,1,1);
        params.weight = 1;
        return params;
    }

    public void colorHeader(int color){
        index_C = 0;
        while (index_C < header.length){
            cell = getCell(0,index_C++);
            cell.setBackgroundColor(color);
            cell.setTypeface(null, Typeface.BOLD);
        }
    }

    public void colorDatos(int color, int color2){
        for (index_R=1; index_R <= datos.size(); index_R++){
            multicolor = !multicolor;
            for(index_C = 0; index_C < header.length; index_C++){
                cell = getCell(index_R,index_C);
                cell.setBackgroundColor((multicolor)?color:color2);
            }
        }
    }

    private TableRow getRow(int index){
        return (TableRow) tabla.getChildAt(index);
    }

    private TextView getCell(int rowIndex, int columnIndex){
        row = getRow(rowIndex);
        return (TextView) row.getChildAt(columnIndex);
    }

    public void addItems(String[] item){
        String info;
        datos.add(item);
        index_C = 0;
        newRow();
        while(index_C < header.length){
            newCell();
            info=(index_C<item.length)?item[index_C++]:"";
            cell.setText(info);
            row.addView(cell,newTableRowParams());
        }
        tabla.addView(row,datos.size()-1);
    }

}

