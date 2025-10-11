import java.time.LocalDateTime;
import java.util.Map;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {

        Plano plano1 = new Plano(200,10,2025,4);


        Aluno aluno1 = new Aluno("João", plano1);
        Aluno aluno2 = new Aluno("Paulo", plano1);
        Aluno aluno3 = new Aluno("Maria", plano1);

        Instrutor instrutor1 = new Instrutor("Roberto");

        Canoa canoa1 = new Canoa(4);

        LocalDateTime dataAula1 = LocalDateTime.of(2025, 10, 03, 16, 30);
        Aula aula1 = new Aula(dataAula1,instrutor1,canoa1);

        Inscricao inscricao1 = new Inscricao(aula1);
        LocalDateTime dataInscricao1 = LocalDateTime.of(2025, 10, 03, 14, 30);
        LocalDateTime dataInscricao2 = LocalDateTime.of(2025, 10, 03, 13, 00);
        LocalDateTime dataInscricao3 = LocalDateTime.of(2025, 10, 03, 13, 10);

        inscricao1.checkIn(aluno1,dataInscricao1);
        inscricao1.checkIn(aluno2,dataInscricao2);
        inscricao1.checkIn(aluno3,dataInscricao3);


        LocalDateTime horaCancelamento = LocalDateTime.of(2025, 10, 03, 15, 25);
        inscricao1.cancelar(horaCancelamento, aluno3);

        inscricao1.finalizarAula();

        for (Map.Entry< String, LocalDateTime> entry : inscricao1.getOperacoes().entrySet()) {
            String dataHora = entry.getKey();
            LocalDateTime descricao = entry.getValue();
            System.out.println( dataHora + " -> " + descricao);
        }

        for (Participante participante : inscricao1.getInscritos()) {
            System.out.println(participante.getNome()+" - "+participante.getClass().getName());

            if (participante instanceof Aluno) {
                Aluno alun = (Aluno) participante;
                System.out.println(alun.getQtdAulas());
            }
        }

        System.out.println();


        //simulando a virada de mês
        Plano plano2 = new Plano(200, 11,2025,4);


        Aluno aluno4 = new Aluno("Marta", plano2);
        Aluno aluno5 = new Aluno("Yuri", plano2);
        Aluno aluno6 = new Aluno("Camila", plano2);
        Aluno aluno7 = new Aluno("José", plano2);
        Aluno aluno8 = new Aluno("Ricardo", plano2);


        Canoa canoa2 = new Canoa(4);

        LocalDateTime dataAula2 = LocalDateTime.of(2025, 11, 03, 16, 30);
        Aula aula2 = new Aula(dataAula2,instrutor1,canoa2);



        Inscricao inscricao2 = new Inscricao(aula2);
        LocalDateTime dataInscricao4 = LocalDateTime.of(2025, 11, 03, 14, 30);
        LocalDateTime dataInscricao5 = LocalDateTime.of(2025, 11, 03, 13, 00);
        LocalDateTime dataInscricao6 = LocalDateTime.of(2025, 11, 03, 13, 10);
        LocalDateTime dataInscricao7 = LocalDateTime.of(2025, 11, 03, 13, 00);
        LocalDateTime dataInscricao8 = LocalDateTime.of(2025, 11, 03, 13, 10);

        inscricao2.checkIn(aluno4,dataInscricao4);
        inscricao2.checkIn(aluno5,dataInscricao5);
        inscricao2.checkIn(aluno6,dataInscricao6);
        inscricao2.checkIn(aluno7,dataInscricao7);
        inscricao2.checkIn(aluno8,dataInscricao8);




        LocalDateTime horaCancelamento2 = LocalDateTime.of(2025, 11, 03, 15, 25);
        inscricao2.cancelar(horaCancelamento2, aluno6);



        inscricao2.finalizarAula();

        for (Map.Entry< String, LocalDateTime> entry : inscricao2.getOperacoes().entrySet()) {
            String dataHora = entry.getKey();
            LocalDateTime descricao = entry.getValue();
            System.out.println( dataHora + " -> " + descricao);
        }

        for (Participante participante : inscricao2.getInscritos()) {
            System.out.println(participante.getNome()+" - "+participante.getClass().getName());

            if (participante instanceof Aluno) {
                Aluno alun = (Aluno) participante;
                System.out.println(alun.getQtdAulas());
            }
        }




    }
}