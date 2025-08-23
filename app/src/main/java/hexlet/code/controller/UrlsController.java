package hexlet.code.controller;

import hexlet.code.dto.urls.BuildUrlPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotAcceptableResponse;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {

    public static void create (Context ctx) {

        var createdAt = LocalDateTime.now();
        try {
           var name = ctx.formParamAsClass("name", String.class)
                   .check(value -> {
                       try {
                           URL url = URI.create(value).toURL();
                           return url.getProtocol().equals("https");
                       } catch (MalformedURLException e) {
                           try {
                               throw new IOException(e);
                           } catch (IOException ex) {
                               throw new RuntimeException(ex);
                           }
                       }
                   }, "Некорректный URL")
                   .check(value -> {
                       try {
                           return !UrlRepository.findSaveRepository(value);
                       } catch (SQLException | URISyntaxException e) {
                           throw new RuntimeException (e);
                       }
                   }, "Страница уже существует")
                   .get();
           var nameURI = new URI(name);
           var nameSave = nameURI.getScheme() + "://" + nameURI.getAuthority();
           var url = new Url(nameSave, createdAt);
           UrlRepository.save(url);
           ctx.sessionAttribute("addUrl", "Адрес добавлен");
           ctx.redirect(NamedRoutes.urlsPaths());
        } catch (ValidationException e) {
            var name = ctx.formParam("name");
            var page = new BuildUrlPage(name, e.getErrors());
            ctx.sessionAttribute("addUrl", "Некорректный URL");
            ctx.render("index.jte", model("page", page));
        }catch (SQLException | URISyntaxException er) {
            throw new RuntimeException(er);
        }
    }

    public static void show(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        try {
            var page = new UrlPage(UrlRepository.getUrls().get((int) (id-1L)));
            /*var url = UrlRepository.find(id)
                    .orElseThrow(() -> new NotFoundResponse("URL c id " + id + " не найден"));
            var page = new UrlPage(url);*/
            ctx.render("urls/show.jte", model("page", page));
        } catch (SQLException exception ) {
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
    public static void check(Context ctx) {

    }
}
