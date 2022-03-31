package com.cos.blog.dto;

import com.cos.blog.model.Board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {

	private int id;
	private String title;
	private String content;

	@Builder
	public BoardDto(int id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public Board toEntity() {
		Board build = Board.builder()
			.id(id)
			.title(title)
			.content(content)
			.build();
		return build;
	}
}
