package Main;


import Main.Boundary.Init;


// Manual compilation and running ON WINDOWS MACHINE!
// javac -classpath ".;./Out/;lib\activation.jar;lib\javax.mail.jar;lib\jcalendar-1.4.jar;lib\jcommon-1.0.23.jar;lib\jfreechart-1.0.19.jar;lib\mysql-connector-j-8.0.32.jar" -d Out Database/*.java Main/*.java Main/Boundary/*.java Main/Boundary/Admin/*.java Main/Boundary/Customer/*.java Main/Boundary/Manager/*.java Main/Boundary/Owner/*.java Main/Controller/*.java Main/Controller/Admin/*.java Main/Controller/Customer/*.java Main/Controller/Manager/*.java Main/Controller/Owner/*.java Main/Entity/*.java 
// java -classpath ".;./Out/;lib\activation.jar;lib\javax.mail.jar;lib\jcalendar-1.4.jar;lib\jcommon-1.0.23.jar;lib\jfreechart-1.0.19.jar;lib\mysql-connector-j-8.0.32.jar"  Main.Driver

public class Driver {
	public static void main(String[] args) {
		new Init();
	}
}
