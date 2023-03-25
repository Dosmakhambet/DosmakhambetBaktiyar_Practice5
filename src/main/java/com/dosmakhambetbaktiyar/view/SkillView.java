package com.dosmakhambetbaktiyar.view;

import com.dosmakhambetbaktiyar.controller.SkillController;
import com.dosmakhambetbaktiyar.model.Skill;

import java.util.Scanner;

public class SkillView {

    public void menu(Scanner in, SkillController controller){

        byte b;

        do{
            System.out.println("Мы в меню Skill");
            System.out.println("1. Создать запись Skill");
            System.out.println("2. Удалить запись");
            System.out.println("3. Все запись");
            System.out.println("4. Запись по id");
            System.out.println("5. Обновить запись");
            System.out.println("6. Выход");
            b = in.nextByte();
            switch((int)b){
                case 1:
                    System.out.print("Напиши имя : ");
                    String name = in.next();
                    Skill newSkill = controller.create(new Skill(name));
                    System.out.println(newSkill);
                    break;
                case 2:
                    System.out.print("Напиши id : ");
                    Integer id = in.nextInt();
                    if(controller.delete(id)){
                        System.out.println("Удаление записи прошла успешно");
                    }else{
                        System.out.println("Удаление записи провалилось, попробуйте снова");
                    }
                    break;
                case 3:
                    System.out.println("Все записи  : ");
                    controller.getAll().forEach((a) -> System.out.println(a.toString()));
                    break;
                case 4:
                    System.out.print("Напиши id : ");
                    Integer getId = in.nextInt();
                    Skill skill = controller.get(getId);

                    if(skill == null)
                        System.out.println("Нету записи по данному id " + getId);
                    else
                        System.out.println(skill);
                    break;
                case 5:
                    System.out.print("Напиши id : ");
                    Integer iD = in.nextInt();
                    Skill skil = controller.get(iD);

                    if(skil == null)
                        System.out.println("Нету записи по данному id " + iD);
                    else {
                        System.out.println(skil);
                        System.out.print("Введите имя : ");
                        String str = in.next();
                        skil.setName(str);
                        Skill updatedSkill = controller.update(skil);
                        System.out.println(updatedSkill);
                    }
                    break;
                default:
                    System.out.println("Выход 6");
                    break;
            }

        } while (((int) b != 6));
    }

}
