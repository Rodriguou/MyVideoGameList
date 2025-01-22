package com.rodrigomatos.myvideogamelist.mapper;

import com.rodrigomatos.myvideogamelist.dto.GameListDTO;
import com.rodrigomatos.myvideogamelist.entity.GameList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameListMapper {

    @Mapping(target = "gameId", source = "game.id")
    @Mapping(target = "gameName", source = "game.name")
    @Mapping(target = "gameReleaseDate", source = "game.releaseDate")
    GameListDTO toDTO(GameList game);

    List<GameListDTO> toDTOList(List<GameList> games);

}
