package site.metacoding.web.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data // Getter, Setter, toString
public class IdFindReqDto {

    @Size(min = 2, max = 30)
    @NotBlank
    private String name;

    @Size(min = 4, max = 30)
    @Email
    @NotBlank
    private String email;
}