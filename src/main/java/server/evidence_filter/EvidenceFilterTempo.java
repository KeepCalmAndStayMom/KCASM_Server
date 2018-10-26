package server.evidence_filter;

public class EvidenceFilterTempo implements EvidenceFilterInterface {

    @Override
    public String getEvidence(Object obj) {

        int settimane = (Integer) obj;

        if(settimane<=1)
            return "Basso";
        if(settimane<=4)
            return "Medio";
        return "Alto";
    }
}
