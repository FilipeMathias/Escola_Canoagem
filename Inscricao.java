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

    public void checkIn() {
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
    public void verificar_aluno(){
        String nome_verificado = aluno.getNome();
        boolean encontrado = true;
        for(String nome: inscritos) {
            if (nome.equals(nome_verificado)) {
                break;
            } else {
                encontrado = false;
            }
        }
        if(!encontrado){
            this.verificar_numeroAula();
        }
    }

    public void verificar_numeroAula(){
        if(aluno.getPlano().getN_aulas()>1){
            this.verificar_mensalidade();
        }

    }

    public void verificar_mensalidade(){
        YearMonth anoMesDaData = YearMonth.from(dataHora);
        if(aluno.getPlano().getMensalidade().equals(anoMesDaData)){
            this.verificar_horario();
        }
    }



    public void verificar_horario(){
        LocalDateTime hora_checkIn = dataHora;
        LocalDateTime hora_aula    = aula.getData_hora();

        if(hora_aula.getHour() - hora_checkIn.getHour() > 1){
            this.checkIn();
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

    public void verificar_cancelamento(LocalDateTime hora_cancelamento, Aluno aluno){

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
