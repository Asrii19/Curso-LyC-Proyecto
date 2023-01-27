
package backup;

import clases.*;
import java.util.ArrayList;

/**
 *
 * @author arnold
 */
public class operaciones_reserva {
    private int i;
    private String linea;
    private int aux;
    public int parentesis_a,parentesis_c;
    scanner scan=new scanner();

    variables_guardar var_guard;
 
    public ArrayList<Float> primer_num = new ArrayList<>();
    public ArrayList<Float> segundo_num = new ArrayList<>();
    public ArrayList<String> operador = new ArrayList<>();
    public ArrayList<Float> total = new ArrayList<>();
    public int j; //iterador de la posicion de los numeros;
    public int aux_casoparentesis=1;
    
    //constructor para recibir los parametros del scanner
    public operaciones_reserva(int i) {
        this.i = i;
        aux=0;
        //aux_casoparentesis=1;
    }
    
    //analisis con el parser LL1
    public int analizar(String linea,variables_guardar v_g){
        this.linea=linea;
        this.parentesis_a=this.parentesis_c=0;
        this.var_guard=v_g;
        scan.setI(i); //i=5
        //se empieza a leer la linea
        System.out.println("Empieza operacion: \n"+scan.scanner(linea+'$'));
        S();
        //resetNums();
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
            i+=scan.token.getNom().length();
            E();
            if(scan.token.getNom().equals(")")){
                System.out.println(scan.scanner(linea+'$'));
                parentesis_c++;
                i+=scan.token.getNom().length();
            }else{
                aux=-1;//error
                return;
            }
        }else if(scan.token.getNom().equals("-")){ //13
            F();
        }else if(scan.token.getTipo().equals("I")||scan.token.getTipo().equals("ND")
                ||scan.token.getTipo().equals("N")){ //14
            //System.out.println("token actual: "+scan.token.getNom());
            //guardarNums(scan.token.getNom());
            System.out.println(scan.scanner(linea+'$')); //NUMEROS, DECIMALES E IDs
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
            //guardarOperador(scan.token.getNom());
            System.out.println(scan.scanner(linea+'$')); //OPERADORES
            i+=scan.token.getNom().length();
            F();
        }else if(scan.token.getNom().equals("/")){ //11
            //guardarOperador(scan.token.getNom());
            System.out.println(scan.scanner(linea+'$')); //OPERADORES
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
            i+=scan.token.getNom().length();
            T();
        }else if(scan.token.getNom().equals("-")){ //6
            //guardarOperador(scan.token.getNom());
            System.out.println(scan.scanner(linea+'$')); //OPERADORES
            i+=scan.token.getNom().length();
            T();
        }else{
            aux=-1; //error
            return;
        }
    }//5,6

    //operar con los valores
    public void guardarNums(String token){
        scanner scan_aux=new scanner();
        String token_before,token_after;
        float num1,num2;
        //guardo en una variable lo que hay una posicion antes del token actual
        scan_aux.setI(scan.getI()-token.length()-1);
        //se guarda el token anterior al actual
        token_before=scan_aux.scanner(linea+'$');
        //System.out.println("TOKEN BEFORE: "+token_before);
        //la posicion del cursor vuelve a la posicion ultima del token
        scan_aux.setI(scan.getI());
        //se guarda el token posterior al actual
        token_after=scan_aux.scanner(linea+'$');
        //System.out.println("TOKEN AFTER: "+token_after);
        //A LOS PRIMEROS NUMEROS LE ANTECEDEN EL PARENTESIS ABIERTO O UN IGUAL
        if((token_before.equals("(")||token_before.equals("="))&&
                (token_after.equals("*")||token_after.equals("/")
                ||token_after.equals("+")||token_after.equals("-"))){
            num1=Float.parseFloat(token);
            if(aux_casoparentesis==1){
                primer_num.add(num1);
            }else{
                primer_num.set(j,num1);
            }   
        }

        //A LOS SEGUNDOS NUMEROS LE SIGUEN UN PARENTESIS CERRADO O FIN DE CADENA
        if((token_before.equals("*")||token_before.equals("/")
           ||token_before.equals("+")||token_before.equals("-"))
                &&(token_after.equals(")")||token_after.equals("$")
                ||token_after.equals("*")||token_after.equals("/")
                ||token_after.equals("+")||token_after.equals("-"))){
            num2=Float.parseFloat(token);
            if(aux_casoparentesis==1){
                segundo_num.add(num2);
            }else{
                segundo_num.set(j,num2);
                
            }
            //el after puede ser otro operando, el cierre de parentesis o fin de cadena
            operar(operador.get(j),token_after);
        }
    }
    public void guardarOperador(String token){ 
        scanner scan_aux=new scanner();
        String token_after;
        //la posicion del cursor vuelve a la posicion ultima del token
        scan_aux.setI(scan.getI());
        //se guarda el token posterior al actual
        token_after=scan_aux.scanner(linea+'$');
        //si el token actual es uno de los operandos, se guarda
        if(token.equals("*")||token.equals("/")||token.equals("+")||token.equals("-")){
            if(aux_casoparentesis==1){
                operador.add(token);
            }else{
                operador.set(j,token);
            }
            //si el token que le sigue al operador es un parentesis abierto, se le suma uno al j
            if(token_after.equals("(")){
                segundo_num.add(Float.parseFloat("0"));
                total.add(Float.parseFloat("0"));
                j++;
                aux_casoparentesis=1;
            }
            
        }
    }
    public void operar(String operador,String token_after){
        //29.8+(222+(2-1)+5+9)
        //29.8+(222+(2-1))+8
        scanner scan_aux=new scanner();
        String token_after2;
        if(!token_after.equals("$")){
            scan_aux.setI(scan.getI()+1);
        }else{
            token_after2="";
        }
        token_after2=scan_aux.scanner(linea+'$');
        System.out.println("OPERACION con j="+j+": "+primer_num.get(j)+operador+segundo_num.get(j));
        switch(operador){
            case "+":
                //si el token after es un operando, el resultado de la operacion se transfroma en el primer numero 
                if(token_after.equals("*")||token_after.equals("/")
                ||token_after.equals("+")||token_after.equals("-")
                ||token_after2.equals("+")||token_after2.equals("/")
                ||token_after2.equals("*")||token_after2.equals("-")){
                    if(aux_casoparentesis==1){ //SI LA OPERACION ESTA DENTRO DE UN PARENTESIS SE CREA UN TOTAL MÁS
                        total.add(primer_num.get(j)+segundo_num.get(j));
                        //System.out.println("RPTA1: "+total.get(j));
                        aux_casoparentesis=0;
                    }else{ //SI LA OPERACION SALE DE UN PARÉNTESIS SE ESTABLECE UN VALOR
                        total.set(j,primer_num.get(j)+segundo_num.get(j));
                        //System.out.println("RPTA2: "+total.get(j));
                    }
                    primer_num.set(j,total.get(j));
                    System.out.println("RPTA: "+primer_num.get(j));
                }else if(token_after.equals(")")||token_after.equals("$")||token_after2.equals(")")||token_after2.equals("$")){ //en caso contrario se guarda como segundo numero con un j--                   
                    if(aux_casoparentesis==1){ //SI LA OPERACION ESTA DENTRO DE UN PARENTESIS SE CREA UN TOTAL MÁS
                        total.add(primer_num.get(j)+segundo_num.get(j));
                        segundo_num.set(j,total.get(j));
                        System.out.println("RPTA: "+segundo_num.get(j));
                        aux_casoparentesis=0;
                    }else{ //SI LA OPERACION SALE DE UN PARÉNTESIS SE ESTABLECE UN VALOR
                        total.set(j,primer_num.get(j)+segundo_num.get(j));
                        //System.out.println("RPTA4: "+total.get(j));
                        if(j==0){
                            primer_num.set(j,total.get(j));
                            aux_casoparentesis=0;
                        }
                    }
                    if(token_after.equals(")")){
                        if(j>0&&aux_casoparentesis==0){
                            j--;
                            segundo_num.set(j,total.get(j+1));
                            System.out.println("RPTA: "+segundo_num.get(j));
                            operar(this.operador.get(j),token_after);
                        }
                    }
                    
                }
                break;
            case "-":
                //si el token after es un operando, el resultado de la operacion se transfroma en el primer numero 
                if(token_after.equals("*")||token_after.equals("/")
                ||token_after.equals("+")||token_after.equals("-")
                ||token_after2.equals("+")||token_after2.equals("/")
                ||token_after2.equals("*")||token_after2.equals("-")){
                    if(aux_casoparentesis==1){ //SI LA OPERACION ESTA DENTRO DE UN PARENTESIS SE CREA UN TOTAL MÁS
                        total.add(primer_num.get(j)-segundo_num.get(j));
                        //System.out.println("RPTA1: "+total.get(j));
                        aux_casoparentesis=0;
                    }else{ //SI LA OPERACION SALE DE UN PARÉNTESIS SE ESTABLECE UN VALOR
                        total.set(j,primer_num.get(j)-segundo_num.get(j));
                        //System.out.println("RPTA2: "+total.get(j));
                    }
                    primer_num.set(j,total.get(j));
                }else if(token_after.equals(")")||token_after.equals("$")||token_after2.equals(")")||token_after2.equals("$")){ //en caso contrario se guarda como segundo numero con un j--                   
                    if(aux_casoparentesis==1){ //SI LA OPERACION ESTA DENTRO DE UN PARENTESIS SE CREA UN TOTAL MÁS
                        total.add(primer_num.get(j)-segundo_num.get(j));
                        segundo_num.set(j,total.get(j));
                        //System.out.println("RPTA3: "+total.get(j));
                        aux_casoparentesis=0;
                    }else{ //SI LA OPERACION SALE DE UN PARÉNTESIS SE ESTABLECE UN VALOR
                        total.set(j,primer_num.get(j)-segundo_num.get(j));
                        //System.out.println("RPTA4: "+total.get(j));
                        if(j==0){
                            primer_num.set(j,total.get(j));
                            aux_casoparentesis=0;
                        }
                    }
                    if(token_after.equals(")")){
                        if(j>0&&aux_casoparentesis==0){
                            j--;
                            segundo_num.set(j,total.get(j+1));
                            operar(this.operador.get(j),token_after);
                        }
                    }
                }
                break;
            case "*":
                break;
            case "/":
                break;
        }
    }
    //CLEAR
    public void resetNums() {
        primer_num.clear();
        segundo_num.clear();
        total.clear();
        operador.clear();
        j=0;
    } 
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