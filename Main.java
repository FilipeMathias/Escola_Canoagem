import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Map;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {

        Plano plano1 = new Plano(200,10,2025,4);
        Plano plano2 = new Plano(300,10,2025,6);
        Plano plano3 = new Plano(800,10,2025,8);

        Aluno aluno1 = new Aluno("João", plano1);
        Aluno aluno2 = new Aluno("Paulo", plano2);
        Aluno aluno3 = new Aluno("Maria", plano3);

        Instrutor instrutor1 = new Instrutor("Roberto");

        Canoa cano1 = new Canoa(4);

        LocalDateTime data_aula = LocalDateTime.of(2025, 10, 03, 16, 30);
        Aula aula1 = new Aula(data_aula,instrutor1,cano1);

        Inscricao inscricao1 = new Inscricao(aula1);
        LocalDateTime data_inscricao1 = LocalDateTime.of(2025, 10, 03, 14, 30);
        LocalDateTime data_inscricao2 = LocalDateTime.of(2025, 10, 03, 13, 00);
        LocalDateTime data_inscricao3 = LocalDateTime.of(2025, 10, 03, 13, 10);

        inscricao1.checkIn(aluno1,data_inscricao1);
        inscricao1.checkIn(aluno2,data_inscricao2);
        inscricao1.checkIn(aluno3,data_inscricao3);

        LocalDateTime hora_cancelamento = LocalDateTime.of(2025, 10, 03, 15, 30);
        inscricao1.cancelar(hora_cancelamento, aluno3);

        inscricao1.finalizar_aula();

        for (Map.Entry< String, LocalDateTime> entry : inscricao1.getOperacoes().entrySet()) {
            String dataHora = entry.getKey();
            LocalDateTime descricao = entry.getValue();
            System.out.println( dataHora + " -> " + descricao);
        }

        for (Participante participante : inscricao1.getInscritos()) {
            System.out.println(participante.getNome());

            if (participante instanceof Aluno) {
                Aluno alun = (Aluno) participante;
                System.out.println(alun.getPlano().getN_aulas());
            }
        }







    }
}