import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.time.YearMonth;



public class Inscricao {
    private Aula aula;
    private Aluno aluno;
    private LocalDateTime dataHora;
    private List<String> inscritos;
    private List<String> fila;
    private Map<LocalDateTime, String> operacoes;


    public Inscricao(Aula aula, Aluno aluno, LocalDateTime dataHora) {
        this.aula = aula;
        this.aluno = aluno;
        this.dataHora = dataHora;
        this.inscritos = new ArrayList<>();
        this.fila = new ArrayList<>();
        this.operacoes = new HashMap<>();
    }

    public void increver_aluno() {
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
    private void verificar_aluno() throws Exception {
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
            this.verificar_numeroAula();
        }
    }



    private void verificar_numeroAula() throws Exception{
        if(aluno.getPlano().getN_aulas()>1){
            this.verificar_mensalidade();
        }else {
            throw new Exception("Excedido o número de aulas do seu plano!");
        }

    }

    private void verificar_mensalidade() throws Exception{
        YearMonth anoMesDaData = YearMonth.from(dataHora);
        if(aluno.getPlano().getMensalidade().equals(anoMesDaData)){
            this.verificar_horario();
        }else{
            throw new Exception("Mensalidade do correte mês em aberto, impossibilitando marcação de aulas!");
        }
    }



    private void verificar_horario() throws Exception{
        LocalDateTime hora_checkIn = dataHora;
        LocalDateTime hora_aula    = aula.getData_hora();

        if(hora_aula.getHour() - hora_checkIn.getHour() > 1){
            this.increver_aluno();
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
            this.finalizar_aula();
            remocao = "Cancelamento aluno: " + aluno.getNome();
            operacoes.put(hora_cancelamento, remocao);
        }else{
            remocao = "Cancelamento tardio aluno: " + aluno.getNome();
            operacoes.put(hora_cancelamento, remocao);
        }


    }

    public void checkIn() throws Exception {
        this.verificar_aluno();

    }

    public void finalizar_aula(){
        int n_aulas             = aluno.getPlano().getN_aulas();
        aluno.getPlano().setN_aulas(n_aulas-1);

    }




    public Aula getAula() {
        return aula;
    }

    public Aluno getAluno() {
        return aluno;
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
