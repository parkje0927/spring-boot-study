package org.zerock.club.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerock.club.entity.ClubMember;
import org.zerock.club.entity.ClubMemberRole;
import org.zerock.club.repository.ClubMemberRepository;
import org.zerock.club.security.dto.ClubAuthMemberDto;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final PasswordEncoder passwordEncoder;
    private final ClubMemberRepository repository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //userRequest 정보로 ClubMember 의 saveSocialMember 메소드 호출하여 생성
        //saveSocialMember 메소드 : 기존에 있는 회원이면 찾아오고, 새로운 회원이면 저장
        //ClubMember 객체를 ClubMemberDto 로 변환하여 return

        log.info("-----");
        log.info("userRequest : " + userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();

        log.info("clientName : " + clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("-----");
        oAuth2User.getAttributes().forEach((k, v) -> {
            log.info(k + " : " + v);
        });

        String id = String.valueOf(oAuth2User.getAttributes().get("id"));
        ClubMember member = saveSocialMember(id);
        ClubAuthMemberDto clubAuthMemberDto = new ClubAuthMemberDto(member.getId(), member.getPassword(), true, member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).collect(Collectors.toList()), oAuth2User.getAttributes());

        return clubAuthMemberDto;
//        return new DefaultOAuth2User(null, oAuth2User.getAttributes(), "id");
//        return oAuth2User;
    }

    private ClubMember saveSocialMember(String id) {
        Optional<ClubMember> result = repository.findById(id);

        if (result.isPresent()) {
            return result.get();
        }

        ClubMember clubMember = ClubMember.builder()
                .id(id)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();

        clubMember.addMemberRole(ClubMemberRole.USER);

        repository.save(clubMember);
        return clubMember;
    }
}
