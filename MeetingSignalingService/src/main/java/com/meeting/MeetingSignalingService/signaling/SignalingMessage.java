package com.meeting.MeetingSignalingService.signaling;

import com.fasterxml.jackson.databind.annotation.EnumNaming;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignalingMessage {

    private String roomId;
    private SignalType signalType;  // "chat", "mic", "video", "hand-raise", etc.
    private String sender;
    @Nullable
    private boolean micStatus;  // On or off
    @Nullable
    private boolean videoStatus; // On or off
    @Nullable
    private boolean handRaised;
    @Nullable
    private String chatMessage; // For chat messages
    @Nullable
    private String toastMessage;
}

