package com.cos.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {

	List<Board> findByTitleContaining(String keyword);
}

