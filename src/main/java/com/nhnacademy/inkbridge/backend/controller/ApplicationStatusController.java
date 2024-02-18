package com.nhnacademy.inkbridge.backend.controller;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.nhnacademy.inkbridge.backend.actuator.ApplicationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: ApplicationStatusController.
 *
 * @author jangjaehun
 * @version 2024/02/18
 */
@RestController
@RequestMapping("/api/actuator/status")
@RequiredArgsConstructor
public class ApplicationStatusController {

    private final ApplicationInfoManager applicationInfoManager;
    private final ApplicationStatus applicationStatus;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void stopStatus() {
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.DOWN);
        applicationStatus.stopService();
    }

}
