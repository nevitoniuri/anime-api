package com.nevitoniuri.essentials.repository;

import com.nevitoniuri.essentials.model.Anime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    boolean existsByName(String name);
    Page<Anime> findByName(String name, Pageable pageable);
}