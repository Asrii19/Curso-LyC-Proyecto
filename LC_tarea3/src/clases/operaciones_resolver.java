
package clases;

/**
 *
 * @author arnol
 */
public class operaciones_resolver {
    /*
    public ArrayList<Float> primer_num = new ArrayList<>();
    public ArrayList<Float> segundo_num = new ArrayList<>();
    public ArrayList<String> operador = new ArrayList<>();
    public ArrayList<Float> total = new ArrayList<>();
    public int j; //iterador de la posicion de los numeros;
    public int aux_casoparentesis=1;
    */
    public String cadena;
    public String cadena_parentesis="";
    public int parentesis_a,parentesis_c;
    public int i,aux;
    public int largo_cadena;
    scanner scan=new scanner();
    scanner scan_aux;
    operaciones_lineales op_lineal;
    public token token_before=new token();
    public token token_after=new token();
    public String ultimo_numero;
    
    public operaciones_resolver(String cadena) {
        this.cadena = cadena;
        this.aux=0;
        this.largo_cadena=cadena.length();
    }
    
    public Float operar(){
        float resultado=0;
        //INICIA EL SCANNER
        do{
            i=0;
            parentesis_a=0;
            parentesis_c=0;
            scan.setI(i);
            System.out.println("Empieza resolver operacion: \n"+scan.scanner(cadena+'$'));
            i+=scan.token.getNom().length();
            S();
        }while(largo_cadena-1!=ultimo_numero.length());
        System.out.println("RESULTADO: "+cadena);
        
        cadena=cadena.substring(0, cadena.length() - 1);
        resultado=Float.parseFloat(cadena);
        
        return resultado;
    }
    
    /* public Float operar_parentesis(int pos_inicio,int pos_final){
        
    }*/
    
