package mcdodik.socks.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mcdodik.socks.domain.model.entity.Sock;
import mcdodik.socks.domain.model.view.*;
import mcdodik.socks.domain.service.api.SockService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/socks")
@RequiredArgsConstructor
@Tag(name = "Sock Controller", description = "Управление количеством носков на складе.")
public class SockController {

    private final SockService sockService;

    @PostMapping("/income")
    @Operation(summary = "Регистрация прихода носков на склад.")
    public ResponseEntity<Void> addIncome(
            @RequestParam Long id,
            @Validated @RequestBody SockViewIncome view
    ) {
        sockService.addIncome(id, view);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/outcome")
    @Operation(summary = "Регистрация отпуска носков со склада.")
    public ResponseEntity<Void> addOutcome(
            @RequestParam Long id,
            @Validated @RequestBody SockViewOutcome view
    ) {
        sockService.addOutcome(id, view);
        return ResponseEntity.ok().build();
    }
    // http://localhost:8080/api/socks?color=синий&cottonPercentRange=от 30 до 90
    // http://localhost:8080/api/socks?color=синий&cottonPercentage=50&cottonPercentageOperations=MORE_THEN
    @GetMapping
    @Operation(summary = "Чтение данных о носках с возможной фильтрацией")
    public ResponseEntity<List<SockViewRead>> findAll(@Validated SockViewFilter filter) {
        List<SockViewRead> socks = sockService.findAll(filter);
        return ResponseEntity.ok(socks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sock> update(
            @PathVariable Long id,
            @Validated @RequestBody SockViewUpdate view
    ) {
        Sock updatedSock = sockService.update(id, view);
        return ResponseEntity.ok(updatedSock);
    }

    @PostMapping(value = "/batch")
    public ResponseEntity<String> uploadBatch(@RequestParam("file") MultipartFile file) {
        Integer n = sockService.uploadBatch(file);
        return ResponseEntity.ok("Импорт " + n + " записей прошел успешно");
    }
}
