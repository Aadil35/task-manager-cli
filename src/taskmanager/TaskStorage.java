package taskmanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskStorage {
    private static final Path TASKS_FILE = Path.of("tasks.json");
    private static final Pattern TASK_PATTERN =
            Pattern.compile("\\{\\s*\"id\"\\s*:\\s*(\\d+)\\s*,\\s*\"title\"\\s*:\\s*\"(.*?)\"\\s*\\}");

    public static List<Task> loadTasks() throws IOException {
        if (!Files.exists(TASKS_FILE)) {
            return new ArrayList<>();
        }

        String content = Files.readString(TASKS_FILE).trim();
        List<Task> tasks = new ArrayList<>();
        if (content.isEmpty() || content.equals("[]")) {
            return tasks;
        }

        Matcher matcher = TASK_PATTERN.matcher(content);
        while (matcher.find()) {
            int id = Integer.parseInt(matcher.group(1));
            String title = unescape(matcher.group(2));
            tasks.add(new Task(id, title));
        }
        return tasks;
    }

    public static void saveTasks(List<Task> tasks) throws IOException {
        StringBuilder builder = new StringBuilder("[\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            builder.append("  {\"id\": ")
                    .append(task.getId())
                    .append(", \"title\": \"")
                    .append(escape(task.getTitle()))
                    .append("\"}");
            if (i < tasks.size() - 1) {
                builder.append(",");
            }
            builder.append("\n");
        }
        builder.append("]\n");
        Files.writeString(TASKS_FILE, builder.toString());
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String unescape(String value) {
        return value.replace("\\\"", "\"").replace("\\\\", "\\");
    }
}
