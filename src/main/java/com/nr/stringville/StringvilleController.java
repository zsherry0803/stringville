package com.nr.stringville;

import com.nr.stringville.dto.User;
import com.nr.stringville.dto.UserDTO;
import com.nr.stringville.service.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StringvilleController {

    @Autowired
    private ModelMapper modelMapper;

    private Service service;

    public StringvilleController(Service service) {
        super();
        this.service = service;
    }

    @RequestMapping("/")
    public String index() {
        return "Welcome to Stringville!";
    }

    @PostMapping("/submission")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity submission(@RequestBody String submission) {
        if (submission == null || submission.length() < 1 || submission.length() > 10000) {
            return ResponseEntity.status(400).body("invalid input");
        }
        if (!submission.contains(",")) {
            return ResponseEntity.status(400).body("name must be present");
        }
            String[] submissionArray = submission.split(",", 2);

            UserDTO userDTO = new UserDTO();
            String str = submissionArray[0];
            userDTO.setName(submissionArray[1]);
            User userSubmission = modelMapper.map(userDTO, User.class);

            User user = service.updateUser(userSubmission.getName(), str);
            return ResponseEntity.status(200).body(user.getName() + " accepted");


    }

    @GetMapping("/results")
    public ResponseEntity results() {
        String res = service.getResult();
        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/health")
    public ResponseEntity health() {
        String res = service.getHealth();
        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/reset")
    public ResponseEntity reset() {
        service.reset();
        return ResponseEntity.status(200).body("");
    }

}
