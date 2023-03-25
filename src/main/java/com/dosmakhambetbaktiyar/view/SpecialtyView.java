package com.dosmakhambetbaktiyar.view;

import com.dosmakhambetbaktiyar.controller.SpecialtyController;
import com.dosmakhambetbaktiyar.model.Specialty;

import java.util.Scanner;

public class SpecialtyView {

    public void menu(Scanner in, SpecialtyController controller) {

        byte b;

        do{
            System.out.println("Мы в меню Specialty");
            System.out.println("1. Создать запись Specialty");
            System.out.println("2. Удалить запись");
            System.out.println("3. Все запись");
            System.out.println("4. Запись по id");
            System.out.println("5. Обновить запись");
            System.out.println("6. Выход");
            b = in.nextByte();

            switch ((int)b){
                case 1:
                    System.out.print("Напиши имя : ");
                    String name = in.next();
                    Specialty newSpecialty = controller.create(new Specialty(name));
                    System.out.println(newSpecialty);
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
                    Specialty specialty = controller.get(getId);

                    if(specialty == null)
                        System.out.println("Нету записи по данному id " + getId);
                    else
                        System.out.println(specialty.toString());
                    break;
                case 5:
                    System.out.print("Напиши id : ");
                    Integer iD = in.nextInt();
                    Specialty spec = controller.get(iD);

                    if(spec == null)
                        System.out.println("Нету записи по данному id " + iD);
                    else {
                        System.out.println(spec);
                        System.out.print("Введите имя : ");
                        String str = in.next();
                        spec.setName(str);
                        Specialty updatedSpecialty = controller.update(spec);
                        System.out.println(updatedSpecialty);
                    }
                    break;
                default:
                    System.out.println("Выход '6'");
                    break;
            }

        } while (((int) b != 6));
    }
}
