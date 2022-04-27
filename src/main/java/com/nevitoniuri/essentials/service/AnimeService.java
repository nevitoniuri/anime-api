package com.nevitoniuri.essentials.service;

import com.nevitoniuri.essentials.exception.BadRequestException;
import com.nevitoniuri.essentials.model.Anime;
import com.nevitoniuri.essentials.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    @Transactional(readOnly = true)
    public Page<Anime> list(String name, Pageable pageable) {
        Page<Anime> animes;
        if (name == null) {
            animes = animeRepository.findAll(pageable);
        } else {
            animes = findByName(name, pageable);
        }
        return animes;
    }

    @Transactional(readOnly = true)
    public Anime findById(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime não encontrado"));
    }

    public Anime save(Anime anime) {
        if (anime.getId() == null) {
            if (isAnimeExists(anime.getName())) {
                throw new BadRequestException("Anime já existe");
            }
            return animeRepository.save(anime);
        } else {
            if (isAnimeExists(anime.getName())) {
                throw new BadRequestException("Anime já existe");
            }
            return animeRepository.saveAndFlush(anime);
        }
    }

    @Transactional
    public void delete(Long id) {
        animeRepository.delete(findById(id));
    }

    public boolean isAnimeExists(String name) {
        return animeRepository.existsByName(name);
    }

    @Transactional(readOnly = true)
    public Page<Anime> findByName(String name, Pageable pageable) {
        Page<Anime> animes = animeRepository.findByName(name, pageable);
        if (animes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found");
        }
        return animes;
    }

}