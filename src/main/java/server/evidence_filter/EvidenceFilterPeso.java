package server.evidence_filter;

public class EvidenceFilterPeso implements EvidenceFilterInterface {

    @Override
    public String getEvidence(Object obj) {

        int peso = (Integer) obj;
        switch (peso)
        {
            case -1: return "Inferiore";
            case 0: return "Normale";
            case 1: return "Superiore";
        }
        return "Normale";
    }
}
