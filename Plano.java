import java.io.Serializable;
import java.time.YearMonth;

public class Plano implements Serializable {
    private double valor;
    private int mes;
    private int ano;
    private int n_aulas;
    private YearMonth mensalidade;

    public Plano(double valor, int mes, int ano, int n_aulas) {
        this.valor = valor;
        this.mes = mes;
        this.ano = ano;
        this.n_aulas = n_aulas;
        this.mensalidade = YearMonth.of(ano, mes);
    }

    public YearMonth getMensalidade() {
        return mensalidade;
    }

    public int getN_aulas() {
        return n_aulas;
    }

    public void setN_aulas(int n_aulas) {
        this.n_aulas = n_aulas;
    }
}
