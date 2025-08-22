package gg.jte.generated.ondemand.layout;
import gg.jte.Content;
import hexlet.code.dto.BasePage;
@SuppressWarnings("unchecked")
public final class JtepageGenerated {
	public static final String JTE_NAME = "layout/page.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,2,15,15,15,17,17,17,19,19,20,20,20,31,31,31,2,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Content content, BasePage page) {
		jteOutput.writeContent("\n<!doctype html>\n<html lang=\"eng\">\n<head>\n    <meta charset=\"utf-8\">\n    <title>Hello Hexlet</title>\n    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css\"\n          integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n</head>\n    <body>\n        <main>\n            ");
		if ( page != null && page.getFlash() != null ) {
			jteOutput.writeContent("\n                <div class=\"alert alert-success\" role=\"alert\">\n                    <p class=\"alert-heading\">");
			jteOutput.setContext("p", null);
			jteOutput.writeUserContent(page.getFlash());
			jteOutput.writeContent("</p>\n                </div>\n            ");
		}
		jteOutput.writeContent("\n            ");
		jteOutput.setContext("main", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\n        </main>\n        <footer class=\"footer border-top py-3 mt-5 bg-light\">\n            <div class=\"container-xl\">\n                <div class=\"text-center\">\n                    created by\n                    <a href=\"https://ru.hexlet.io\" target=\"_blank\">Hexlet</a>\n                </div>\n            </div>\n        </footer>\n    </body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Content content = (Content)params.get("content");
		BasePage page = (BasePage)params.getOrDefault("page", null);
		render(jteOutput, jteHtmlInterceptor, content, page);
	}
}
