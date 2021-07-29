package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;

@ControllerAdvice //어떤 페이지에서라도 Exception이 발생하게 되면 , 이함수가 실행되게 해준다.
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(value=Exception.class) //Exception가 발생하면 아래의 함수가 처리한다.
	public ResponseDto<String> handleArgumentException(IllegalArgumentException e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
	
	
}
