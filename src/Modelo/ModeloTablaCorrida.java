/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author manel
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import Controll.EstrucuturaCorrida;
import java.util.List;
public class ModeloTablaCorrida extends AbstractTableModel{
     private List<Object[]> datos;
  private EstrucuturaCorrida est;
  private String encabezados[];
  private int colExis;
  private Class tipos[];
    public ModeloTablaCorrida (EstrucuturaCorrida esm,List <Corridas> entrada){
         est=esm;
       datos=esm.getRegistros(entrada);
       tipos=esm.getEstrucutura();
       encabezados=esm.getEncabezados();
    }

    @Override
    public int getRowCount() {
       return datos.size(); // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getColumnCount() {
        return encabezados.length; // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return datos.get(rowIndex)[columnIndex]; // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public Class getColumnClass(int c){return tipos[c];}
     @Override
    public String getColumnName(int c) {
        return encabezados[c];
    }
}
