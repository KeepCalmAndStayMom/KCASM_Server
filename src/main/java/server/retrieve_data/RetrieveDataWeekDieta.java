package server.retrieve_data;

import server.database.v2.TaskDietDB;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class RetrieveDataWeekDieta implements RetrieveDataInterface {
    @Override
    public Object getData(int patientID, LocalDate actualDate, double actualPeso) {
        List<Map<String, Object>> listDieta = TaskDietDB.selectProgram(patientID, "patient");
        assert listDieta != null;
        if(listDieta.size()==0)
            return 0;
        return (int) ChronoUnit.WEEKS.between(LocalDate.parse(String.valueOf(listDieta.get(listDieta.size()-1).get("date"))), actualDate);

    }
}
