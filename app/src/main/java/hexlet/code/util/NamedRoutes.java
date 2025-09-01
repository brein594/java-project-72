package hexlet.code.util;

public class NamedRoutes {
    public static String rootPath() {
        return "/";
    }

    public static String urlsPath() {
        return "/urls";
    }

    public static String urlPath(String id) {
        return "/urls/" + id;
    }

    public static String urlPath(Long id) {
        return "/urls/" + String.valueOf(id);
    }

    public static String checkPath(String id) {
        return "/urls/" + id + "/checks";
    }
    public static String checkPath(long id) {
        return "/urls/" + String.valueOf(id) + "/checks";
    }
}
