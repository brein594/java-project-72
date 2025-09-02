package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
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
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AppTest {
    private Javalin app;

    @BeforeEach
    public final void setUp() throws SQLException {
        app = App.getApp();
        UrlRepository.removeAll();
    }

    @Test
    public void testUrlRepositoryFindToId() throws SQLException {
        var urlExpert = new Url("https://wwww.exemple.com", LocalDateTime.now());
        UrlRepository.save(urlExpert);
        var id = urlExpert.getId();
        var urlAction = UrlRepository.findToId(id).orElseThrow(() -> new NotFoundResponse("Test. url not Found"));
        assertThat(urlAction.getName()).isEqualTo(urlExpert.getName());
        assertThat(urlAction.getCreatedAt().toLocalDate()).isEqualTo(urlExpert.getCreatedAt().toLocalDate());
    }

    @Test
    public void testUrlRepositoryFindToIdEmpty() throws SQLException {
        var id = 1L;
        var urlAction = UrlRepository.findToId(id);
        assertThat(urlAction.isPresent()).isFalse();
    }

    @Test
    public void testUrlRepositoryFindToName() throws SQLException, URISyntaxException {
        var urlExpert = new Url("https://wwww.exemple.com", LocalDateTime.now());
        UrlRepository.save(urlExpert);
        assertThat(UrlRepository.findToName(urlExpert.getName())).isTrue();
        assertThat(UrlRepository.findToName("https://wwww.exemple.ru")).isFalse();
    }

    @Test
    public void testUrlRepositoryRemoveAll() throws SQLException, URISyntaxException {
        var urlExpert = new Url("https://wwww.exemple.com", LocalDateTime.now());
        UrlRepository.save(urlExpert);
        UrlRepository.removeAll();
        assertThat(UrlRepository.findToName(urlExpert.getName())).isFalse();
    }

    @Test
    public void testUrlCheckRepositorySaveAndGetLastUrlChecks() throws SQLException {
        var url = new Url("https://wwww.exemple.com", LocalDateTime.now());
        UrlRepository.save(url);
        var dateTimeEarly = LocalDateTime.now();
        var urlCheckEarly = new UrlCheck(200, "title", "h1", "description",
                url.getId(), dateTimeEarly);
        UrlCheckRepository.save(urlCheckEarly);
        var dateTimeLast = LocalDateTime.now();
        var urlCheckLast = new UrlCheck(200, "title", "h1", "description",
                url.getId(), dateTimeLast);
        UrlCheckRepository.save(urlCheckLast);
        var urlCheckAction = UrlCheckRepository.getLastUrlChecks(url.getId())
                .orElseThrow(() -> new NotFoundResponse("Test. urlCheck not Found"));
        assertThat(urlCheckAction.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .isEqualTo(dateTimeLast.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }


    @Test
    public void testNamedRoutesUrlsPathsLong() {
        var actual = NamedRoutes.urlPath(1L);
        assertThat(actual).isEqualTo("/urls/1");
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
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlsPath());
            assertThat(response.code()).isEqualTo(200);
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
    public void testAddUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://wwww.exemple.com";
            var response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertNotNull(response.body());
            assertThat(response.body().string()).contains("https://wwww.exemple.com");
        });
    }

    @Test
    public void testAddUrlError() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=htt://www.example.com";
            var response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertNotNull(response.body());
            assertThat(response.body().string()).contains("Некорректный URL");
        });
    }

    @Test
    public void testUrlCheks() throws IOException {
        var path = Paths.get("./src/test/resources/indexWebServer.html").toAbsolutePath().normalize();
        var file = Files.readString(path).trim();
        MockWebServer mockWebServer = new MockWebServer();
        var builder = new MockResponse.Builder()
                .code(200)
                .body(file)
                .build();
        mockWebServer.enqueue(builder);
        mockWebServer.start();
        String baseUrl = mockWebServer.url("/").toString().replaceAll("/$", "");
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=" + baseUrl;
            var response1 = client.post(NamedRoutes.urlsPath(), requestBody);

            assertThat(client.post(NamedRoutes.urlsPath(), requestBody).code()).isEqualTo(200);
            assertThat(UrlRepository.findToName(baseUrl)).isTrue();
            var id = UrlRepository.getUrls().getFirst().getId();
            var responseCheck = client.post(NamedRoutes.checkPath(id));
            var check = UrlCheckRepository.getUrlChecks(id).getFirst();

            assertThat(check.getStatusCode()).isEqualTo(200);
            assertThat(check.getTitle()).isEqualTo("Title web server");
            assertThat(check.getH1()).isEqualTo("H1 head Web server");
            assertThat(check.getDescription()).isEqualTo("content web server");
            assertThat(responseCheck.body().string()).contains("H1 head Web server");
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
