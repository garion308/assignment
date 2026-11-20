package com.example.weather.controller;

import java.util.Map;

import com.example.weather.service.UENCheckService;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/uen")
public class UENCheckController {

    private final UENCheckService checkService;

    public UENCheckController(UENCheckService checkService){
        this.checkService = checkService;
    }

    @GetMapping("/validate")
    public Map<String, Object> validateUen(
            @RequestParam String uen) {

        String type = checkService.identifyUenType(uen);

        return Map.of(
            "uen", uen,
            "validFormat", !type.equals("Invalid"),
            "entityType", type
        );
    }
}
