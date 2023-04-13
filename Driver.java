import Main.Boundary.*;
import java.awt.HeadlessException;

import Database.DB;
import java.sql.Connection;

public class Driver {
	public static void main(String[] args) {
		new Init();
		// try {
		// 	new Init();
		// } catch (HeadlessException e) {
		// 	System.out.println("Compiled Successfully in HEADLESS enviroment");
		// 	DB db = new DB("TestDB");
		// 	Connection conn = db.getConnection();

		// 	if (conn != null) {
		// 		System.out.println("[+] Connected to <" + "TestDB" + "> database");
		// 	} else {
		// 		System.out.println("[!] Failed to connect to <" + "TestDB" + "> database");
		// 	}
		// }
	}
}
