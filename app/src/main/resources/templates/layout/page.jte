@import gg.jte.Content
@import hexlet.code.dto.BasePage
@param Content content
@param BasePage page = null

<!doctype html>
<html lang="eng">
<head>
    <meta charset="utf-8">
    <title>Hello Hexlet</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
    <body>
        <main>
            @if( page != null && page.getFlash() != null )
                <div class="alert alert-success" role="alert">
                    <p class="alert-heading">${page.getFlash()}</p>
                </div>
            @endif
            ${content}
        </main>
        <footer class="footer border-top py-3 mt-5 bg-light">
            <div class="container-xl">
                <div class="text-center">
                    created by
                    <a href="https://ru.hexlet.io" target="_blank">Hexlet</a>
                </div>
            </div>
        </footer>
    </body>
</html>