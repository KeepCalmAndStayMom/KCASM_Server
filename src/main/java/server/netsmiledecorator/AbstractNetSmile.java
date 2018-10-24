package server.netsmiledecorator;

import smile.Network;

import java.time.LocalDate;

public abstract class AbstractNetSmile {

    protected Network net;

    public void runNet(){
        net.updateBeliefs();
    }

    public void clearNet(){
        net.clearAllEvidence();
    }

    public abstract void setEvidence(int patientId, LocalDate actual_date, double actual_peso);

    public abstract String getResultUtility();
}
