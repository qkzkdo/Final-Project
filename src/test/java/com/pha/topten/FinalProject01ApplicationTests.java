package com.pha.topten;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pha.topten.domain.entity.CategoryEntity;
import com.pha.topten.domain.entity.CategoryEntityRepository;
import com.pha.topten.domain.entity.MyUserEntity;
import com.pha.topten.domain.entity.MyUserEntityRepository;
import com.pha.topten.security.MyRole;

@SpringBootTest
class FinalProject01ApplicationTests {

	@Autowired
	private MyUserEntityRepository dao;
	@Autowired
	private PasswordEncoder pe;
	@Autowired
	private CategoryEntityRepository cateRepo;
	
	//admin계정 생성
	//@Test
	void admin계정생성() {
		
		dao.save(MyUserEntity.builder()
				.email("admin").pass(pe.encode("1234"))
				.nickName("관리자")
				.build()
				.addRole(MyRole.USER)
				.addRole(MyRole.SELLER)
				.addRole(MyRole.ADMIN)
				);
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/* 카테고리 이름 한글+영어 입력 */
	
	// 1차 카테고리 입력 //
	
	//1차카테고리
	String[] kName = {"여성", "남성", "유아동", "잡화"};
	String[] eName = {"female", "male", "child", "goods"};
	
	//@Test
	void 카테1입력_한영() { 
		for (int i = 0; i < kName.length; i++) {
	        cateRepo.save(CategoryEntity.builder()
	                .kName(kName[i])
	                .eName(eName[i])
	                .build());
	    }
	}
	
	
	// 2차 카테고리 입력 //
	
	//2차 카테고리
	String cate1 = "잡화";
	//여성
	String[] femaleKor = {"티셔츠","니트/스웨터","블라우스/셔츠","아우터","스커트","팬츠","원피스","라운지/언더웨어","비치웨어","이너웨어","패션잡화"};
	String[] femaleEng = {"Tshirt","knit_sweater","blouse_shirt","outer","skirt","pants","onepiece","underwear","beachwear","innerwear","fashion_goods"};
	//남성
	String[] maleKor = {"티셔츠","니트/스웨터","가디건","셔츠","아우터","재킷/베스트","팬츠","정장","라운지/언더웨어","비치웨어","시즌이너웨어","패션잡화"};
	String[] maleEng = {"Tshirt","knit_sweater","cardigan","shirt","outer","jacket","pants","suit","underwear","beachwear","innerwear","fashion_goods"};
	//유아동
	String[] childKor = {"여아","남아","베이비","패션잡화"};
	String[] childEng = {"f_child","m_child","baby","fashion_goods"};
	//패션잡화
	String[] goodsKor = {"여성가방","여성지갑","여성패션잡화","남성가방","남성지갑","남성슈즈","남성패션잡화"};
	String[] goodsEng = {"woman_bag","woman_wallet","woman_goods","man_bag","man_wallet","man_shoes","man_goods"};
	
	//@Test
	void 카테2입력_한영() {
	    CategoryEntity parentCategory = cateRepo.findBykName(cate1); //부모 변경
	    if (parentCategory != null) {
	        for (int i = 0; i < goodsKor.length; i++) { //길이변경
	            CategoryEntity category = CategoryEntity.builder()
	                    .kName(goodsKor[i]) //한글 카테고리
	                    .eName(goodsEng[i]) //영어 카테고리
	                    .parent(parentCategory)
	                    .build();
	            cateRepo.save(category);
	        }
	    }
	}

	
}
