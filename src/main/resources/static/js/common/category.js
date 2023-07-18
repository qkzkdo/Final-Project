/**
 * 카테고리 가변적으로 적용할때 사용할수 있는 JS
 */
//카테고리 n차 개수
const CATE_EA=2;
$(function(){
	createCategoryTag(CATE_EA);
});

//카테고리 선택 태그 생성
function createCategoryTag(depth){
	
	for(var i=1; i<= depth ; i++){
		var tag=`
		<span class="cate-wrap">
			<select name="categoryNo" class="cate-${i}" ${i==depth?'':'onchange="category(this)"'}  >
				<option value="0">${i}차카테고리</option>
			</select>
		</span>
		`;
		$("#category").append(tag);
	}
	category($(".cate-1"));
}

//선택된 태그 처리
function category(selectTag){
	var cateWrap=$(selectTag).parents(".cate-wrap");
	var cateNo=$(selectTag).val();
	var i=cateWrap.index(); //i==0: 1차
	var target=(i==0&&cateNo==0)?cateWrap:cateWrap.next();
	
	if( i>0 && cateNo==0 )return;//0이면 함수종료
	//0이아닌경우 아래쪽 실행
	$.ajax({ //ajax 요청을 통해 URL로 서버에 요청을 전송
		url:`/common/category-select/${cateNo}`,
		success:function(result){
			targetNextAllTagReset(target);
			target.find("select").append(result);
		}
	});
}

//target 이후의 모든 태그를 초기화하는 함수
function targetNextAllTagReset(target){
	var ti=target.index()
	$(".cate-wrap").each((i,el)=>{
		if(i>=ti){
			$(el).find(".cate").remove();
		}
	});
	
}