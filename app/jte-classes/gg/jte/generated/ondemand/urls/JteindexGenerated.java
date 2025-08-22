package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.urls.UrlsPage;
@SuppressWarnings("unchecked")
public final class JteindexGenerated {
	public static final String JTE_NAME = "urls/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,1,3,3,5,5,6,6,22,22,22,23,23,23,23,23,23,23,30,30,31,31,31,32,32,32,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    ");
				for (var url : page.getUrls()) {
					jteOutput.writeContent("\n        <div>\n            <table class = \"table table-border table-hover mt-3\">\n                <caption>\n                    Сайты\n                </caption>\n                <thead>\n                    <tr>\n                        <th class=\"col-1\">ID</th>\n                        <th class=\"col-1\">Имя</th>\n                        <th class=\"col-1\">Последняя проверка</th>\n                        <th class=\"col-1\">Код ответа</th>\n                    </tr>\n                </thead>\n                <tbody>\n                    <tr>\n                        <th>");
					jteOutput.setContext("th", null);
					jteOutput.writeUserContent(url.getId());
					jteOutput.writeContent("</th>\n                        <th><a href = \"/urls/");
					jteOutput.setContext("a", "href");
					jteOutput.writeUserContent(url.getId());
					jteOutput.setContext("a", null);
					jteOutput.writeContent("\">");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(url.getName());
					jteOutput.writeContent("</a></th>\n                        <th> </th>\n                        <th> </th>\n                    </tr>\n                </tbody>\n            </table>\n        </div>\n    ");
				}
				jteOutput.writeContent("\n");
			}
		}, page);
		jteOutput.writeContent("\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage page = (UrlsPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
