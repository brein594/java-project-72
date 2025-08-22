@import hexlet.code.dto.urls.UrlPage
@param UrlPage page

@template.layout.page(
    page = page,
    content=@`
    <table class = "table table-border table-hover mt-3">
        <caption>
            Сайт: ${page.getUrl().getName()}
        </caption>
        <thead>
            <tr>
                <th class="col-1">ID</th>
                <th class="col-1">Имя</th>
                <th class="col-1">Дата создания</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <th>${page.getUrl().getId()}</th>
                <th>${page.getUrl().getName()}</th>
                <th>${page.getUrl().getCreatedAt().toString()}</th>
            </tr>
        </tbody>
    </table>
    <h2>Проверки</h2>
    <form action="#" method="post">
    <button type="submit" class = "btn btn-primary">Запустить проверку</button>
    </form>
    <p><a href="/">Home</a></p>
`)