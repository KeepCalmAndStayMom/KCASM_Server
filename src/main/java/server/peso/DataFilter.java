package server.peso;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataFilter {

    public static int dayPesoOut(LocalDate start_date, float start_peso, Map<String, Float> pesi){
        int count = 0;
        Object[] date = pesi.keySet().toArray();
        int last_date_index = date.length-1;
        int week = ControllerPeso.getWeekOfPregnancy(start_date, LocalDate.parse(String.valueOf(date[last_date_index])));
        while(ControllerPeso.checkPeso(week, start_peso, pesi.get(date[last_date_index]))!=0)
        {
            count++;
            last_date_index--;
            if(date[last_date_index]==null)
                break;
        }

        return count;
    }

    public static int typeDieta(List<Map<String, Object>> list){
        if(list==null)
            return 0;
        switch (String.valueOf(list.get(list.size()-1).get("categoria")))
        {
            case "Nessuna": return 0;
            case "Ingrassante": return 1;
            case "Dimagrante": return 2;
        }
        return 0;
    }

    public static int typeAttivita(List<Map<String, Object>> list){
        if(list==null)
            return 0;
        switch (String.valueOf(list.get(list.size()-1).get("categoria")))
        {
            case "Nessuna": return 0;
            case "Relax": return 1;
            case "Dimagrante": return 2;
        }
        return 0;
    }

    public static int weekOfDietaOrAttivita(List<Map<String, Object>> list){
        if(list==null)
            return 0;
        LocalDate date_start_dieta = LocalDate.parse(String.valueOf(list.get(list.size()-1).get("data")));
        return (int) ChronoUnit.WEEKS.between(date_start_dieta, LocalDate.now());
    }

}
