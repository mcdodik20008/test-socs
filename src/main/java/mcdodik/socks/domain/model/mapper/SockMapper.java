package mcdodik.socks.domain.model.mapper;

import mcdodik.socks.domain.model.entity.Sock;
import mcdodik.socks.domain.model.view.SockViewRead;
import mcdodik.socks.domain.model.view.SockViewUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SockMapper {

    SockMapper INSTANCE = Mappers.getMapper(SockMapper.class);

    Sock formViewUpdate(@MappingTarget Sock entity, SockViewUpdate view);

    List<Sock> formViewUpdate(List<SockViewUpdate> view);

    SockViewRead toViewRead(Sock view);

}
