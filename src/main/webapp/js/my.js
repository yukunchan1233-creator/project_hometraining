// jslhrd가 ROOT(/)로 설정되어 있어서 무조건 /hometraining 사용
var contextPath = '/hometraining';

//로그인 check() 함수 시작
	function check() {
		if(!my.userid.value){
			alert("ユーザーIDを入力してください");
			my.userid.focus();
			return false;
		}
		if(!my.password.value){
			alert("パスワードを入力してください");
			my.password.focus();
			return false;
		}
		return true;
	}
//jQuery 시작문
$(function() {	
	// 헤더 드롭다운 메뉴 (유산소운동, 근력운동) - 메뉴 항목으로 이동해도 유지
	$(".gnb_group .main_nav .nav_1depth > li").hover(
		function() {
			// 마우스 진입
			$(this).find(".nav_2depth").stop(true, false).css({
				'display': 'block',
				'opacity': '1',
				'transform': 'translateY(0)',
				'pointer-events': 'auto'
			});
		},
		function() {
			// 마우스 이탈
			var $menu = $(this).find(".nav_2depth");
			var self = this;
			setTimeout(function() {
				if (!$menu.is(':hover') && !$(self).is(':hover')) {
					$menu.css({
						'display': 'none',
						'opacity': '0',
						'transform': 'translateY(-8px)',
						'pointer-events': 'none'
					});
				}
			}, 150);
		}
	);
	
	$(".gnb_group .main_nav .nav_2depth").hover(
		function() {
			// 메뉴 위에 마우스
			$(this).css({
				'display': 'block',
				'opacity': '1',
				'transform': 'translateY(0)',
				'pointer-events': 'auto'
			});
		},
		function() {
			// 메뉴에서 마우스 이탈
			$(this).css({
				'display': 'none',
				'opacity': '0',
				'transform': 'translateY(-8px)',
				'pointer-events': 'none'
			});
		}
	);
	
	// 마이페이지 드롭다운 메뉴 (개인정보 수정, 찜 목록) - 메뉴 항목으로 이동해도 유지
	$(".gnb_group .user_nav .mypage-dropdown").hover(
		function() {
			// 마우스 진입
			$(this).find(".mypage-menu").stop(true, false).css({
				'display': 'block',
				'opacity': '1',
				'transform': 'translateY(0)',
				'pointer-events': 'auto'
			});
		},
		function() {
			// 마우스 이탈
			var $menu = $(this).find(".mypage-menu");
			var self = this;
			setTimeout(function() {
				if (!$menu.is(':hover') && !$(self).is(':hover')) {
					$menu.css({
						'display': 'none',
						'opacity': '0',
						'transform': 'translateY(-8px)',
						'pointer-events': 'none'
					});
				}
			}, 150);
		}
	);
	
	$(".gnb_group .user_nav .mypage-menu").hover(
		function() {
			// 메뉴 위에 마우스
			$(this).css({
				'display': 'block',
				'opacity': '1',
				'transform': 'translateY(0)',
				'pointer-events': 'auto'
			});
		},
		function() {
			// 메뉴에서 마우스 이탈
			$(this).css({
				'display': 'none',
				'opacity': '0',
				'transform': 'translateY(-8px)',
				'pointer-events': 'none'
			});
		}
	);
	
	//회원가입 페이지에서만 writer 유효성 검사 실행
	if($("#writer").length > 0) {
		$("#writer").focus();
		$("#writer").blur(function() {
			//아이디가 writer인 태그에서 다른곳으로 커서가 이동하면 (blur) 아래를 실행
			if(!$("#writer").val()) {
				//만약에 아이디(#)가 writer인 태그의 값(val)이 없으면(!) 아래를 실행
				//$(".writer-msg").text("사용자명을 입력하세요");
				$(".writer-msg").html("<span style='color:#f00'>ユーザー名を入力してください</span>");
				//클래스(.)이름이 writer-msg인 태그의 내용(html)을 변경
				$("#writer").focus();
				//커서를 다시 아이디(#)가 writer인 태그로 이동(focus)시킨다
			}else{
				$(".writer-msg").text("");
				//클래스(.)이름이 writer-msg인 택드의 내용을 ""로 변경(초기화)한다
			}
		});
	}
		
	//회원가입 페이지에서만 userid 중복체크 실행
	if($("#userid").length > 0) {
		$("#userid").blur(function() {    //아이디가 userid인 태그에서 다른곳으로 커서가 이동하면 (blur) 아래를 실행
			if(!$("#userid").val()) {     //만약에 아이디(#)가 userid인 태그의 값(val)이 없으면(!) 아래를 실행
				$(".userid-msg").html("<span style='color:#f00'>ユーザーIDは必須入力項目です。ユーザーIDを入力してください。</span>"); 		//클래스(.)이름이 userid-msg인 태그의 내용(html)을 변경
				$("#userid").focus();
				return;                 //커서를 다시 아이디(#)가 userid인 태그로 이동(focus)시킨다//함수 종료 
			}else{
				$(".userid-msg").text(""); //클래스(.)이름이 userid-msg인 택드의 내용을 ""로 변경(초기화)한다
			}
			
			// contextPath 보호(전역값 덮어쓰기 방지)
			if (typeof contextPath === 'undefined' || contextPath === '') {
			    contextPath = '/hometraining';
			}


			
			//ajax 시작	//비동기방식으로 서버와 통신	//페이지 새로고침 없이 서버와 통신 가능	//백그라운드에서 서버와 통신	//필요한 데이터만 주고받음	//
			$.ajax({
				type:"POST", //전송방식 안보이게
				url : contextPath + "/mem/useridCheck.do" , //요청경로
				data : {userid:$("#userid").val()}, //요청파라미터(데이터)	//
				success:function(result) {//응답데이터받는 곳.
					//alert("result : "+result);
					if(result==1) {//중복아이디 있음
						$(".userid-msg").html("<span style='color:#f00'>既に使用中のユーザーIDです。</span>");
					}else{
						$(".userid-msg").html("<span style='color:#f00'>使用可能なユーザーIDです。</span>");
					}
				},error:function() { //통신실패
					alert("エラーが発生しました");
				}
			});
		});  //end of userid blur
	}
	
	//회원가입 페이지에서만 실행되는 유효성 검사
	if($("#btn-submit").length > 0) {
			$("#password").blur(function() {
				if(!$("#password").val()) {
					$(".password-msg").html("<span style='color:#f00'>パスワードは必須入力項目です。</span>");
					$("#password").focus();
				}
			})
			
			$("#pw2").blur(function() {
				// 설명: pw2 필드에서 포커스가 벗어날 때 (blur 이벤트) 실행
				if(!$("#pw2").val()) {
					// 설명: pw2 필드의 값이 없으면 (!$("#pw2").val())
					$(".pw2-msg").html("<span style='color:#f00'>パスワード確認は必須入力項目です。</span>");
					// 설명: pw2-msg 클래스를 가진 <p> 태그에 빨간색 에러 메시지 표시
					$("#pw2").focus();
					// 설명: 다시 pw2 필드로 커서 이동
				}
			})
			
			$("#pw2").blur(function() {
				let pw1 = $("#password").val(); //let 은 재선언 불가능, 값 재할당 가능
				let pw2 = $("#pw2").val();
				if(pw1 != pw2) {
					$(".pw2-msg").html("<span style='color:#f00'>パスワードが一致しません。</span>");
				}else {
					$(".pw2-msg").text("");
				}
			})
			$("#email").blur(function() {
				if(!$("#email").val()) {
					$(".email-msg").html("<span style='color:#f00'>メールアドレスを入力してください。</span>");
					$("#email").focus();
				}
			})
			$("#phone").blur(function() {
				if(!$("#phone").val()) {
					$(".phone-msg").html("<span style='color:#f00'>電話番号を入力してください。</span>");
					$("#phone").focus();
				}
			});
			$("#btn-submit").click(function() {
				//아이디가 btn-submit인 태그를 클릭하면 아래를 실행
				var isvalid = true;
				$("#writer, #userid, #password, #pw2, #email, #phone").each(function() {
					//아이디가 writer, userid, password, pw2, email, phone 인 태그를 각각(each) 돌면서 아래를 실행
					if(!$(this).val()){
						//만약에 각각의 태그($(this))의 값(val)이 없으면 (!) 아래를 실행
						isvalid = false;
						$(this).focus();
						//커서를 각각의 태그로 이동(focus)시킨다
						return false; //each 탈출
					}
				});
				if(isvalid) {
					//만약에 isvalid가 true이면 아래를 실행
					$("#myform").attr("method","post");
					//아이디가 myform인 태그의 속성(attr) method를 post로 설정
					$("#myform").attr("action", contextPath + "/mem/membersave.do");
					//아이디가 myform인 태그의 속성(attr) action을 /mem/membersave.do로 설정
					$("#myform").submit();		//아이디가 myform인 태그를 전송(submit)
				}
			})
	}
	
	
	
	//로그인 페이지에서만 쿠키 처리 실행
	if($("#loginUserid").length > 0 && $("#saveid").length > 0) {
		if($.cookie("saveid")) {
			//만약에 saveid라는 이름의 크키가 있으면 아래를 실행
			$("#loginUserid").val($.cookie("saveid"));
			//아이디 입력칸에 쿠키의 값을 넣음
			$("#saveid").prop("checked", true);
			//체크박스를 체크된 상태로 만들어 준다
		}
	}
	
	//login 유효성 검사 (로그인 페이지에서만 실행)
	if($("#btn-login").length > 0) {
		$("#btn-login").click(function() {
		let userid = $("#loginUserid").val();
		let password = $("#password").val();
		if(!userid) {
			$("#errmsg").html("<span style='color:#f00'>ユーザーIDを入力してください</span>");
			return;
		}
		if(!password) {
			$("#errmsg").html("<span style='color:#f00'>パスワードを入力してください</span>");
			return;
		}
		$.ajax({
			type:"POST",
			url: contextPath + "/mem/loginpro.do",
			data:{userid:userid, password:password},
			success:function(result) {
				if(result === "success") {//로그인성공
					if($("#saveid").is(":checked")) {
						//만약에 아이디 저장(체크박스)가 체크되어 있으면 아래를 실행
						$.cookie("saveid",userid,{expires:7, path:contextPath});
						//쿠키이름,쿠키값,옵션(7일간유지,웹사이트 전체경로)
					}else {
						$.removeCookie("saveid", {path:contextPath});
						//쿠키이름, 옵션(웹사이트 전체경로)
					}
					//메인페이지 이동
					location.href = contextPath + '/main.do';
				}else {
					$("#errmsg").html("<span style='color:#f00'>ユーザーIDまたはパスワードを確認してください</span>");
				}
			},error:function() {
				$("#errmsg").html("<span style='color:#f00'>サーバーエラーが発生しました</span>");
			}
			
		})
		})
	}	
	//슬라이더 기능
	let currentSlide = 0;
	const slides = $(".slide");
	const totalSlides = slides.length;
	
	if(totalSlides > 0) {
		// 첫 번째 슬라이드 초기화 (페이지 로드 시 첫 번째 슬라이드 표시)
		$(".slider-wrapper").css("transform", "translateX(0%)");
		
		console.log("[슬라이더] 총 슬라이드 개수: " + totalSlides);
		
		// 슬라이드 이동 함수
		function moveSlide(direction) {
			if(direction === 'next') {
				currentSlide = (currentSlide + 1) % totalSlides;
			} else if(direction === 'prev') {
				currentSlide = (currentSlide - 1 + totalSlides) % totalSlides;
			}
			
			// 슬라이드 이동 (각 슬라이드가 wrapper의 33.333%를 차지하므로)
			const translateX = -currentSlide * (100 / totalSlides);
			$(".slider-wrapper").css("transform", "translateX(" + translateX + "%)");
			
			// active 클래스 업데이트
			slides.removeClass("active");
			slides.eq(currentSlide).addClass("active");
			
			// 인디케이터 업데이트
			$(".indicator").removeClass("active");
			$(".indicator").eq(currentSlide).addClass("active");
		}
		
		// 자동 슬라이드 변수
		let autoSlideInterval = null;
		let isPaused = false;
		
		// 자동 슬라이드 시작 함수
		function startAutoSlide() {
			// 기존 인터벌이 있으면 제거
			if(autoSlideInterval) {
				clearInterval(autoSlideInterval);
			}
			// 3초마다 자동으로 다음 슬라이드
			autoSlideInterval = setInterval(function() {
				if(!isPaused) {
					moveSlide('next');
				}
			}, 3000);
		}
		
		// 자동 슬라이드 일시 정지 함수
		function pauseAutoSlide() {
			isPaused = true;
			if(autoSlideInterval) {
				clearInterval(autoSlideInterval);
			}
		}
		
		// 자동 슬라이드 재개 함수
		function resumeAutoSlide() {
			isPaused = false;
			startAutoSlide();
		}
		
		// 이전 버튼 클릭
		$("#prevBtn").click(function() {
			moveSlide('prev');
			pauseAutoSlide();
			setTimeout(resumeAutoSlide, 5000); // 5초 후 재개
		});
		
		// 다음 버튼 클릭
		$("#nextBtn").click(function() {
			moveSlide('next');
			pauseAutoSlide();
			setTimeout(resumeAutoSlide, 5000); // 5초 후 재개
		});
		
		// 인디케이터 클릭
		$(".indicator").click(function() {
			currentSlide = $(this).data("slide");
			const translateX = -currentSlide * (100 / totalSlides);
			$(".slider-wrapper").css("transform", "translateX(" + translateX + "%)");
			
			// active 클래스 업데이트
			slides.removeClass("active");
			slides.eq(currentSlide).addClass("active");
			
			// 인디케이터 업데이트
			$(".indicator").removeClass("active");
			$(this).addClass("active");
			
			pauseAutoSlide();
			setTimeout(resumeAutoSlide, 5000); // 5초 후 재개
		});
		
		// 마우스가 슬라이더 위에 있을 때 일시 정지
		$(".mainvisual").hover(
			function() {
				pauseAutoSlide();
			},
			function() {
				resumeAutoSlide();
			}
		);
		
		// 초기 자동 슬라이드 시작
		startAutoSlide();
		console.log("[슬라이더] 자동 슬라이드 시작됨");
	} else {
		console.log("[슬라이더] 슬라이드가 없습니다.");
	}

	
	

}) //end of $(function()
