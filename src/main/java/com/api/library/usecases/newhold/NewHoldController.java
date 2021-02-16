package com.api.library.usecases.newhold;

import com.api.library.domain.Hold;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Map;

@RestController
public class NewHoldController {

    @PersistenceContext
    private EntityManager manager;
    private final NewHoldValidator newHoldValidator;

    public NewHoldController(NewHoldValidator newHoldValidator) {
        this.newHoldValidator = newHoldValidator;
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(newHoldValidator);
    }

    @PostMapping(value = "/holds")
    @Transactional
    public Map<String, Object> newHold(@RequestBody @Valid NewHoldRequest request) {
        Hold newHold = request.newHold(manager);
        manager.persist(newHold);
        return Map.of("response", newHold.toString());
    }
}
