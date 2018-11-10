package server.retrieve_data;

import server.database.v2.TaskActivityDB;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class RetrieveDataAttivita implements RetrieveDataInterface {

    @Override
    public Object getData(int patientID, LocalDate actualDate, double actualPeso) {
        List<Map<String, Object>> listAttivita = TaskActivityDB.selectProgram(patientID, "patient");
        assert listAttivita != null;
        if(listAttivita.size()==0)
            return "Nessuna";
        return String.valueOf(listAttivita.get(listAttivita.size()-1).get("category"));
    }
}
