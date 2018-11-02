package server.retrieve_data;

import server.database2.TaskActivityDB;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class RetrieveDataWeekAttivita implements RetrieveDataInterface {
    @Override
    public Object getData(int patientID, LocalDate actualDate, double actualPeso) {
        List<Map<String, Object>> listAttivita = TaskActivityDB.selectProgram(patientID, "patient");
        assert listAttivita != null;
        if(listAttivita.size()==0)
            return 0;
        return (int) ChronoUnit.WEEKS.between(LocalDate.parse(String.valueOf(listAttivita.get(listAttivita.size()-1).get("date"))), actualDate);

    }
}
