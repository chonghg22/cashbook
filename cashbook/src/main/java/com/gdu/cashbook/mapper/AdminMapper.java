package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Admin;
import com.gdu.cashbook.vo.LoginMember;
@Mapper
public interface AdminMapper {
	public int insertAdmin(String memberId);
	public String selectLoginAdmin(Admin admin);
}
