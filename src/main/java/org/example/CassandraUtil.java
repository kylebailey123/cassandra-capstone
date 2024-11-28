package org.example;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class CassandraUtil {

    private final Session session = CassandraConnection.getInstance().getSession();


    public UUID createNewUser(User newUser) {
        UUID newUserId = UUID.randomUUID();
        session.execute("insert into users (user_id, username, email) values (?,?,?)", newUserId, newUser.getUsername(), newUser.getEmail());
        return newUserId;
    }

    public UUID createNewTask(Task newTask) {
        UUID newTaskId = UUID.randomUUID();
        session.execute("insert into tasks (task_id, user_id, title, description, status, created_at) values (?,?,?,?,?,?)",
                newTaskId, newTask.getUserId(), newTask.getTitle(), newTask.getDescription(), Status.PENDING.getTaskStatus(),Date.from(Instant.now()));
        session.execute("insert into tasks_by_user_id (task_id, user_id, title, description, status, created_at) values (?,?,?,?,?,?)",
                newTaskId, newTask.getUserId(), newTask.getTitle(), newTask.getDescription(), Status.PENDING.getTaskStatus(),Date.from(Instant.now()));
        return newTaskId;
    }

    public List<Task> getAllTasksForAUser(UUID userId) {
        return session.execute("select * from tasks_by_user_id WHERE user_id = ?", userId).all()
                .stream()
                .map(row -> Task.builder()
                        .taskId(row.getUUID("task_id"))
                        .userId(row.getUUID("user_id"))
                        .title(row.getString("title"))
                        .description(row.getString("description"))
                        .status(row.getString("status"))
                        .createdAt(row.getTimestamp("created_at").toInstant())
                        .build()
                ).collect(Collectors.toList());
    }

    public Optional<Task> getTaskById(UUID taskId) {
        Row result = session.execute("select * from tasks WHERE task_id = ?", taskId).one();
        if (result == null) {
            log.info("Task with id {} not found", taskId);
            return Optional.empty();
        }
        return Optional.ofNullable(Task.builder()
                .taskId(result.getUUID("task_id"))
                .userId(result.getUUID("user_id"))
                .title(result.getString("title"))
                .description(result.getString("description"))
                .status(result.getString("status"))
                .createdAt(result.getTimestamp("created_at").toInstant())
                .build());
    }

    public void updateTaskStatus(UUID taskId,UUID userId, String status) {
        updateTaskByUserId(taskId, userId, status);
        updateTaskById(taskId, status);
        log.info("Task with id {} updated with new status: {}", taskId, status);
    }

    private void updateTaskById(UUID taskId, String status) {
        session.execute("update tasks set status = ? WHERE task_id = ?", status, taskId);
    }
    
    private void updateTaskByUserId(UUID taskId, UUID userId, String status) {
        session.execute("update tasks_by_user_id set status = ? WHERE user_id = ? AND task_id = ?", status, userId, taskId);
    }
    

    public void deleteUserTask(UUID taskId,UUID userId) {
        session.execute("delete from tasks WHERE task_id = ?", taskId);
        session.execute("delete from tasks_by_user_id WHERE user_id = ? AND task_id = ?", userId, taskId);
        log.info("Task with id {} deleted", taskId);
    }

}
