package hexlet.code.controller;

import hexlet.code.dto.urls.BuildUrlPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {

    public static void create(Context ctx) {

        var createdAt = LocalDateTime.now();
        try {
            var name = ctx.formParamAsClass("url", String.class)
                    .check(value -> Pattern.matches("https?://(([\\w.-]+(?:/[^\\s\"']*)?)|(localhost:.*))",
                            value), "Некорректный URL").get();
            var nameURI = new URI(name);
            var nameSave = nameURI.toURL().toString();
            var urlSave = new Url(nameSave, createdAt);
            if (!UrlRepository.findToName(nameSave)) {
                UrlRepository.save(urlSave);
                ctx.sessionAttribute("type", "success");
                ctx.sessionAttribute("flash", "Адрес добавлен");
            } else {
                ctx.sessionAttribute("type", "info");
                ctx.sessionAttribute("flash", "Страница уже существует");
            }
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (ValidationException e) {
            var name = ctx.formParam("url");
            var page = new BuildUrlPage(name, e.getErrors());
            ctx.sessionAttribute("type", "danger");
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.render("index.jte", model("page", page));
        } catch (SQLException | URISyntaxException | MalformedURLException er) {
            throw new RuntimeException(er);
        }
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        try {
            var page = new UrlPage(UrlRepository.getUrls().get((int) (id - 1L)),
                    UrlCheckRepository.getUrlChecks(id));
            page.setType(ctx.consumeSessionAttribute("type"));
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            ctx.render("urls/show.jte", model("page", page));
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void index(Context ctx) {
        try {
            var urls = UrlRepository.getUrls();
            Map<String, UrlCheck> lastChecks = new HashMap<>();
            for (var url : urls) {
                var id = url.getId();
                var name = url.getName();
                var urlCheck = UrlCheckRepository.getLastUrlChecks(id).orElse(new UrlCheck(null, "", "", "", id, null));
                lastChecks.put(name, urlCheck);
            }
            var page = new UrlsPage(lastChecks);
            page.setType(ctx.consumeSessionAttribute("type"));
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            ctx.render("urls/index.jte", model("page", page));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
