package com.lockbeck.entities.user;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class  ChangePasswordRequest {

    private String currentPsw;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$!%*?&.,_:;])[A-Za-z\\d@#$!%*?&.,_:;]{8,}$",
            message = "Parol kamida bitta bosh harf, bitta kichik harf, bitta raqam, bitta belgidan iborat bolishi " +
                    "va umumiy 8ta belgidan kam bo'lmasligi lozim")

    private String newPsw;
    private String confirmPsw;
}
