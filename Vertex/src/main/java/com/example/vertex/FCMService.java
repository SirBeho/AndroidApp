package com.example.vertex;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import java.io.FileInputStream;
import java.io.IOException;

public class FCMService {
    public static void main(String[] args) {
        try {
            // Ruta al archivo de la cuenta de servicio (JSON)
            FileInputStream serviceAccount = new FileInputStream("./appmovil-c76f0-firebase-adminsdk-lr832-4d1165673b.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);

            // Crear un mensaje y enviarlo
            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle("Tarea Completada")
                            .setBody("La tarea se ha completado correctamente")
                            .build())
                    .setTopic("tasks") 
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Mensaje enviado exitosamente: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
