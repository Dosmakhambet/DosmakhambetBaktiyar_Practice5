import controller.DeveloperController;
import controller.SkillController;
import controller.SpecialtyController;
import database.Database;
import repository.impl.DeveloperRepositoryImpl;
import repository.impl.SkillRepositoryImpl;
import repository.impl.SpecialtyRepositoryImpl;
import view.DeveloperView;
import view.SkillView;
import view.SpecialtyView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class PracticeDemo {
    public static void main(String args[]) throws SQLException {

        System.out.println("Start");
        Database database = Database.getInstance();
        System.out.println("Start connect to database");
        Connection connection = database.getConnection();
        System.out.println("connection success");

        byte b;
        Scanner in = new Scanner(System.in);
        DeveloperView developerView = new DeveloperView(new SkillRepositoryImpl(connection), new SpecialtyRepositoryImpl(connection));
        SkillView skillView = new SkillView();
        SpecialtyView specialtyView = new SpecialtyView();
        do{
            System.out.println("Добро пожаловать в меню");
            System.out.println("1. Developers");
            System.out.println("2. Specialty");
            System.out.println("3. Skills");
            System.out.println("4. Quit");

            b = in.nextByte();

            switch ((int) b) {
                case 1:
                    developerView.menu(in, new DeveloperController(new DeveloperRepositoryImpl(connection)));
                    break;
                case 2:
                    specialtyView.menu(in, new SpecialtyController(new SpecialtyRepositoryImpl(connection)));
                    break;
                case 3:
                    skillView.menu(in, new SkillController(new SkillRepositoryImpl(connection)));
                    break;
                default:
                    System.out.println("Для выхода нажмите 4");
                    break;
            }
        }while ((int)b != 4);

    }
}
