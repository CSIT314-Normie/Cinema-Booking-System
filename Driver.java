import Main.Boundary.*;
import java.awt.HeadlessException;

public class Driver {
	public static void main(String[] args) {
		try {
			new Init();
		} catch (HeadlessException e) {
			System.out.println("Compiled Successfully in HEADLESS enviroment");
		}
	}
}
