package server.retrieve_data;

import server.MainServer;
import server.weight_control.ControllerPesoTest;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RetrieveDataWeekAvviso implements RetrieveDataInterface {

    @Override
    public Object getData(int patientID, LocalDate actualDate, double actualPeso) {
        LocalDate lastDate = MainServer.cpt.getLastAvviso(patientID);
        if(lastDate==null)
            return 50;
        return (int) ChronoUnit.WEEKS.between(lastDate, actualDate);
    }
}
