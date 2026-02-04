package org.example.controller;

import org.example.dto.CreateURLCollectionRequest;
import org.example.dto.CreateURLCollectionResponse;
import org.example.dto.CreateURLRequest;
import org.example.dto.CreateURLResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shortener.ShortenedURL;
import shortener.ShortenedURLCollection;
import usecases.CollectionShortenerUseCase;
import usecases.ShortenerUseCase;

import java.net.URI;
import java.util.function.Function;

@RestController
public class ShortenerController {

    @Autowired
    private ShortenerUseCase shortener;

    @Autowired
    private CollectionShortenerUseCase collectionShortener;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        if (id.length() == 6) {
            return shortener.getById(id)
                    .map(getResponse()).orElse(notFound());
        }

        if (id.length() == 5) {
            return collectionShortener.getById(id).map(getCollectionResponse()).orElse(notFound());
        }

        return notFound();
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

    private static @NonNull Function<ShortenedURLCollection, ResponseEntity<Object>> getCollectionResponse() {
        return s -> ResponseEntity
                .status(HttpStatus.OK)
                .body(new CreateURLCollectionResponse("https://urle.ss/" + s.getId(),
                        s.getUrls().stream().map(u -> "https://urle.ss/" + u.getId()).toList()));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody CreateURLRequest request) {
        var r = shortener.create(request.getUrl());
        var url = "https://urle.ss/" + r.getId();
        return ResponseEntity.created(URI.create(url)).body(new CreateURLResponse(url, r.getUrl()));
    }

    @PostMapping("/collections")
    public ResponseEntity<?> postCollections(@RequestBody CreateURLCollectionRequest request) {
        var r = collectionShortener.create(request.getUrls());
        var url = "https://urle.ss/" + r.getId();
        return ResponseEntity.created(URI.create(url))
                .body(new CreateURLCollectionResponse(url, r.getUrls().stream().map(ShortenedURL::getId).toList()));
    }
}
