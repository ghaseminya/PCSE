/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mnm.SE.database;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
/**
 *
 * @author mnm
 */
public class database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DATABASE_URL = "jdbc:mysql://localhost/SE?characterEncoding=UTF-8";
 public ResultSet resultSet; 
public static void insert(String a,int line,String id)
{
     Connection connection = null; // manages connection
        Statement statement = null; // query statement
        try {
            Class.forName(JDBC_DRIVER); // load database driver class
            connection =
                    DriverManager.getConnection(DATABASE_URL, "mnm", "mnm");
            statement = connection.createStatement();
ResultSet resultSet = statement.executeQuery("select MAX(ky) from terms");
int max=0;
            while(resultSet.next())
            max=Integer.parseInt(resultSet.getObject(1).toString());


statement.executeUpdate("insert into terms (filed,lineid,score,addressid,id,ky,wscore) VALUES ('"+a+"','"+line+"',score+1,0,'"+id+"','"+max+"',wscore+1)");

             
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        } // end catch
        catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } // end catch
        finally // ensure statement and connection are closed properly
        {
            try {
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
                System.exit(1);
            } // end catch
        } // end finally
        //System.out.pr
}
static class WordData {
      String word;
      
      int count;
      WordData(String w) {
          word = w;
          count = 1;
      }
   }
public static void insertrelease(Map a,String id)
{
     Connection connection = null; // manages connection
        Statement statement = null; // query statement
        try {
            Class.forName(JDBC_DRIVER); // load database driver class
            connection =
                    DriverManager.getConnection(DATABASE_URL, "mnm", "mnm");
            statement = connection.createStatement();
ResultSet resultSet = statement.executeQuery("select MAX(ky) from terms");
int max=0;
            while(resultSet.next())
            max=Integer.parseInt(resultSet.getObject(1).toString());

Set<String>keys=a.keySet();
TreeSet<String > sortedkeys=new TreeSet<String> (keys);

int i=0;
for(String key:sortedkeys)
{

            ResultSet resultSet1 = statement.executeQuery("select wscore from terms where filed='"+key+"'");
            int wholenum=0;
            while(resultSet1.next())
            wholenum=Integer.parseInt(resultSet1.getObject(1).toString());
            wholenum+=Integer.parseInt(a.get(key).toString());

    //System.out.println("\t"+key+"\t"+a.get(key));
    statement.executeUpdate("insert into terms (filed,lineid,score,addressid,id,ky,wscore) VALUES ('"+key+"','"+a.get(key)+"','"+a.get(key)+"',0,'"+id+"','"+(max+i)+"',wscore)");
statement.executeUpdate("update terms set wscore='"+wholenum+"' where filed='"+key+"'");

    i++;
}

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        } // end catch
        catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } // end catch
        finally // ensure statement and connection are closed properly
        {
            try {
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
                System.exit(1);
            } // end catch
        } // end finally
        //System.out.pr
}
public static void update(String a,String id)///gheyre ghabel estefade
{
     Connection connection = null; // manages connection
        Statement statement = null; // query statement
        try {
            Class.forName(JDBC_DRIVER); // load database driver class
            connection =
                    DriverManager.getConnection(DATABASE_URL, "mnm", "mnm");
            statement = connection.createStatement();
statement.executeUpdate("update terms set score=score+1 where filed='"+a+"' AND id='"+id+"' ");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        } // end catch
        catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } // end catch
        finally // ensure statement and connection are closed properly
        {
            try {
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
                System.exit(1);
            } // end catch
        } // end finally
        //System.out.pr
}

public  boolean test(String a,String id)
{
     Connection connection = null; // manages connection
        Statement statement = null; // query statement
        try {
            Class.forName(JDBC_DRIVER); // load database driver class
            connection =
                    DriverManager.getConnection(DATABASE_URL, "mnm", "mnm");
            statement = connection.createStatement();
          ResultSet resultSet = statement.executeQuery("SELECT *  FROM terms where filed='"+a+"' AND id='"+id+"'");//cur_timestamp//WHERE name LIKE '%"+s2+"%'

            while (resultSet.next()) {
                return false;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        } // end catch
        catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } // end catch
        finally // ensure statement and connection are closed properly
        {
            try {
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
                System.exit(1);
            } // end catch
        } // end finally
        //System.out.pr
        return true;
}

}
