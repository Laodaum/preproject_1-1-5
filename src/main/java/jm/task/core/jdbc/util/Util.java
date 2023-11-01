package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import javax.imageio.spi.ServiceRegistry;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    // реализуйте настройку соеденения с БД
    private static SessionFactory sessionFactory;

    public SessionFactory getConnection() {
      try {
          Configuration configuration = new Configuration();

          // Hibernate settings equivalent to hibernate.cfg.xml's properties
          Properties settings = new Properties();
          settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
          settings.put(Environment.URL, "jdbc:mysql://localhost:3306/1_1_3-4_jdbc");
          settings.put(Environment.USER, "root");
          settings.put(Environment.PASS, "root");
          settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

          settings.put(Environment.SHOW_SQL, "true");

          settings.put( Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

          settings.put(Environment.HBM2DDL_AUTO, "none");

          configuration.setProperties(settings);

          configuration.addAnnotatedClass(User.class);

          StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                  .applySettings(configuration.getProperties()).build();

          sessionFactory = configuration.buildSessionFactory( serviceRegistry );
          if (!sessionFactory.isClosed()) {
              System.out.println("соединение установлено");
          } else {
              System.out.println("не успех");
          }
      } catch (Exception e) {
          System.err.println("не удалось загрузить класс драйвера");
          e.printStackTrace();
      }
        return sessionFactory;
    }

}
