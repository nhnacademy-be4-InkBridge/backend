package com.nhnacademy.inkbridge.backend.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * class: CustomHealthIndicator.
 *
 * @author jangjaehun
 * @version 2024/02/18
 */
@Component
@RequiredArgsConstructor
public class CustomHealthIndicator implements HealthIndicator {

    private final ApplicationStatus applicationStatus;

    @Override
    public Health health() {
        if (!applicationStatus.getStatus()) {
            return Health.down().build();
        }

        return Health.up().build();
    }
}
