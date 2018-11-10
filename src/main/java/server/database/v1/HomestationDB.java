package server.database.v1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomestationDB {

    public static String[] getAllHomestations() {
        final String sql = "SELECT * FROM homestation";

        StringBuilder builder = new StringBuilder();

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                builder.append(rs.getInt("id")).append(",");
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return builder.toString().split(",");
    }
}