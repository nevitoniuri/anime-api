package com.nevitoniuri.essentials.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AnimeRequest {
    @NotEmpty(message = "O nome do Anime não pode ser vazio")
    @NotNull(message = "O nome do Anime não pode ser nulo")
    private String name;
}
