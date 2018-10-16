package server.net_influence;

import smile.Network;

import java.util.Map;

public class NetSmileRetePeso implements NetSmile {

    private Network net;

    public NetSmileRetePeso(){
        initialSetting();
        clearNet();
    }

    @Override
    public void initialSetting(){
        System.setProperty("jsmile.native.library", "C:\\Users\\Gabriele\\IdeaProjects\\KCASM_Server\\lib\\jsmile.dll");

        new smile.License(
                "SMILE LICENSE fda8796e a63255b4 d9d96382 " +
                        "THIS IS AN ACADEMIC LICENSE AND CAN BE USED " +
                        "SOLELY FOR ACADEMIC RESEARCH AND TEACHING, " +
                        "AS DEFINED IN THE BAYESFUSION ACADEMIC " +
                        "SOFTWARE LICENSING AGREEMENT. " +
                        "Serial #: epjydt4iu3l6w7s9ydx79q8b " +
                        "Issued for: GABRIELE BOLAMPERTI (20016516@studenti.uniupo.it) " +
                        "Academic institution: Universit\u00e0 Del Piemonte Orientale " +
                        "Valid until: 2019-04-11 " +
                        "Issued by BayesFusion activation server",
                new byte[] {
                        116,-39,117,-13,-121,-94,-88,73,65,30,-73,-106,-122,-27,51,-85,
                        31,118,-19,79,-77,-100,-11,97,95,97,-3,118,-46,-28,-104,-116,
                        8,-26,17,-53,-18,81,-18,-67,-33,-113,78,88,-82,86,-11,-103,
                        86,22,119,86,117,-41,-15,125,61,80,12,-12,12,-5,9,92
                }
        );

        net = new Network();
        net.readFile("src\\main\\resources\\RetePeso.xdsl");
    }

    @Override
    public void runNet(){
        net.updateBeliefs();
    }

    @Override
    public void clearNet(){
        net.clearAllEvidence();
    }

    @Override
    public void setAllEvidence(Map<String, Object> evidences){
        if(evidences.get("Peso")!=null)
            net.setEvidence("Peso",EvidenceFilter.getPeso((Integer) evidences.get("Peso")));
        if(evidences.get("Dieta")!=null)
            net.setEvidence("Dieta",EvidenceFilter.getDieta((String) evidences.get("Dieta")));
        if(evidences.get("Attivita")!=null)
            net.setEvidence("Attivita",EvidenceFilter.getAttivita((String) evidences.get("Attivita")));
        if(evidences.get("Tempo_Peso")!=null)
            net.setEvidence("Tempo_Peso", EvidenceFilter.getTempo((Integer) evidences.get("Tempo_Peso")));
        if(evidences.get("Tempo_Dieta")!=null)
            net.setEvidence("Tempo_Dieta", EvidenceFilter.getTempo((Integer) evidences.get("Tempo_Dieta")));
        if(evidences.get("Tempo_Attivita")!=null)
            net.setEvidence("Tempo_Attivita", EvidenceFilter.getTempo((Integer) evidences.get("Tempo_Attivita")));
        if(evidences.get("Ultimo_Avviso")!=null)
            net.setEvidence("Ultimo_Avviso", EvidenceFilter.getTempoLastAvviso((Integer) evidences.get("Ultimo_Avviso")));
    }

    @Override
    public String getResultUtility(){

        double[] result = net.getNodeValue("Utility");
        return "Avvisare = "+result[0]+"\nNon Avvisare = "+result[1];
    }
}
