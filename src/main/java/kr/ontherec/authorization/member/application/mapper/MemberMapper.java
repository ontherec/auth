package kr.ontherec.authorization.member.application.mapper;

import kr.ontherec.authorization.member.domain.Member;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(
        config = MapperConfig.class
)
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    void update(Member source, @MappingTarget Member target);
}