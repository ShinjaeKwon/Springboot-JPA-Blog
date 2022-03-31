package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.service.ReleaseService;

@Controller
public class ReleaseController {

	@Autowired
	private ReleaseService releaseService;

	@GetMapping({"/release"})
	public String index(Model model,
		@PageableDefault(size = 10, sort = "id", direction = Direction.DESC) Pageable pageable) {
		model.addAttribute("release", releaseService.글목록(pageable));
		return "release/release";
	}

	@GetMapping("/release/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("release", releaseService.글상세보기(id));

		return "release/detail";
	}

	@GetMapping("release/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("release", releaseService.글상세보기(id));
		return "release/updateForm";
	}

	@GetMapping("/release/saveForm")
	public String saveForm() {
		return "release/saveForm";
	}

}