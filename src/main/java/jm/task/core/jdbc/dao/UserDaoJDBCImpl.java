package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    // 4. UserDaoJDBCImpl - здесь расписываем основной функционал с таблицей
    // (создать, удалить, сохранить пользователя и т.д.)
    //Все поля должны быть private
    //Обработка всех исключений, связанных с работой с базой данных должна находиться в dao
    private Connection connection = new Util().getConnection();
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private List<User>  listUsers = new ArrayList<>();

    private final String SAVE_USER = "INSERT INTO users (name,lastName,age) VALUES (?,?,?)";
    private final String GET_ALL = "SELECT * FROM users";
    private final String DELETE_BY_ID = "DELETE FROM users WHERE id=?";
    private final String DELETE_All = "DELETE FROM users";
    private final String CREATE_TABLE = "CREATE TABLE users (id int NOT NULL PRIMARY KEY AUTO_INCREMENT, name varchar(45), lastName varchar(45), age int)";
    private final String DROP_TABLE = "DROP TABLE users";

    public UserDaoJDBCImpl() {
        // Класс dao должен иметь конструктор пустой/по умолчанию

    }

    public void createUsersTable() {
        try {
            connection.prepareStatement(CREATE_TABLE).execute();

            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("Таблица НЕ создана");
        }
    }

    public void dropUsersTable() {
        try {
            connection.prepareStatement( DROP_TABLE ).executeUpdate();

            System.out.println( "Таблица удален" );
        } catch (SQLException e) {
            System.out.println( "Таблица НЕ удален" );
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            preparedStatement = connection.prepareStatement(SAVE_USER);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt( 3, age);

            preparedStatement.execute();

            System.out.println("User с именем – "+name+ " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("User с именем – "+name+ " НЕ добавлен в базу данных");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            preparedStatement = connection.prepareStatement(DELETE_BY_ID);
            preparedStatement.setInt( 1, (int) id);

            preparedStatement.executeUpdate();

            System.out.println("User с id – "+id+ " удален");
        } catch (SQLException e) {
            System.out.println("User с id – "+id+ " НЕ удален");
        }
    }

    public List<User> getAllUsers() {
        try {
        resultSet = connection.prepareStatement(GET_ALL).executeQuery();

        while (resultSet.next()) {
            User user = new User(
                    resultSet.getString( "name"),
                    resultSet.getString( "lastName"),
                    resultSet.getByte( "age")
                    );
            user.setId(resultSet.getLong("id")  );
            listUsers.add(user);
        }
        } catch (SQLException e) {
            System.out.println("Не удалось выгрузить всех Users");
        }

        return listUsers;
    }

    public void cleanUsersTable() {
        try {
            connection.prepareStatement(DELETE_All).executeUpdate();

            System.out.println("База очищена");
        } catch (SQLException e) {
            System.out.println("База НЕ очищена");
        }
    }

    }

