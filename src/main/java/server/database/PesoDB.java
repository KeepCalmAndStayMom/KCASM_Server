package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PesoDB {

    public static Map<String,Float> getPeso(int homestation_id){
        final String sql = "SELECT * FROM peso WHERE homestation_id=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);

            ResultSet rs = st.executeQuery();

            HashMap<String, Float> map = new HashMap<>();

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
