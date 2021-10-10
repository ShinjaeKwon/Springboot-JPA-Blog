let index = {
	init: function() {
		$("#btn-save").on("click", () => {
			this.save();
		});
		$("#btn-delete").on("click", () => {
			this.deleteById();
		});
		$("#btn-update").on("click", () => {
			this.update();
		});
		$("#btn-release-update").on("click", () => {
			this.release_update();
		})		
		
	},

	save: function() {
		if($("#title").val() == ""){
			alert("제목을 입력해주세요.");
			$("#title").focus();
			return false;
		}
		if($("#content").val() == ""){
			alert("내용을 입력해주세요.");
			$("#content").focus();
			return false;
		}
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};

		$.ajax({
			type: "POST",
			url: "/api/release",
			data: JSON.stringify(data),
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		}).done(function(resp) {
			alert("글쓰기가 완료되었습니다.");
			location.href = "/release"
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});

	},
	deleteById: function() {
		let id = $("#id").text();

		$.ajax({
			type: "DELETE",
			url: "/api/release/"+id,
			dataType: "json"
		}).done(function(resp) {
			alert("삭제가 완료되었습니다.");
			location.href = "/release"
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	update: function() {
		if($("#title").val() == ""){
			alert("제목을 입력해주세요.");
			$("#title").focus();
			return false;
		}
		if($("#content").val() == ""){
			alert("내용을 입력해주세요.");
			$("#content").focus();
			return false;
		}
		let id= $("#id").val();
			
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
	
		$.ajax({
			type: "PUT",
			url: "/api/release/"+id,
			data: JSON.stringify(data),
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		}).done(function(resp) {
			alert("글수정이 완료되었습니다.");
			location.href = "/release"
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});

	},
}

index.init();
