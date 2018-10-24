package server.netsmiledecorator;

import server.database2.TaskDietDB;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class RetrieveDataDieta implements RetrieveDataInterface {

    @Override
    public Object getData(int patientID, LocalDate actual_date, double actual_peso) {

        List<Map<String, Object>> list_dieta = TaskDietDB.SelectProgram(patientID);
        assert list_dieta != null;
        if(list_dieta.size()==0)
            return "Nessuna";
        return String.valueOf(list_dieta.get(list_dieta.size()-1).get("category"));
    }
}
