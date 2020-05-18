package com.gdu.cashbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.mapper.MemberidMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.Memberid;

@Service
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private MemberidMapper memberidMapper;
	public void insertMember(Member member) {
		memberMapper.insertMember(member);
	}
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}
	public String checkMemberId(String memberIdCheck) {
		return memberMapper.selectMemberId(memberIdCheck);
	}
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	}
	public void removeMember(LoginMember loginMember) {
		Memberid memberid = new Memberid();
		memberid.setMemberId(loginMember.getMemberId());
		memberidMapper.insertMemberid(memberid);
		
		memberMapper.deleteMember(loginMember);
	}
	public String modifyPwCk(String memberPw) {
		return memberMapper.updatePwCk(memberPw);
	}
	public int modifyMemberInfo(Member member) {
		return memberMapper.updateMemberInfo(member);
	}
	public Member getModifyMemberOne(LoginMember loginMember) {
		return memberMapper.updateMemberOne(loginMember);
	}
}
