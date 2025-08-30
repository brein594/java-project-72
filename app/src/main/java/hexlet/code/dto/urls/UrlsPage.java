package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UrlsPage extends BasePage {
    private List<Url> urls;
    private List<String> listCreateAt;
    private List<Integer> listCodeStatus;
}
