package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.ReleaseShoe;
import com.cos.blog.model.User;
import com.cos.blog.repository.ReleaseRepository;

@Service
public class ReleaseService {

	@Autowired
	private ReleaseRepository releaseRepository;

	@Transactional
	public void 글쓰기(ReleaseShoe board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);
		releaseRepository.save(board);
	}

	@Transactional
	public void 발매정보업데이트(ReleaseShoe board, User user) {
		board.setCount(0);
		board.setUser(user);
		releaseRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<ReleaseShoe> 글목록(Pageable pageable) {
		return releaseRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public ReleaseShoe 글상세보기(int id) {
		return releaseRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});
	}

	@Transactional
	public void 글삭제하기(int id) {
		releaseRepository.deleteById(id);
	}

	@Transactional
	public void 글수정하기(int id, ReleaseShoe requestBoard) {
		ReleaseShoe board = releaseRepository.findById(id)
			.orElseThrow(() -> {
				return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
			}); //영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수가 종료시에 (Service가 종료될 때) 트랜잭션이 종료된다. 이때 더티체킹
		//자동 업데이트가됨. db쪽에 flush
	}

}
