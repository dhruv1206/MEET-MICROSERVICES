package com.meeting.MeetingSignalingService.config;
import com.meeting.MeetingSignalingService.signaling.SignalType;
import com.meeting.MeetingSignalingService.signaling.SignalingMessage;
import com.meeting.MeetingSignalingService.signaling.SignalingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;
    private final SignalingService signalingService;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");
        if (username != null) {
            log.info("user disconnected: {}", username);
            final String msg = username+ " left the meet!";
            // Remove the user from the room

            final SignalingMessage signalingMessage = SignalingMessage.builder()
                    .sender(username)
                    .roomId(roomId)
                    .signalType(SignalType.LEAVE)
                    .toastMessage(msg)
                    .chatMessage(msg).build();
            signalingService.removeUserFromRoom(signalingMessage, headerAccessor);
        }
    }

}
