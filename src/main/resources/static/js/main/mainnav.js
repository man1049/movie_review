//네비게이션 버튼이 햄버거 버튼일 때
$('.nav-button-o').click(function() {
	$('.nav-button-o').css("display","none");
	$('.nav-button-c').css("display","block");
	$('.nav').slideDown('fast').css("display", "flex");
	});
//네비게이션 버튼이 X 버튼일 때
$('.nav-button-c').click(function() {
	$('.nav-button-c').css("display","none");
	$('.nav-button-o').css("display","block");
	$('.nav').slideUp('fast');
	});