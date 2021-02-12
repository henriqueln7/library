package com.api.library.usecases.newhold;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class NewHoldController {

    private final NewHoldValidator newHoldValidator;

    public NewHoldController(NewHoldValidator newHoldValidator) {
        this.newHoldValidator = newHoldValidator;
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(newHoldValidator);
    }

    @PostMapping(value = "/holds")
    public Map<String, Object> newHold(@RequestBody @Valid NewHoldRequest request) {
        return Map.of("response", request.toString());
    }
}
