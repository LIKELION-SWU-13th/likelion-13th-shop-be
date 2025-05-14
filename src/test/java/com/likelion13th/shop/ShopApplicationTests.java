package com.likelion13th.shop;

import com.likelion13th.shop.constant.Role;
import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ShopApplicationTests {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	void contextLoads() {
		// 스프링 컨텍스트가 정상적으로 로드되는지 확인
	}

	@Test
	void testSaveMember() {
		// given
		Member member = Member.createMember(
				"테스트회원",
				"test@example.com",
				"1234",
				"서울시 강남구",
				Role.USER
		);


		// when
		Member savedMember = memberRepository.saveAndFlush(member);

		// then
		assertThat(savedMember).isNotNull();
		assertThat(savedMember.getId()).isNotNull();
		assertThat(savedMember.getEmail()).isEqualTo("test@example.com");
		assertThat(savedMember.getName()).isEqualTo("테스트회원");
	}
}
