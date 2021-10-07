package in.nareshit.raghu;

import java.util.UUID;

public class Test {

	public static void main(String[] args) {
		//Universally Unique IDentifier  : UUID (String)
		String pwd = UUID.randomUUID()
				.toString()
				.replace("-","")
				.substring(0, 8);
		System.out.println(pwd);
	}
}
