package server.evidence_filter;

public class EvidenceFilterAttivita implements EvidenceFilterInterface {

    @Override
    public String getEvidence(Object obj) {
        String attivita = (String) obj;
        switch (attivita)
        {
            case "Relax":
            case "Dimagrante": return attivita;
        }
        return "Nessuna";
    }
}
