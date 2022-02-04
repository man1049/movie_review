////////////////////////////////////////////////////////////////////////////
/*여기부터 페이지 이동버튼입니다*/
$(document).ready(function (){
	$('.login').fadeIn(1000);
	/*
	let auth = new URLSearchParams(location.search).get("authentication");
	let join = new URLSearchParams(location.search).get("join");
	let findpw = new URLSearchParams(location.search).get("findpw");
	*/
	setTimeout(function () {
		let auth = $('#alert_auth').text();
		let join = $('#alert_join').text();
		let findpw = $('#alert_findpw').text();
		let pwchange = $('#alert_pwchange').text();

		if(auth === "true"){
			alert("이미 인증이 완료되었습니다.")
		}else if(auth === "false"){
			alert("인증이 완료되었습니다.")
		}

		if(join === "true"){
			alert("회원가입이 완료되었습니다. 인증메일을 확인해주세요.")
		}else if(join === "false"){
			alert("알 수 없는 이유로 가입에 실패하였습니다.")
		}
		if(findpw === "true"){
			alert("비밀번호 변경을 메일이 발송되었습니다.")
		}else if(findpw === "false"){
			alert("이메일또는 질문또는 답변이 잘못되었습니다.")
		}
		if(pwchange === "true"){
			alert("비밀번호 변경이 완료되었습니다.")
		}else if(pwchange === "false"){
			alert("알 수 없는 이유로 비밀번호 변경에 실패하였습니다.")
		}
	},1000)
	// 로컬스토리지에 해당 키가 있다면
	// 불러옵니다.(자동로그인)
	let user_email = localStorage.getItem("user_email")
	let user_password = localStorage.getItem("user_password")

	if(localStorage.getItem("user_autologin") == "true"){
		$("#autologin").prop("checked",true)
		$("#login_user_email").prop("value",user_email)
		$("#login_user_password").prop("value",user_password)
	}
})

$('.user-join').on('click',function(){
	$('.login').fadeOut(500);
	setTimeout(function() {
		$('.join').fadeIn(500);
	}, 500)
});

$('.user-findid').on('click',function(){
	$('.login').fadeOut(500);
	setTimeout(function() {
		$('.findid').fadeIn(500);
	}, 500)
});

$('.user-findpw').on('click',function(){
	$('.login').fadeOut(500);
	setTimeout(function() {
		$('.findpw').fadeIn(500);
	}, 500)
});

$('.findid-close').on('click',function(){
	$('.findid').fadeOut(500);
	setTimeout(function() {
		$('.login').fadeIn(500);
	}, 500)
});

$('.findpw-close').on('click',function(){
	$('.findpw').fadeOut(500);
	setTimeout(function() {
		$('.login').fadeIn(500);
	}, 500)
});

$('.join-close').on('click',function(){
	$('.join').fadeOut(500);
	setTimeout(function() {
		$('.login').fadeIn(500);
	}, 500)
});
/*여기까지 페이지 이동버튼입니다*/
////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////
/*여기서 부터 로그인 페이지입니다*/

//자동 로그인
$(".login-border-contents-form").submit(function () {
	let user_email = document.getElementById("login_user_email").value
	let user_password = document.getElementById("login_user_password").value

	if($("#autologin").is(":checked")){
		localStorage.setItem("user_email",user_email)
		localStorage.setItem("user_password",user_password)
		localStorage.setItem("user_autologin","true")
	}else{
		localStorage.removeItem("user_email")
		localStorage.removeItem("user_password")
		localStorage.removeItem("user_autologin")
	}
})

/*여기까지 로그인 페이지입니다*/
////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////
/*여기서부터 회원가입 페이지입니다*/
let password
let join_user_yearmonthday

let nowDate = new Date()
let nowYear = nowDate.getFullYear()

let email_check = false
let password1_check = false
let password2_check = false
let nickname_check = false
let yearmonthday_check = false
let gender_check = false
let user_question_check = false
let user_answer_check = false

// 이메일
function emailChecking() {
	let email = document.getElementById("join_user_email").value
	if(email === "" || email.trim() === "") {
		email_check = false
		$('.input-warning-email').html("이메일을 입력해주세요")
		join_submit_button_return()
	}else{
		if (email.includes('@')) {
			let emailId = email.split('@')[0]
			let emailServer = email.split('@')[1]
			if (emailId === "" || emailServer === "") {
				email_check = false
				$('.input-warning-email').html("이메일이 올바르지 않습니다")
				join_submit_button_return()
			} else {
				$.ajax({
					type: "POST",
					url: "join/emailcheck",
					data: $("#join_user_email").val(),
					success:function (flag) {
						if(flag){
							$('.input-warning-email').html("이미 가입 된 이메일입니다")
							$('#join_user_email').prop("value","").focus()
							email_check = false
							join_submit_button_return()
						}else{
							email_check = true
							$('.input-warning-email').html("")
							join_submit_button()
						}
					},
					error:function (XMLHttpRequest, textStatus, errorThrown) {
						alert("잠시 후에 다시 시도해주세요.")
					}
				})
			}
		} else {
			email_check = false
			$('.input-warning-email').html("이메일이 올바르지 않습니다")
			join_submit_button_return()
		}
	}
}

