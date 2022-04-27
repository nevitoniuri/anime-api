package com.nevitoniuri.essentials.controller;

import com.nevitoniuri.essentials.controller.request.AnimeRequest;
import com.nevitoniuri.essentials.model.Anime;
import com.nevitoniuri.essentials.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeService animeService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<Anime>> list(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(animeService.list(name));
    }

    @GetMapping("{id}")
    public ResponseEntity<Anime> findById(@PathVariable Long id) {
        if (animeService.findById(id) == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(animeService.findById(id));
        }
    }

    @PostMapping
    public ResponseEntity<Anime> create(@RequestBody @Valid AnimeRequest animeRequest, UriComponentsBuilder uriComponentsBuilder) {
        Anime animeSalvo = animeService.save(modelMapper.map(animeRequest, Anime.class));
        URI uri = uriComponentsBuilder.path("/animes/{id}").buildAndExpand(animeSalvo.getId()).toUri();
        return ResponseEntity.created(uri).body(animeSalvo);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        animeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Anime> replace(@PathVariable Long id, @RequestBody @Valid AnimeRequest animeRequest) {
        Anime anime = animeService.findById(id);
        modelMapper.map(animeRequest, anime);
        return ResponseEntity.ok(animeService.save(anime));
    }
}
