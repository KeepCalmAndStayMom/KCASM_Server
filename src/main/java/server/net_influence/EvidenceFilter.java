package server.net_influence;

public class EvidenceFilter {

    public static String getPeso(int peso){

        switch (peso)
        {
            case -1: return "Inferiore";
            case 0: return "Normale";
            case 1: return "Superiore";
        }

        return null;
    }

    public static String getDieta(String dieta){
        return dieta;
    }

    public static String getAttività(String attivita){
        return attivita;
    }

    public static String getTempo(int settimane){

        if(settimane<=2)
            return "Basso";
        if(settimane<=5)
            return "Medio";
        return "Alto";
    }

    public static String getTempoLastAvviso(int settimane){

        if(settimane<=1)
            return "Basso";
        if(settimane<=3)
            return "Medio";
        return "Alto";
    }

}
