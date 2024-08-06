package com.lockbeck.entities.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCreateDTO {
    @NotBlank(message = "F.I.Sh kiritilmagan")
    private String name;
    @Email(message = "email yaroqsiz")
    private String email;
    @NotBlank(message = "username kiritilmagan")
    private String username;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$!%*?&.,_:;])[A-Za-z\\d@#$!%*?&.,_:;]{8,}$",
            message = "Parol kamida bitta bosh harf, bitta kichik harf, bitta raqam, bitta belgidan iborat bolishi " +
                    "va umumiy 8ta belgidan kam bo'lmasligi lozim")

    private String password;
    private String confirmPsw;
    private Role role;


    //    @NotBlank(message = "subjectId  required mal")
}
