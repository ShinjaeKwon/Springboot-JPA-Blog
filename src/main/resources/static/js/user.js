let index = {
	init: function() {
		$("#btn-save").on("click", () => { //function(){}, ()=> 을 대신 사용하는 이유는 this를 바인딩하기 위해서이다.
			this.save();
		});
		$("#btn-update").on("click", () => { //function(){}, ()=> 을 대신 사용하는 이유는 this를 바인딩하기 위해서이다.
			this.update();
		});
		
	},

	save: function() {
		//alert('user의 save함수 호출됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),	
			email: $("#email").val()
		};
	
		//console.log(data);
		
		// ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		//회원가입시 ajax를 사용하는 2가지 이유.
		//1. 요청에 대한 응답을 html이 아닌 Data(Json)를 받기 위해서
		//2. 비동기 통신을 하기 위해서
		
		//ajax호출시 default가 비동기 호출
		//ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해준다.
		$.ajax({ //회원가입 수행을 요청 
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), //JSON 데이터로 변경, http body데이터
			contentType: "application/json; charset=UTF-8", //body 데이터가 어떤 타입인지(MIME)
			dataType: "json" //요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면)=> javascript 오브젝트로 변경을 해준다.
		}).done(function(resp){ //정상일시 실행
			if(resp.status === 500){
				alert("회원가입이 실패 하였습니다.");
			}else{
				alert("회원 가입이 완료 되었습니다.");
			}
			location.href = "/"
		}).fail(function(error){ //실패할시 실행
			alert(JSON.stringify(error));
		});
		
	},
	update: function() {
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		$.ajax({ 
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data),
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		}).done(function(resp){ 
			alert("회원 수정이 완료 되었습니다.");
			console.log(resp);
			location.href = "/"
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
		
	},
}

index.init();


/*	$("#btn-login").on("click", () => { //function(){}, ()=> 을 대신 사용하는 이유는 this를 바인딩하기 위해서이다.
			this.login();
		});*/
		
		
/*	login: function() {
		//alert('user의 save함수 호출됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val()
		};
		

		$.ajax({
			type: "POST",
			url: "/api/user/login",
			data: JSON.stringify(data),
			contentType: "application/json; charset=UTF-8",
			dataType: "json" 
		}).done(function(resp){
			alert("로그인이 완료 되었습니다.");
			location.href = "/"
		}).fail(function(error){ 
			alert(JSON.stringify(error));
		});
		
	}	*/	
		