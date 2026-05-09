package util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordBcrypt {

	//비밀번호 암호화
	public static String hashPassword(String password) {
		//password: 입력받은 비밀번호
		return BCrypt.hashpw(password, BCrypt.gensalt());	
		// gensalt()메서드는 솔트(salt)를 자동으로 생성
		// salt: 해시 함수에 추가되는 임의의 데이터로,
		//동일한 비밀번호라도 솔트가 다르면 해시값이 달라짐
		//해시된 비밀번호 반환
	}
	
	//비밀번호 비교
	public static boolean checkPassword(String password, String hashed) {
		//hashed : 해시된 비밀번호
		return BCrypt.checkpw(password, hashed);
		//입력된 비밀번호와 해시된 비밀번호를 비교
		//일치하면 true, 아니면 false 반환
	}
	
}




