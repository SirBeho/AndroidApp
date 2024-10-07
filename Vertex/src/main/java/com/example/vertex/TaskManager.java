import io.vertx.core.json.JsonObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskManager {
    private final Map<String, TaskProgress> tasks = new ConcurrentHashMap<>();

    public void startTask(String taskId) {
        // Verificar si la tarea ya existe en memoria
        if (tasks.containsKey(taskId)) {
            System.out.println("Task " + taskId + " is already being processed.");
            return;
        }

        // Agregar la tarea en memoria con estado "inprogress"
        TaskProgress taskProgress = new TaskProgress("inprogress", 0);
        tasks.put(taskId, taskProgress);

        // Simular el progreso de la tarea
        long timerId = Vertx.currentContext().owner().setPeriodic(1000, id -> {
            taskProgress.progress += 10;

            // Actualizar el estado cuando la tarea se complete
            if (taskProgress.progress >= 100) {
                Vertx.currentContext().owner().cancelTimer(id);
                taskProgress.status = "completed";
                taskProgress.progress = 100;
                System.out.println("Task " + taskId + " completed.");
            }

            // Imprimir el progreso actual (solo para depuración)
            System.out.println("Task " + taskId + " progress: " + taskProgress.progress + "%");
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
