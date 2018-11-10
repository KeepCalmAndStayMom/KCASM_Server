package server.database.v1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserInitialDateDB {

    public static Map<String,Object> getUserInitialDate(int homestation_id){
        final String sql = "SELECT * FROM user_initial_date WHERE homestation_id=?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, homestation_id);

            ResultSet rs = st.executeQuery();

            HashMap<String, Object> map = new HashMap<>();

            map.put("data_inizio_gravidanza", rs.getString("data_inizio_gravidanza"));
            map.put("peso", rs.getFloat("peso"));
            map.put("altezza", rs.getInt("altezza"));
            map.put("BMI", rs.getString("BMI"));
            map.put("gemelli", rs.getString("gemelli"));

            conn.close();
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
