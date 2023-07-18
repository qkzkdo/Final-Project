/**
 * 회원가입 정규식
 */
//정규식
var check =[
	{
		item: "이메일",
		regex:/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
		comment: "예) test@test.co.kr, test@test.com"
	},
	{
		item: "비밀번호",
		regex:/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/,
		comment: "대문자,소문자,숫자 조합 8자리이상"
		
	},
	{
		item: "이름",
		regex:/^[가-힣]{2,6}$/,
		comment: "한글 2글자이상 6자리까지만 허용"
	}
]
var msg="";

function validateInput(target, idx) {
	
	//*
	idx=parseInt(idx);
	//alert(check[idx])
	var inputText = $(target).val().trim();
	if (inputText == ""){//비어있을때
		msg= check[idx].item
		if(idx==1) msg+="를 "
		else msg+="을 "
		msg+="입력해주세요.";
		$(target).val("");
	}else if ( !(check[idx].regex.test(inputText)) ){//유효성검사 실패시
		msg= "유효한 "+check[idx].item+" 형식이 아닙니다.";
		
		if(idx==1){
			msg+="</br>대문자,소문자,숫자 조합 8자리이상으로"
		}
	}else if(idx==0 && existEmailCheck(inputText)){
		msg="이미 존재하거나 탈퇴한 회원입니다.";
	}else{
		//문제가 없는경우
		checkTrue(target);
		$(target).parents("li").find(".msg").html("");
		return;
	}
	
	
	//에러메세지 출력
	msgPrint(target);
	//*/
}
function checkTrue(target){
	$(target).siblings(".required").prop('checked', true); // 체크됨
}
function msgPrint(target){
	$(target).parents("li").find(".msg").html(msg);
	$(target).siblings(".required").prop('checked', false); // 체크해제됨
}

//비밀번호 확인 필드의 유효성을 검사하는 함수 입력된값과 일치하는지 확인
function validateRePass(target){
	var rePass=$(target).val()
	var pass=$("#pass").val()
	if(rePass==""){
		msg="비밀번호 재확인 입력하지 않았습니다.";
		msgPrint(target);
		return;
	}else if(pass==rePass){
		checkTrue(target);
		$(target).parents("li").find(".msg").html("");
		return;
	}
	msg="비밀번호가 일치하지 않습니다.";
	msgPrint(target);
}

// 회원가입 폼 유효성 검사 함수
function validateForm() {
	var checkall=true;
	$("#form-signup .required").each(function() {
	    // 체크박스마다 실행할 코드 작성
	    if (!$(this).is(":checked")) {
			console.log("체크누락!")
	        msg="필수입력 항목 입니다.";
	        msgPrint($(this));
	        checkall=false;
	        return;
	    }
	});
	return checkall;
}

function existEmailCheck(email){
	var isExist=false;
	$.ajax({
			url:"/common/email-check",
			async:false,
			data: {email: email},
			success: function(result){
				isExist=result;
			}
		});
	return isExist;
}