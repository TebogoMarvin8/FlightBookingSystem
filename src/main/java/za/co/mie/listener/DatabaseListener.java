
package za.co.mie.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import za.co.mie.manager.DbManager;

@WebListener
public class DatabaseListener implements ServletContextListener{
    @Override
  public void contextInitialized(ServletContextEvent sce){
    ServletContext sc = sce.getServletContext();
    String url = sc.getInitParameter("url");
    String database = sc.getInitParameter("database");
    String username = sc.getInitParameter("username");
    String password = sc.getInitParameter("password");
    String driver = sc.getInitParameter("driver");
    //DbManager dbm = new DbManager(url, database, driver, username, password);
//    dbm.getConnection();
//    sc.setAttribute("dbm", dbm);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce){
    System.out.println("Bye!");
  }
}
