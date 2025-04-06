package kr.ontherec.authorization.security;

import java.util.List;
import java.util.stream.Collectors;
import kr.ontherec.authorization.member.dao.MemberRepository;
import kr.ontherec.authorization.member.domain.Member;
import kr.ontherec.authorization.security.exception.SecurityException;
import kr.ontherec.authorization.security.exception.SecurityExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new SecurityException(SecurityExceptionCode.UNAUTHORIZED));

        List<GrantedAuthority> authorities = member.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());

        return new User(member.getUsername(), member.getPassword(), authorities);
    }
}
