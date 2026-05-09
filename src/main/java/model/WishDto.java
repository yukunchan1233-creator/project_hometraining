package model;

public class WishDto {
	private int wish_bno;
	private int bno;
    private String name;
    private String title;
    private String content;
    private String imgfile;
    private int views;
    private String  regdate;
    
    // 제품 찜용 필드
    private String type;  // "blog" 또는 "product"
    private int pno;  // 제품 번호 (제품 찜일 경우)
    private String product_name;  // 제품명
    private String site_name;  // 사이트명
    private int price;  // 가격
    private String subcategory;  // 하위카테고리
    
	
    
    public int getWish_bno() {
    	return wish_bno;
    }
    public void setWish_bno(int wish_bno) {
    	this.wish_bno = wish_bno;
    }
    public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImgfile() {
		return imgfile;
	}
	public void setImgfile(String imgfile) {
		this.imgfile = imgfile;
	}
	
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
	// 제품 찜용 getter/setter
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
}




