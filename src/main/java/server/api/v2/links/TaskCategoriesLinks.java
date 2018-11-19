package server.api.v2.links;

public class TaskCategoriesLinks {

    public static String categoriesLinks() {
        StringBuilder json = new StringBuilder();

        json.append(Link.jsonLink("http://localhost:4567/api/v2/task_categories/activities", "task_categories/activities", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/task_categories/diets", "task_categories/diets", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/task_categories/general", "task_categories/general", "GET"));

        return json.toString();
    }
}
