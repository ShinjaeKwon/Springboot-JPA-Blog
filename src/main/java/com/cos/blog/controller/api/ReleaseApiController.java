package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.ReleaseShoe;
import com.cos.blog.service.ReleaseService;

@RestController
public class ReleaseApiController {

	@Autowired
	private ReleaseService releaseService;

	@PostMapping("/api/release")
	public ResponseDto<Integer> save(@RequestBody ReleaseShoe board, @AuthenticationPrincipal PrincipalDetail principal) { 
		releaseService.글쓰기(board, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
	@DeleteMapping("/api/release/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id){
		releaseService.글삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}

	@PutMapping("/api/release/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody ReleaseShoe board){	
		releaseService.글수정하기(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	

}


