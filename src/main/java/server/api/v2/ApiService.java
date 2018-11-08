package server.api.v2;

import server.api.v2.patient.ApiPatient;
import server.database2.LoginDB;
import static spark.Spark.*;

import java.util.Base64;
import java.util.Map;

public class ApiService {
    private final static String baseURL = "/api/v2";

    public ApiService() {
        new ApiPatient();
        login();
    }

    public void login() {
        post(baseURL + "/login_data", (request, response) -> {
            String base64 = request.headers("Authorization").replace("Basic ", "");
            String[] auth = new String(Base64.getDecoder().decode(base64), "UTF-8").split(":");

            Map query = LoginDB.select(auth[0], auth[1]);

            if(query != null) {
                response.status(200);
                response.type("application/json");
                return JsonBuilder.jsonObject(query, null);
            }

            response.status(401);
            return "ERRORE";
        });
    }
}
