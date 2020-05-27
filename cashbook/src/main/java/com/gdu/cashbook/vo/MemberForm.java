package com.gdu.cashbook.vo;

import org.springframework.web.multipart.MultipartFile;

public class MemberForm {
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberPhone;
	private String memberAddress;
	private String memberEmail;
	private MultipartFile memberPic; //여러 개의 파일을 받고 싶을 땐 MultipartFile[], List<MultipartFile> 배열로 받는다.
	private String memberDate;
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public String getMemberAddress() {
		return memberAddress;
	}
	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public MultipartFile getMemberPic() {
		return memberPic;
	}
	public void setMemberPic(MultipartFile memberPic) {
		this.memberPic = memberPic;
	}
	public String getMemberDate() {
		return memberDate;
	}
	public void setMemberDate(String memberDate) {
		this.memberDate = memberDate;
	}
	@Override
	public String toString() {
		return "MemberForm [memberId=" + memberId + ", memberPw=" + memberPw + ", memberName=" + memberName
				+ ", memberPhone=" + memberPhone + ", memberAddress=" + memberAddress + ", memberEmail=" + memberEmail
				+ ", memberPic=" + memberPic + ", memberDate=" + memberDate + "]";
	}
	
	
}
