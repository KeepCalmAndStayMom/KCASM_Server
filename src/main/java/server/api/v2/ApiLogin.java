package server.api.v2;

import com.google.gson.Gson;
import server.api.v2.links.LinksBuilder;
import server.database2.LoginDB;
import server.database2.PatientDB;

import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;

public class ApiLogin {
    private final static String baseURL = "/api/v2";
    private Gson gson = new Gson();

    public ApiLogin() {
        apiLogin();
    }

    private void apiLogin() {

            path(baseURL + "/login", () -> {
                get("", (request, response) -> {

                    String email = String.valueOf(request.queryParams("email"));
                    String password = String.valueOf(request.queryParams("password"));
                    Map<String, Object> query = LoginDB.selectLogin(email, password);

                    if (query != null) {
                        response.status(200);
                        response.type("application/json");

                        return JsonBuilder.jsonObject(query, null).toString();
                    }

                    response.status(404);
                    return "";
                });
            });
    }
}