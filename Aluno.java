public class Aluno extends Participante {
    private String nome;
    private final Plano plano;
    private int qtdAulas;


    public Aluno(String nome, Plano plano) {
        super(nome);
        this.plano = plano;
        this.qtdAulas = plano.getN_aulas();
    }

    public Plano getPlano() {
        return plano;
    }

    public int getQtdAulas() {
        return qtdAulas;
    }

    public void setQtdAulas(int qtdAulas) {
        this.qtdAulas = qtdAulas;
    }
}
