package server.netsmiledecorator;

import server.database2.PatientInitialDB;
import server.database2.WeightDB;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class RetrieveDataWeekPesoOut implements RetrieveDataInterface{
    @Override
    public Object getData(int patientID, LocalDate actual_date, double actual_peso) {
        Map<String, Object> initial = PatientInitialDB.Select(patientID);
        LocalDate start_date = LocalDate.parse(String.valueOf(initial.get("pregnancy_start_date")));
        double start_peso = (Double) initial.get("weight");

        int count = 1;

        Map<String, Double> pesi = WeightDB.Select(patientID);
        assert pesi != null;
        Object[] date = pesi.keySet().toArray();
        int last_date_index = date.length-1;

        LocalDate previousDate = LocalDate.parse(String.valueOf(date[last_date_index]));
        LocalDate actualDate = LocalDate.parse(String.valueOf(date[last_date_index]));

        int check;
        do {
            check = checkPeso(start_date, actualDate, start_peso, pesi.get(date[last_date_index]));
            if(check!=0)
            {
                count += (int) ChronoUnit.DAYS.between(actualDate, previousDate);
                previousDate = actualDate;
                last_date_index--;
                if(date[last_date_index]==null)
                    break;
                actualDate = LocalDate.parse(String.valueOf(date[last_date_index]));
            }
        }while(check!=0);

        return count/7;
    }

    private static final double SOGLIE_LIMITI = 2.0;

    private int checkPeso(LocalDate start_date, LocalDate actual_date, double start_peso, double actual_peso)
    {
        if(actual_peso < (start_peso + ControllerPesoTest.list_min.get((int) ChronoUnit.WEEKS.between(start_date, actual_date)) - SOGLIE_LIMITI))
            return -1;

        if(actual_peso > (start_peso + ControllerPesoTest.list_max.get((int) ChronoUnit.WEEKS.between(start_date, actual_date)) + SOGLIE_LIMITI))
            return 1;

        return 0;
    }
}
