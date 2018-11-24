package server.api.v2;

import com.google.gson.Gson;
import server.api.v2.links.TaskCategoriesLinks;
import server.database.v2.TaskCategories;

import java.util.List;

import static spark.Spark.*;

public class ApiTaskCategories {

    private Gson gson = new Gson();

    public ApiTaskCategories() {
        getTaskCategoriesActivities();
        getTaskCategoriesDiets();
        getTaskCategoriesGeneral();
        showTaskCategories();
    }

    private void showTaskCategories() {
        get("/api/v2/task_categories", (request, response) -> {
            response.status(200);
            response.type("application/json");


            return TaskCategoriesLinks.categoriesLinks();
        }, gson::toJson);
    }

    private void getTaskCategoriesActivities() {
        get("/api/v2/task_categories/activities", (request, response) -> {
            List activities = TaskCategories.selectActivities();

            if(activities != null) {
                response.status(200);
                response.type("application/json");

                return activities;
            }

            response.status(404);
            return "";
        }, gson::toJson);
    }

    private void getTaskCategoriesDiets() {
        get("/api/v2/task_categories/diets", (request, response) -> {
            List diets = TaskCategories.selectDiets();

            if(diets != null) {
                response.status(200);
                response.type("application/json");

                return diets;
            }

            response.status(404);
            return "";
        }, gson::toJson);
    }

    private void getTaskCategoriesGeneral() {
        get("/api/v2/task_categories/general", (request, response) -> {
            List general = TaskCategories.selectGeneral();

            if(general != null) {
                response.status(200);
                response.type("application/json");

                return general;
            }

            response.status(404);
            return "";
        }, gson::toJson);
    }
}
