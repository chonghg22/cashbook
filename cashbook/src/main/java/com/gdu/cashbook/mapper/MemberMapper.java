package com.gdu.cashbook.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
@Mapper
public interface MemberMapper {
	public String selectMemberId(String memberidCheck);
	public LoginMember selectLoginMember(LoginMember loginMember);
	public void insertMember(Member member);
	public Member selectMemberOne(LoginMember loginMember);
	public String updatePwCk(String memberPw);
	public int updateMemberInfo(Member member);
	public Member updateMemberOne(LoginMember loginMember);
	public int updateMemberPw(Member member);
	public void deleteMember(LoginMember loginMember);
	public Member selectMemberByIdAndEmail(Member member); // 비밀번호 찾기
	public String selectMemberIdBymember(Member member); // 아이디 찾기	
	public Member selectmemberOne(LoginMember loginMember);
}
