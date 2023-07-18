/**
 * 
 */
$(function(){
	$("form").submit(function(event){
		event.preventDefault();
	});
	
	$("#btn-search").click(function(){
		btnSearchClicked(1);
	});
	restBoardList(1)
	
});

function btnSearchClicked(page){
	
	var data=$("#form-search").serialize()+"&page="+page;
	$.ajax({
		url:"/rest-item/search",
		type: "PATCH",
		data:data,
		success:function(result){
			$("#item-list").html(result);
		}
	});
}

function restBoardList(page){
	$.ajax({
		url:"/rest-item",
		type: "PATCH",
		data:{page:page},
		success:function(result){
			$("#item-list").html(result);
		}
	});
}