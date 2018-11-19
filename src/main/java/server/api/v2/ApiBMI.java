package server.api.v2;

import com.google.gson.Gson;
import server.database.v2.BMIDB;

import java.util.List;

import static spark.Spark.*;

public class ApiBMI {

    private Gson gson = new Gson();

    public ApiBMI() {
        getBMI();
    }

    private void getBMI() {
        get("/api/v2/bmi", (request, response) -> {
            List bmi = BMIDB.select();

            if(bmi != null && bmi.size() > 0) {
                response.status(200);
                response.type("application/json");

                return bmi;
            }

            response.status(404);
            return "";
        }, gson::toJson);
    }
}
