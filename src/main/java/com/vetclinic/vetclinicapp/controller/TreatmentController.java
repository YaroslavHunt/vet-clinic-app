package com.vetclinic.vetclinicapp.controller;

import com.vetclinic.vetclinicapp.dto.treatment.AnyTreatmentDTO;
import com.vetclinic.vetclinicapp.dto.treatment.TreatmentDTO;
import com.vetclinic.vetclinicapp.service.TreatmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/treatments")
@RequiredArgsConstructor
public class TreatmentController {

    private final TreatmentService treatmentService;

    @GetMapping
    public ResponseEntity<List<TreatmentDTO>> getAll(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String year
    ) {
        List<TreatmentDTO> treatments;

        if (date != null) {
            LocalDate localDate = LocalDate.parse(date);
            treatments = treatmentService.findByDate(localDate);
        } else if (month != null && year != null) {
            YearMonth yearMonth = YearMonth.parse(year + "-" + month);
            treatments = treatmentService.findByMonth(yearMonth);
        } else if (year != null) {
            treatments = treatmentService.findByYear(Integer.parseInt(year));
        } else {
            treatments = treatmentService.findAll();
        }

        return treatments.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.ok(treatments);
    }

    @GetMapping("/by-pet")
    public ResponseEntity<List<TreatmentDTO>> getByPetId(@RequestParam(name = "id") Long petId) {
        List<TreatmentDTO> treatments;
        treatments = treatmentService.findByPetId(petId);
        return treatments.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.ok(treatments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentDTO> getTreatment(@PathVariable Long id) {
        TreatmentDTO dto = treatmentService.findTreatmentById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/save")
    public ResponseEntity<AnyTreatmentDTO> add(@RequestBody @Valid TreatmentDTO dto) {
        AnyTreatmentDTO newTreatment = treatmentService.addTreatment(dto);
        return ResponseEntity.status(CREATED).body(newTreatment);
    }


    @PatchMapping("/appointment={appId}")
    public ResponseEntity<Void> addTreatmentToAppointment(
            @PathVariable Long appId,
            @RequestParam(name = "add_treatment") Long treatmentId
    ) {
        treatmentService.addTreatmentToAppointment(appId, treatmentId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        treatmentService.deleteTreatment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
