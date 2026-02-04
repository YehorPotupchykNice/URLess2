package org.example.controller;

import org.example.dto.CreateURLRequest;
import org.example.dto.CreateURLResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shortener.ShortenedURL;
import usecases.ShortenerUseCase;

import java.net.URI;
import java.util.function.Function;

@RestController
public class ShortenerController {

    @Autowired
    private ShortenerUseCase shortener;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return shortener.getById(id)
                .map(getResponse()).orElse(notFound());
    }

    private static @NonNull ResponseEntity<Object> notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("URL does not exist");
    }

    private static @NonNull Function<ShortenedURL, ResponseEntity<Object>> getResponse() {
        return s -> ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .header("Location", s.getUrl())
                .build();
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody CreateURLRequest request) {
        var r = shortener.create(request.getUrl());
        var url = "https://urle.ss/" + r.getId();
        return ResponseEntity.created(URI.create(url)).body(new CreateURLResponse(url, request.getUrl()));
    }
}
