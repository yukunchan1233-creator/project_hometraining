package model;

// ProductDto : 운동기구 제품 정보를 담는 DTO 클래스
public class ProductDto {
	
	// 제품 기본 정보
	private int pno;                    // 제품번호
	private String category;            // 카테고리 (가슴, 등, 하체, 유산소)
	private String subcategory;        // 하위카테고리 (벤치프레스, 딥스 등)
	private String site_name;          // 사이트명 (이고진, 반석 등)
	private String product_name;       // 제품명
	private int price;                  // 가격
	private int review_count;           // 후기 개수
	private String image_path;          // 대표 이미지 경로
	private String detail_images;       // 상세 이미지들 (쉼표로 구분)
	private String buy_link;            // 구매 링크
	private String userid;              // 등록자 아이디 (관리자 계정)
	private String regdate;             // 등록일
	
	// Getter/Setter 메서드들
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getReview_count() {
		return review_count;
	}
	public void setReview_count(int review_count) {
		this.review_count = review_count;
	}
	public String getImage_path() {
		return image_path;
	}
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	public String getDetail_images() {
		return detail_images;
	}
	public void setDetail_images(String detail_images) {
		this.detail_images = detail_images;
	}
	public String getBuy_link() {
		return buy_link;
	}
	public void setBuy_link(String buy_link) {
		this.buy_link = buy_link;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
}







