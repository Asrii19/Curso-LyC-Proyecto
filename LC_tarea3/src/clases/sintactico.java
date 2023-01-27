
package clases;

import Frames.Principal;
import java.util.ArrayList;

public class sintactico {
    public ArrayList<String> cad_cand = new ArrayList<>(); //vector con cadenas candidatas
    public ArrayList<Boolean> errores = new ArrayList<>(); //vector con el reconocimiento en cada linea
    public static int q; //ESTADO
    Boolean multiplelinea=Boolean.FALSE;
    //Se añade la linea del texto al array
    public void crearCadena(String s) {
        this.cad_cand.add(s); //CON UNA ITERACION EN LA CLASE PRINCIPAL, SE OBTIENE TODO EL TEXTO
    }
    public void Analisis(){
        //Se itera cada linea del texto
        for(int i=0;i<cad_cand.size();i++){
            //Se crea el objeto tipo scanner, el iterador i del scanner se iguala a 0
            scanner scan=new scanner();
            
            //NUEVO
            String tipo="";
            
            q = 0;
            //Obtenemos, por linea, toda la cadena de caracteres
            String texto = cad_cand.get(i);
            while(q!=100&&q!=-1){
                //Con el metodo scanner se analiza cada parte de la cadena, por linea
                System.out.println(scan.scanner(texto+'$'));
                switch(q){
                    case 0:
                        if(scan.token.getNom().equals("$")){ //lineas en blanco
                            q=100;
                        }else if(scan.token.getNom().equals("int")||scan.token.getNom().equals("float")){
                            q=1;
                            tipo=scan.token.getNom();
                        }else if(multiplelinea){
                            q=24;
                        }else if(scan.token.getNom().equals("escribe")){ //PALABRA RESERVADA, SE LEE PRIMERO
                            q=3;
                        }else if(scan.token.getNom().equals("lee")){  // ==
                            q=6;
                        }else if(scan.token.getNom().equals("//")){
                            q=23;
                        }else if(scan.token.getNom().equals("/*")){
                            q=24;
                        }else if(scan.token.getTipo().equals("I")){
                            q =13; //aqui
                            //si no existe la actual variable
                            if(!variables_guardar.var.contains(scan.token.getNom())){
                                System.out.println("\nVariable no existe!!\n");
                                Principal.salida+="Variable '"+scan.token.getNom()+"' no declarada.\n";
                                q=-1;
                            }else{
                                variables_guardar.actual="LLAMADO";
                                variables_guardar.var_actual=scan.token.getNom();
                            }
                        }else{
                            q=-1;
                        }
                        break;
                    case 1:
                        if(scan.token.getTipo().equals("I")){
                            q=2;
                            //si no existe la actual variable
                            if(!variables_guardar.var.contains(scan.token.getNom())){
                                variables_guardar.guardarid(scan.token.getNom(),tipo);
                            }else{
                                System.out.println("\nVariable duplicada!!\n");
                                Principal.salida+="Variable '"+scan.token.getNom()+"' duplicada.\n";
                                q=-1;
                            }
                        }else{
                            q=-1;
                        }
                        break;
                    case 2:
                        if(scan.token.getNom().equals(",")){
                            q=1;
                            variables_guardar.aumentar_posicion();
                        }else if(scan.token.getNom().equals("=")){  //AQUI
                            q=27;//nuevo estado
                        }else if(scan.token.getNom().equals("$")){
                            q=100;
                        }else{
                            q=-1;
                        }
                        break;
                    case 3:
                        if(scan.token.getNom().equals("<<")){
                            q=4;
                        }else{
                            q=-1;
                        }
                        break;
                    case 4:
                        if(scan.token.getTipo().equals("I")){
                            q=5;
                        }else{
                            q=-1;
                        }
                        break;
                    case 5:
                        if(scan.token.getNom().equals("<<")){
                            q=4;
                        }else if(scan.token.getNom().equals("$")){
                            q=100;
                        }else{
                            q=-1;
                        }
                        break;
                    case 6:
                        if(scan.token.getNom().equals(">>")){
                            q=7;
                        }else{
                            q=-1;
                        }
                        break;
                    case 7:
                        if(scan.token.getTipo().equals("I")){
                            q=8;
                        }else{
                            q=-1;
                        }
                        break;
                    case 8:
                        if(scan.token.getNom().equals(">>")){
                            q=7;
                        }else if(scan.token.getNom().equals("$")){
                            q=100;
                        }else{
                            q=-1;
                        }
                        break;
                    case 9:/*
                        if(scan.token.getTipo().equals("N")){
                            q=10;
                        }else if(scan.token.getTipo().equals("I")){
                            q=12;
                        }else{
                            q=-1;
                        }
                        break;
                    case 10:
                        if(scan.token.getNom().equals(".")){
                            q=11;
                        }else if(scan.token.getNom().equals(",")){
                            q=1;
                        }else if(scan.token.getNom().equals("$")){
                            q=100;
                        }else{
                            q=-1;
                        }
                        break;
                    case 11:
                        if(scan.token.getTipo().equals("N")){
                            q=12;
                        }else{
                            q=-1;
                        }
                        break;
                    case 12:
                        if(scan.token.getNom().equals(",")){
                            q=1;
                        }else if(scan.token.getNom().equals("$")){
                            q=100;
                        }else{
                            q=-1;
                        }
                        break; */
                    case 13:
                        if(scan.token.getNom().equals("=")){ //AQUI
                            q=26; //NUEVO ESTADO
                        }else{
                            q=-1;
                        }
                        break;
                    case 14:/*
                        if(scan.token.getTipo().equals("I")){
                            q=15;
                        }else if(scan.token.getTipo().equals("N")){
                            q=20;
                        }else{
                            q=-1;
                        }
                        break;
                    case 15:
                        if(scan.token.getTipo().equals("OA")){
                            q=16;
                        }else if(scan.token.getNom().equals("$")){
                            q=100;
                        }else{
                            q=-1;
                        }
                        break;
                    case 16:
                        if(scan.token.getTipo().equals("I")){
                            q=17;
                        }else if(scan.token.getTipo().equals("N")){
                            q=18;
                        }else{
                            q=-1;
                        }
                        break;
                    case 17:
                        if(scan.token.getNom().equals("$")){
                            q=100;
                        }else{
                            q=-1;
                        }
                        break;
                    case 18:
                        if(scan.token.getNom().equals(".")){
                            q=19;
                        }else if(scan.token.getNom().equals("$")){
                            q=100;
                        }else{
                            q=-1;
                        }
                        break;
                    case 19:
                        if(scan.token.getNom().equals("$")){
                            q=100;
                        }else{
                            q=-1;
                        }
                        break;
                    case 20:
                        if(scan.token.getNom().equals(".")){
                            q=21;
                        }else if(scan.token.getTipo().equals("OA")){
                            q=16;
                        }else if(scan.token.getNom().equals("$")){
                            q=100;
                        }else{
                            q=-1;
                        }
                        break;
                    case 21:
                        if(scan.token.getTipo().equals("N")){
                            q=22;
                        }else{
                            q=-1;
                        }
                        break;
                    case 22:
                        if(scan.token.getTipo().equals("OA")){
                            q=16;
                        }else if(scan.token.getNom().equals("$")){
                            q=100;
                        }else{
                            q=-1;
                        }
                        break;*/
                    case 23:
                        if(scan.token.getNom().equals("$")){
                            q=100;
                        }else{
                            q=23;
                        }
                        break;
                    case 24:
                        if(scan.token.getNom().equals("*/")){
                            multiplelinea=Boolean.FALSE;
                            q=25;
                        }else if(scan.token.getNom().equals("$")){
                            multiplelinea=Boolean.TRUE;
                            q=100;
                        }else{
                            q=24;
                        }
                        break;
                    case 25:
                        if(scan.token.getNom().equals("$")){
                            q=100;
                        }else{
                            q=-1;
                        }
                    //NUEVO
                    case 26:
                        operaciones op = new operaciones(scan.getI()-scan.token.getNom().length());
                        int aux=op.analizar(texto);
                        scan.setI(op.scan.getI()-1);
                        if(aux==-1){
                            String avanzar;
                            avanzar=scan.scanner(texto+'$');
                        }
                        if(q==-1){
                            break;
                        }
                        if(aux==1){ //SI LLEGA 1, RECONOCE
                            q=100;
                        }else{ //SI NO, ERROR
                            q=-1;
                        }
                        break;
                    case 27://MODIFICACIONES
                        operaciones op1 = new operaciones(scan.getI()-scan.token.getNom().length());
                        int aux1=op1.analizar(texto);
                        scan.setI(op1.scan.getI()-1);
                        if(aux1==-1){
                            String avanzar;
                            avanzar=scan.scanner(texto+'$');
                        }
                        if(q==-1){
                            break;
                        }
                        if(scan.token.getNom().equals(",")&&aux1==-1){//si llega -1 puede que el parser se haya chocado con una ,
                            q=1;
                        }else if(aux1==1){
                            q=100;
                        }else{ //SI NO, ERROR
                            q=-1;
                        }
                        break;
                }
            }
            if(q==100){
                errores.add(Boolean.TRUE);
                System.out.println("RECONOCE\n");
            }else{
                errores.add(Boolean.FALSE);
                System.out.println("ERROR\n");
            }
        }
    }
    
    //PERMITE LIMPIAR EL ARRAY DE ERRORES OBTENIDOS
    public void resetBool() {
        errores.clear();
    }

}
