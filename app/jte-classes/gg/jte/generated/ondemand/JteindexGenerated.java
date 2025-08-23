package gg.jte.generated.ondemand;
import hexlet.code.dto.urls.BuildUrlPage;
@SuppressWarnings("unchecked")
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,1,3,3,4,4,5,5,8,8,9,9,10,10,10,11,11,12,12,15,15,40,49,49,49,50,50,50,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, BuildUrlPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    ");
				if (page.getErrors() != null) {
					jteOutput.writeContent("\n        <div class=\"rounded-0 m-0 alert alert-dismissible fade show alert-danger\" role=\"alert\">\n        <ul>\n            ");
					for (var validator : page.getErrors().values()) {
						jteOutput.writeContent("\n                ");
						for (var error : validator) {
							jteOutput.writeContent("\n                    <li><p class=\"alert-heading\">");
							jteOutput.setContext("p", null);
							jteOutput.writeUserContent(error.getMessage());
							jteOutput.writeContent("</p></li>\n                ");
						}
						jteOutput.writeContent("\n            ");
					}
					jteOutput.writeContent("\n        </ul>\n        </div>\n    ");
				}
				jteOutput.writeContent("\n\n    <div class=\"container-fluid bg-dark p-5\">\n        <div class=\"row\">\n            <div class=\"col-md-10 col-lg-8 mx-auto text-white\">\n                <h1 class=\"display-3 mb-0\">Анализатор страниц</h1>\n                <p class=\"lead\">Бесплатно проверяйте сайты на SEO пригодность</p>\n                <form action=\"/urls\" method=\"post\" class=\"rss-form text-body\">\n                    <div class=\"row\">\n                        <div class=\"col\">\n                            <div class=\"form-floating\">\n                                <input id=\"url-input\" autofocus=\"\" type=\"url\" required=\"\" name=\"name\" aria-label=\"url\" class=\"form-control w-100\" placeholder=\"ссылка\" autocomplete=\"off\">\n                                <label for=\"url-input\">Ссылка</label>\n                            </div>\n                        </div>\n                        <div class=\"col-auto\">\n                            <button type=\"submit\" class=\"h-100 btn btn-lg btn-primary px-sm-5\">Проверить</button>\n                        </div>\n                    </div>\n                </form>\n                <p class=\"mt-2 mb-0 text-muted\">Пример: https://www.example.com</p>\n            </div>\n        </div>\n    </div>\n\n     ");
				jteOutput.writeContent("\n\n");
			}
		}, null);
		jteOutput.writeContent("\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		BuildUrlPage page = (BuildUrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
