package com.nr.stringville;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;



import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StringvilleControllerTest {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void getHello() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        assertThat(response.getBody(), equalTo("Welcome to Stringville!"));
    }

    @Test
    public void testSubmission() throws Exception {
        ResponseEntity<String> response = template.postForEntity(base.toString()+"submission","aaaiibbbru,Ivaana Bello",String.class);
        assertThat(response.getBody(), equalTo("Ivaana Bello accepted"));
    }

    @Test
    public void testScore() throws Exception {
        template.postForEntity(base.toString()+"submission","aaaiibbbru,Ivaana Bello",String.class);
        template.postForEntity(base.toString()+"submission","eiiegiebeici,Camille Diaz",String.class);
        ResponseEntity<String> response = template.getForEntity(base.toString()+"results",String.class);
        assertThat(response.getBody(), equalTo("Camille Diaz,7\n" +
                "Ivaana Bello,4"));
    }

    @Test
    public void testReset() throws Exception {
        template.postForEntity(base.toString()+"submission","aaaiibbbru,Ivaana Bello",String.class);
        template.postForEntity(base.toString()+"submission","eiiegiebeici,Camille Diaz",String.class);
        ResponseEntity<String> response = template.getForEntity(base.toString()+"results",String.class);
        assertThat(response.getBody(), equalTo("Camille Diaz,7\n" +
                "Ivaana Bello,4"));
        ResponseEntity<String> response2 = template.getForEntity(base.toString()+"reset",String.class);
        assertThat(response2.getBody(), equalTo(null));
    }
}
