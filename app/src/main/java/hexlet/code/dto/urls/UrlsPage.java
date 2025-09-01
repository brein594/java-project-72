package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;

import hexlet.code.model.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UrlsPage extends BasePage {
    private Map<String, UrlCheck> lastChecks;
}
