package ru.neoflex.order.load;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoadController {

    @GetMapping("/load")
    public String heavyLoad() {
        log.info("Heavy load STARTED at {}", System.currentTimeMillis());
        long sum = 0;
        for (long i = 0; i < 2_000_000_000L; i++) {
            sum += Math.sqrt(i);
        }
        log.info("Heavy load FINISHED at {}", System.currentTimeMillis());
        return "Sum: " + sum;
    }
}
