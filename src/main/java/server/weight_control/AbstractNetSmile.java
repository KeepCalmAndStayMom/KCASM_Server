package server.weight_control;

import smile.Network;
import java.time.LocalDate;

public abstract class AbstractNetSmile {

    Network net;

    void runNet(){
        net.updateBeliefs();
    }

    void clearNet(){ net.clearAllEvidence(); }

    public abstract void setEvidence(int patientId, LocalDate actual_date, double actual_peso);

    public abstract String getResultUtility();
}
