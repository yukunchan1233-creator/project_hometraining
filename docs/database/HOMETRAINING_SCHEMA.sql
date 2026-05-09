-- ============================================
-- HOMETRAINING 프로젝트 - 전체 데이터베이스 스키마 생성 SQL
-- 작성일: 2024
-- 설명: 홈트레이닝 가격비교 사이트의 테이블/시퀀스/인덱스 생성
-- DB: Oracle
-- ============================================

-- ============================================
-- 1. 회원 테이블 (htm_member)
-- ============================================
CREATE TABLE htm_member (
    userid VARCHAR2(50) NOT NULL,
    writer VARCHAR2(20) NOT NULL,
    password VARCHAR2(300),
    phone VARCHAR2(20),
    email VARCHAR2(50) NOT NULL,
    CONSTRAINT htm_mem_pk PRIMARY KEY(userid)
);

-- ============================================
-- 2. 블로그/포트폴리오 테이블 (htm_blog)
-- ============================================
CREATE TABLE htm_blog (
    bno NUMBER NOT NULL,
    name VARCHAR2(20) NOT NULL,
    title VARCHAR2(100) NOT NULL,
    content VARCHAR2(4000) NOT NULL,
    imgfile VARCHAR2(500) NOT NULL,
    views NUMBER DEFAULT 0,
    regdate DATE DEFAULT SYSDATE,
    CONSTRAINT htm_blog_pk PRIMARY KEY (bno)
);

-- 블로그 시퀀스
CREATE SEQUENCE htm_blog_seq;

-- ============================================
-- 3. 찜하기 테이블 (htm_mywish)
-- ============================================
CREATE TABLE htm_mywish (
    wish_bno NUMBER NOT NULL,
    blog_bno NUMBER NOT NULL,
    userid VARCHAR2(50) NOT NULL,
    CONSTRAINT htm_mywish_blog_fk FOREIGN KEY (blog_bno) REFERENCES htm_blog (bno) ON DELETE CASCADE,
    CONSTRAINT htm_mywish_member_fk FOREIGN KEY (userid) REFERENCES htm_member (userid) ON DELETE CASCADE,
    CONSTRAINT htm_mywish_pk PRIMARY KEY (wish_bno),
    CONSTRAINT htm_mywish_unique UNIQUE (blog_bno, userid)
);

-- 찜하기 시퀀스
CREATE SEQUENCE htm_mywish_seq;

-- ============================================
-- 4. 제품 테이블 (TBL_PRODUCT)
-- ============================================
CREATE TABLE TBL_PRODUCT (
    pno NUMBER PRIMARY KEY,
    category VARCHAR2(20) NOT NULL,
    subcategory VARCHAR2(50) NOT NULL,
    site_name VARCHAR2(50) NOT NULL,
    product_name VARCHAR2(200) NOT NULL,
    price NUMBER,
    review_count NUMBER DEFAULT 0,
    image_path VARCHAR2(500),
    detail_images VARCHAR2(2000),
    buy_link VARCHAR2(500),
    userid VARCHAR2(50),
    regdate DATE DEFAULT SYSDATE
);

-- 제품 시퀀스
CREATE SEQUENCE SEQ_PRODUCT
START WITH 1
INCREMENT BY 1;

-- 제품 테이블 인덱스 (조회 성능 향상)
CREATE INDEX IDX_PRODUCT_USERID ON TBL_PRODUCT(userid);
CREATE INDEX IDX_PRODUCT_SUBCATEGORY ON TBL_PRODUCT(subcategory);
CREATE INDEX IDX_PRODUCT_CATEGORY ON TBL_PRODUCT(category);

-- ============================================
-- 5. 제품 후기 테이블 (TBL_PRODUCT_REVIEW)
-- ============================================
CREATE TABLE TBL_PRODUCT_REVIEW (
    review_no NUMBER PRIMARY KEY,
    pno NUMBER NOT NULL,
    userid VARCHAR2(50) NOT NULL,
    rating NUMBER NOT NULL,
    review_text VARCHAR2(2000),
    review_image VARCHAR2(500),
    regdate DATE DEFAULT SYSDATE,
    CONSTRAINT review_product_fk FOREIGN KEY (pno) REFERENCES TBL_PRODUCT (pno) ON DELETE CASCADE,
    CONSTRAINT review_member_fk FOREIGN KEY (userid) REFERENCES htm_member (userid) ON DELETE CASCADE,
    CONSTRAINT review_rating_check CHECK (rating >= 1 AND rating <= 5),
    CONSTRAINT review_unique UNIQUE (pno, userid)
);

-- 제품 후기 시퀀스
CREATE SEQUENCE SEQ_PRODUCT_REVIEW
START WITH 1
INCREMENT BY 1;

-- 제품 후기 인덱스
CREATE INDEX IDX_REVIEW_PNO ON TBL_PRODUCT_REVIEW(pno);
CREATE INDEX IDX_REVIEW_USERID ON TBL_PRODUCT_REVIEW(userid);
CREATE INDEX IDX_REVIEW_RATING ON TBL_PRODUCT_REVIEW(rating);

-- ============================================
-- 6. 운영 편의용 데이터 정리 SQL
-- ============================================

-- 기존 데이터에 admin 계정 설정
UPDATE TBL_PRODUCT
SET userid = 'admin',
    regdate = SYSDATE
WHERE userid IS NULL OR userid = 'java02';

-- 이미 기존 테이블이 있고 review_image만 추가해야 한다면 사용
-- ALTER TABLE TBL_PRODUCT_REVIEW ADD review_image VARCHAR2(500);

COMMIT;

-- ============================================
-- [주의] 아래는 삭제 SQL 예시(필요할 때만 수동 실행)
-- ============================================
-- DROP SEQUENCE htm_reply_seq;
-- DROP TABLE htm_reply;
-- COMMIT;
