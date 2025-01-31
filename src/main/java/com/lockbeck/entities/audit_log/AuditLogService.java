package com.lockbeck.entities.audit_log;

import com.lockbeck.config.web_socket.WebSocketHandler;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {

    private final WebSocketHandler webSocketHandler;

    public AuditLogService(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    public void logUserAction(String user, String action) {
        // Call WebSocket handler to broadcast the audit log
        webSocketHandler.sendAuditLogToClients(user, action);
    }
}
