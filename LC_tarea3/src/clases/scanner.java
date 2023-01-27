
package clases;

public class scanner {
    //Atributo del indicador de cada caracter
    private int i;
    //Crear objeto tipo token
    public token token = new token();
    
    //Constructores
    public scanner() {
        i=0;
    }
    
    public String scanner(String cadena) { //se recibe cada linea del texto + '$'
        //Establecemos el tipo y nombre del token en vacio
        token.setNom("");
        token.setTipo("");
         
        //Siempre que se encuentre espacios en blanco en la cadena se ignora aumentando el indicador en uno
        while(cadena.charAt(i)==' '){
            i++;
        }
        
        //Se inicializa la variable que leerá cada caracter con el indicador en 0
        char c = cadena.charAt(i);
        if(c=='\r' && cadena.charAt(i+1)=='\n'){
            token.setNom(token.getNom());
            token.setTipo("L");//Linea
            i++;
            c=cadena.charAt(i++);
        } //LEE SALTOS DE LINEA
        
        if(c>='a'&&c<='z'){
        //Si parte de la cadena empieza con una letra, le puede seguir otra letra o numero
            //Y se lee el resto de cadena con un while
            while((c>='a'&&c<='z')||(c>='0'&&c<='9')){
                token.setNom(token.getNom()+c);
                i++;
                c=cadena.charAt(i);
            }
            token.setTipo("I");  // Identificador
        }else if(c>='0'&& c<='9') {
        //Si parte de la cadena empieza con un numero, necesariamente le sigue otro numero
            while (c >= '0' && c <= '9') {
                token.setNom(token.getNom()+c);
                i++;
                c=cadena.charAt(i);
                if(c=='.'&&cadena.charAt(i+1)>='0'&&cadena.charAt(i+1)<='9'){ //si es decimal
                    token.setNom(token.getNom()+c); //se obtiene el punto
                    i++;
                    c=cadena.charAt(i);
                    while (c >= '0' && c <= '9') { //se guardan lo demás numeros
                        token.setNom(token.getNom()+c);
                        i++;
                        c=cadena.charAt(i);
                    }
                    token.setTipo("ND"); // NUMERO DECIMAL
                }else{
                    token.setTipo("N");// NUMERO NORMAL
                }
            }
        }else if((c==','||c=='('||c==')'||c=='='||c=='*'||c=='/'||c=='-'||c=='+'||
                 c=='<'||c=='>'||c==';'||c=='{'||c=='}'||c=='['||c==']'||c=='.')){
        //Si parte de la cadena empieza con uno de estos simbolos, se analiza el siguiente caracter    
            token.setNom(String.valueOf(c));
            token.setTipo("O");
            if(c=='+'||c=='-'||c=='*'||c=='/'){
                token.setTipo("OA");  // Operador Aritmetico
            }
            if((c=='<'&&cadena.charAt(i+1)=='=')
                    ||(c=='>'&&cadena.charAt(i+1)=='=')
                    ||(c=='<'&&cadena.charAt(i+1)=='<')
                    ||(c=='>'&&cadena.charAt(i+1)=='>')
                    ||(c=='/'&&cadena.charAt(i+1)=='*')
                    ||(c=='*'&&cadena.charAt(i+1)=='/')
                    ||(c=='/'&&cadena.charAt(i+1)=='/')){
                token.setNom(token.getNom()+cadena.charAt(i+1));
                token.setTipo("O");  // Operador normal
                i++;
            }else if((c=='-'&&cadena.charAt(i+1)=='-')
                    ||(c=='+'&&cadena.charAt(i+1)=='+')){
                token.setNom(token.getNom()+cadena.charAt(i+1));
                token.setTipo("OA");  // Operador Aritmetico
                i++;
            }
            i++;
        }else if(c=='$'){
            //Si parte de la cadena empieza con "$" significa que ha llegado a su fin
            token.setNom(String.valueOf(c));
        }
        //Se retorna el nombre del token obtenido
        return token.getNom();
    } 
    
    //getters y setters
    public void setI(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }
}
