package com.example.springxmltojson.controller;

import com.example.springxmltojson.dto.request.ConverterRequest;
import com.example.springxmltojson.dto.response.ConverterResponse;
import com.example.springxmltojson.service.ConverterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/convert")
public class ConverterContoller {
    private final ConverterService converterService;
    private final Logger logger = LoggerFactory.getLogger(ConverterContoller.class);

    public ConverterContoller(ConverterService converterService) {
        this.converterService = converterService;
    }

    @GetMapping
    public ResponseEntity<String> convert() throws JsonProcessingException {
        return ResponseEntity.ok(converterService.convert());
    }
    @PostMapping("/post")
    public ResponseEntity<ConverterResponse> moneyConverter(
            @RequestBody ConverterRequest converterRequest) throws JsonProcessingException {
        logger.info("Converter Request : {}" + converterRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(converterService.convertMoney(converterRequest));
    }


}
