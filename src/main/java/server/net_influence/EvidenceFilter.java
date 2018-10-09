package server.net_influence;

public class EvidenceFilter {

    public static String getPeso(int peso){

        switch (peso)
        {
            case -1: return "Inferiore";
            case 1: return "Superiore";
        }

        return null;
    }

    public static String getDieta(int dieta){

        switch (dieta)
        {
            case 0: return "Nessuna";
            case 1: return "Ingrassante";
            case 2: return "Dimagrante";
        }

        return null;
    }

    public static String getAttività(int attivita){

        switch (attivita)
        {
            case 0: return "Nessuna";
            case 1: return "Relax";
            case 2: return "Dimagrante";
        }
        return null;
    }

    public static String getTempo(int settimane){

        if(settimane<=2)
            return "Basso";
        if(settimane<=5)
            return "Medio";
        return "Alto";
    }

}
