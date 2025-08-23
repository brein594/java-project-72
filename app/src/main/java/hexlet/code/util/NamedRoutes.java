package hexlet.code.util;

public class NamedRoutes {
    public static String rootPath() {
        return "/";
    }
    public static String urlsPaths() {
        return "/urls";
    }
    public static String urlsPaths(String id) {
        return  "/urls/" + id;
    }
    public static String urlsPaths(Long id) {
        return  "/urls/" + String.valueOf(id);
    }
    public static String checkPaths(String id) {
        return  "/urls/" + id +"/check";
    }
}
