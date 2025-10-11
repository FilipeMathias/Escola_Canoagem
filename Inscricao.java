import java.io.Serializable;
import java.util.*;
import java.time.LocalDateTime;
import java.time.YearMonth;



public class Inscricao implements Serializable {
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

    private void inscreverAluno(Participante participante, LocalDateTime dataHora) {
        String insercao;
        if (aula.getCanoa().isStatus()) {
            if (inscritos.isEmpty()) {
                inscritos.addLast(aula.getInstrutor());
                aula.getCanoa().ocuparVaga();
            }

            inscritos.addFirst(participante);
            aula.getCanoa().ocuparVaga();
            insercao = "checkIn: " + participante.getNome();
            operacoes.put(insercao, dataHora);

        } else {
            fila.addFirst(participante);
            insercao = "Entrou em lista de espera: " + participante.getNome();
            operacoes.put(insercao, dataHora);
        }

    }
    private void verificarAluno(Aluno aluno, LocalDateTime dataHora) throws Exception {
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
            this.verificarNumeroAula(aluno, dataHora);
        }
    }



    private void verificarNumeroAula(Aluno aluno, LocalDateTime dataHora) throws Exception{
        if(aluno.getQtdAulas()>1){
            this.verificarMensalidade(aluno, dataHora);
        }else {
            throw new Exception("Excedido o número de aulas do seu plano!");
        }

    }

    private void verificarMensalidade(Aluno aluno, LocalDateTime dataHora) throws Exception{
        YearMonth anoMesDaData = YearMonth.from(dataHora);
        if(aluno.getPlano().getMensalidade().equals(anoMesDaData)){
            this.verificarHorario(aluno, dataHora);
        }else{
            throw new Exception("Mensalidade do correte mês em aberto, impossibilitando marcação de aulas!");
        }
    }



    private void verificarHorario(Aluno aluno, LocalDateTime dataHora) throws Exception{
        LocalDateTime hora_checkIn = dataHora;
        LocalDateTime hora_aula    = aula.getData_hora();

        if(hora_aula.getHour() - hora_checkIn.getHour() > 1){
            this.inscreverAluno(aluno, dataHora);
        }else{
            throw new Exception("Horário de check in inválido!");
        }
    }

    public void cancelar(LocalDateTime hora_cancelamento, Aluno aluno) {

        for(Participante participante: inscritos) {
            if(participante.getNome().equals(aluno.getNome())) {
                inscritos.remove(aluno);
                this.verificarCancelamento(hora_cancelamento, aluno);
                if (!fila.isEmpty()) {
                    Participante novoAluno = fila.removeLast(); // remove e guarda
                    inscritos.addLast(novoAluno);
                    String inscricao = "Inscrito aluno em lista de espera: " + novoAluno.getNome();
                    operacoes.put(inscricao, hora_cancelamento);
                }

                break;
            }
        }




    }


    private void verificarCancelamento(LocalDateTime hora_cancelamento, Aluno aluno){

        LocalDateTime hora_aula = aula.getData_hora();
        String remocao;

        if(hora_aula.getHour() - hora_cancelamento.getHour() > 1){
            remocao = "Cancelamento aluno: " + aluno.getNome();
            operacoes.put(remocao, hora_cancelamento);
        }else{
            this.deduzirAula(aluno);
            remocao = "Cancelamento tardio aluno: " + aluno.getNome();
            operacoes.put(remocao, hora_cancelamento);
        }


    }

    public void checkIn(Aluno aluno, LocalDateTime dataHora) throws Exception {
        this.verificarAluno(aluno, dataHora);

    }

    private void deduzirAula(Aluno aluno){
        int n_aulas = aluno.getQtdAulas();
        aluno.setQtdAulas(n_aulas-1);

    }

    public void finalizarAula(){
        String aulaRealizada = "Aula realizada com os seguintes alunos: ";
        for(Participante inscrito: inscritos ){
            if(inscrito instanceof Aluno){
                Aluno alun = (Aluno) inscrito;
                deduzirAula(alun);
                aulaRealizada += alun.getNome() + ',';
            }

        }
        operacoes.put(aulaRealizada, aula.getData_hora());
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
