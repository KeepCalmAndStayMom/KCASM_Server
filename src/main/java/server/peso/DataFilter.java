package server.peso;

import server.database.AttivitaDB;
import server.database.DietaDB;
import server.database.PesoDB;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

class DataFilter {

    private static final float SOGLIE_LIMITI = 2.0f;

    static int checkPeso(LocalDate start_date, LocalDate actual_date, float start_peso, float actual_peso)
    {
        if(actual_peso < (start_peso + ControllerPeso.list_min.get(DataFilter.getWeek(start_date, actual_date)) - SOGLIE_LIMITI))
            return -1;

        if(actual_peso > (start_peso + ControllerPeso.list_max.get(DataFilter.getWeek(start_date, actual_date)) + SOGLIE_LIMITI))
            return 1;

        return 0;
    }

    private static int getWeek(LocalDate start_date, LocalDate date)
    {
        return (int) ChronoUnit.WEEKS.between(start_date, date);
    }

    static int dayPesoOut(int homestation_id, LocalDate start_date, float start_peso){

        int count = 1;

        Map<String, Float> pesi = PesoDB.getPeso(homestation_id);
        assert pesi != null;
        Object[] date = pesi.keySet().toArray();
        int last_date_index = date.length-1;

        LocalDate previous_date = LocalDate.parse(String.valueOf(date[last_date_index]));
        LocalDate actual_date = LocalDate.parse(String.valueOf(date[last_date_index]));

        int check;
        do {
            check = checkPeso(start_date, actual_date, start_peso, pesi.get(date[last_date_index]));
            if(check!=0)
            {
                count += (int) ChronoUnit.DAYS.between(actual_date, previous_date);
                previous_date = actual_date;
                last_date_index--;
                if(date[last_date_index]==null)
                    break;
                actual_date = LocalDate.parse(String.valueOf(date[last_date_index]));
            }
        }while(check!=0);

        return count;
    }

    static String typeDieta(int homestation_id){
        List<Map<String, Object>> list_dieta = DietaDB.getDietaPrograms(homestation_id);
        assert list_dieta != null;
        if(list_dieta.size()==0)
            return "Nessuna";
        return String.valueOf(list_dieta.get(list_dieta.size()-1).get("categoria"));
    }

    static String typeAttivita(int homestation_id){
        List<Map<String, Object>> list_attivita = AttivitaDB.getAttivitaPrograms(homestation_id);
        assert list_attivita != null;
        if(list_attivita.size()==0)
            return "Nessuna";
        return String.valueOf(list_attivita.get(list_attivita.size()-1).get("categoria"));
    }

    static int weekOfDieta(int homestation_id, LocalDate actual_date){
        List<Map<String, Object>> list_dieta = DietaDB.getDietaPrograms(homestation_id);
        assert list_dieta != null;
        if(list_dieta.size()==0)
            return 0;
        return getWeek(LocalDate.parse(String.valueOf(list_dieta.get(list_dieta.size()-1).get("data"))), actual_date);
    }

    static int weekOfAttivita(int homestation_id, LocalDate actual_date){
        List<Map<String, Object>> list_attivita = AttivitaDB.getAttivitaPrograms(homestation_id);
        assert list_attivita != null;
        if(list_attivita.size()==0)
            return 0;
        return getWeek(LocalDate.parse(String.valueOf(list_attivita.get(list_attivita.size()-1).get("data"))), actual_date);
    }

    static int weekOfLastAvviso(LocalDate last_date, LocalDate actual_date)
    {
        if(last_date==null)
            return 50;
        return getWeek(last_date, actual_date);
    }

}
