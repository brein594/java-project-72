package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Url {
    @Setter
    private Long id;
    private String name;
    private Long createdAt;

    public Url(String name, Long createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }
}
