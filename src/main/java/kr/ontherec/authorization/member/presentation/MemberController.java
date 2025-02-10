package kr.ontherec.authorization.member.presentation;

import jakarta.validation.Valid;
import java.net.URI;
import kr.ontherec.authorization.member.application.MemberService;
import kr.ontherec.authorization.member.application.MemberMapper;
import kr.ontherec.authorization.member.domain.Member;
import kr.ontherec.authorization.member.dto.MemberSignUpRequestDto;
import kr.ontherec.authorization.member.dto.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper = MemberMapper.INSTANCE;

    @PostMapping
    public ResponseEntity<Void> signUp(@Valid @RequestBody MemberSignUpRequestDto requestDto) {
        Member newMember = mapper.signUpRequestDtoToEntity(requestDto);
        Long id = memberService.signUp(newMember);
        return ResponseEntity.created(URI.create("/v1/members/" + id)).build();
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> update(Authentication authentication, @Valid @RequestBody MemberUpdateRequestDto requestDto) {
        memberService.update(authentication.getName(), requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> withdraw(Authentication authentication) {
        memberService.withdraw(authentication.getName());
        return ResponseEntity.ok().build();
    }
}
