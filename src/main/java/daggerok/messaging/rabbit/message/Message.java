package daggerok.messaging.rabbit.message;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class Message {
    @NonNull String content;
    LocalDateTime createdAt = LocalDateTime.now();
}