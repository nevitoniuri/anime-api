package com.nevitoniuri.essentials.controller;

import com.nevitoniuri.essentials.controller.request.AnimeRequest;
import com.nevitoniuri.essentials.model.Anime;
import com.nevitoniuri.essentials.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeService animeService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Page<Anime>> list(@RequestParam(required = false) String name,
                                            @PageableDefault(page = 0, size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(animeService.list(name, pageable));
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
