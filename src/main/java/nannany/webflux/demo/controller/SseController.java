package nannany.webflux.demo.controller;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
public class SseController {

    @GetMapping("/stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(5))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("periodic-event")
                        .data(getDateJsonData())
                        .build());
    }

    @Data
    @AllArgsConstructor
    private class TimeData {
        private int year;
        private int month;
        private int day;
        private String time;
    }

    private String getDateJsonData() {
        Gson gson = new Gson();
        LocalDateTime ldt = LocalDateTime.now();
        TimeData td = new TimeData(ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), ldt.toLocalTime().toString());
        return gson.toJson(td);
    }
}

