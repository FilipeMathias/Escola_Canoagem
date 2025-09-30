public class Aluno extends Participante {
    private String nome;
    private Plano plano;


    public Aluno(String nome, Plano plano) {
        super(nome);
        this.plano = plano;
    }

    public Plano getPlano() {
        return plano;
    }
}
