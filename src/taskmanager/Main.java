package taskmanager;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        String command = args[0];

        if ("add".equals(command)) {
            if (args.length < 2) {
                System.out.println("Error: Task title is required.");
                return;
            }
            AddTaskCommand.handle(String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length)));
        } else {
            System.out.println("Command '" + command + "' is not implemented yet.");
        }
    }

    private static void printUsage() {
        System.out.println("Task Manager CLI");
        System.out.println("Usage:");
        System.out.println("  java -cp out taskmanager.Main add <title>");
        System.out.println("  java -cp out taskmanager.Main show");
        System.out.println("  java -cp out taskmanager.Main delete <id>");
    }
}
