package com.sg.relief.domain.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.FirebaseApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class FCMInitilizer {
    @Value("${app.firebase-configuration-file}")
    private String pathToFirebaseConfig;
    Logger logger = LoggerFactory.getLogger(FCMInitilizer.class);

    // initialize
    @PostConstruct
    public void initialize() {
        try {
            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(pathToFirebaseConfig).getInputStream()))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(firebaseOptions);
                logger.info("Firebase application has been initialized");
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
