package server.peso;

import java.time.LocalDate;
import java.util.ArrayList;
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
}
