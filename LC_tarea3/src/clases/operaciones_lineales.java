
package clases;

import Frames.Principal;
import java.util.ArrayList;


/**
 *
 * @author arnol
 */
public class operaciones_lineales {
    public String cadena;
    public ArrayList<Float> num1 = new ArrayList<>();
    public ArrayList<String> op = new ArrayList<>();
    public scanner scan_aux = new scanner();
    public int i;
    public operaciones_lineales(String cadena) {
        this.cadena = cadena;
        this.i=0;
        clear();
    }
    public Float operar(){
        float resultado=0;
        System.out.println("Cadena: "+cadena);
        scan_aux.setI(0);
        leer(cadena);
        do{
            resolver();
        }while(num1.size()!=1);
        resultado=num1.get(0);
        
        
        
        return resultado;
    }
    public void leer(String c){
        do{
            System.out.println("Token: "+scan_aux.scanner(cadena+"$"));
            i+=scan_aux.token.getNom().length();
            guardar(scan_aux.token);
        }while(!scan_aux.token.getNom().equals("$"));
    }
    public void guardar(token tok){
        if(tok.getTipo().equals("I")){
            if(variables_guardar.var.contains(tok.getNom())){
                num1.add(variables_guardar.val.get(variables_guardar.var.indexOf(tok.getNom())));
            }else{
                Principal.salida+="Variable '"+scan_aux.token.getNom()+"' no declarada.\n";//se guarda el error
                sintactico.q=-1;//ERROR
            }
        }else if(tok.getTipo().equals("N")||tok.getTipo().equals("ND")){
            num1.add(Float.parseFloat(tok.getNom()));
        }else if(tok.getTipo().equals("O")||tok.getTipo().equals("OA")){
            op.add(tok.getNom());
        }
    }
    public void resolver(){
        if(op.contains("*")||op.contains("/")){
            int casoproducto=op.indexOf("*");
            int casodivision=op.indexOf("/");
            int caso;
            if(casoproducto==-1){
                caso=casodivision;
            }else if(casodivision==-1){
                caso=casoproducto;
            }else if(casoproducto<casodivision){
                caso=casoproducto;
            }else{
                caso=casodivision;
            }
            if(casoproducto==-1&&casodivision==-1){
                return;
            }
            switch(op.get(caso)){
                case "*":
                    num1.set(caso,num1.get(caso)*num1.get(caso+1));
                    num1.remove(caso+1);
                    op.remove(caso);
                    break;
                case "/":
                    num1.set(caso,num1.get(caso)/num1.get(caso+1));
                    num1.remove(caso+1);
                    op.remove(caso);
                    break;
            }
        }else{
            int casosuma=op.indexOf("+");
            int casoresta=op.indexOf("-");
            int caso;
            if(casosuma==-1){
                caso=casoresta;
            }else if(casoresta==-1){
                caso=casosuma;
            }else if(casosuma<casoresta){
                caso=casosuma;
            }else{
                caso=casoresta;
            }
            if(casosuma==-1&&casoresta==-1){
                return;
            }
            switch(op.get(caso)){
                case "+":
                    num1.set(caso,num1.get(caso)+num1.get(caso+1));
                    num1.remove(caso+1);
                    op.remove(caso);
                    break;
                case "-":
                    num1.set(caso,num1.get(caso)-num1.get(caso+1));
                    num1.remove(caso+1);
                    op.remove(caso);
                    break;
            }
        }
    }
    public void clear(){
        num1.clear();
        op.clear();
    }
    
}
