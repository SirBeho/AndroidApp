package com.example.vertex;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

public class TaskManager {

    private final Map<String, TaskProgress> tasks = new ConcurrentHashMap<>();
    private final WebClient webClient;
    private DBUtils dbUtils;

    // Constructor que acepta una instancia de Vertx para inicializar WebClient
    public TaskManager(Vertx vertx, DBUtils dbUtils) {
        this.webClient = WebClient.create(vertx);
        this.dbUtils = dbUtils;
    }

    public void startTask(String taskId) {
        
        if (tasks.containsKey(taskId)) {
            System.out.println("Task " + taskId + " is already being processed.");
            return;
        }

        
        int incremento = (int) (Math.random() * 10) + 1;


        TaskProgress taskProgress = new TaskProgress("inprogress", 0, new Date(), null);
        tasks.put(taskId, taskProgress);

        System.out.print("\033[H\033[2J");
        System.out.flush();

        long timerId = Vertx.currentContext().owner().setPeriodic(500, id -> {
            
             System.out.print("\033[H");
            System.out.flush();
     
            for (Map.Entry<String, TaskProgress> entry : tasks.entrySet()) {
                String codigo = entry.getKey();
                TaskProgress progress = entry.getValue();
                Duration duration = Duration.between(progress.startTime.toInstant(), Instant.now());
                
                if (progress.progress >= 100) {
                    progress.progress = 100;
                }
    
                System.out.println("Tarea [" + codigo + "] [" +
                    "*".repeat(progress.progress / 5) + " ".repeat(20 - (progress.progress / 5)) + "] " +
                    progress.progress + "%" + " [" + duration.getSeconds() + " seg]" );
            }
          

            if (taskProgress.progress >= 100) {
               
                Vertx.currentContext().owner().cancelTimer(id);
                taskProgress.status = "completed";
                taskProgress.progress = 100;
                taskProgress.endTime = new Date();
               
                tasks.remove(taskId);
                
                PdfReportGenerator.generatePdf(dbUtils,taskId,taskProgress);
                
                System.out.print("\033[H\033[2J");
                System.out.flush();

               
                sendPushNotification(taskId, "Task Completed", "Task " + taskId + " has been completed.");
            }
           
            taskProgress.progress += incremento;
        });
    }

    public JsonObject getTaskStatus(String taskId) {
        if (tasks.containsKey(taskId)) {
            TaskProgress taskProgress = tasks.get(taskId);
            //todos los atributos de la clase
            return new JsonObject()
                    .put("status", taskProgress.status)
                    .put("progress", taskProgress.progress)
                    .put("startTime", taskProgress.startTime)
                    .put("endTime", taskProgress.endTime);
        } else {
            return new JsonObject().put("error", "Tarea {" + taskId + "} no se esta procesando.");
        }
    }

    public static class TaskProgress {
        String status;
        int progress;
        Date startTime;
        Date endTime;

        TaskProgress(String status, int progress, Date startTime, Date endTime) {
            this.status = status;
            this.progress = progress;
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
}