// 비밀번호
function passwordChecking() {
	password = document.getElementById("join_user_password1").value
	let pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{0,}$"
	if (password === "") {
		password1_check = false
		$('.input-warning-userpassword1').html("비밀번호를 입력해주세요")
		join_submit_button_return()
	} else {
		$('.input-warning-userpassword1').html("")
		if (password.match(pattern)){
			$('.input-warning-userpassword1').html("")
			if(password.length < 8 || password.length > 16){
				password1_check = false
				$('.input-warning-userpassword1').html("8자리이상 16자리이하여야 합니다")
				join_submit_button_return()
			}else{
				password1_check = true
				$('.input-warning-userpassword1').html("")
				join_submit_button()
			}
		}else{
			password1_check = false
			$('.input-warning-userpassword1').html("특수문자,알파벳 대,소문자,숫자가 포함되어야합니다")
			join_submit_button_return()
		}
	}
}

// 비밀번호 확인
function password2Checking() {
	let password2 = document.getElementById("join_user_password2").value
	if(password1_check == true){
		$('.input-warning-userpassword2').html("")

		if(password2 === "" && password2.trim() === ""){
			password2_check = false
			$('.input-warning-userpassword2').html("비밀번호를 입력해주세요")
			join_submit_button_return()
		}else{
			$('.input-warning-userpassword2').html("")
			if(password !== password2){
				$('.input-warning-userpassword2').html("비밀번호가 다릅니다")
				join_submit_button_return()
			}else{
				password2_check = true
				$('.input-warning-userpassword2').html("")
				join_submit_button()
			}
		}
	}else{
		password2_check = false
		$('.input-warning-userpassword2').html("비밀번호를 확인해주세요")
		join_submit_button_return()
	}
}

// 닉네임
function nicknameChecking() {
	let nickname = document.getElementById("join_user_nickname").value
	let pattern = "^[a-zA-Zㄱ-힣0-9]*$"
	if(nickname === ""){
		nickname_check = false
		$('.input-warning-nickname').html("닉네임을 입력해주세요")
		join_submit_button_return()

	}else{
		if(nickname.length < 2 || nickname.length > 7){
			$('.input-warning-nickname').html("2자리 이상 7자리 이하여야 합니다")
			join_submit_button_return()
		}else{
			$('.input-warning-nickname').html("")
			if(nickname.match(pattern)){

				$.ajax({
					type: "POST",
					url: "join/nicknamecheck",
					data: $("#join_user_nickname").val(),
					success:function (flag) {
						if(flag){
							nickname_check = false
							$('.input-warning-nickname').html("중복 된 닉네임입니다")
							$('#join_user_nickname').prop("value","").focus()
							join_submit_button_return()
						}else{
							nickname_check = true
							$('.input-warning-nickname').html("")
							join_submit_button()
						}
					}
				})
			}else{
				nickname_check = false
				$('.input-warning-nickname').html("영어와 한글만 사용이 가능합니다")
				join_submit_button_return()
			}

		}

	}
}

//주민번호 왼쪽
function rrnLeftChecking() {
	join_user_yearmonthday = document.getElementById("join_user_yearmonthday").value
	let pattern = "^[0-9]*$"
	if(join_user_yearmonthday === ""){
		yearmonthday_check = false
		$('.input-warning-yearmonthday').html("주민번호 앞자리 6자리를 입력해주세요")
		join_submit_button_return()
	}else{
		if(join_user_yearmonthday.length < 6){
			if(join_user_yearmonthday.match(pattern)){
				yearmonthday_check = false
				$('.input-warning-yearmonthday').html("6자리를 입력해주세요")
				join_submit_button_return()
			}else{
				yearmonthday_check = false
				$('.input-warning-yearmonthday').html("숫자 6자리를 입력해주세요")
				join_submit_button_return()
			}
			join_submit_button_return()
		}else{
			if(join_user_yearmonthday.match(pattern)){
				let join_user_month = parseInt(join_user_yearmonthday.substr(2,2))
				if(join_user_month > 0 && join_user_month < 13){
					let join_user_day = parseInt(join_user_yearmonthday.substr(4,2))
					if(join_user_day > 0 && join_user_day < 32){
						yearmonthday_check = true
						$('.input-warning-yearmonthday').html("")
						join_submit_button()
					}else{
						yearmonthday_check = false
						$('.input-warning-yearmonthday').html("올바르지 않은 주민번호입니다")
						join_submit_button_return()
					}
				}else{
					yearmonthday_check = false
					$('.input-warning-yearmonthday').html("올바르지 않은 주민번호입니다")
					join_submit_button_return()
				}

			}else{
				yearmonthday_check = false
				$('.input-warning-yearmonthday').html("숫자를 입력해주세요")
				join_submit_button_return()
			}
		}
	}
}