    //LOGICA DEL LL1
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
            //EL PARSER LL1
            parentesis_a++;
            System.out.println(scan.scanner(cadena+'$')); //LEE PARENTESIS ABIERTOS
            i+=scan.token.getNom().length();
            E();
            if(scan.token.getNom().equals(")")){
                parentesis_c++;
                if(parentesis_a+1==(parentesis_a+parentesis_c)){
                    reemplazar();
                }
                
                //LO DEL LL1
                System.out.println(scan.scanner(cadena+'$')); //LEE PARENTESIS CERRADOS
                
                i+=scan.token.getNom().length();
                
            }else{
                aux=-1;//error
                return;
            }
            
            
        }else if(scan.token.getNom().equals("-")){ //13
            F();
        }else if(scan.token.getTipo().equals("I")||scan.token.getTipo().equals("ND")
                ||scan.token.getTipo().equals("N")){ //14
            //EN NUMEROS ENTEROS Y DECIMALES
            //OBTENER TOKENS
            obtener_tokens(scan.token);
            //SE GUARDA EL VALOR DEL ULTIMO NUMERO LEIDO
            ultimo_numero=scan.token.getNom();
            
            //LO DE LL1
            System.out.println(scan.scanner(cadena+'$')); //LEE NUMEROS, DECIMALES E IDs
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
                scan.token.getNom().equals("$")||scan.token.getNom().equals(")")||scan.token.getNom().equals(",")){ //9
            //lambda
        }else{
            aux=-1; //error
            return;
        }
    }//8,9
    void Y(){
        if(scan.token.getNom().equals("*")){ //10
            //EN EL SIGNO MULTIPLICACION
            //OBTENER TOKENS
            obtener_tokens(scan.token);
            
            //LO DEL PARSER LL1
            System.out.println(scan.scanner(cadena+'$')); //LEE OPERADORES MULTIPLICACION
            i+=scan.token.getNom().length();
            
            F();
        }else if(scan.token.getNom().equals("/")){ //11
            //EN EL SIGNO DIVISION
            //OBTENER TOKENS
            obtener_tokens(scan.token);
            
            //LO DEL PARSER LL1
            System.out.println(scan.scanner(cadena+'$')); //LEE OPERADORES DIVISION
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
        }else if((scan.token.getNom().equals("$")||scan.token.getNom().equals(","))&&parentesis_a==parentesis_c){ //4
            if(parentesis_a==0){
                cadena_parentesis=cadena;
                //SE QUITA EL FIN DE CADENA PARA RESOLVER
                cadena_parentesis=cadena_parentesis.substring(0, cadena_parentesis.length()-1);
                resolver();
            }
            aux=1; //reconoce
            return;
        }else{
            aux=-1; //error
            return;
        }
    }//3,4
    void X(){
        if(scan.token.getNom().equals("+")){ //5
            //EN EL SIGNO SUMA
            //OBTENER TOKENS
            obtener_tokens(scan.token);
            
            //LO DEL PARSER LL1
            System.out.println(scan.scanner(cadena+'$')); //LEE OPERADORES SUMA
            i+=scan.token.getNom().length();
            
            T();
        }else if(scan.token.getNom().equals("-")){ //6
            //EN EL SIGNO RESTA
            //OBTENER TOKENS
            obtener_tokens(scan.token);
            
            //LO DEL PARSER LL1
            System.out.println(scan.scanner(cadena+'$')); //LEE OPERADORES RESTA
            i+=scan.token.getNom().length();
            
            T();
        }else{
            aux=-1; //error
            return;
        }
    }//5,6
    
    public void obtener_tokens(token scan_token){
        String avanzar;
        int aux1=i;
        //EN CASO LA OPERACION LINEAL RECIEN EMPIECE
        if(i==token_before.getNom().length()){
            aux1++;
        }
        if(i==0||cadena.charAt(aux1-token_before.getNom().length()-1)=='('){//1+...
            //posicionamos un nuevo scanner en la posicion del scanner actual
            scan_aux=new scanner();
            scan_aux.setI(i);
            //guardamos el siguiente elemento en la cadena
            avanzar=scan_aux.scanner(cadena+'$');
            token_after=scan_aux.token;
            //SI RECIEN EMPIEZA
            cadena_parentesis="";
            cadena_parentesis+=scan.token.getNom();
        }else{
            //posicionamos un nuevo scanner en la posicion del scanner actual
            scan_aux=new scanner();
            scan_aux.setI(i);
            //EL TOKEN ANTERIOR
            token_before=scan.token; //se guarda el valor anterior del token
            //guardamos el siguiente elemento en la cadena
            avanzar=scan_aux.scanner(cadena+'$');
            token_after=scan_aux.token;
            cadena_parentesis+=scan.token.getNom();
            if(token_after.getNom().equals("(")){
                cadena_parentesis="";
            }
        }
    }
    public void reemplazar(){
        //SE OPERA LA CADENA LINEAL OBTENIDA
        float resultado;
        //System.out.println("La cadena a operar es: "+cadena_parentesis);
        op_lineal = new operaciones_lineales(cadena_parentesis);
        resultado=op_lineal.operar();
        //se reemplaza la cadena
        cadena=cadena.replace("("+cadena_parentesis+")",Float.toString(resultado));
        System.out.println("LA NUEVA CADENA ES: "+cadena);
        //se disminuye el tamaño
        largo_cadena=cadena.length();
        i=i-cadena_parentesis.length()-2+Float.toString(resultado).length();
        //SE REESTABLECEN LOS VALORES Y SE VUELVE A OPERAR
        cadena_parentesis="";
        scan.setI(i);
    }
    public void resolver(){
        //SE OPERA LA CADENA LINEAL OBTENIDA
        float resultado;
        //System.out.println("La cadena a operar es: "+cadena_parentesis);
        op_lineal = new operaciones_lineales(cadena_parentesis);
        resultado=op_lineal.operar();
             
        //se reemplaza la cadena
        cadena=cadena.replace(cadena_parentesis,Float.toString(resultado));
        System.out.println("LA NUEVA CADENA ES: "+cadena);
        //se disminuye el tamaño
        largo_cadena=cadena.length();
        i=i-cadena_parentesis.length()+Float.toString(resultado).length();
        //SE REESTABLECEN LOS VALORES Y SE VUELVE A OPERAR
        cadena_parentesis="";
        scan.setI(i);
    }
}
