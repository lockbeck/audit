package com.lockbeck.entities.audit_log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AuditLog {
    private String username;
    private String action;
    private String date;
}
