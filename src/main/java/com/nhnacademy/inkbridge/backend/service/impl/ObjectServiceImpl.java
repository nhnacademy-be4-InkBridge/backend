package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.service.AuthService;
import com.nhnacademy.inkbridge.backend.service.ObjectService;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: ObjectService.
 *
 * @author jeongbyeonghun
 * @version 2/27/24
 */

@Service
@NoArgsConstructor
public class ObjectServiceImpl implements ObjectService {

    private String tokenId;
    String storageUrl = "https://kr1-api-object-storage.nhncloudservice.com/v1/AUTH_e805e9a72d2f47338a0a463196c36314";
    String containerName = "inkbridge";

    AuthService authService;

    @Override
    public ResponseEntity<byte[]> downloadObject(String objectName) {
        setTokenId();
        RestTemplate restTemplate = new RestTemplate();

        String url = this.getUrl(objectName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", tokenId);
        headers.setAccept(List.of(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(null, headers);
        return restTemplate.exchange(url, HttpMethod.GET, requestHttpEntity, byte[].class);
    }

    @Override
    public String uploadObject(MultipartFile multipartFile) throws IOException {
        setTokenId();
        String fileName = LocalDateTime.now() + multipartFile.getOriginalFilename();
        InputStream inputStream = multipartFile.getInputStream();
        String url = this.getUrl(fileName);
        final RequestCallback requestCallback = new RequestCallback() {
            public void doWithRequest(final ClientHttpRequest request) throws IOException {
                request.getHeaders().add("X-Auth-Token", tokenId);
                IOUtils.copy(inputStream, request.getBody());
            }
        };

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpMessageConverterExtractor<String> responseExtractor
            = new HttpMessageConverterExtractor<>(String.class,
            restTemplate.getMessageConverters());
        restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor);

        return fileName;
    }

    /** 새토큰을 가져와 세팅하는 메소드.
     *
     */
    void setTokenId() {
        tokenId = authService.requestToken();
    }

    private String getUrl(String objectName) {
        return storageUrl + "/" + containerName + "/" + objectName;
    }
}
