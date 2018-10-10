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

    public static List<Map<String,Object>> getAttivitaPrograms(int homestation_id){
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