//주민번호 오른쪽
function rrnRightChecking() {
	let join_user_gender = document.getElementById("join_user_gender").value
	let pattern = "^[1-4]*$"
	if(join_user_gender === ""){
		$('.input-warning-gender').html("주민번호 뒷자리 첫째자리를 입력해주세요")
	}else{
		if(join_user_gender.match(pattern)){
			let join_user_year = parseInt(join_user_yearmonthday.substr(0,2))
			join_user_gender = parseInt(join_user_gender)

			if(((join_user_year+1900) > nowYear-100) && (join_user_gender === 1 || join_user_gender === 2))
			{
				gender_check = true
				$('.input-warning-gender').html("")
				join_submit_button()
			}else{
				if(((join_user_year+2000) < nowYear) && (join_user_gender === 3 || join_user_gender === 4)){
					gender_check = true
					$('.input-warning-gender').html("")
					join_submit_button()
				}else{
					gender_check = false
					$('.input-warning-gender').html("올바르지 않은 주민번호입니다")
					join_submit_button_return()
				}
			}
		}else{
			gender_check = false
			$('.input-warning-gender').html("1~4만 입력 가능합니다")
			join_submit_button_return()
		}
	}
}

function questionChecking() {
	let user_question = document.getElementById("join_user_question").value
	if(user_question === ""){
		user_question_check = false
		$('.input-warning-question').html("아이디및 비밀번호 찾기 질문을 입력해주세요")
		join_submit_button_return()
	}else{
		user_question_check = true
		$('.input-warning-question').html("")
		join_submit_button()
	}
}

function answerChecking() {
	let user_answer = document.getElementById("join_user_answer").value
	if(user_answer === ""){
		user_answer_check = false
		$('.input-warning-answer').html("아이디및 비밀번호 찾기 답변을 입력해주세요")
		join_submit_button_return()
	}else{
		user_answer_check = true
		$('.input-warning-answer').html("")
		join_submit_button()
	}
}


$('#join_user_email').on('focusout',function (event) {
	emailChecking()
})

$('#join_user_password1').on('focusout',function (event) {
	passwordChecking()
})

$('#join_user_password2').on('focusout',function (event) {
	password2Checking()
})

$('#join_user_nickname').on('focusout',function (event) {
	nicknameChecking()
})

$('#join_user_yearmonthday').on('focusout',function (event) {
	rrnLeftChecking()
})

$('#join_user_gender').on('focusout',function (event) {
	rrnRightChecking()
})

$('#join_user_question').on('focusout',function (event) {
	questionChecking()
})

$('#join_user_answer').on('focusout',function (event) {
	answerChecking()
})

function join_submit_button(){
	if(email_check == true && password1_check == true && password2_check == true && nickname_check == true &&
	gender_check == true && yearmonthday_check == true && user_question_check == true && user_answer_check == true){
		$('#join_submit_notready_div').html("<input type=\"submit\" value=\"가입하기\" id=\"join_submit_ready\"/>")
	}else{
		join_submit_button_return()
	}
}

function join_submit_button_return() {
	$('#join_submit_notready_div').html("<input type=\"button\" value=\"가입하기\" id=\"join_submit_notready\" onclick=\"join_submit_notready_alert();\" />")
}

function join_submit_notready_alert() {
	emailChecking()
	passwordChecking()
	password2Checking()
	nicknameChecking()
	rrnLeftChecking()
	rrnRightChecking()
	questionChecking()
	answerChecking()
}

function joinReturnToLogin() {
	setTimeout(function () {
		document.getElementById("join_user_email").value = ""
		document.getElementById("join_user_password1").value = ""
		document.getElementById("join_user_password2").value = ""
		document.getElementById("join_user_nickname").value = ""
		document.getElementById("join_user_yearmonthday").value = ""
		document.getElementById("join_user_gender").value = ""
		document.getElementById("join_user_question").value = ""
		document.getElementById("join_user_answer").value = ""
		$('.input-warning-email').html("")
		$('.input-warning-userpassword1').html("")
		$('.input-warning-userpassword2').html("")
		$('.input-warning-nickname').html("")
		$('.input-warning-yearmonthday').html("")
		$('.input-warning-gender').html("")
		$('.input-warning-question').html("")
		$('.input-warning-answer').html("")

		join_submit_button_return()
	},500)

}

/*여기까지 회원가입 페이지입니다.*/
/////////////////////////////////////////////////////////////////////////////////