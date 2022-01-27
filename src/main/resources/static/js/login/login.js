window.addEventListener('load', function(e) {
	$('.login').fadeIn(1000);
});

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



let name = document.getElementById("user_nickname").value

let passwordCheck = document.getElementById("join_user_password2").value
let gender_man = document.getElementById("gender_man").checked
let gender_woman = document.getElementById("gender_woman").checked
let user_year = document.getElementById("user_year").value
let user_month = document.getElementById("user_month").value
let user_day = document.getElementById("user_day").value
let user_question = document.getElementById("user_question").value
let user_answer = document.getElementById("user_answer").value
let email_check = false;
let password_check = false;

// 이메일확인
$('#join_user_email').on('focusout',function (event) {
	let email = document.getElementById("join_user_email").value
	if (email.includes('@')) {
		let emailId = email.split('@')[0]
		let emailServer = email.split('@')[1]
		if (emailId === "" || emailServer === "") {
			$('.input-warning-email').html("이메일이 올바르지 않습니다.").css("color","white")
			email_check = false
		} else {
			$('.input-warning-email').html("")
			email_check = true
		}
	} else {
		$('.input-warning-email').html("이메일이 올바르지 않습니다.").css("color","white")
		email_check = false
	}
})

$('#user_password1').on('focusout',function (event) {
	let password = document.getElementById("join_user_password1").value
	if (password.includes('!') || password.includes('@')||
		password.includes('#') || password.includes('&')||
		password.includes('$') || password.includes('*')||
		password.includes('%') || password.includes('^')){


	}

	if (password === "") {
		document.getElementById("passwordError").innerHTML = "비밀번호를 입력해주세요."
		check = false
	} else {
		document.getElementById("passwordError").innerHTML=""
	}
})

