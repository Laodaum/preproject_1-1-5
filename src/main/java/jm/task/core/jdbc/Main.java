package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        final String testName = "Ivan";
        final String testLastName = "Ivanov";
        final byte testAge = 6;

        UserService userService = new UserServiceImpl();

        //Создание таблицы User(ов) – не должно приводить к исключению, если такая таблица уже существует
        userService.createUsersTable();

        //Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
        userService.saveUser(testName, testLastName, testAge);
        userService.saveUser("Степа", testLastName, testAge);
        userService.saveUser(testName, testLastName, testAge);
        userService.saveUser(testName, testLastName, testAge);

        //userService.removeUserById(2);

        //Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        List<User> userList = userService.getAllUsers();
        System.out.println(userList);

        //Очистка таблицы User(ов)

        userService.cleanUsersTable();

       //Удаление таблицы – не должно приводить к исключению, если таблицы не существует
        userService.dropUsersTable();

    }
}
