package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PesoDB {

    public static boolean addPeso(int homestation_id, String data, Float peso) {
        final String sql = "INSERT INTO peso(homestation_id, data, peso) VALUES (?, ?, ?)";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);
            st.setString(2, data);
            st.setFloat(3, peso);

            st.executeUpdate();

            conn.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Map<String,Float> getPeso(int homestation_id){
        final String sql = "SELECT * FROM peso WHERE homestation_id=? order by data";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);

            ResultSet rs = st.executeQuery();

            LinkedHashMap<String, Float> map = new LinkedHashMap<>();

            while(rs.next())
                map.put(rs.getString("data"), rs.getFloat("peso"));

            conn.close();
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
