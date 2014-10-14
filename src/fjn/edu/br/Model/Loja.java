package fjn.edu.br.Model;

/**
 *
 * @author UelioNobre
 */
public class Loja {

    private int id;
    private String nomeLoja;

    public Loja() {
    }

    public Loja(int id, String nomeLoja) {
        this.id = id;
        this.nomeLoja = nomeLoja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeLoja() {
        return nomeLoja;
    }

    public void setNomeLoja(String nomeLoja) {
        this.nomeLoja = nomeLoja;
    }

}
