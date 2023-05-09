package com.example.springxmltojson.service;

import com.example.springxmltojson.dto.request.ConverterRequest;
import com.example.springxmltojson.dto.response.ConverterResponse;
import com.example.springxmltojson.model.ValCurs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class ConverterService {
    private final RestTemplate restTemplate;

    public ConverterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String convert() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ValCurs valcurs = converted();
        String converted = objectMapper.writeValueAsString(valcurs);
        return converted;
    }
    public ConverterResponse convertMoney(ConverterRequest converterRequest) throws JsonProcessingException {
        ConverterResponse converterResponse = new ConverterResponse();



            Optional<BigDecimal> collectAzn = convertToAzn(converterRequest);


        if (converterRequest.getConvertedTo().equalsIgnoreCase("AZN")) {
            converterResponse.setAmount(collectAzn.get())
                    .setCurrency(converterRequest.getConvertedTo());
        }
        else {
            Optional<BigDecimal> collectOther = convertToOtherValute(converterRequest);

            BigDecimal collect = collectAzn.get().divide(collectOther.get(),2, RoundingMode.HALF_UP);
            converterResponse.setAmount(collect)
                    .setCurrency(converterRequest.getConvertedTo());
        }
      return converterResponse;
    }

    private Optional<BigDecimal> convertToAzn(ConverterRequest converterRequest) throws JsonProcessingException {
        ValCurs valCurs = converted();
        Optional<BigDecimal> valute = valCurs.getValType().stream()
                .flatMap(valType -> valType.getValute().stream().
                        filter(v->v.getCode().equalsIgnoreCase(converterRequest.getCurrency()))
                        .map(valute1 -> valute1.getValue().multiply(converterRequest.getAmount())))
                .findFirst();
        return valute;
    }

    private  Optional<BigDecimal> convertToOtherValute(ConverterRequest converterRequest)
            throws JsonProcessingException {
        ValCurs valCurs = converted();


            Optional<BigDecimal> valute = valCurs.getValType().stream()
                    .flatMap(valType -> valType.getValute().stream()
                            .filter(v -> v.getCode().equalsIgnoreCase(converterRequest.getConvertedTo()))
                            .map(valute2 -> valute2.getValue())).findFirst();
            return valute;

    }

    private ValCurs  converted()  {

        String url = "https://www.cbar.az/currencies/{date}" + ".xml";

        List<HttpMessageConverter<?>> messageConverters = messageConverters();
        restTemplate.setMessageConverters(messageConverters);

        String text = dateFormatter();

        ValCurs valcurs = restTemplate.getForObject(url, ValCurs.class, text);

        return  valcurs;
    }

    private String dateFormatter() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String text = date.format(formatter);
        return text;
    }

    private List<HttpMessageConverter<?>> messageConverters(){
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2XmlHttpMessageConverter converter = new MappingJackson2XmlHttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_XML));
        messageConverters.add(converter);
        return  messageConverters;
    }


}
