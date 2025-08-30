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
        var actual = NamedRoutes.urlsPaths(1L);
        assertThat(actual).isEqualTo("/urls/1");
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlPage() {
        JavalinTest.test(app, (server, client) -> {
            var inputUrl = "https://wwww.exemple.com";
            var url = new Url(inputUrl, LocalDateTime.now());
            UrlRepository.save(url);
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains(inputUrl);
        });
    }

    @Test
    public void testUrlNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(500);
        });
    }

    @Test
    public void testAddUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://wwww.exemple.com";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertNotNull(response.body());
            assertThat(response.body().string()).contains("https://wwww.exemple.com");
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
        //assertThat(baseUrl).isEqualTo("http://");
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=" + baseUrl;
            var response = client.post("/urls", requestBody);

            assertThat(client.post("/urls", requestBody).code()).isEqualTo(200);
            assertThat(UrlRepository.findToName(baseUrl)).isTrue();
            //var url = new Url(baseUrl, LocalDateTime.now());
            //UrlRepository.save(url);
            //var id = url.getId();
            var id = 1L;
            var responseCheck = client.post("/urls/" + id + "/checks");
            var check = UrlCheckRepository.getUrlChecks(id).getFirst();

            assertThat(check.getStatusCode()).isEqualTo(200);
            assertThat(check.getTitle()).isEqualTo("Title web server");
            assertThat(check.getH1()).isEqualTo("H1 head Web server");
            assertThat(check.getDescription()).isEqualTo("content web server");
            assertThat(responseCheck.body().string()).contains("H1 head Web server");
        });
        mockWebServer.close();
    }
/*
    @Test
    void testStore() {
        var path = Paths.get("./src/test/resources/indexWebServer.html").toAbsolutePath().normalize();
        var file = Files.readString(path).trim();
        MockWebServer mockWebServer = new MockWebServer();
        var builder = new MockResponse.Builder()
                .code(200)
                .body(file)
                .build();
        mockWebServer.enqueue(builder);
        mockWebServer.start();
        String url = mockWebServer.url("/").toString().replaceAll("/$", "");

        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=" + url;
            assertThat(client.post("/urls", requestBody).code()).isEqualTo(200);

            var actualUrl = TestUtils.getUrlByName(dataSource, url);
            assertThat(actualUrl).isNotNull();
            System.out.println("\n!!!!!");
            System.out.println(actualUrl);

            System.out.println("\n");
            assertThat(actualUrl.get("name").toString()).isEqualTo(url);

            client.post("/urls/" + actualUrl.get("id") + "/checks");

            assertThat(client.get("/urls/" + actualUrl.get("id")).code())
                    .isEqualTo(200);

            var actualCheck = TestUtils.getUrlCheck(dataSource, (long) actualUrl.get("id"));
            assertThat(actualCheck).isNotNull();
            assertThat(actualCheck.get("title")).isEqualTo("Title web server");
            assertThat(actualCheck.get("h1")).isEqualTo("H1 head Web server");
            assertThat(actualCheck.get("description")).isEqualTo("content web server");
        });
    }

 */
}
