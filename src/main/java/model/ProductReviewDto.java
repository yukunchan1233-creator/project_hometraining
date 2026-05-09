package model;

// ProductReviewDto : 제품 후기 정보를 담는 DTO 클래스
public class ProductReviewDto {
	
	private int review_no;        // 후기번호
	private int pno;              // 제품번호
	private String userid;        // 작성자 아이디
	private int rating;           // 별점 (1~5)
	private String review_text;   // 후기 내용
	private String review_image;  // 후기 이미지 경로
	private String regdate;       // 작성일
	
	// 제품 정보 (전체 후기 목록 조회 시 사용)
	private String product_name;  // 제품명
	private String product_image; // 제품 이미지 경로
	private String subcategory;   // 하위카테고리
	
	public int getReview_no() {
		return review_no;
	}
	public void setReview_no(int review_no) {
		this.review_no = review_no;
	}
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getReview_text() {
		return review_text;
	}
	public void setReview_text(String review_text) {
		this.review_text = review_text;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getReview_image() {
		return review_image;
	}
	public void setReview_image(String review_image) {
		this.review_image = review_image;
	}
	
	// 제품 정보 getter/setter
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_image() {
		return product_image;
	}
	public void setProduct_image(String product_image) {
		this.product_image = product_image;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
}
