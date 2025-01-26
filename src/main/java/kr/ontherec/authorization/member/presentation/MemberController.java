package kr.ontherec.authorization.member.presentation;

import jakarta.validation.Valid;
import kr.ontherec.authorization.member.application.MemberService;
import kr.ontherec.authorization.member.application.mapper.MemberMapper;
import kr.ontherec.authorization.member.domain.Member;
import kr.ontherec.authorization.member.dto.MemberResponseDto;
import kr.ontherec.authorization.member.dto.MemberSignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper = MemberMapper.INSTANCE;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signUp(@Valid @RequestBody MemberSignUpRequestDto requestDto) {
        Member newMember = mapper.signUpRequestDtoToEntity(requestDto);
        Member savedMember = memberService.register(newMember);
        MemberResponseDto responseDto = mapper.entityToResponseDto(savedMember);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
