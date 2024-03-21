package com.meeting.MeetingSignalingService.signaling;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SignalingController {

    final private SignalingService signalingService;

    @MessageMapping("/sendSignal")
    @SendTo("/topic/room/roomId")
    public SignalingMessage handleSignalingMessage(SignalingMessage message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        System.out.println(message);
        if (SignalType.JOIN.equals(message.getSignalType())) {
            signalingService.addUserToRoom(message, headerAccessor);
        } else if (SignalType.LEAVE.equals(message.getSignalType())) {
            signalingService.removeUserFromRoom(message, headerAccessor);
        } else if (SignalType.CHAT.equals(message.getSignalType())) {
            signalingService.broadcastChatMessage(message, headerAccessor);
        } else if (SignalType.MIC.equals(message.getSignalType())) {
            signalingService.broadcastMicStatusUpdate(message, headerAccessor);
        } else if (SignalType.VIDEO.equals(message.getSignalType())) {
            signalingService.broadcastVideoStatusUpdate(message, headerAccessor);
        } else if (SignalType.HAND_RAISE.equals(message.getSignalType())) {
            signalingService.broadcastHandRaise(message, headerAccessor);
        }
        return message;
    }
}
