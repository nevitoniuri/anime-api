package com.nevitoniuri.essentials.repository;

import com.nevitoniuri.essentials.model.Anime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Teste do AnimeRepository")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @AfterEach
    void tearDown() {
        animeRepository.deleteAll();
    }

    private Anime createAnime() {
        return Anime.builder().name("Naruto").build();
    }

    @Test
    @DisplayName("Deve salvar um anime")
    void deveSalvarAnime() {
        Anime anime = createAnime();
        Anime animeSalvo = animeRepository.save(anime);

        assertNotNull(animeSalvo);
        assertNotNull(animeSalvo.getId());
        assertEquals(anime.getName(), animeSalvo.getName());
    }

    @Test
    @DisplayName("Deve atualizar um anime")
    void deveAtualizarAnime() {
        Anime animeQueSeraSalvo = createAnime();
        Anime animeSalvo = animeRepository.save(animeQueSeraSalvo);

        animeSalvo.setName("Naruto Shippuden");
        Anime animeAtualizadoSalvo = animeRepository.save(animeSalvo);

        assertNotNull(animeSalvo);
        assertNotNull(animeAtualizadoSalvo);
        assertEquals("Naruto Shippuden", animeAtualizadoSalvo.getName());
    }

    @Test
    @DisplayName("Deve deletar um anime")
    void deveDeletarAnime() {
        Anime animeQueSeraSalvo = createAnime();
        Anime animeSalvo = animeRepository.save(animeQueSeraSalvo);

        animeRepository.delete(animeSalvo);

        assertNull(animeRepository.findById(animeSalvo.getId()).orElse(null));
    }

    @Test
    @DisplayName("Deve checar se um anime existe pelo nome")
    void deveChecarSeExistePeloNome() {
        Anime animeQueSeraSalvo = createAnime();
        Anime animeSalvo = animeRepository.save(animeQueSeraSalvo);

        assertTrue(animeRepository.existsByName(animeSalvo.getName()));
    }

    @Test
    @DisplayName("Deve buscar um anime pelo nome")
    void deveBuscarPeloNome() {
        Anime animeQueSeraSalvo = createAnime();
        Anime animeSalvo = animeRepository.save(animeQueSeraSalvo);

        Page<Anime> animes = animeRepository.findByName(animeSalvo.getName(), Pageable.unpaged());
        assertNotNull(animes);
        assertEquals(1, animes.getTotalElements());
        assertThat(animes).contains(animeSalvo);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não encontrar anime pelo nome")
    void deveRetornarListaVaziaQuandoNaoEncontrarPeloNome() {
        Page<Anime> animes = animeRepository.findByName("Naruto", Pageable.unpaged());
        assertNotNull(animes);
        assertEquals(0, animes.getTotalElements());
        assertThat(animes).isEmpty();
    }
}
