package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ReplyRepository replyRepository;

	@Transactional
	public void 글쓰기(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		board.setState(0);
		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다."));
	}

	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}

	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다."));
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());

	}

	@Transactional
	public void 업데이트(int id, Board requestBoard) {
		System.out.println("Service : 변경 시작");
		Board board = boardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다."));
		System.out.println("ID 찾기 완료 ");
		if (requestBoard.getState() != 1) {
			board.setTitle("(판매완료) " + requestBoard.getTitle());
			board.setContent(requestBoard.getContent());
			board.setState(1);
		}
		System.out.println("title : " + board.getTitle());
		System.out.println("content : " + board.getContent());
		System.out.println("state : " + board.getState());
		System.out.println("boardstate 변경 완료");
	}

	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
		replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(),
			replySaveRequestDto.getContent());
	}

	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}

	@Transactional(readOnly = true)
	public Page<Board> searchPosts(String keyword, Pageable pageable) {
		boardRepository.findByTitleContaining(keyword, pageable);
		return boardRepository.findByTitleContaining(keyword, pageable);
	}

}