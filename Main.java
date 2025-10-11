import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.*;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {
    public static void main(String[] args) throws Exception {

        // ======== INSCRIÇÃO 1 ========
        Plano plano1 = new Plano(200, 10, 2025, 4);

        Aluno aluno1 = new Aluno("João", plano1);
        Aluno aluno2 = new Aluno("Paulo", plano1);
        Aluno aluno3 = new Aluno("Maria", plano1);

        Instrutor instrutor1 = new Instrutor("Roberto");
        Canoa canoa1 = new Canoa(4);

        LocalDateTime dataAula1 = LocalDateTime.of(2025, 10, 3, 16, 30);
        Aula aula1 = new Aula(dataAula1, instrutor1, canoa1);
        Inscricao inscricao1 = new Inscricao(aula1);

        inscricao1.checkIn(aluno1, LocalDateTime.of(2025, 10, 3, 14, 30));
        inscricao1.checkIn(aluno2, LocalDateTime.of(2025, 10, 3, 13, 0));
        inscricao1.checkIn(aluno3, LocalDateTime.of(2025, 10, 3, 13, 10));
        inscricao1.cancelar(LocalDateTime.of(2025, 10, 3, 15, 25), aluno3);
        inscricao1.finalizarAula();

        // ======== INSCRIÇÃO 2 ========
        Plano plano2 = new Plano(200, 11, 2025, 4);

        Aluno aluno4 = new Aluno("Marta", plano2);
        Aluno aluno5 = new Aluno("Yuri", plano2);
        Aluno aluno6 = new Aluno("Camila", plano2);
        Aluno aluno7 = new Aluno("José", plano2);
        Aluno aluno8 = new Aluno("Ricardo", plano2);

        Canoa canoa2 = new Canoa(4);
        Aula aula2 = new Aula(LocalDateTime.of(2025, 11, 3, 16, 30), instrutor1, canoa2);
        Inscricao inscricao2 = new Inscricao(aula2);

        inscricao2.checkIn(aluno4, LocalDateTime.of(2025, 11, 3, 14, 30));
        inscricao2.checkIn(aluno5, LocalDateTime.of(2025, 11, 3, 13, 0));
        inscricao2.checkIn(aluno6, LocalDateTime.of(2025, 11, 3, 13, 10));
        inscricao2.checkIn(aluno7, LocalDateTime.of(2025, 11, 3, 13, 0));
        inscricao2.checkIn(aluno8, LocalDateTime.of(2025, 11, 3, 13, 10));

        inscricao2.cancelar(LocalDateTime.of(2025, 11, 3, 15, 25), aluno6);
        inscricao2.finalizarAula();

        // ======== SALVAR AS INSCRIÇÕES ========
        List<Inscricao> listaInscricoes = new ArrayList<>();
        listaInscricoes.add(inscricao1);
        listaInscricoes.add(inscricao2);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("inscricoes.dat"))) {
            out.writeObject(listaInscricoes);
            System.out.println("\n✅ As inscrições foram salvas no arquivo 'inscricoes.dat'.");
        }

        // ======== LER E IMPRIMIR O ARQUIVO ========
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("inscricoes.dat"))) {
            List<Inscricao> inscricoesLidas = (List<Inscricao>) in.readObject();

            System.out.println("\n=== INSCRIÇÕES LIDAS DO ARQUIVO ===");
            for (Inscricao ins : inscricoesLidas) {
                System.out.println("\n--- Aula em " + ins.getAula().getData_hora() + " ---");

                for (Participante p : ins.getInscritos()) {
                    System.out.println("Participante: " + p.getClass().getName()+": "+p.getNome());
                    if (p instanceof Aluno){
                        System.out.println("Número de aulas restantes: " + ((Aluno) p).getQtdAulas());
                        System.out.println();
                    }


                }

                if(ins.getFila().isEmpty()){
                    System.out.println("Não há alunos em fila de espera.");
                }else{
                    for (Participante p : ins.getFila()) {
                        System.out.println("Em fila: " + p.getNome());
                        if (p instanceof Aluno){
                            System.out.println("Número de aulas restantes: " + ((Aluno) p).getQtdAulas());
                        }
                    }
                }System.out.println();



                System.out.println("Operações:");
                for (Map.Entry<String, LocalDateTime> entry : ins.getOperacoes().entrySet()) {
                    System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
                }
            }
        }

        System.out.println("\n✅ Leitura concluída com sucesso!");
    }
}