package server.net_influence;

class EvidenceFilter {

    static String getPeso(int peso){

        switch (peso)
        {
            case -1: return "Inferiore";
            case 0: return "Normale";
            case 1: return "Superiore";
        }

        return "Normale";
    }

    static String getDieta(String dieta){
        if(dieta==null)
            return "Nessuna";
        return dieta;
    }

    static String getAttivita(String attivita){
        if(attivita==null)
            return "Nessuna";
        return attivita;
    }

    static String getTempo(int settimane){

        if(settimane<=2)
            return "Basso";
        if(settimane<=5)
            return "Medio";
        return "Alto";
    }

    static String getTempoLastAvviso(int settimane){

        if(settimane<=1)
            return "Basso";
        if(settimane<=3)
            return "Medio";
        return "Alto";
    }

}
