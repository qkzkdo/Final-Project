/**
 * 
 * 
 */
/*
$(function(){
	alert("jquery");
})
*/

const I_TAG=3
$(function(){
	ceratedTag();
});

//이미지 전송?
function imgSend(){
	var bks=$(".bucket-key");
	var ons=$(".org-name");
	var nns=$(".new-name");
	var dto=[];
	for(var i=0; i<bks.length; i++){
		dto[i]={
			bucketKey: $(bks[i]).val(),
			orgName: $(ons[i]).val(),
			newName: $(nns[i]).val()
		}
	}
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	 $.ajax({
		 beforeSend:function(xhr) {//csrf적용시 
			xhr.setRequestHeader(header, token); 
		 },		
		url:"/admin/item/test",
		type:"post",
		
		data: {dtos:JSON.stringify(dto)}
	});
	console.log(dto);
}

//이미지 저장 썸네일 보이게
function ceratedTag(){
	var tag=`
	<i class="img-wrap">
		<label >
			<span>+</span>
			<input type="file" onchange="tempUpload(this)">
		</label>
		<input type="hidden" class="bucket-key" name="bucketKey" >
		<input type="hidden" class="org-name" name="orgName" >
		<input type="hidden" class="new-name" name="newName" >
	</i>
	`
	$("#img-area").append(tag);
}


//ajax로 비동기 파일업로드
//FormData를 활용해서 적용하는 방법
 function tempUpload(fileTag){
	 
	 var target=$(fileTag);
	 
	 var img=target[0].files[0];
	 var formData=new FormData();
	 
	 //Controller의 MultipartFile 매개변수와 일치하게 MultipartFile tempImg
	 formData.append("tempImg", img);
	 
	 
	//*
	//csrf적용시 
	//https://docs.spring.io/spring-security/reference/5.8/servlet/exploits/csrf.html
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	 $.ajax({
		 beforeSend:function(xhr) {//csrf적용시 
			xhr.setRequestHeader(header, token); 
		 },	//w3s 에서 	
		 url:"/admin/item/temp",
		 type:"post",
		 data: formData,
		 contentType:false,
		 processData:false,
		 success: function(map){ //콘솔에서 이미지주소가 나오는지 확인 가능 
			 target.parents(".img-wrap").find("label")
			 	.css("background-image", `url(${map.url})`);
			 target.parents(".img-wrap").find(".bucket-key")
			 	.val(map.bucketKey);
			 target.parents(".img-wrap").find(".org-name")
			 	.val(map.orgName);
			 target.parents(".img-wrap").find(".new-name")
			 	.val(map.newName);	
			 
			 	
			 //추가 이미지입력하기위한 태그 생성
			 //1. 최대 몇개가지 추가 할껀지
			 var iTagEa=$("#img-area>.img-wrap").length;
			 console.log("태그 총 개수:"+iTagEa);
			 var pos=target.parents(".img-wrap").index()+1;
			 console.log("이미지 위치 :"+pos);
			 if(iTagEa<I_TAG &&  (iTagEa-pos)<=0){
			 	ceratedTag();
			 }
			 //2. 중간 이미지를 수정한다면 추가하지 않기
		 }
	 })
	 //*/
 }