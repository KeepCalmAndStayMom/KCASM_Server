package server.database.v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskCategories {
    private final static String SELECT_ACTIVITIES = "SELECT * FROM Category_Activity";
    private final static String SELECT_DIETS = "SELECT * FROM Category_Diet";
    private final static String SELECT_GENERAL = "SELECT * FROM Category_General";

    private static Connection conn;

    public static List<String> selectActivities() { return select(SELECT_ACTIVITIES); }
    public static List<String> selectDiets() { return select(SELECT_DIETS); }
    public static List<String> selectGeneral() { return select(SELECT_GENERAL); }

    private static List<String> select(String sql) {
        try {
            conn = DBConnectOnline.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List result = new ArrayList();

            while(rs.next()) {
                result.add(rs.getString("name"));
            }

            return result;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


}
