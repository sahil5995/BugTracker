package de.bugtracker.controller.mapper;

import de.bugtracker.dto.BugDTO;
import de.bugtracker.domainobject.BugDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BugMapper {

    BugMapper INSTANCE = Mappers.getMapper(BugMapper.class);

    BugDO makeDO(BugDTO source);

    BugDTO makeDTO(BugDO bugDO);

    List<BugDTO> createList(List<BugDO> bugDo);
}