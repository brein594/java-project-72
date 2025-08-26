package hexlet.code.controller;

import hexlet.code.dto.urls.BuildUrlPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {

    public static void create(Context ctx) {

        var createdAt = LocalDateTime.now();
        try {
            var name = ctx.formParamAsClass("name", String.class)
                    .check(value -> Pattern.matches("https?://[\\w.-]+(?:/[^\\s\"']*)?",value), "Некорректный URL")
                    .get();
            var nameURI = new URI(name);
            URL url = nameURI.toURL();
            var nameSave = nameURI.getScheme() + "://" + nameURI.getAuthority();
            var urlSave = new Url(nameSave, createdAt);
            if (!UrlRepository.findSaveRepository(nameSave)) {
                UrlRepository.save(urlSave);
                ctx.sessionAttribute("addUrl", "Адрес добавлен");
            } else {
                ctx.sessionAttribute("addUrl", "Страница уже существует");
            }
            ctx.redirect(NamedRoutes.urlsPaths());
        } catch (ValidationException e) {
            var name = ctx.formParam("name");
            var page = new BuildUrlPage(name, e.getErrors());
            ctx.sessionAttribute("addUrl", "Некорректный URL");
            ctx.render("index.jte", model("page", page));
            //ctx.redirect(NamedRoutes.rootPath());
        } catch (SQLException | URISyntaxException | MalformedURLException er) {
            throw new RuntimeException(er);
        }
    }

    public static void show(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        try {
            var page = new UrlPage(UrlRepository.getUrls().get((int) (id - 1L)), UrlCheckRepository.getUrlChecks(id));
            /*var url = UrlRepository.find(id)
                    .orElseThrow(() -> new NotFoundResponse("URL c id " + id + " не найден"));
            var page = new UrlPage(url);*/
            ctx.render("urls/show.jte", model("page", page));
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void index(Context ctx) {
        try {
            var urls = UrlRepository.getUrls();
            var page = new UrlsPage(urls);
            page.setFlash(ctx.consumeSessionAttribute("addUrl"));
            ctx.render("urls/index.jte", model("page", page));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
