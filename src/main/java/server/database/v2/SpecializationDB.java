package server.database.v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpecializationDB {
    private final static String SELECT_SPECIALIZATIONS= "SELECT * FROM Specialization";

    public static List<String> select() {
        try {
            Connection conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(SELECT_SPECIALIZATIONS);
            ResultSet rs = st.executeQuery();
            List result = new ArrayList();

            while(rs.next()) {
                result.add(rs.getString("name"));
            }

            return result;
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
