package homework.three;

public class Main {

	public static void main(String[] args) {

		Group group = new Group("FEA");
		
		Test test = new Test(group);
		test.testAddStudentsInGroup();
		test.serializeGroupToFile();
		test.testClearGroup();
		test.deserializeGroupFromFile();
		test.testPrintGroup();
		/**
		 *  work with database
		 */
		Database db = new Database();
		db.loadDataBase();
		db.workWithBase();
		db.saveDataBase();
	}
}
