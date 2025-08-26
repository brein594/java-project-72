package hexlet.code.controller;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;

import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlCheksController {
    public static void create(Context ctx) throws SQLException {

        var url_id = ctx.pathParamAsClass("id", Long.class).get();
        var createdAt = LocalDateTime.now();
        var url = UrlRepository.find(url_id)
                .orElseThrow(() -> new NotFoundResponse("Url with id " + url_id + " not found"));
        try {

            HttpResponse<String> response = Unirest.get(url.getName())
                    .header("Accept", "application/json")
                    .asString();
            var status_code = response.getStatus();
            String html = response.getBody();
            Document doc = Jsoup.parse(html);

            var title = doc.title();
            var h1 = "";
            if (doc.selectFirst("h1") != null) {
                h1 = doc.selectFirst("h1").text();
            }
            var description = "";
            if (doc.selectFirst("description") != null) {
                description = doc.selectFirst("h1").text();
            }
            Unirest.shutDown();
            var urlCheck = new UrlCheck(status_code, title, h1, description, url_id, createdAt);
            UrlCheckRepository.save(urlCheck);
            ctx.sessionAttribute("addUrl", "Страница успешно проверена");
           /*
            var page = new UrlPage(UrlRepository.getUrls().get((int) (url_id - 1L)),
                    UrlCheckRepository.getUrlChecks(url_id));
            ctx.render("urls/show.jte", model("page", page));

            */
            ctx.redirect(NamedRoutes.urlsPaths(url_id));
        } catch (ValidationException e) {
            ctx.sessionAttribute("addUrl", "Ошибка проверки");
            ctx.redirect(NamedRoutes.urlsPaths(url_id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
