Group: 22
Names: Hamza Niaz, Thevin Mahawatte, Zane Regel, Mohammad Mustafa

To compile and run this program, follow the steps below.
1. Open up CMD and type the following to compile and run:

	javac -cp .;lib/mysql-connector-java-8.0.23.jar edu/ucalgary/ensf409/GUI.java
	java -cp .;lib/mysql-connector-java-8.0.23.jar edu.ucalgary.ensf409.GUI

2. A GUI will pop up and promt you for hampers.
3. If the user inputs the correct hampers and orders and hit the "submit" button, it will
print a txt file in the working directory by the name "Order Form".


For the test file, follow the instructions below.

	javac -cp .;lib/* edu/ucalgary/ensf409/FoodBankTest.java
	java -cp .;lib/* org.junit.runner.JUnitCore edu.ucalgary.ensf409.FoodBankTest