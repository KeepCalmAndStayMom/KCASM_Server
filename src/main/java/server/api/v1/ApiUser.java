package server.api.v1;

import com.google.gson.Gson;
import server.database.LoginDB;
import server.database.UserDB;
import server.database.UserInitialDateDB;

import java.util.Map;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

class ApiUser {

    private Gson gson = new Gson();
    private String baseURL = "/api";

    ApiUser(){
        sparkGetUser();
        sparkPostUser();
        sparkPutUserLogin();
        sparkGetUserInitialDate();
    }

    private void sparkGetUser() {
        get(baseURL + "/users/:homestation_id", "application/json", (request, response) -> {

            String json = UserDB.getUser(Integer.valueOf(request.params(":homestation_id")));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\"}";
            }

            response.status(200);
            return json;
        });
    }

    private void sparkPostUser() {
        post(baseURL + "/users", (request, response) -> {

            Map addRequest = gson.fromJson(request.body(), Map.class);
            response.type("application/json");

            if (addRequest != null && addRequest.containsKey("homestation_id")
                    && addRequest.containsKey("name")
                    && addRequest.containsKey("age")
                    && addRequest.containsKey("height")
                    && addRequest.containsKey("weight")
                    && addRequest.containsKey("medic_id")
                    && addRequest.containsKey("home_address")
                    && addRequest.containsKey("hospital_address")
                    && addRequest.containsKey("phone_number"))
            {
                Integer homestation_id = ((Double) addRequest.get("homestation_id")).intValue();
                String name = String.valueOf(addRequest.get("name"));
                Integer age = ((Double) addRequest.get("age")).intValue();
                Integer height = ((Double) addRequest.get("height")).intValue();
                Integer weight = ((Double) addRequest.get("weight")).intValue();
                Integer medic_id = ((Double) addRequest.get("medic_id")).intValue();
                String home_address = String.valueOf(addRequest.get("home_address"));
                String hospital_address = String.valueOf(addRequest.get("hospital_address"));
                String phone_number = String.valueOf(addRequest.get("phone_number"));

                if (!UserDB.addUser(homestation_id, name, age, height, weight, medic_id, home_address, hospital_address, phone_number)) {
                    response.status(400);
                    return "{\"Errore 400\": \"richiesta non valida\"}";
                }
            }
            else {
                response.status(400);
                return "{\"Errore 400\": \"richiesta non valida\"}";
            }

            response.status(201);
            return "{ \"Success\": \"risorsa aggiunta\"}";
        });
    }

    private void sparkPutUserLogin() {
        put(baseURL + "/login/:username/:homestation_id", (request, response) -> {

            Map addRequest = gson.fromJson(request.body(), Map.class);
            response.type("application/json");

            if (addRequest != null && addRequest.containsKey("username")
                    && addRequest.containsKey("email")
                    && addRequest.containsKey("name")
                    && addRequest.containsKey("age")
                    && addRequest.containsKey("height")
                    && addRequest.containsKey("weight")
                    && addRequest.containsKey("home_address")
                    && addRequest.containsKey("hospital_address")
                    && addRequest.containsKey("phone_number"))
            {

                if(!LoginDB.updateLogin(request.params(":username"),String.valueOf(addRequest.get("username")),
                        String.valueOf(addRequest.get("email"))))
                {
                    response.status(409);
                    return "{\"Errore 409\": \"conflitto generato\"}";
                }

                if(!UserDB.updateUser(Integer.valueOf(request.params(":homestation_id")),
                        String.valueOf(addRequest.get("name")),
                        ((Double) addRequest.get("age")).intValue(),
                        ((Double) addRequest.get("height")).intValue(),
                        ((Double) addRequest.get("weight")).intValue(),
                        String.valueOf(addRequest.get("home_address")),
                        String.valueOf(addRequest.get("hospital_address")),
                        String.valueOf(addRequest.get("phone_number"))))
                {
                    response.status(400);
                    return "{\"Errore 400\": \"richiesta non valida\"}";
                }

            }
            else {
                response.status(400);
                return "{\"Errore 400\": \"richiesta non valida\"}";
            }

            response.status(200);
            return "{\"Success\": \"risorsa modificata\"}";
        });
    }

    private void sparkGetUserInitialDate() {
        get(baseURL + "/users/initial_date/:homestation_id", "application/json", (request, response) -> {

            Map result = UserInitialDateDB.getUserInitialDate(Integer.valueOf(request.params(":homestation_id")));
            response.type("application/json");

            if (result == null) {
                response.status(404);
                return "{\"Errore 404\": \"risorsa non trovata\"}";
            }

            String json = gson.toJson(result);

            response.status(200);
            return json;
        });
    }
}