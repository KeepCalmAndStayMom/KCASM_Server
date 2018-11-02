package server.database2;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;
import java.util.Map;

public class TaskDietDB {

    static public List<Map<String, Object>> selectDate(Integer patientId, String medic_id, String date, String executed, String userType) {
        String sql = "SELECT * FROM Task_Diet WHERE date=?";

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

    static public List<Map<String, Object>> selectProgram(int patientId, String userType) {
        String sql = "SELECT * FROM Task_Diet WHERE starting_program=1";

        if(userType.equals("patient"))
            sql+=" AND Patient=id=" + patientId;
        else
            sql+=" AND Medic_id=" + patientId;

        return SharedTaskFunctionDB.getTaskPatientId(sql, patientId);
    }

    static public List<Map<String, Object>> selectExecuted(int patientId) {
        final String sql = "SELECT * FROM Task_Diet WHERE Patient_id=? AND executed=1";
        return SharedTaskFunctionDB.getTaskPatientId(sql, patientId);
    }

    static public List<Map<String, Object>> select(Integer patientId, String medic_id, String executed, String userType) {
        String sql = "SELECT * FROM Task_Diet WHERE";

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
        final String sql = "UPDATE Task_Diet SET Patient_id=?, date=?, category=?, description=?, starting_program=?, executed=? WHERE id=?";
        return SharedTaskFunctionDB.executeUpdate(sql, map);
    }

    static public boolean insert(Map<String, Object> map) {
        final String sql = "INSERT INTO Task_Diet(id, Patient_id, Medic_id, date, category, description, starting_program, executed) VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
        return SharedTaskFunctionDB.executeInsert(sql, map);
    }

    static public boolean delete(Map<String, Object> map) {
        final String sql = "DELETE from Task_Diet WHERE id=?";
        return SharedTaskFunctionDB.executeDelete(sql, (Integer) map.get("id"));
    }

    public static List<Map<String, Object>> selectDateInterval(Integer patientId, String medic_id, String startdate, String enddate, String executed, String userType) {
        String sql = "SELECT * FROM Task_Diet WHERE date BETWEEN ? AND ?";

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
}
