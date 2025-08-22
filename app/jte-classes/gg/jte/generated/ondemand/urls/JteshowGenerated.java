package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.urls.UrlPage;
@SuppressWarnings("unchecked")
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,1,3,3,5,5,8,8,8,19,19,19,20,20,20,21,21,21,30,30,30,30,30,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <table class = \"table table-border table-hover mt-3\">\n        <caption>\n            Сайт: ");
				jteOutput.setContext("caption", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("\n        </caption>\n        <thead>\n            <tr>\n                <th class=\"col-1\">ID</th>\n                <th class=\"col-1\">Имя</th>\n                <th class=\"col-1\">Дата создания</th>\n            </tr>\n        </thead>\n        <tbody>\n            <tr>\n                <th>");
				jteOutput.setContext("th", null);
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.writeContent("</th>\n                <th>");
				jteOutput.setContext("th", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</th>\n                <th>");
				jteOutput.setContext("th", null);
				jteOutput.writeUserContent(page.getUrl().getCreatedAt().toString());
				jteOutput.writeContent("</th>\n            </tr>\n        </tbody>\n    </table>\n    <h2>Проверки</h2>\n    <form action=\"#\" method=\"post\">\n    <button type=\"submit\" class = \"btn btn-primary\">Запустить проверку</button>\n    </form>\n    <p><a href=\"/\">Home</a></p>\n");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlPage page = (UrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
