@import hexlet.code.dto.urls.UrlsPage
@param UrlsPage page

@template.layout.page(
page = page,
content =@`
    <div class="container-lg mt-5">
        <h1>Сайты</h1>
        <table class="table table-border table-hover mt-3">
            <thead>
            <tr>
                <th class="col-1">ID</th>
                <th>Имя</th>
                <th class="col-2">Последняя проверка</th>
                <th class="col-1">Код ответа</th>
            </tr>
            </thead>
            <tbody>
            @for (int i=0; i < page.getUrls().size(); ++i)
                <tr>
                    <td>${page.getUrls().get(i).getId()}</td>
                    <td><a href="/urls/${page.getUrls().get(i).getId()}">${page.getUrls().get(i).getName()}</a></td>
                    <td>${page.getListCreateAt().get(i)}</td>
                    <td>${page.getListCodeStatus().get(i)} </td>
                </tr>
            @endfor
            </tbody>
        </table>
    </div>
`)
