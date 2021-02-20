/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo.analizador;

import java.io.Serializable;

/**
 *
 * @author yelbetto
 */
public class Operadores implements Serializable{
    private String operacion;
    private String fila;
    private String columna;
    private String ocurrencia;
    
    public Operadores(){
    
    }
    public Operadores(String operacion, String fila, String columna, String ocurrencia){
        this.operacion = operacion;
        this.fila = fila;
        this.columna = columna;
        this.ocurrencia = ocurrencia;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public String getOcurrencia() {
        return ocurrencia;
    }

    public void setOcurrencia(String ocurrencia) {
        this.ocurrencia = ocurrencia;
    }
    
}
