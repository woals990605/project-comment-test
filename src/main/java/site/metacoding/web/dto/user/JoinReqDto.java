package site.metacoding.web.dto.user;

import javax.validation.constraints.Email;
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
public class JoinReqDto {

    @NotBlank
    private String name;

    @Pattern(regexp = "[a-zA-Z0-9]{4,20}", message = "유저네임은 한글이 들어갈 수 없습니다.")
    @Size(min = 4, max = 20)
    @NotBlank
    private String id;

    @Size(min = 4, max = 20)
    @NotBlank
    private String password;

    @Size(min = 8, max = 60)
    @NotBlank // @NotNull, @NotEmpty 두개의 조합
    @Email
    private String email;

    public User toEntity() {
        User user = new User();
        user.setName(name);
        user.setId(id);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }
}
