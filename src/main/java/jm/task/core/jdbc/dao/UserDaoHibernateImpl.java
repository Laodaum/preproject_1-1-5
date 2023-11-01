package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final String CREATE_TABLE = "CREATE TABLE users (id int NOT NULL PRIMARY KEY AUTO_INCREMENT, name varchar(45), lastName varchar(45), age int)";
    private final String DROP_TABLE = "DROP TABLE users";
    private final String DELETE_All = "DELETE FROM users";

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = new Util().getConnection().openSession()) {
            Transaction transaction = session.beginTransaction();
            SQLQuery query = session.createSQLQuery(CREATE_TABLE);
            query.executeUpdate();
            transaction.commit();

            System.out.println("Таблица создана");
        } catch (Exception e) {
            System.out.println("Таблица НЕ создана");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = new Util().getConnection().openSession()) {
            Transaction transaction = session.beginTransaction();
            SQLQuery query = session.createSQLQuery(DROP_TABLE);
            query.executeUpdate();
            transaction.commit();
            System.out.println( "Таблица удалена" );
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = new Util().getConnection().openSession()) {
                // start a transaction
                transaction = session.beginTransaction();
                // save the student object
                session.save(new User(name, lastName, age));
                // commit transaction
                transaction.commit();

            System.out.println("User с именем – "+name+ " добавлен в базу данных");
        } catch (Exception e) {
            System.out.println("User с именем – "+name+ " НЕ добавлен в базу данных");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = new Util().getConnection().openSession()) {
            System.out.println(session.get(User.class, id));
            // start a transaction
            transaction = session.beginTransaction();
            // delete user by id
            session.delete(session.get(User.class, id));
            // commit transaction
            transaction.commit();


            System.out.println("User с id – "+id+ " удален");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = new Util().getConnection().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = new Util().getConnection().openSession()) {
            Transaction transaction = session.beginTransaction();
            SQLQuery query = session.createSQLQuery(DELETE_All);
            query.executeUpdate();
            transaction.commit();
            System.out.println("База очищена");
        }
    }
}
