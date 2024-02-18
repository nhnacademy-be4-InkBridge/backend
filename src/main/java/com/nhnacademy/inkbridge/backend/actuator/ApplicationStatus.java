package com.nhnacademy.inkbridge.backend.actuator;

import org.springframework.stereotype.Component;

/**
 * class: ApplicationStatus.
 *
 * @author jangjaehun
 * @version 2024/02/18
 */
@Component
public class ApplicationStatus {

    private boolean status = true;

    public void stopService() {
        this.status = false;
    }

    public boolean getStatus() {
        return status;
    }

}
