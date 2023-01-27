
package clases;

import java.util.ArrayList;

/**
 *
 * @author arnold
 */
public class operaciones {
    private int i;
    private String linea;
    private int aux;
    public int prioridad;
    public int parentesis_a,parentesis_c;
    scanner scan=new scanner();

    public String operacion_s;
    
    operaciones_resolver op_resolv;
    
    //constructor para recibir los parametros del scanner
    public operaciones(int i) {
        this.i = i;
        aux=0;
    }
    
    //analisis con el parser LL1
    public int analizar(String linea){
        this.linea=linea;
        this.parentesis_a=this.parentesis_c=0;
        this.operacion_s="";
        float op_resultado=0;
        //se inicializa la i en la posicion en la que se quedo el scanner
        scan.setI(i);
        //se empieza a leer la linea
        System.out.println("Empieza operacion: \n"+scan.scanner(linea+'$'));
        operacion_s+=scan.token.getNom();
        S();
        //muestra operacion y la resuelve
        System.out.println("La operacion es: "+operacion_s);
        op_resolv = new operaciones_resolver(operacion_s);
        op_resultado=op_resolv.operar();
        //guarda valores dependiendo si se declara o se establece
        if(!variables_guardar.actual.equals("LLAMADO")){
            if(variables_guardar.tipo.get(variables_guardar.posicion).equals("int")){
                variables_guardar.guardarvalor((int)op_resultado);
            }else{
                variables_guardar.guardarvalor(op_resultado);
            }
            variables_guardar.aumentar_posicion();
        }else{
            int pos_aux=variables_guardar.var.indexOf(variables_guardar.var_actual);
            if(variables_guardar.tipo.get(pos_aux).equals("int")){
                variables_guardar.establecervalor((int)op_resultado,pos_aux);
            }else{
                variables_guardar.establecervalor(op_resultado,pos_aux);
            }
            variables_guardar.actual="";
            variables_guardar.var_actual="";
        }
        return aux;
    }
    
    void S(){
        E();
        if(aux==1){ //reconoce
            return;
        }else{
            aux=-1; //error
            return;
        }
    }//1
    void E(){
        T();
        W();
    }//2
    void T(){
        F();
        R();
    }//7
    void F(){
        if(scan.token.getNom().equals("(")){ //12
            System.out.println(scan.scanner(linea+'$'));
            parentesis_a++;
            operacion_s+=scan.token.getNom();
            i+=scan.token.getNom().length();
            E();
            if(scan.token.getNom().equals(")")){                
                System.out.println(scan.scanner(linea+'$'));
                parentesis_c++;
                operacion_s+=scan.token.getNom();
                i+=scan.token.getNom().length();
            }else{
                aux=-1;//error
                return;
            }
        }else if(scan.token.getNom().equals("-")){ //13
            F();
        }else if(scan.token.getTipo().equals("I")||scan.token.getTipo().equals("ND")
                ||scan.token.getTipo().equals("N")){ //14
            System.out.println(scan.scanner(linea+'$')); //NUMEROS, DECIMALES E IDs
            operacion_s+=scan.token.getNom();
            i+=scan.token.getNom().length();
        }else{
            aux=-1; //error
            return;
        }
    }//12,13,14
    void R(){
        if(scan.token.getNom().equals("*")||scan.token.getNom().equals("/")){ //8
            Y();
            R();
        }else if(scan.token.getNom().equals("+")||scan.token.getNom().equals("-")||
                scan.token.getNom().equals("$")||scan.token.getNom().equals(")")){ //9
            //lambda
        }else{
            aux=-1; //error
            return;
        }
    }//8,9
    void Y(){
        if(scan.token.getNom().equals("*")){ //10
            System.out.println(scan.scanner(linea+'$')); //OPERADORES
            operacion_s+=scan.token.getNom();
            i+=scan.token.getNom().length();
            F();
        }else if(scan.token.getNom().equals("/")){ //11
            //guardarOperador(scan.token.getNom());
            System.out.println(scan.scanner(linea+'$')); //OPERADORES
            operacion_s+=scan.token.getNom();
            i+=scan.token.getNom().length();
            F();
        }else{
            aux=-1; //error
            return;
        }
    }//10,11
    void W(){
        if(scan.token.getNom().equals("+")||scan.token.getNom().equals("-")){ //3
            X();
            W();//aqui
        }else if(scan.token.getNom().equals("$")&&parentesis_a==parentesis_c){ //4
            aux=1; //reconoce
            return;
        }else{
            aux=-1; //error
            return;
        }
    }//3,4
    void X(){
        if(scan.token.getNom().equals("+")){ //5
            //guardarOperador(scan.token.getNom());
            System.out.println(scan.scanner(linea+'$')); //OPERADORES
            operacion_s+=scan.token.getNom();
            i+=scan.token.getNom().length();
            T();
        }else if(scan.token.getNom().equals("-")){ //6
            //guardarOperador(scan.token.getNom());
            System.out.println(scan.scanner(linea+'$')); //OPERADORES
            operacion_s+=scan.token.getNom();
            i+=scan.token.getNom().length();
            T();
        }else{
            aux=-1; //error
            return;
        }
    }//5,6
    
    
    
    //GETTERS Y SETTERS
    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
    
}
/*
operacion=(3*(5+6)+2)

S
 E
  T
   F->(
    E
     T
      F->3
     T
      R 
       Y->*
        F->(
	 E
	  T
	   F->5
	  T
	   R
	  T
	 E
	  W
	   X->+
	    T
	     F->6
	    T
	     R
	    T
	   X
	  W
	 E
	F->)
       Y
      R
       R
      R
     T
    E
     W
      X->+
       T
        F->2
       T
        R
       T
      X
     W
    E
   F->)
  T
   R
  T
 E
S  
*/