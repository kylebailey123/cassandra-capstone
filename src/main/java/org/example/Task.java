package org.example;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Builder
public class Task {
    private UUID taskId;
    private UUID userId;
    private String title;
    private String description;
    private String status;
    private Instant createdAt;

    @Override
    public String toString() {
        return "Task{" +
                "\n\t taskId=" + taskId + "," +
                "\n\t userId=" + userId + "," +
                "\n\t description='" + description + "'," +
                "\n\t title='" + title + "'," +
                "\n\t status='" + status + "'," +
                "\n\t createdAt=" + createdAt +
                "\n}";
    }
}
