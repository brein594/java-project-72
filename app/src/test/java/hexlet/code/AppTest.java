package hexlet.code;

import hexlet.code.model.Url;

import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.testtools.JavalinTest;

import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AppTest {
    private static Javalin app;

    @BeforeEach
    public final void eraseBD() throws SQLException {
        app = App.getApp();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.rootPath());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testUrlsPageClear() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlsPath());
            assertThat(response.code()).isEqualTo(200);
            var resulTest = response.body().string();
            assertThat(resulTest).contains("Последняя проверка");
            assertThat(resulTest).contains("Код ответа");
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody1 = "url=https://wwww.exemple.com";
            client.post(NamedRoutes.urlsPath(), requestBody1);
            var requestBody2 = "url=https://ya.ru";
            var response = client.post(NamedRoutes.urlsPath(), requestBody2);
            //var response = client.get(NamedRoutes.urlsPath());
            assertThat(response.code()).isEqualTo(200);
            var resulTest = response.body().string();
            assertThat(resulTest).contains("wwww.exemple.com");
            assertThat(resulTest).contains("ya.ru");
        });
    }

    @Test
    public void testUrlPage() {
        JavalinTest.test(app, (server, client) -> {
            var inputUrl = "https://wwww.exemple.com";
            var url = new Url(inputUrl, LocalDateTime.now());
            UrlRepository.save(url);
            var response = client.get(NamedRoutes.urlPath(url.getId()));
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains(inputUrl);
        });
    }

    @Test
    public void testUrlNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlPath(999999L));
            assertThat(response.code()).isEqualTo(500);
        });
    }

    @Test
    public void testAddUrlAndClearBd() {
        JavalinTest.test(app, (server, client) -> {
            String url = "https://wwww.exemple.com";
            var requestBody = "url=" + url;
            var response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertNotNull(response.body());
            assertThat(response.body().string()).contains("https://wwww.exemple.com");
            assertThat(UrlRepository.findToName(url)).isTrue();

            UrlRepository.removeAll();
            assertThat(UrlRepository.findToName(url)).isFalse();
        });
    }

    @Test
    public void testAddUrlError() {
        JavalinTest.test(app, (server, client) -> {
            String url = "htt://wwww.exemple.com";
            var requestBody = "url=" + url;
            var response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertNotNull(response.body());
            assertThat(response.body().string()).contains("Некорректный URL");
        });
    }

    @Test
    //@Timeout(6)
    public void testUrlCheks() throws IOException {
        var path = Paths.get("./src/test/resources/indexWebServer.html").toAbsolutePath().normalize();
        var file = Files.readString(path).trim();
        MockWebServer mockWebServer = new MockWebServer();
        var builder = new MockResponse.Builder()
                .code(200)
                .body(file)
                .build();
        mockWebServer.enqueue(builder);
        mockWebServer.enqueue(builder);
        mockWebServer.enqueue(builder);
        mockWebServer.start();
        String baseUrl = mockWebServer.url("/").toString().replaceAll("/$", "");
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=" + baseUrl;
            var response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(UrlRepository.findToName(baseUrl)).isTrue();
            assertThat(response.body().string()).contains(baseUrl);
            var id = UrlRepository.getUrls().getFirst().getId();
            client.post(NamedRoutes.checkPath(id));
            client.post(NamedRoutes.checkPath(id));
            var responseCheck = client.post(NamedRoutes.checkPath(id));
            var check = UrlCheckRepository.getUrlChecks(id).get(2);
            var lastCheck = UrlCheckRepository.getLastUrlCheck(id).orElseThrow(() ->
                    new NotFoundResponse("lastCheck " + id + " not found"));
            String responseBody = responseCheck.body().string();
            assertThat(check.getStatusCode()).isEqualTo(200);
            assertThat(check.getTitle()).isEqualTo("Title web server");
            assertThat(check.getH1()).isEqualTo("H1 head Web server");
            assertThat(check.getDescription()).isEqualTo("content web server");
            assertThat(responseBody).contains("H1 head Web server");
            assertThat(responseBody).contains("3");
            assertThat(check.getCreatedAt()).isEqualTo(lastCheck.getCreatedAt());
            var responseIndexCheck = client.get(NamedRoutes.urlsPath());
            assertThat(responseIndexCheck.body().string())
                    .contains(lastCheck.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        });
        mockWebServer.close();
    }

    @Test
    public void testUrlCheksError() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        var builder = new MockResponse.Builder()
                .code(404)
                .body("404 Not Found")
                .build();
        mockWebServer.enqueue(builder);
        mockWebServer.start();
        String baseUrl = mockWebServer.url("/index.html").toString().replaceAll("/$", "");
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=" + baseUrl;
            var response1 = client.post(NamedRoutes.urlsPath(), requestBody);

            assertThat(client.post(NamedRoutes.urlsPath(), requestBody).code()).isEqualTo(200);
            assertThat(UrlRepository.findToName(baseUrl)).isTrue();
            var id = UrlRepository.getUrls().getFirst().getId();
            var responseCheck = client.post(NamedRoutes.checkPath(id));
            var check = UrlCheckRepository.getUrlChecks(id).getFirst();

            assertThat(check.getStatusCode()).isEqualTo(404);
            assertThat(responseCheck.body().string()).contains("404");
        });
        mockWebServer.close();
    }

}
