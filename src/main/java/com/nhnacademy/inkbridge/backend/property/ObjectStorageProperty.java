package com.nhnacademy.inkbridge.backend.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * class: ObjectStorageProperty.
 *
 * @author jeongbyeonghun
 * @version 3/12/24
 */

@Component
@ConfigurationProperties(prefix = "object-storage")
@Setter
@Getter
public class ObjectStorageProperty {


    String storageUrl;
    String containerName;
    String password;
    String username;
    String tenantId;
    String authUrl;


}
