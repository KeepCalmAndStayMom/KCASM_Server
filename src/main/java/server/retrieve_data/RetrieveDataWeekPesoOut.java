package server.retrieve_data;

import server.database.v2.PatientInitialDB;
import server.database.v2.WeightDB;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class RetrieveDataWeekPesoOut implements RetrieveDataInterface{

    private List<Double> listMin;
    private List<Double> listMax;

    @Override
    public Object getData(int patientID, LocalDate actual_date, double actualPeso) {
        Map<String, Object> initial = PatientInitialDB.select(patientID);
        LocalDate startDate = LocalDate.parse(String.valueOf(initial.get("pregnancy_start_date")));
        double startPeso = (Double) initial.get("weight");
        boolean gemelli = (Boolean) initial.get("twin");
        String bmi = String.valueOf(initial.get("bmi"));
        listMin = SogliePeso.getListSogliaMin(bmi, gemelli);
        listMax = SogliePeso.getListSogliaMax(bmi, gemelli);

        int count = 1;

        Map<String, Double> pesi = WeightDB.select(patientID);
        assert pesi != null;
        Object[] date = pesi.keySet().toArray();
        int lastDateIndex = date.length-1;

        LocalDate previousDate = LocalDate.parse(String.valueOf(date[lastDateIndex]));
        LocalDate actualDate = LocalDate.parse(String.valueOf(date[lastDateIndex]));

        int check;
        do {
            check = checkPeso(startDate, actualDate, startPeso, pesi.get(date[lastDateIndex]));
            if(check!=0)
            {
                count += (int) ChronoUnit.DAYS.between(actualDate, previousDate);
                previousDate = actualDate;
                lastDateIndex--;
                if(lastDateIndex<0)
                    break;
                actualDate = LocalDate.parse(String.valueOf(date[lastDateIndex]));
            }
        }while(check!=0);

        return count/7;
    }

    private static final double SOGLIE_LIMITI = 2.0;

    private int checkPeso(LocalDate startDate, LocalDate actualDate, double startPeso, double actualPeso)
    {
        if(actualPeso < (startPeso + listMin.get((int) ChronoUnit.WEEKS.between(startDate, actualDate)) - SOGLIE_LIMITI))
            return -1;

        if(actualPeso > (startPeso + listMax.get((int) ChronoUnit.WEEKS.between(startDate, actualDate)) + SOGLIE_LIMITI))
            return 1;

        return 0;
    }
}
