package server.api.v2.patient;

import com.google.gson.Gson;
import server.database.v2.ChromotherapyDB;

import java.util.List;

import static spark.Spark.*;

public class ApiChromotherapyTypes {

    private Gson gson = new Gson();

    public ApiChromotherapyTypes() {
        getChromotherapyTypes();
    }

    private void getChromotherapyTypes() {
        get("/api/v2/chromotherapy_types", (request, response) -> {
            List chromo = ChromotherapyDB.select();

            if(chromo != null && chromo.size() > 0) {
                response.status(200);
                response.type("application/json");

                return chromo;
            }

            response.status(404);
            return null;
        }, gson::toJson);
    }
}
