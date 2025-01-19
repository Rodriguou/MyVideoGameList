package com.rodrigomatos.myvideogamelist.mapper;

import com.rodrigomatos.myvideogamelist.dto.GameDTO;
import com.rodrigomatos.myvideogamelist.entity.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameMapper {

    GameDTO toDTO(Game game);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", expression = "java(gameDTO.name().trim())")
    Game toEntity(GameDTO gameDTO);

    List<GameDTO> toDTOList(List<Game> games);

}
