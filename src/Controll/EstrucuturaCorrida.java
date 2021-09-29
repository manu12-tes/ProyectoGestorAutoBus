/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controll;

/**
 *
 * @author manel
 */
import java.util.ArrayList;
import java.util.List;
import Modelo.Corridas;
public class EstrucuturaCorrida {
     private Class estructura[]={String.class,String.class,String.class,String.class,String.class, String.class,String.class,String.class};
   private String encabezados[]={"numm_codigo","origen ","destino","fecha","hora","Autobus","Num_asientos","precio"};
   public Class[] getEstrucutura(){return estructura;}
   public String [] getEncabezados (){return encabezados;}
   public List<Object[]>getRegistros(List<Corridas> entra){
     List <Object[]> este=new ArrayList<Object[]>();
     
     for(Corridas cor : entra){
       Object dato[]= new Object[estructura.length];
     dato[0]=cor.getIdCorrida();
     dato[1]=cor.getOrigen().getNombre();
     dato[2]=cor.getDestino().getNombre();
     dato[3]=cor.getFecha();
     dato[4]=cor.getHora();
     dato[5]=cor.getAutobus().getNombre();
     dato[6]=cor.getAutobus().getNumAisentos();
     dato[7]=cor.getCosto();
     este.add(dato);
         
     }
     return este;
   }
    
}
