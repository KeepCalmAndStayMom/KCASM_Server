package server.api.v2.links;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskCategoriesLinks {

    public static List<Map<String, String>> categoriesLinks() {
        List<Map<String, String>> links = new ArrayList<>();

        links.add(Link.linkMap("http://localhost:4567/api/v2/task_categories/activities", "task_categories/activities", "GET"));
        links.add(Link.linkMap("http://localhost:4567/api/v2/task_categories/diets", "task_categories/diets", "GET"));
        links.add(Link.linkMap("http://localhost:4567/api/v2/task_categories/general", "task_categories/general", "GET"));

        return links;
    }
}
