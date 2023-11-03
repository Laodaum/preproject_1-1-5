package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = new Util().getConnection().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id int NOT NULL PRIMARY KEY AUTO_INCREMENT, name varchar(45), lastName varchar(45), age int)").executeUpdate();
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
            SQLQuery query = session.createSQLQuery("DROP TABLE IF EXISTS users");
            query.executeUpdate();
            transaction.commit();
            System.out.println( "Таблица удалена" );
        } catch (Exception e) {
            System.out.println("Таблица НЕ удалена");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = new Util().getConnection().openSession()) {
                transaction = session.beginTransaction();
                session.save(new User(name, lastName, age));
                transaction.commit();

            System.out.println("User с именем – "+name+ " добавлен в базу данных");
        } catch (Exception e) {
            System.out.println("User с именем – "+name+ " НЕ добавлен в базу данных");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        Session session = new Util().getConnection().openSession();
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE User WHERE id = :id").setParameter( "id",id );
            query.executeUpdate();
            transaction.commit();

            System.out.println("User с id – "+id+ " удален");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();

            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        Session session = new Util().getConnection().openSession();
        try  {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return session.createQuery("from User", User.class).list();
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        Session session = new Util().getConnection().openSession();
        try {
            transaction = session.beginTransaction();
            SQLQuery query = session.createSQLQuery("DELETE FROM users");
            query.executeUpdate();
            transaction.commit();
            System.out.println("База очищена");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
