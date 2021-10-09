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
	
//	@Autowired의 의미 1
//	private BoardRepository boardRepository;
//	private ReplyRepository replyRepository;
//	
//	public BoardService(BoardRepository bRepo, ReplyRepository rRepo) {
//		this.boardRepository = bRepo;
//		this.replyRepository = rRepo;
//	}
	
//	@Autowired의 의미 2
//	Service 클래스에 @RequiredArgsConstructor를 붙여준다. (final도 알아서 생성자로 초기화시켜줌)
//	private final BoardRepository boardRepository;
//	private final ReplyRepository replyRepository;


	@Transactional
	public void 글쓰기(Board board, User user) { // title, content
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
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});
	}

	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(() -> {
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				}); //영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수가 종료시에 (Service가 종료될 때) 트랜잭션이 종료된다. 이때 더티체킹
		//자동 업데이트가됨. db쪽에 flush
	}
	
	@Transactional
	public void 업데이트(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(() -> {
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				}); //영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		board.setState(1);
		//해당 함수가 종료시에 (Service가 종료될 때) 트랜잭션이 종료된다. 이때 더티체킹
		//자동 업데이트가됨. db쪽에 flush
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
		replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
	}
	
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}

}

//User user = userRepository.findById(replySaveRequestDto.getUserId())
//.orElseThrow(() -> {
//return new IllegalArgumentException("댓글 쓰기 실패 : 유저 id를 찾을 수 없습니다.");
//});
//
//Board board = boardRepository.findById(replySaveRequestDto.getBoardId())
//.orElseThrow(() -> {
//return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id를 찾을 수 없습니다.");
//});
//
//Reply reply = Reply.builder()
//.user(user)
//.board(board)
//.content(replySaveRequestDto.getContent())
//.build();
//
