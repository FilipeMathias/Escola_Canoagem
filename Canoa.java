import java.io.Serializable;

public class Canoa implements Serializable {
    private int vagasRestantes;
    private boolean status;

    public Canoa(int vagas) {
        this.vagasRestantes = vagas;
        this.status = true;
    }

    public int getVagas() {
        return vagasRestantes;
    }

    public void ocuparVaga() {
        if (vagasRestantes > 0) {
            vagasRestantes--;
            if (vagasRestantes == 0) {
                status = false;
            }
        }
    }

    public boolean isStatus() {
        return status;
    }
}
