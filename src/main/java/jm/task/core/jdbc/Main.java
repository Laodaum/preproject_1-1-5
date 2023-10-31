package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        final String testName = "Ivan";
        final String testLastName = "Ivanov";
        final byte testAge = 5;

        UserService userService = new UserServiceImpl();

        //Создание таблицы User(ов) – не должно приводить к исключению, если такая таблица уже существует
        userService.createUsersTable();

        //Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
        userService.saveUser(testName, testLastName, testAge);
        userService.saveUser(testName, testLastName, testAge);
        userService.saveUser(testName, testLastName, testAge);
        userService.saveUser(testName, testLastName, testAge);

        //userService.removeUserById(3);

        //Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        List<User> userList = userService.getAllUsers();
        System.out.println(userList);

        //Очистка таблицы User(ов)

        userService.cleanUsersTable();

       //Удаление таблицы – не должно приводить к исключению, если таблицы не существует
        userService.dropUsersTable();


        //3. После всех операций в Main нужно закрыть connection, для этого нужно создай метод закрытия в классе Util

    }
}
