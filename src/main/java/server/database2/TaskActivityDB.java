package server.database2;

import java.util.List;
import java.util.Map;

public class TaskActivityDB {

    static public List<Map<String, Object>> selectDate(Integer patientId, String medic_id, String date, String executed, String userType) {
        String sql = "SELECT * FROM Task_Activity WHERE date=?";

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

    static public List<Map<String, Object>> selectProgram(Integer patientId, String userType) {
        String sql = "SELECT * FROM Task_Activity WHERE starting_program=1";

        if(userType.equals("patient"))
            sql+=" AND Patient_id=" + patientId;
        else
            sql+=" AND Medic_id=" + patientId;

        return SharedTaskFunctionDB.getTaskPatientId(sql, patientId);
    }

    static public List<Map<String, Object>> select(Integer patientId, String medic_id, String executed, String userType) {
        String sql = "SELECT * FROM Task_Activity WHERE";

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
        final String sql = "UPDATE Task_Activity SET Patient_id=?, date=?, category=?, description=?, starting_program=?, executed=? WHERE id=?";
        return SharedTaskFunctionDB.executeUpdate(sql, map);
    }

    static public boolean insert(Map<String, Object> map) {
        final String sql = "INSERT INTO Task_Activity(id, Patient_id, Medic_id, date, category, description, starting_program, executed) VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
        return SharedTaskFunctionDB.executeInsert(sql, map);
    }

    static public boolean delete(Map<String, Object> map) {
        final String sql = "DELETE from Task_Activity WHERE id=?";
        return SharedTaskFunctionDB.executeDelete(sql, (Integer) map.get("id"));
    }

    public static List<Map<String, Object>> selectDateInterval(Integer patientId, String medic_id, String startdate, String enddate, String executed, String userType) {
        String sql = "SELECT * FROM Task_Activity WHERE date BETWEEN ? AND ?";

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
