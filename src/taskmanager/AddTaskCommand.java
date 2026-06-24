package taskmanager;

import java.io.IOException;
import java.util.List;

public class AddTaskCommand {
    public static void handle(String title) {
        String trimmedTitle = title.trim();
        if (trimmedTitle.isEmpty()) {
            System.out.println("Error: Task title cannot be empty.");
            return;
        }

        try {
            List<Task> tasks = TaskStorage.loadTasks();
            int nextId = tasks.isEmpty() ? 1 : tasks.get(tasks.size() - 1).getId() + 1;
            tasks.add(new Task(nextId, trimmedTitle));
            TaskStorage.saveTasks(tasks);
            System.out.println("Task added: [" + nextId + "] " + trimmedTitle);
        } catch (IOException e) {
            System.out.println("Error: Could not save task.");
        }
    }
}
