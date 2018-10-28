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
    public Object getData(int patientId, LocalDate actualDate, double actualPeso) {

        Map<String, Object> initial = PatientInitialDB.select(patientId);
        LocalDate startDate = LocalDate.parse(String.valueOf(initial.get("pregnancy_start_date")));
        double startPeso = (Double) initial.get("weight");
        boolean gemelli = (Boolean) initial.get("twin");
        String bmi = String.valueOf(initial.get("bmi"));
        List<Double> listMin = SogliePeso.getListSogliaMin(bmi, gemelli);
        List<Double> listMax = SogliePeso.getListSogliaMax(bmi, gemelli);

        if(actualPeso < (startPeso + listMin.get((int) ChronoUnit.WEEKS.between(startDate, actualDate)) - SOGLIE_LIMITI))
            return -1;

        if(actualPeso > (startPeso + listMax.get((int) ChronoUnit.WEEKS.between(startDate, actualDate)) + SOGLIE_LIMITI))
            return 1;

        return 0;
    }

}
