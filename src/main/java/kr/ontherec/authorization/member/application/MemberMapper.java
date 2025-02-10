package kr.ontherec.authorization.member.application;

import java.util.Set;
import java.util.stream.Collectors;
import kr.ontherec.authorization.global.config.MapperConfig;
import kr.ontherec.authorization.member.domain.Authority;
import kr.ontherec.authorization.member.domain.Member;
import kr.ontherec.authorization.member.domain.Role;
import kr.ontherec.authorization.member.dto.MemberResponseDto;
import kr.ontherec.authorization.member.dto.MemberSignUpRequestDto;
import kr.ontherec.authorization.member.dto.MemberUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(
        config = MapperConfig.class
)
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Member signUpRequestDtoToEntity(MemberSignUpRequestDto dto);

    MemberResponseDto entityToResponseDto(Member entity);

    default Set<Authority> rolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(Role::getAuthority).collect(Collectors.toSet());
    }

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "username", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void update(MemberUpdateRequestDto dto, @MappingTarget Member target);
}
