package com.marketplace.CodeChallenge.controller;

import com.marketplace.CodeChallenge.model.ServiceType;
import com.marketplace.CodeChallenge.service.ProviderTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/provider/type")
public class ServiceTypeController {
    @Autowired
    private ProviderTypeService providerTypeService;

    @PostMapping("/add")
    public ResponseEntity addType(@RequestBody @Validated ServiceType type) {
        return ResponseEntity.ok(
                this.providerTypeService
                        .saveProviderType(type)
        );
    }

    @PutMapping("/edit")
    public ResponseEntity editType(@RequestBody @Validated ServiceType type) {
        return ResponseEntity.ok(
                this.providerTypeService
                        .updateProviderType(type, type.getTypeId())
        );
    }

    @GetMapping("/{typeId}")
    public ResponseEntity getTypeById(@PathVariable long typeId) {
        return ResponseEntity.ok(
                this.providerTypeService
                        .fetchType(typeId)
        );
    }

    @GetMapping("/list")
    public ResponseEntity getTypes() {
        return ResponseEntity.ok(
                this.providerTypeService
                        .fetchTypes()
        );
    }

    //todo: delete function for admin dashboard
}
