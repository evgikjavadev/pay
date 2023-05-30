package ru.vtb.msa.rfrm.сontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.msa.rfrm.service.ServiceTest;

@RestController
@RequiredArgsConstructor
public class ControllerTest {

    private final ServiceTest serviceTest;


    @GetMapping("/hello")
    public String hello() {

        serviceTest.test();
        return "Hello!";
    }

}
