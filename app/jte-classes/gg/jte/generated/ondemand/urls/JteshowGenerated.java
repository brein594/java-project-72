package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.urls.UrlPage;
import java.time.format.DateTimeFormatter;
@SuppressWarnings("unchecked")
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,2,4,4,6,6,8,8,8,13,13,13,18,18,18,22,22,22,28,28,28,28,44,44,44,44,44,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <div class=\"container-lg mt-5\">\n        <h1>Сайт: ");
				jteOutput.setContext("h1", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</h1>\n    <table class = \"table table-border table-hover mt-3\">\n        <tbody>\n        <tr>\n            <th>ID</th>\n            <th>");
				jteOutput.setContext("th", null);
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.writeContent("</th>\n        </tr>\n            <tr>\n\n                <th>Имя</th>\n                <th>");
				jteOutput.setContext("th", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</th>\n            </tr>\n        <tr>\n            <th>Дата создания</th>\n            <th>");
				jteOutput.setContext("th", null);
				jteOutput.writeUserContent(page.getUrl().getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				jteOutput.writeContent("</th>\n        </tr>\n        </tbody>\n    </table>\n    </div>\n    <h2>Проверки</h2>\n    <form action=\"/urls/");
				jteOutput.setContext("form", "action");
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.setContext("form", null);
				jteOutput.writeContent("/checks\" method=\"post\">\n    <button type=\"submit\" class = \"btn btn-primary\">Запустить проверку</button>\n    </form>\n    <table class=\"table table-bordered table-hover mt-3\">\n        <thead>\n        <tr><th class=\"col-1\">ID</th>\n            <th class=\"col-1\">Код ответа</th>\n            <th>title</th>\n            <th>h1</th>\n            <th>description</th>\n            <th class=\"col-2\">Дата проверки</th>\n        </tr></thead>\n        <tbody>\n        </tbody>\n    </table>\n    <p><a href=\"/\">Home</a></p>\n");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlPage page = (UrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
