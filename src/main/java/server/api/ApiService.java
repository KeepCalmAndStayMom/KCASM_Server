package server.api;

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
    }

    private void sparkGetServer() {
        get("/api/", "application/json", (request, response) -> {
            response.status(200);
            return "{ \"Success\": \"server raggiungibile\" }";
        });
    }
}