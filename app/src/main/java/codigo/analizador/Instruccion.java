/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo.analizador;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author yelbetto
 */
public class Instruccion implements Serializable {
    private boolean graficar;
    private String figura;
    private ArrayList<String> parametros;

    public Instruccion(){

    }

    public boolean isGraficar() {
        return graficar;
    }

    public void setGraficar(boolean graficar) {
        this.graficar = graficar;
    }

    public String getFigura() {
        return figura;
    }

    public void setFigura(String figura) {
        this.figura = figura;
    }

    public ArrayList<String> getParametros() {
        return parametros;
    }

    public void setParametros(ArrayList<String> parametros) {
        this.parametros = parametros;
    }


}
