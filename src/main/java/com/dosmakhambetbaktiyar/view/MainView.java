package com.dosmakhambetbaktiyar.view;

import com.dosmakhambetbaktiyar.controller.DeveloperController;
import com.dosmakhambetbaktiyar.controller.SkillController;
import com.dosmakhambetbaktiyar.controller.SpecialtyController;
import com.dosmakhambetbaktiyar.repository.impl.DeveloperRepositoryImpl;
import com.dosmakhambetbaktiyar.repository.impl.SkillRepositoryImpl;
import com.dosmakhambetbaktiyar.repository.impl.SpecialtyRepositoryImpl;
import com.dosmakhambetbaktiyar.service.impl.DeveloperServiceImpl;
import com.dosmakhambetbaktiyar.service.impl.SkillServiceImpl;
import com.dosmakhambetbaktiyar.service.impl.SpecialtyServiceImpl;

import java.sql.SQLException;
import java.util.Scanner;

public class MainView {
    public static void run() throws SQLException {
        byte b;
        Scanner in = new Scanner(System.in);
        DeveloperView developerView = new DeveloperView(new SkillRepositoryImpl(), new SpecialtyRepositoryImpl());
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
                    developerView.menu(in, new DeveloperController(new DeveloperServiceImpl(new DeveloperRepositoryImpl())));
                    break;
                case 2:
                    specialtyView.menu(in, new SpecialtyController(new SpecialtyServiceImpl(new SpecialtyRepositoryImpl())));
                    break;
                case 3:
                    skillView.menu(in, new SkillController(new SkillServiceImpl(new SkillRepositoryImpl())));
                    break;
                default:
                    System.out.println("Для выхода нажмите 4");
                    break;
            }
        }while ((int)b != 4);
    }
}
