package com.nr.stringville;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StringvilleController {

    @RequestMapping("/")
    public String index() {
        return "Welcome to Stringville!";
    }

    @PostMapping("/submission")
    public ResponseEntity submission(@RequestBody String body) {
        return ResponseEntity.status(200).body("accepted");
    }

    @GetMapping("/results")
    public ResponseEntity results() {
        return ResponseEntity.status(200).body("");
    }

    @GetMapping("/health")
    public ResponseEntity health() {
        return ResponseEntity.status(200).body("");
    }

    @GetMapping("/reset")
    public ResponseEntity reset() {
        return ResponseEntity.status(200).body("");
    }

}
