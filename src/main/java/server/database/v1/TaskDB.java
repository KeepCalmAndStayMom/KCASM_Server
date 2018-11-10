package server.database.v1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskDB {

    public static void addTask(int homestation_id, String title, String description, String programmed_date) {
        final String sql = "INSERT INTO task(homestation_id, title, description, programmed_date) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            st.setString(2, title);
            st.setString(3, description);
            st.setString(4, programmed_date);

            st.executeUpdate();

            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean updateTask(int id, String executed) {
        final String sql = "UPDATE task SET executed=? WHERE id=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            if (executed.equalsIgnoreCase("true"))
                st.setInt(1, 1);
            else
                st.setInt(1, 0);
            st.setInt(2, id);

            if(st.executeUpdate() == 0) {
                conn.close();
                return false;
            }

            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean deleteTask(int id) {
        final String sql = "DELETE from task WHERE id=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);

            st.setInt(1, id);

            if(st.executeUpdate() == 0) {
                conn.close();
                return false;
            }

            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static String getAllTasks(int homestation_id) {
        final String sql = "SELECT * FROM task WHERE homestation_id=?";

        String json = executeGetTasks(homestation_id, sql);
        if (json != null) return json;

        return null;
    }

    public static String getTask(int id) {
        final String sql = "SELECT * FROM task WHERE id=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            StringBuilder builder = new StringBuilder();

            builder.append("{\"id\": ").append(rs.getInt("id")).append(",");
            builder.append("\"title\": \"").append(rs.getString("title")).append("\",");
            builder.append("\"description\": \"").append(rs.getString("description")).append("\",");
            builder.append("\"programmed_date\": \"").append(rs.getString("programmed_date")).append("\",");
            builder.append("\"executed\": ").append(rs.getBoolean("executed")).append("}");

            conn.close();
            return builder.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getTasksWithDate(int homestation_id, String date) {
        final String sql = "SELECT * FROM task WHERE homestation_id=? AND programmed_date=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            st.setString(2, date);
            String json = getTaskJson(st.executeQuery());
            conn.close();
            return json;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getTasksToExecute(int homestation_id) {
        final String sql = "SELECT * FROM task WHERE homestation_id=? AND executed=0";

        String json = executeGetTasks(homestation_id, sql);
        if (json != null) return json;

        return null;
    }

    public static String getExecutedTasks(int homestation_id) {
        final String sql = "SELECT * FROM task WHERE homestation_id=? AND executed=1";

        String json = executeGetTasks(homestation_id, sql);
        if (json != null) return json;

        return null;
    }

    private static String executeGetTasks(int homestation_id, String sql) {
        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            String json = getTaskJson(st.executeQuery());
            conn.close();
            return json;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getTaskJson(ResultSet rs) throws SQLException {
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        while (rs.next()) {
            builder.append("\"").append(rs.getString("id")).append("\": {");
            builder.append("\"id\": \"").append(rs.getString("id")).append("\",");
            builder.append("\"title\": \"").append(rs.getString("title")).append("\",");
            builder.append("\"description\": \"").append(rs.getString("description")).append("\",");
            builder.append("\"programmed_date\": \"").append(rs.getString("programmed_date")).append("\",");
            builder.append("\"executed\": ").append(rs.getBoolean("executed")).append("},");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append("}");

        return builder.toString();
    }
}
