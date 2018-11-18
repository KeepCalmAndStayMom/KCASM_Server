package server.database.v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ChromotherapyDB {

    static public List<String> select()
    {
        final String sql = "SELECT * FROM Chromotherapy";
        try {
            Connection conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<String> list = new LinkedList<>();

            while(rs.next())
                list.add(rs.getString("chromotherapy"));

            conn.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
