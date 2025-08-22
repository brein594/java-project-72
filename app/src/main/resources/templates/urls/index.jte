@import hexlet.code.dto.urls.UrlsPage
@param UrlsPage page

@template.layout.page(
page = page,
content =@`
    @for (var url : page.getUrls())
        <div>
            <table class = "table table-border table-hover mt-3">
                <caption>
                    Сайты
                </caption>
                <thead>
                    <tr>
                        <th class="col-1">ID</th>
                        <th class="col-1">Имя</th>
                        <th class="col-1">Последняя проверка</th>
                        <th class="col-1">Код ответа</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>${url.getId()}</th>
                        <th><a href = "/urls/${url.getId()}">${url.getName()}</a></th>
                        <th> </th>
                        <th> </th>
                    </tr>
                </tbody>
            </table>
        </div>
    @endfor
`)
