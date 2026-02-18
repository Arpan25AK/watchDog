package com.example.watchDog.ServiceTest;

import com.example.watchDog.Entity.Websites;
import com.example.watchDog.Repository.Repo;
import com.example.watchDog.Services.WebsiteCheckService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class websiteCheckServiceTest {
    @Mock
    private Repo websiteRepo;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WebsiteCheckService websiteCheckServiceTest;

    @Test
    public void test_WebsiteCheckService_AllSuccess(){
        Websites mocksite = new Websites();
        mocksite.setId(1);
        mocksite.setUrl("https://www.test.com");

        when(websiteRepo.findActive()).thenReturn(List.of(mocksite));

        ResponseEntity<Websites> response = new ResponseEntity<>(new Websites(), HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(Websites.class))).thenReturn(response);

        websiteCheckServiceTest.checkAllWebsites();

        verify(websiteRepo, times(1)).healthCheckLog(eq(1), eq(200), anyInt());
    }
}
