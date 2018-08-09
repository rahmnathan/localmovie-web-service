package com.github.rahmnathan.localmovie.web.control;

import com.github.rahmnathan.localmovie.domain.AndroidPushClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PushNotificationHandler {
    private final Logger logger = LoggerFactory.getLogger(PushNotificationHandler.class);
    private final AndroidPushTokenRepository pushTokenRepository;

    public PushNotificationHandler(AndroidPushTokenRepository pushTokenRepository) {
        this.pushTokenRepository = pushTokenRepository;
    }

    public void addPushToken(AndroidPushClient pushClient) {
        Optional<AndroidPushClient> optionalPushClient = pushTokenRepository.findById(pushClient.getDeviceId());
        if (optionalPushClient.isPresent()) {
            AndroidPushClient managedPushClient = optionalPushClient.get();
            if (!managedPushClient.getPushToken().equals(pushClient.getPushToken())) {
                managedPushClient.setPushToken(pushClient.getPushToken());
            }
        } else {
            pushTokenRepository.save(pushClient);
        }
    }
}
