import java.io.Serializable;

public abstract class Participante implements Serializable {
    private String nome;


    public Participante(String nome) {
        this.nome = nome;

    }

    public String getNome() {
        return nome;
    }
}