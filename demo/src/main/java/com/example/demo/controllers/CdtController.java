package com.example.demo.controllers;

import com.example.demo.DTOs.CdtDTO;
import com.example.demo.entities.Cdt;
import com.example.demo.services.CdtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("cdt")
public class CdtController {

    private CdtService cdtService;

    public CdtController(CdtService cdtService) {this.cdtService = cdtService;}


    @PostMapping
    public ResponseEntity<String> createNewCdt(@Valid @RequestBody Cdt cdt) { //@PathVariable("id") Long id
        try {
            cdtService.createNewCdt(cdt);
        } catch (IllegalTransactionStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Cdt creado");
    }

    @PostMapping("/simular")
    public ResponseEntity<?> simulateCdt(@RequestBody Cdt cdt) {
        try {
            cdtService.simulateNewCdt(cdt);
        } catch (IllegalTransactionStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        Map<String, Object> body = Map.of(
                "plazo", cdt.getPlazo(),
                "ganancia", cdt.getGanancia(),
                "impuesto", cdt.getImpuesto(),
                "ganancia_neta", cdt.getGanancia() - cdt.getImpuesto(),
                "taza", cdt.getTaza()
        );

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCdtById(@PathVariable("id") Long id) {
        Optional<Cdt> cdtOptional = cdtService.findById(id);

        if (cdtOptional.isEmpty())
            return new ResponseEntity<>("El CDT con id: " + id + " no existe o fue eliminado.", HttpStatus.NOT_FOUND);



        return new ResponseEntity<>(cdtService.getCdtDTO(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCdtById(@PathVariable("id") Long id) {
        Optional<Cdt> cdtOptional = cdtService.findById(id);

        if (cdtOptional.isEmpty())
            return new ResponseEntity<>("El CDT con id: " + id + " no existe o fue eliminado.", HttpStatus.NOT_FOUND);

        Cdt cdt = cdtOptional.get();
        cdtService.deleteCdt(cdt);
        return ResponseEntity.ok("Cdt eliminado");
    }

}
