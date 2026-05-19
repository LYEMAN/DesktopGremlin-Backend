package com.desktopgremlin.backend;

import com.desktopgremlin.backend.models.FactResponse;
import com.desktopgremlin.backend.services.FactService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/facts")
@CrossOrigin(origins = "*")
public class FactController {

    private final FactService factService;

    public FactController(FactService factService) {
        this.factService = factService;
    }

    @GetMapping("/random")
    public FactResponse randomFact() {
        String factText = factService.getRandomFact();
        return new FactResponse(factText);
    }
}
