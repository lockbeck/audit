package com.lockbeck.entities.audit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditCreateRequest {
    private Integer subjectId;
    private Integer inLetterId;
}
