import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

public class Aula {
    private LocalDateTime data_hora;
    private Instrutor instrutor;
    private Canoa canoa;

    public Aula(LocalDateTime data_hora, Instrutor instrutor, Canoa canoa) {
        this.data_hora = data_hora;
        this.instrutor = instrutor;
        this.canoa = canoa;
    }

    public LocalDateTime getData_hora() {
        return data_hora;
    }

    public Canoa getCanoa() {
        return canoa;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }
}
