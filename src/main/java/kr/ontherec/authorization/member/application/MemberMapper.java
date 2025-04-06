package kr.ontherec.authorization.member.application;

import kr.ontherec.authorization.global.config.MapperConfig;
import kr.ontherec.authorization.member.domain.Member;
import kr.ontherec.authorization.member.dto.MemberResponseDto;
import kr.ontherec.authorization.member.dto.MemberSignUpRequestDto;
import kr.ontherec.authorization.member.dto.MemberUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(
        config = MapperConfig.class,
        imports = LocalDateTime.class
)
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "modifiedAt", expression = "java(LocalDateTime.now())")
    Member signUpRequestDtoToEntity(MemberSignUpRequestDto dto);

    void update(MemberUpdateRequestDto dto, @MappingTarget Member target);

    MemberResponseDto entityToResponseDto(Member entity);
}
