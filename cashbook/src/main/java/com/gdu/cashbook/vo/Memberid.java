package com.gdu.cashbook.vo;

public class Memberid {
	private int memberidNo;
	private String memberId;
	@Override
	public String toString() {
		return "Memberid [memberidNo=" + memberidNo + ", memberId=" + memberId + "]";
	}
	public int getMemberidNo() {
		return memberidNo;
	}
	public void setMemberidNo(int memberidNo) {
		this.memberidNo = memberidNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
}
