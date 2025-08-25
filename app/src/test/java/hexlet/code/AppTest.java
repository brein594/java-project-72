package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

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
            var url = new Url("https://wwww.exemple.com", LocalDateTime.now());
            UrlRepository.save(url);
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
        });
    }
    /*
    @Test
    public void testUrlPageException() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "name=www.exemple.com";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(500);
            assertThat(response.body().string()).contains("Некорректный URL");
        });
    }
    */

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
            var requestBody = "name=https://wwww.exemple.com";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertNotNull(response.body());
            assertThat(response.body().string()).contains("https://wwww.exemple.com");
        });
    }


}
