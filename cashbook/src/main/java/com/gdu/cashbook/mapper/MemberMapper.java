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
	public void deleteMember(LoginMember loginMember);
	public Member selectmemberOne(LoginMember loginMember);
}
