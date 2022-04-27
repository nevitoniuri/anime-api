package com.nevitoniuri.essentials.repository;

import com.nevitoniuri.essentials.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    boolean existsByName(String name);
    List<Anime> findByName(String name);
}