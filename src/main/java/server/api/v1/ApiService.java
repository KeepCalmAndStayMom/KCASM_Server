package server.api.v1;

import static spark.Spark.get;

public class ApiService {

    public ApiService() {

        sparkGetServer();

        new ApiFitbit();
        new ApiHue();
        new ApiZway();
        new ApiTask();
        new ApiUser();
        new ApiMedic();
        new ApiLogin();

        new ApiPeso();
        new ApiAttivita();
    }

    private void sparkGetServer() {
        get("/api/", "application/json", (request, response) -> {
            response.status(200);
            return "{ \"Success\": \"server raggiungibile\" }";
        });
    }
}