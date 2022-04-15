package site.metacoding.web.dto.user;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.domain.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Data // Getter, Setter, toString
public class LoginReqDto {
    @Pattern(regexp = "[a-zA-Z0-9]{4,20}", message = "유저네임은 한글이 들어갈 수 없습니다.")
    @Size(min = 4, max = 20)
    @NotBlank
    private String id;

    @Size(min = 4, max = 20)
    @NotBlank
    private String password;

    @Transient
    private String remember;

    public User toEntity() {
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        user.setRemember(remember);

        return user;
    }
}
