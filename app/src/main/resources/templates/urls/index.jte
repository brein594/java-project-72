@import hexlet.code.dto.urls.UrlsPage
@import java.time.format.DateTimeFormatter
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
            @for (var lastChek : page.getLastChecks().entrySet())
                <tr>
                    <td>${lastChek.getValue().getUrlId()}</td>
                    <td><a href="/urls/${lastChek.getValue().getUrlId()}">${lastChek.getKey()}</a></td>
                    <td> @if (lastChek.getValue().getCreatedAt() != null)
                        <p>${lastChek.getValue().getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}</p>
                        @endif
                    </td>
                    <td> ${lastChek.getValue().getStatusCode()}</td>
                </tr>
            @endfor
            </tbody>
        </table>
    </div>
`)
