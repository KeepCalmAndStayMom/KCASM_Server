package server.api.v2;

import com.google.gson.Gson;
import server.api.v2.medic.ApiMedic;
import server.api.v2.patient.ApiPatient;
import server.database.v2.LoginDB;
import static spark.Spark.*;

import java.util.Base64;
import java.util.Map;

public class ApiServiceV2 {
    private final static String BASE_URL = "/api/v2";
    private Gson gson = new Gson();

    public ApiServiceV2() {
        new ApiPatient();
        new ApiMedic();
        new ApiTaskCategories();
        new ApiBMI();
        new ApiChromotherapyTypes();
        login();
    }

    private void login() {
        post(BASE_URL + "/login_data", (request, response) -> {
            String base64 = request.headers("Authorization").replace("Basic ", "");
            String[] auth = new String(Base64.getDecoder().decode(base64), "UTF-8").split(":");

            Map query = LoginDB.selectLogin(auth[0], auth[1]);

            if(query != null) {
                response.status(200);
                response.type("application/json");
                return JsonBuilder.jsonMap(query, null);
            }

            response.status(401);
            return "ERRORE";
        });

        get(BASE_URL + "/password_reset", (request, response) ->{
            String email = request.queryParams("email");

            if(email != null && email.matches(Regex.EMAIL_REGEX)) {
                Map password = LoginDB.selectForPasswordReset(email);
                if(password != null) {
                    response.status(200);
                    response.type("application/json");

                    return password;
                }

                response.status(404);
                return "Email non trovata";
            }

            response.status(400);
            response.type("application/json");
            return "Specifica una email";

        }, gson::toJson);
    }
}
