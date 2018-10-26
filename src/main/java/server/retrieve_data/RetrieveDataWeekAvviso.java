package server.retrieve_data;

import server.weight_control.ControllerPesoTest;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RetrieveDataWeekAvviso implements RetrieveDataInterface {

    @Override
    public Object getData(int patientID, LocalDate actual_date, double actual_peso) {

        LocalDate last_date = ControllerPesoTest.getLastAvviso(patientID);
        if(last_date==null)
            return 50;
        return (int) ChronoUnit.WEEKS.between(last_date, actual_date);
    }
}
