package server.database2;

import java.util.List;
import java.util.Map;

public class TaskGeneralDB {

    static public List<Map<String, Object>> selectDate(Integer patientId, String medic_id, String date, String executed, String userType) {
        String sql = "SELECT * FROM Task_General WHERE date=?";

        if(patientId != null)
            sql+=" AND Patient_id=" + patientId;
        if(medic_id != null)
            sql+=" AND Medic_id=" + medic_id;
        if(executed != null)
            sql+=" AND executed=" + executed;

        if(userType.equals("patient"))
            return SharedTaskFunctionDB.getTaskPatientIdDate(sql, patientId, date);
        else
            return SharedTaskFunctionDB.getTaskPatientIdDate(sql, Integer.parseInt(medic_id), date);
    }

    public static List<Map<String, Object>> selectDateInterval(Integer patientId, String medic_id, String startdate, String enddate, String executed, String userType) {
        String sql = "SELECT * FROM Task_General WHERE date BETWEEN ? AND ?";

        if(patientId != null)
            sql+=" AND Patient_id=" + patientId;
        if(medic_id != null)
            sql+=" AND Medic_id=" + medic_id;
        if(executed != null)
            sql+=" AND executed=" + executed;

        if(userType.equals("patient"))
            return SharedTaskFunctionDB.getTaskPatientIdDateInterval(sql, patientId, startdate, enddate);
        else
            return SharedTaskFunctionDB.getTaskPatientIdDateInterval(sql, Integer.parseInt(medic_id), startdate, enddate);
    }

    static public List<Map<String, Object>> selectProgram(Integer patientId, String userType) {
        String sql = "SELECT * FROM Task_General WHERE starting_program=1";

        if(userType.equals("patient"))
            sql+=" AND Patient=id=" + patientId;
        else
            sql+=" AND Medic_id=" + patientId;

        return SharedTaskFunctionDB.getTaskPatientId(sql, patientId);
    }

    static public List<Map<String, Object>> selectExecuted(int patientId) {
        final String sql = "SELECT * FROM Task_General WHERE Patient_id=? AND executed=1";
        return SharedTaskFunctionDB.getTaskPatientId(sql, patientId);
    }

    static public List<Map<String, Object>> select(Integer patientId, String medic_id, String executed, String userType) {
        String sql = "SELECT * FROM Task_General WHERE";

        if(patientId != null)
            sql+=" Patient_id=" + patientId;
        if(medic_id != null && patientId == null)
            sql+=" Medic_id=" + medic_id;
        else if(medic_id !=null && patientId !=null)
            sql+=" AND Medic_id=" + medic_id;
        if(executed != null)
            sql+=" AND executed=" + executed;

        if(userType.equals("patient"))
            return SharedTaskFunctionDB.getTaskPatientId(sql, patientId);

        return SharedTaskFunctionDB.getTaskPatientId(sql, Integer.parseInt(medic_id));
    }

    static public boolean update(Map<String, Object> map) {
        final String sql = "UPDATE Task_General SET Patient_id=?, date=?, category=?, description=?, starting_program=?, executed=? WHERE id=?";
        return SharedTaskFunctionDB.executeUpdate(sql, map);
    }

    static public boolean insert(Map<String, Object> map) {
        final String sql = "INSERT INTO Task_General(id, Patient_id, Medic_id, date, category, description, starting_program, executed) VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
        return SharedTaskFunctionDB.executeInsert(sql, map);
    }

    static public boolean delete(Map<String, Object> map) {
        final String sql = "DELETE from Task_General WHERE id=?";
        return SharedTaskFunctionDB.executeDelete(sql, (Integer) map.get("id"));
    }
}
