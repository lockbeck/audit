package com.lockbeck.config.web_socket;

import com.google.gson.Gson;
import com.lockbeck.entities.audit_log.AuditLog;
import com.lockbeck.utils.LocalDateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final WebSocketSessionManager sessionManager;
    private final LocalDateFormatter localDateFormatter;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionManager.addSession(session);
        System.out.println("New WebSocket connection established.");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessionManager.removeSession(session);
        System.out.println("WebSocket connection closed.");
    }

    public void sendAuditLogToClients(String user, String action) {
        AuditLog auditLog = new AuditLog(user,action, localDateFormatter.getStringDateTime(LocalDateTime.now()));
        Gson gson = new Gson();
        String json = gson.toJson(auditLog);
        try {
            sessionManager.sendMessageToAll(json);
        }catch (Exception e){
            e.printStackTrace();
        }
        /*String message = String.format("User: %s, Action: %s, Date: %s", user, action, java.time.LocalDateTime.now());
        sessionManager.sendMessageToAll(message);*/
    }
}
