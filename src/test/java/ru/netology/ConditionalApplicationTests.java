package ru.netology;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConditionalApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    private static final GenericContainer<?> myAppDev = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    private static final GenericContainer<?> myAppProd = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        myAppDev.start();
        myAppProd.start();
    }

    @Test
    void contextLoads() {
        ResponseEntity<String> devappEntity = restTemplate.getForEntity("http://localhost:" + myAppDev.getMappedPort(8080) + "/profile", String.class);
        ResponseEntity<String> prodappEntity = restTemplate.getForEntity("http://localhost:" + myAppProd.getMappedPort(8081) + "/profile", String.class);
        Assert.assertEquals("Current profile is dev", devappEntity.getBody());
        Assert.assertEquals("Current profile is production", prodappEntity.getBody());
        System.out.println(devappEntity.getBody());
        System.out.println(prodappEntity.getBody());
    }
}