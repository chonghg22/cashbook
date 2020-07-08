package com.gdu.cashbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.AdminMapper;
import com.gdu.cashbook.vo.Admin;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Service
public class AdminService {
	@Autowired
	private AdminMapper adminMapper;
	public String loginAdmin(Admin admin) {
		return adminMapper.selectLoginAdmin(admin);
	}

}
