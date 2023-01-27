
package clases;

import java.util.ArrayList;

/**
 *
 * @author arnol
 */
public class variables_guardar {
    public static ArrayList<String> tipo = new ArrayList<>();
    public static ArrayList<String> var = new ArrayList<>();
    public static ArrayList<Float> val = new ArrayList<>();
    public static String actual="",var_actual="";
    //POSICION DE TODAS LAS VARIABLES
    public static int posicion;
    
    
    public static void aumentar_posicion(){
        posicion++;
    }
    public static void guardarid(String variable, String tip){
        tipo.add(tip);
        var.add(variable);
        val.add(Float.parseFloat("0"));
    }
    public static void guardarvalor(float valor){
        val.set(posicion,valor);
    }
    public static void establecervalor(float valor,int pos_aux){
        val.set(pos_aux,valor);
    }
    public static void clear(){
        tipo.clear();
        var.clear();
        val.clear();
        posicion=0;
    }
    
}
