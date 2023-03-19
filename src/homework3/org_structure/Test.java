package homework3.org_structure;

import java.io.*;


public class Test {
    public static void main(String[] args) {
        // для удобного тестирования не были удалены Employee.ToString() и OrgStructureParserImpl.printEmployees()

        OrgStructureParser parser = new OrgStructureParserImpl();
        File file = new File("src/homework3/org_structure/data.txt");
        try {
            Employee boss = parser.parseStructure(file);
            System.out.println(boss);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
