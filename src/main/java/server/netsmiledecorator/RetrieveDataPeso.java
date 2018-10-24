package server.netsmiledecorator;

import server.database2.PatientInitialDB;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class RetrieveDataPeso implements RetrieveDataInterface{

    private static final double SOGLIE_LIMITI = 2.0;

    @Override
    public Object getData(int patientId, LocalDate actual_date, double actual_peso) {

        Map<String, Object> initial = PatientInitialDB.Select(patientId);
        LocalDate start_date = LocalDate.parse(String.valueOf(initial.get("pregnancy_start_date")));
        double start_peso = (Double) initial.get("weight");

        if(actual_peso < (start_peso + ControllerPesoTest.list_min.get((int) ChronoUnit.WEEKS.between(start_date, actual_date)) - SOGLIE_LIMITI))
            return -1;

        if(actual_peso > (start_peso + ControllerPesoTest.list_max.get((int) ChronoUnit.WEEKS.between(start_date, actual_date)) + SOGLIE_LIMITI))
            return 1;

        return 0;
    }

}
