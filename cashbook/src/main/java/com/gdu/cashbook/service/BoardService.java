package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.BoardMapper;
import com.gdu.cashbook.vo.Board;

@Service
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	public int insertBoard(Board board) {
		return boardMapper.insertBoard(board);
	}
	public List<Board> selectBoardList(){
		return boardMapper.selectBoardList();
	}
	
}
