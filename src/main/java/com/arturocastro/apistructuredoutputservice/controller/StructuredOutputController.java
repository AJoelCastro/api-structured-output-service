package com.arturocastro.apistructuredoutputservice.controller;

import com.arturocastro.apistructuredoutputservice.model.SOModel;
import com.arturocastro.apistructuredoutputservice.service.StructuredOutputService;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseOutputItem;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/openai/structured-output")
public class StructuredOutputController {

    private final StructuredOutputService so;

    public StructuredOutputController(StructuredOutputService so) {
        this.so = so;
    }

    @PostMapping
    public ResponseEntity<ResponseOutputItem> getStructuredOutput(@RequestBody SOModel som){
        return ResponseEntity.ok(so.getStructuredOutput(som).output().getFirst());
    }

}
