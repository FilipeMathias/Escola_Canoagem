import java.util.*;
import java.time.LocalDateTime;
import java.time.YearMonth;



public class Inscricao {
    private Aula aula;
    private ArrayDeque<Participante> inscritos;
    private ArrayDeque<Participante> fila;
    private Map<String, LocalDateTime> operacoes;


    public Inscricao(Aula aula) {
        this.aula = aula;
        this.inscritos = new ArrayDeque<>();
        this.fila = new ArrayDeque<>();
        this.operacoes = new LinkedHashMap<>();
    }

    private void increver_aluno(Participante participante, LocalDateTime dataHora) {
        String insercao;
        if (aula.getCanoa().isStatus()) {
            if (inscritos.isEmpty()) {
                inscritos.addLast(aula.getInstrutor());
            }

            inscritos.addFirst(participante);
            aula.getCanoa().ocuparVaga();
            insercao = "checkIn " + participante.getNome();
            operacoes.put(insercao, dataHora);

        } else {
            fila.addFirst(participante);
            insercao = "Entrou em lista de espera" + participante.getNome();
            operacoes.put(insercao, dataHora);
        }

    }
    private void verificar_aluno(Aluno aluno, LocalDateTime dataHora) throws Exception {
        String nome_verificado = aluno.getNome();

        boolean encontrado = false;
        for (Participante participante : inscritos) {
            if (participante.getNome().equals(nome_verificado)) {
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
        inscritos.remove(aluno); // Corrigido

        this.verificar_cancelamento(hora_cancelamento, aluno);

        if (!fila.isEmpty()) {
            Participante novoAluno = fila.removeFirst(); // remove e guarda
            inscritos.addLast(novoAluno);
            String inscricao = "Inscrito aluno em lista de espera: " + novoAluno.getNome();
            operacoes.put(inscricao, hora_cancelamento);
        }
    }


    private void verificar_cancelamento(LocalDateTime hora_cancelamento, Aluno aluno){

        LocalDateTime hora_aula = aula.getData_hora();
        String remocao;

        if(hora_aula.getHour() - hora_cancelamento.getHour() > 1){
            this.deduzir_aula(aluno);
            remocao = "Cancelamento aluno: " + aluno.getNome();
            operacoes.put(remocao, hora_cancelamento);
        }else{
            remocao = "Cancelamento tardio aluno: " + aluno.getNome();
            operacoes.put(remocao, hora_cancelamento);
        }


    }

    public void checkIn(Aluno aluno, LocalDateTime dataHora) throws Exception {
        this.verificar_aluno(aluno, dataHora);

    }

    private void deduzir_aula(Aluno aluno){
        int n_aulas = aluno.getPlano().getN_aulas();
        aluno.getPlano().setN_aulas(n_aulas-1);

    }

    public void finalizar_aula(){
        String aula_realizada = "Aula realizada com os seguintes alunos: ";
        for(Participante inscrito: inscritos ){
            if(inscrito instanceof Aluno){
                Aluno alun = (Aluno) inscrito;
                deduzir_aula(alun);
                aula_realizada += alun.getNome() + ',';
            }

        }
        operacoes.put(aula_realizada, aula.getData_hora());
    }




    public Aula getAula() {
        return aula;
    }



    public ArrayDeque<Participante> getInscritos() {
        return inscritos;
    }

    public  ArrayDeque<Participante> getFila() {
        return fila;
    }

    public Map<String, LocalDateTime> getOperacoes() {
        return operacoes;
    }
}
