package com.lockbeck.entities.user;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  ChangePasswordRequest {

    private String currentPsw;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$!%*?&.,_:;])[A-Za-z\\d@#$!%*?&.,_:;]{8,}$",
            message = "Parol kamida bitta bosh harf, bitta kichik harf, bitta raqam, bitta belgidan iborat bolishi " +
                    "va umumiy 8ta belgidan kam bo'lmasligi lozim")


    private String newPsw;
    private String confirmPsw;
}
