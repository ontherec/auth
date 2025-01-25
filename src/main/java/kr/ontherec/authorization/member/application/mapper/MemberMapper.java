package kr.ontherec.authorization.member.application.mapper;

import kr.ontherec.authorization.member.domain.Member;
import kr.ontherec.authorization.member.dto.MemberSignUpRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(
        config = MapperConfig.class
)
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);


    Member signUpDtoToEntity(MemberSignUpRequestDto dto);

	@Mapping(source = "id", target = "id", ignore = true)
	@Mapping(source = "username", target = "username", ignore = true)
    void update(Member source, @MappingTarget Member target);
}
