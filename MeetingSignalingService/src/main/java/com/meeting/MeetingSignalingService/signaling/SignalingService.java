package com.meeting.MeetingSignalingService.signaling;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
@Singleton
@RequiredArgsConstructor
public class SignalingService {

    private final ConcurrentHashMap<String, Set<String>> rooms = new ConcurrentHashMap<>();

    final private SimpMessagingTemplate messagingTemplate;

    public void addUserToRoom(SignalingMessage message, SimpMessageHeaderAccessor headerAccessor) {
        // if already 2 users in the room, reject the request
        System.out.println("ROOMS: " + rooms);
        if (rooms.computeIfAbsent(message.getRoomId(), k -> new ConcurrentSkipListSet<>()).size() >= 2) {
            System.out.println("ROOM FULL");
            return;
        }
        System.out.println(message.getSender() +" JOINED THE ROOM " + message.getRoomId());
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", message.getSender());
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("roomId", message.getRoomId());
        rooms.computeIfAbsent(message.getRoomId(), k -> new ConcurrentSkipListSet<>()).add(headerAccessor.getSessionId());
        messagingTemplate.convertAndSend("/topic/room/"+message.getRoomId(), message);
    }


    public void removeUserFromRoom(SignalingMessage message, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println(message.getSender() +" LEFT THE ROOM " + message.getRoomId());
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).remove("username", message.getSender());
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).remove("roomId", message.getRoomId());
        rooms.computeIfAbsent(message.getRoomId(), k -> new ConcurrentSkipListSet<>()).remove(headerAccessor.getSessionId());
        messagingTemplate.convertAndSend("/topic/room/"+message.getRoomId(), message);
    }

    public void broadcastChatMessage(SignalingMessage message, SimpMessageHeaderAccessor headerAccessor) {

        messagingTemplate.convertAndSend("/topic/room/" + message.getRoomId(), message);
    }

    public void broadcastMicStatusUpdate(SignalingMessage message, SimpMessageHeaderAccessor headerAccessor) {
        // Add sender information to the message if needed
        messagingTemplate.convertAndSend("/topic/room/" + message.getRoomId(), message);
    }

    public void broadcastVideoStatusUpdate(SignalingMessage message, SimpMessageHeaderAccessor headerAccessor) {
        // Add sender information to the message if needed
        messagingTemplate.convertAndSend("/topic/room/" + message.getRoomId(), message);
    }

    public void broadcastHandRaise(SignalingMessage message, SimpMessageHeaderAccessor headerAccessor) {
        // Add sender information to the message if needed
        messagingTemplate.convertAndSend("/topic/room/" + message.getRoomId(), message);
    }
}