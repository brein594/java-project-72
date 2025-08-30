@import hexlet.code.dto.urls.UrlPage
@import java.time.format.DateTimeFormatter


@param UrlPage page


@template.layout.page(
    page = page,
    content=@`
       <div class="container-lg mt-5">
        <h1>Сайт: ${page.getUrl().getName()}</h1>
    <table class = "table table-border table-hover mt-3">
        <tbody>
        <tr>
            <th>ID</th>
            <th>${page.getUrl().getId()}</th>
        </tr>
            <tr>

                <th>Имя</th>
                <th>${page.getUrl().getName()}</th>
            </tr>
        <tr>
            <th>Дата создания</th>
            <th>${page.getUrl().getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}</th>
        </tr>
        </tbody>
    </table>
    </div>
    <h2>Проверки</h2>
    <form action="/urls/${page.getUrl().getId()}/checks" method="post">
    <button type="submit" class = "btn btn-primary">Запустить проверку</button>
    </form>
    <table class="table table-bordered table-hover mt-3">
        <thead>
        <tr><th class="col-1">ID</th>
            <th class="col-1">Код ответа</th>
            <th>title</th>
            <th>h1</th>
            <th>description</th>
            <th class="col-2">Дата проверки</th>
        </tr></thead>
        <tbody>
        @for (var pageCheck : page.getUrlCheck() )
        <tr>
            <td>${pageCheck.getId()}</td>
            <td>${pageCheck.getStatusCode()}</td>
            <td>${pageCheck.getTitle()}</td>
            <td>${pageCheck.getH1()}</td>
            <td>${pageCheck.getDescription()}</td>
            <td>${pageCheck.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}</td>
        </tr>
        @endfor
        </tbody>
    </table>
    <p><a href="/">Home</a></p>
`)