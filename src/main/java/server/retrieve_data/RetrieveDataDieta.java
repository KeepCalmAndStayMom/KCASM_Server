package server.retrieve_data;

import server.database.v2.TaskDietDB;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class RetrieveDataDieta implements RetrieveDataInterface {

    @Override
    public Object getData(int patientID, LocalDate actualDate, double actualPeso) {
        List<Map<String, Object>> listDieta = TaskDietDB.selectProgram(patientID, "patient");
        assert listDieta != null;
        if(listDieta.size()==0)
            return "Nessuna";
        return String.valueOf(listDieta.get(listDieta.size()-1).get("category"));
    }
}
