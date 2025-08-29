package hexlet.code.controller;

import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;

import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import kong.unirest.core.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.SQLException;
import java.time.LocalDateTime;


public class UrlCheksController {
    public static void create(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();
        var createdAt = LocalDateTime.now();
        var url = UrlRepository.findToId(urlId)
                .orElseThrow(() -> new NotFoundResponse("Url with id " + urlId + " not found"));
        try {
            HttpResponse<String> response = Unirest.get(url.getName())
                    .header("Accept", "application/json")
                    .asString();
            var statusCode = response.getStatus();
            String html = response.getBody();
            Document doc = Jsoup.parse(html);

            var title = doc.title();
            var h1 = "";
            if (doc.selectFirst("h1") != null) {
                h1 = doc.selectFirst("h1").text();
            }
            var description = "";
            if (doc.selectFirst("meta[name=description]").attr("content") != null) {
                description = doc.selectFirst("meta[name=description]").attr("content");
            }
            var urlCheck = new UrlCheck(statusCode, title, h1, description, urlId, createdAt);
            UrlCheckRepository.save(urlCheck);
            ctx.sessionAttribute("addUrl", "Страница успешно проверена");
        } catch (ValidationException | UnirestException | SQLException e) {
            ctx.sessionAttribute("addUrl", "Ошибка проверки");
            throw new RuntimeException(e);
        } finally {
            // Завершение всех фоновых запросов Unirest
            Unirest.shutDown();
            ctx.redirect(NamedRoutes.urlsPaths(urlId));
        }
    }

}
