
package clases;

public class token {
    private String nom;
    private String tipo;

    //CONSTRUCTOR
    public token() {
        nom="";
        tipo="";
    }

    //Getters y setters
    public String getNom() {
        return nom;
    }

    public String getTipo() {
        return tipo;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Token{" + "nom=" + nom + ", tipo=" + tipo + '}';
    }
    
}
