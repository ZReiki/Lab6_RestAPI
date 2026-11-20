package ua.edu.nuos.lab6_restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.nuos.lab6_restapi.model.Watch;
import ua.edu.nuos.lab6_restapi.service.WatchService;

import java.util.List;

@RestController
@RequestMapping("/api/watches")
@RequiredArgsConstructor
public class WatchRestController {
    private final WatchService watchService;

    @GetMapping
    public List<Watch> getAll() {
        return watchService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Watch> getById(@PathVariable Long id) {
        return watchService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Watch> create(@RequestBody Watch watch) {
        Watch created = watchService.create(watch);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Watch> update(@PathVariable Long id, @RequestBody Watch watch) {
        return watchService.update(id, watch)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (watchService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
