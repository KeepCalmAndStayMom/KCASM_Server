package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttivitaDB {

    public static List<Map<String,Object>> getAttivitaPrograms(int homestation_id) {
        final String sql = "SELECT * FROM attivita WHERE homestation_id=? AND inizio_programma=1";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);

            ResultSet rs = st.executeQuery();

            List<Map<String, Object>> list = new ArrayList<>();
            HashMap<String, Object> map;

            while(rs.next()) {
                map = new HashMap<>();
                map.put("data", rs.getString("data"));
                map.put("categoria", rs.getString("categoria"));
                map.put("descrizione", rs.getString("descrizione"));
                map.put("executed", rs.getBoolean("executed"));
                list.add(map);
            }

            conn.close();
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Map<String, Object>> getActivitiesOfTheDay(int homestation_id, String data) {
        final String sql = "SELECT * FROM attivita WHERE homestation_id=? AND data=? AND inizio_programma=0 AND executed='false'";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            st.setString(2, data);

            ResultSet rs = st.executeQuery();

            List<Map<String, Object>> list = new ArrayList<>();
            HashMap<String, Object> map;

            while(rs.next()) {
                map = new HashMap<>();
                map.put("id", rs.getInt("id"));
                map.put("categoria", rs.getString("categoria"));
                map.put("descrizione", rs.getString("descrizione"));
                list.add(map);
            }

            conn.close();
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}