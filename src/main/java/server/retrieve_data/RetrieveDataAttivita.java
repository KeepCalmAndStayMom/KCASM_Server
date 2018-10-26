package server.retrieve_data;

import server.database2.TaskActivityDB;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class RetrieveDataAttivita implements RetrieveDataInterface {

    @Override
    public Object getData(int patientID, LocalDate actual_date, double actual_peso) {
        List<Map<String, Object>> list_attivita = TaskActivityDB.SelectProgram(patientID);
        assert list_attivita != null;
        if(list_attivita.size()==0)
            return "Nessuna";
        return String.valueOf(list_attivita.get(list_attivita.size()-1).get("category"));
    }
}
