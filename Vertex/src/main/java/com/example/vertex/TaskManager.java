import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

public class TaskManager {
    private final Map<String, TaskProgress> tasks = new ConcurrentHashMap<>();
    private final WebClient webClient;

    // Constructor que acepta una instancia de Vertx para inicializar WebClient
    public TaskManager(Vertx vertx) {
        this.webClient = WebClient.create(vertx);
    }

    public void startTask(String taskId) {
        if (tasks.containsKey(taskId)) {
            System.out.println("Task " + taskId + " is already being processed.");
            return;
        }

        TaskProgress taskProgress = new TaskProgress("inprogress", 0);
        tasks.put(taskId, taskProgress);

        long timerId = Vertx.currentContext().owner().setPeriodic(1000, id -> {
            taskProgress.progress += 10;

            if (taskProgress.progress >= 100) {
                Vertx.currentContext().owner().cancelTimer(id);
                taskProgress.status = "completed";
                taskProgress.progress = 100;
                System.out.println("Task " + taskId + " completed.");

                // Enviar notificación push al completar la tarea
                sendPushNotification(taskId, "Task Completed", "Task " + taskId + " has been completed.");
            }

            System.out.println("Task " + taskId + " progress: " + taskProgress.progress + "%");
        });
    }

    private void sendPushNotification(String taskId, String title, String message) {
        JsonObject notification = new JsonObject()
                .put("title", title)
                .put("body", message);

        JsonObject data = new JsonObject()
                .put("taskId", taskId);

        JsonObject payload = new JsonObject()
                .put("to", "/topics/tasks")  // Enviar a un tema o un token específico
                .put("notification", notification)
                .put("data", data);

        webClient.post(443, "fcm.googleapis.com", "/fcm/send")
                .putHeader("Authorization", "key=YOUR_SERVER_KEY")
                .putHeader("Content-Type", "application/json")
                .ssl(true)
                .sendJsonObject(payload, ar -> {
                    if (ar.succeeded()) {
                        System.out.println("Push notification sent: " + ar.result().bodyAsString());
                    } else {
                        System.err.println("Failed to send push notification: " + ar.cause().getMessage());
                    }
                });
    }

    public JsonObject getTaskStatus(String taskId) {
        if (tasks.containsKey(taskId)) {
            TaskProgress taskProgress = tasks.get(taskId);
            return new JsonObject()
                    .put("status", taskProgress.status)
                    .put("progress", taskProgress.progress);
        } else {
            return new JsonObject().put("error", "Task not found");
        }
    }

    private static class TaskProgress {
        String status;
        int progress;

        TaskProgress(String status, int progress) {
            this.status = status;
            this.progress = progress;
        }
    }
}
