package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.controller.UrlsController;
import hexlet.code.dto.urls.BuildUrlPage;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.stream.Collectors;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;

import static io.javalin.rendering.template.TemplateUtil.model;


public class App {
    private static Integer getPort() {
        String port = System.getenv().getOrDefault("PORT", "7071");
        return Integer.valueOf(port);
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        return templateEngine;
    }

    public static Javalin getApp() throws SQLException {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(System.getenv().getOrDefault("JDBC_DATABASE_URL",
                "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;"));
        var dataSource = new HikariDataSource(hikariConfig);

        var url = App.class.getClassLoader().getResourceAsStream("schema.sql");
        var sql = new BufferedReader(new InputStreamReader(url))
                .lines().collect(Collectors.joining("\n"));
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
        BaseRepository.dataSource = dataSource;

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            //config.fileRenderer(new JavalinJte());
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });
/*
        var url1 = new Url("nameUrl", 12L);
        UrlRepository.save(url1);


        var url2 = UrlRepository.find(1L).orElseThrow(() -> new NotFoundResponse("User with id not found"));
        System.out.println(url2.getName());
*/
        app.get(NamedRoutes.rootPath(), ctx -> {
            var page = new BuildUrlPage();
            ctx.render("index.jte", model("page", page));
        });
        app.get(NamedRoutes.urlsPaths("{id}"), UrlsController::show);
        app.get(NamedRoutes.urlsPaths(), UrlsController::index);
        app.post(NamedRoutes.urlsPaths(), UrlsController::create);
        app.post(NamedRoutes.checkPaths("{id}"), UrlsController::check);
        return app;
    }

    public static void main(String[] args) throws Exception {
        var app = getApp();
        app.start(getPort());
    }
}
