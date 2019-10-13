package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            .withLocale(Locale.UK);

    @Test
    public void shouldReturnCurrentTime() throws Exception {
        // this will fail sometimes
        String currentDT = LocalDateTime.now().format(formatter);

        this.mockMvc.perform(get("/api/v1/time/now")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(currentDT));
    }

    @Test
    public void shouldReturnCurrentTimeWOMock() throws Exception {
        // this will fail sometimes
        String response = getHTTPResponse("http://localhost:8080/api/v1/time/now");

        String currentDT = LocalDateTime.now().format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                        .withLocale(Locale.UK));
        assertThat(String.valueOf(response)).isEqualTo(currentDT);
    }

    @Test
    public void shouldInRange() throws Exception{
        String response = getHTTPResponse("http://localhost:8080/api/v1/time/now");

        LocalDateTime currentDT = LocalDateTime.now();
        LocalDateTime convertedDT = LocalDateTime.parse(String.valueOf(response), formatter);

        assertTrue(convertedDT.isAfter(currentDT.minusSeconds(1)));
        assertTrue(convertedDT.isBefore(currentDT.plusSeconds(1)));
    }

    private String getHTTPResponse(String Url) throws IOException {
        HttpURLConnection con = null;
        StringBuffer content = new StringBuffer();

        URL url = new URL(Url);
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return String.valueOf(content);
    }
}
