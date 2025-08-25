package hexlet.code.controller;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;

import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlCheksController {
    public static void create(Context ctx) throws SQLException {
        var status_code = 404;
        var title = "title ";
        var h1 = "h1";
        var description = "description";
        var url_id = ctx.pathParamAsClass("id", Long.class).get();
        var createdAt = LocalDateTime.now();
        try {
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
            /*
            var page = new UrlPage(UrlRepository.getUrls().get((int) (url_id - 1L)),
                    UrlCheckRepository.getUrlChecks(url_id));
            ctx.render("urls/show.jte", model("page", page));
*/
            ctx.redirect(NamedRoutes.urlsPaths(url_id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
