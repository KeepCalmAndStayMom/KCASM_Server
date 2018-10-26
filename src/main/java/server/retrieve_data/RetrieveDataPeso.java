package server.retrieve_data;

import server.database2.PatientInitialDB;
import server.weight_control.SogliePeso;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class RetrieveDataPeso implements RetrieveDataInterface{

    private static final double SOGLIE_LIMITI = 2.0;

    @Override
    public Object getData(int patientId, LocalDate actual_date, double actual_peso) {

        Map<String, Object> initial = PatientInitialDB.Select(patientId);
        LocalDate start_date = LocalDate.parse(String.valueOf(initial.get("pregnancy_start_date")));
        double start_peso = (Double) initial.get("weight");
        boolean gemelli = (Boolean) initial.get("twin");
        String bmi = String.valueOf(initial.get("bmi"));
        List<Double> list_min = SogliePeso.getListSogliaMin(bmi, gemelli);
        List<Double> list_max = SogliePeso.getListSogliaMax(bmi, gemelli);

        if(actual_peso < (start_peso + list_min.get((int) ChronoUnit.WEEKS.between(start_date, actual_date)) - SOGLIE_LIMITI))
            return -1;

        if(actual_peso > (start_peso + list_max.get((int) ChronoUnit.WEEKS.between(start_date, actual_date)) + SOGLIE_LIMITI))
            return 1;

        return 0;
    }

}
