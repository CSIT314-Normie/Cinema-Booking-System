import Main.Boundary.*;
import java.awt.GraphicsEnvironment;

public class Driver {
	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");
		if (GraphicsEnvironment.isHeadless()) {
			System.out.println("Headless mode detected!");
			System.exit(1);
		} else {
			new Init();
		}
	}
}
