package server.peso;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFilter {


    public static int dayPesoOut(LocalDate start_date, float start_peso, Map<String, Float> pesi){
        int count = 1;
        Object[] date = pesi.keySet().toArray();
        int last_date_index = date.length-1;

        int check;
        int week;
        LocalDate previous_date = LocalDate.parse(String.valueOf(date[last_date_index]));
        LocalDate actual_date = LocalDate.parse(String.valueOf(date[last_date_index]));

        do {
            week = ControllerPeso.getWeekOfPregnancy(start_date, actual_date);
            check = ControllerPeso.checkPeso(week, start_peso, pesi.get(date[last_date_index]));
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

    public static String typeDietaOrAttivita(List<Map<String, Object>> list){
        if(list==null)
            return "Nessuna";
        return String.valueOf(list.get(list.size()-1).get("categoria"));
    }

    public static int weekOfDietaOrAttivita(List<Map<String, Object>> list, LocalDate actual_date){
        if(list==null)
            return 0;
        LocalDate date_start_dieta = LocalDate.parse(String.valueOf(list.get(list.size()-1).get("data")));
        return (int) ChronoUnit.WEEKS.between(date_start_dieta, actual_date);
    }

    public static int weekOfLastAvviso(String last_date, LocalDate actual_date)
    {
        if(last_date==null)
            return 50;
        return (int) ChronoUnit.WEEKS.between(LocalDate.parse(last_date), actual_date);
    }

}
