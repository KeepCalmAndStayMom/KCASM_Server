package server.database.v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SharedTaskFunctionDB {

    static Connection conn;

    private static List<Map<String,Object>> getListTask(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        LinkedHashMap<String, Object> map;

        while(rs.next()) {
            map = new LinkedHashMap<>();
            map.put("id", rs.getInt("id"));
            map.put("patient_id", rs.getInt("patient_id"));
            map.put("medic_id", rs.getInt("medic_id"));
            map.put("date", rs.getString("date"));
            map.put("category", rs.getString("category"));
            map.put("description", rs.getString("description"));
            map.put("starting_program", rs.getBoolean("starting_program"));
            map.put("executed", rs.getBoolean("executed"));
            list.add(map);
        }
        conn.close();
        return list;
    }

    static List<Map<String,Object>> getTaskPatientId(String sql, int patientId) {

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);

            return getListTask(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static List<Map<String, Object>> getTaskPatientIdDate(String sql, int patientId, String date)
    {
        try {
            Connection conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            //st.setInt(1, patientId);
            st.setString(1, date);

            return getListTask(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static boolean executeUpdate(String sql, Map<String, Object> map)
    {
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, (Integer) map.get("patient_id"));
            st.setString(2, String.valueOf(map.get("date")));
            st.setString(3, String.valueOf(map.get("category")));
            st.setString(4, String.valueOf(map.get("description")));
            st.setBoolean(5, (Boolean) map.get("starting_program"));
            st.setBoolean(6, (Boolean) map.get("executed"));
            st.setInt(7, (Integer) map.get("id"));


            if(st.executeUpdate() != 0) {
                conn.close();
                return true;
            }
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    static boolean executeInsert(String sql, Map<String,Object> map)
    {
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, ((Double) map.get("patient_id")).intValue());
            st.setInt(2, (Integer) map.get("medic_id"));
            st.setString(3, String.valueOf(map.get("date")));
            st.setString(4, String.valueOf(map.get("category")));
            st.setString(5, String.valueOf(map.get("description")));
            st.setBoolean(6, (Boolean) map.get("starting_program"));
            st.executeUpdate();
            conn.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    static boolean executeDelete(String sql, int id)
    {
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);

            st.setInt(1, id);

            if(st.executeUpdate() != 0) {
                conn.close();
                return true;
            }

            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();

        }

        return false;
    }

    public static List<Map<String, Object>> getTaskPatientIdDateInterval(String sql, int patientId, String startdate, String enddate) {

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            //st.setInt(1, patientId);
            st.setString(1, startdate);
            st.setString(2, enddate);

            return getListTask(st.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> selectSingleTaskGeneral(int id, int taskId, String typeUser) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Task_General WHERE id=" + taskId);

        if(typeUser.equals("patient"))
            sql.append(" AND patient_id=" + id);
        else
            sql.append(" AND medic_id=" + id);

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql.toString());
            //st.setInt(1, taskId);

            return singleTaskMap(st.executeQuery());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Map<String, Object> selectSingleTaskActivities(int id, int taskId, String typeUser) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Task_Activity WHERE id=" + taskId);

        if(typeUser.equals("patient"))
            sql.append(" AND patient_id=" + id);
        else
            sql.append(" AND medic_id=" + id);

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql.toString());
            //st.setInt(1, taskId);

            return singleTaskMap(st.executeQuery());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Map<String, Object> selectSingleTaskDiets(int id, int taskId, String typeUser) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Task_Diet WHERE id=" +  taskId);

        if(typeUser.equals("patient"))
            sql.append(" AND patient_id=" + id);
        else
            sql.append(" AND medic_id=" + id);

        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql.toString());
            //st.setInt(1, taskId);

            return singleTaskMap(st.executeQuery());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Map<String, Object> singleTaskMap(ResultSet rs) throws SQLException {
        Map<String, Object> map = new LinkedHashMap<>();

        rs.next();
        map.put("id", rs.getInt("id"));
        map.put("patient_id", rs.getInt("patient_id"));
        map.put("medic_id", rs.getInt("medic_id"));
        map.put("date", rs.getString("date"));
        map.put("category", rs.getString("category"));
        map.put("description", rs.getString("description"));
        map.put("starting_program", rs.getBoolean("starting_program"));
        map.put("executed", rs.getBoolean("executed"));
        conn.close();
        return map;
    }

    static boolean update(String sql) {
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);

            if(st.executeUpdate() != 0) {
                conn.close();
                return true;
            }
            conn.close();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
