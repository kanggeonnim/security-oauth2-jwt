package com.example.springsecurity3.config.oauth;

import java.util.Map;
import java.util.Optional;

import com.example.springsecurity3.config.auth.PrincipalDetails;
import com.example.springsecurity3.config.oauth.provider.*;
import com.example.springsecurity3.dto.User;
import com.example.springsecurity3.dto.UserAuthority;
import com.example.springsecurity3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	// userRequest 는 code를 받아서 accessToken을 응답 받은 객체
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest); // google의 회원 프로필 조회

		// code를 통해 구성한 정보
		System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
		// token을 통해 응답받은 회원정보
		System.out.println("oAuth2User : " + oAuth2User);
	
		return processOAuth2User(userRequest, oAuth2User);
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

		// Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함.
		OAuth2UserInfo oAuth2UserInfo = null;
		if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인 요청");
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		} else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
			System.out.println("카카오 로그인 요청");
			oAuth2UserInfo = new KakaoUserInfo((Map)oAuth2User.getAttributes());
		}

		Optional<User> user =
				userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());

		if (user.isEmpty()) {
			// 신규 회원가입
			user = Optional.of(User.builder()
                    .username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
					.userNickname(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
                    .email(oAuth2UserInfo.getEmail())
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build());

			UserAuthority auth = UserAuthority.builder()
					.user(user.get())
					.role("ROLE_USER")
					.build();

			user.get().addAuthority(auth);
			userRepository.save(user.get());
		}

		return new PrincipalDetails(user.get(), oAuth2User.getAttributes());
	}
}
