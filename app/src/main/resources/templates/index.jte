@import hexlet.code.dto.urls.BuildUrlPage
@param BuildUrlPage page

@template.layout.page(
content=@`
    @if(page.getErrors() != null)
        <ul>
            @for(var validator : page.getErrors().values())
                @for(var error : validator)
                    <li>${error.getMessage()}</li>
                @endfor
            @endfor
        </ul>
    @endif
        <h2>Hello World!</h2>
        <form action = "/urls" method = "post">
            <div><label>
                    Добавьте адрес
                    <input type="text" required name="name">
                </label></div>
            <button type="submit">Добавить</button>
        </form>
`)
