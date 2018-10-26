package server.evidence_filter;

public class EvidenceFilterDieta implements EvidenceFilterInterface {
    @Override
    public String getEvidence(Object obj) {

        String dieta = (String) obj;
        switch (dieta)
        {
            case "Ingrassante_Lieve":
            case "Ingrassante_Forte":
            case "Dimagrante_Lieve":
            case "Dimagrante_Forte": return dieta;
        }

        return "Nessuna";
    }
}
