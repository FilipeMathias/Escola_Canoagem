import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.time.YearMonth;



public class Inscricao {
    private Aula aula;
    private List<String> inscritos;
    private List<String> fila;
    private Map<LocalDateTime, String> operacoes;


    public Inscricao(Aula aula) {
        this.aula = aula;
        this.inscritos = new ArrayList<>();
        this.fila = new ArrayList<>();
        this.operacoes = new HashMap<>();
    }

    private void increver_aluno(Aluno aluno, LocalDateTime dataHora) {
        String insercao;
        if (aula.getCanoa().isStatus()) {
            if (inscritos.isEmpty()) {
                inscritos.add(aula.getInstrutor().getNome());
                aula.getCanoa().ocuparVaga();
                insercao = "checkIn" + aula.getInstrutor().getNome();
                operacoes.put(dataHora, insercao);

            } else {
                inscritos.add(aluno.getNome());
                aula.getCanoa().ocuparVaga();
                insercao = "checkIn" + aluno.getNome();
                operacoes.put(dataHora, insercao);
            }
        } else {
            fila.add(aluno.getNome());
            insercao = "Entrou em lista de espera" + aluno.getNome();
            operacoes.put(dataHora, insercao);
        }

    }
    private void verificar_aluno(Aluno aluno, LocalDateTime dataHora) throws Exception {
        String nome_verificado = aluno.getNome();

        boolean encontrado = false;
        for (String nome : inscritos) {
            if (nome.equals(nome_verificado)) {
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            throw new Exception("Aluno já inscrito!");
        } else {
            this.verificar_numeroAula(aluno, dataHora);
        }
    }



    private void verificar_numeroAula(Aluno aluno, LocalDateTime dataHora) throws Exception{
        if(aluno.getPlano().getN_aulas()>1){
            this.verificar_mensalidade(aluno, dataHora);
        }else {
            throw new Exception("Excedido o número de aulas do seu plano!");
        }

    }

    private void verificar_mensalidade(Aluno aluno, LocalDateTime dataHora) throws Exception{
        YearMonth anoMesDaData = YearMonth.from(dataHora);
        if(aluno.getPlano().getMensalidade().equals(anoMesDaData)){
            this.verificar_horario(aluno, dataHora);
        }else{
            throw new Exception("Mensalidade do correte mês em aberto, impossibilitando marcação de aulas!");
        }
    }



    private void verificar_horario(Aluno aluno, LocalDateTime dataHora) throws Exception{
        LocalDateTime hora_checkIn = dataHora;
        LocalDateTime hora_aula    = aula.getData_hora();

        if(hora_aula.getHour() - hora_checkIn.getHour() > 1){
            this.increver_aluno(aluno, dataHora);
        }else{
            throw new Exception("Horário de check in inválido!");
        }
    }

    public void cancelar(LocalDateTime hora_cancelamento, Aluno aluno) {

        String inscricao;

        inscritos.remove(aluno.getNome());
        this.verificar_cancelamento(hora_cancelamento, aluno);



        if (!fila.isEmpty()) {
            inscritos.add(fila.getFirst());
            fila.removeFirst();
            inscricao = "Inscrito aluno em lista de espera: " + fila.getFirst();
            operacoes.put(hora_cancelamento, inscricao);
        }

    }

    private void verificar_cancelamento(LocalDateTime hora_cancelamento, Aluno aluno){

        LocalDateTime hora_aula = aula.getData_hora();
        String remocao;

        if(hora_aula.getHour() - hora_cancelamento.getHour() < 1){
            this.finalizar_aula(aluno);
            remocao = "Cancelamento aluno: " + aluno.getNome();
            operacoes.put(hora_cancelamento, remocao);
        }else{
            remocao = "Cancelamento tardio aluno: " + aluno.getNome();
            operacoes.put(hora_cancelamento, remocao);
        }


    }

    public void checkIn(Aluno aluno, LocalDateTime dataHora) throws Exception {
        this.verificar_aluno(aluno, dataHora);

    }

    public void finalizar_aula(Aluno aluno){
        int n_aulas             = aluno.getPlano().getN_aulas();
        aluno.getPlano().setN_aulas(n_aulas-1);

    }




    public Aula getAula() {
        return aula;
    }



    public List<String> getInscritos() {
        return inscritos;
    }

    public List<String> getFila() {
        return fila;
    }

    public Map<LocalDateTime, String> getOperacoes() {
        return operacoes;
    }
}
