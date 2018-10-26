package server.weight_control;

import server.evidence_filter.EvidenceFilterInterface;
import server.retrieve_data.RetrieveDataInterface;
import java.time.LocalDate;

public class NetSmileNode extends AbstractNetSmile {

    private AbstractNetSmile abstractNetSmile;
    private String nodeId;
    private EvidenceFilterInterface ef;
    private RetrieveDataInterface rd;

    NetSmileNode(AbstractNetSmile abstractNetSmile, String nodeId, EvidenceFilterInterface ef, RetrieveDataInterface rd)
    {
        net = abstractNetSmile.net;
        this.abstractNetSmile = abstractNetSmile;
        this.nodeId = nodeId;
        this.ef = ef;
        this.rd = rd;
    }

    @Override
    public void setEvidence(int patientId, LocalDate actual_date, double actual_peso) {
        net.setEvidence(nodeId, ef.getEvidence(rd.getData(patientId, actual_date, actual_peso)));
        abstractNetSmile.setEvidence(patientId, actual_date, actual_peso);
    }

    @Override
    public String getResultUtility() {
        return abstractNetSmile.getResultUtility();
    }
}
