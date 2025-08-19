package hexlet.code;

import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

public class App {

    public static Javalin getApp() throws Exception {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get(NamedRoutes.rootPath(), ctx -> {
            ctx.render("index.jte");
        });

        return app;
    }

    public static void main(String[] args) throws Exception {
        var app = getApp();
        app.start(Integer.valueOf(System.getenv().getOrDefault("PORT", "7071")));
    }
}
