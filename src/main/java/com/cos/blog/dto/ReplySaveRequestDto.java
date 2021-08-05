package com.cos.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplySaveRequestDto {
	private int userId;
	private int boardId;
	private String content;
}

//Dto의 장점
//데이터를 한방에 받을 수 있다.
