/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mnm.SE.directory;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author mnm
 */
public class dirindex{

private int f;
 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DATABASE_URL = "jdbc:mysql://localhost/SE?characterEncoding=UTF-8";
 public ResultSet resultSet; /** Creates new form NewJFrame1 */

     public void recurseDir(String dir) {
      String result, _result[];

      result = recurseInDirFrom(dir);
      _result = result.split("\\|");
      //for(int i=0;i<_result.length;i++)
      ownfetcher(_result);
  }

  private  String recurseInDirFrom(String dirItem) {
    File file;
    String list[], result;

    result = dirItem;
    file = new File(dirItem);
    if (file.isDirectory()) {
      list = file.list();
     
      for (int i = 0; i < list.length; i++)
        result = result + "|"
           + recurseInDirFrom(dirItem + File.separatorChar + list[i]);

      }
    return result;

  }
  public void ownfetcher(String a[]){

    Connection connection = null; // manages connection
        Statement statement = null; // query statement
        try {
            Class.forName(JDBC_DRIVER); // load database driver class
            connection =
                    DriverManager.getConnection(DATABASE_URL, "mnm", "mnm");
            statement = connection.createStatement();
resultSet = statement.executeQuery("select MAX(id) from directory");
            int p=0;
            int h=0,h3;

            String dir="",file="",max="";
            String aasli="";

            while(resultSet.next())
            max=resultSet.getObject(1).toString();
            p=Integer.parseInt(max) ;
           
for(int ww=0;ww<a.length;ww++){

                 aasli=a[ww];
                 h=a[ww].lastIndexOf("/");
                 a[ww]=aasli;
                 file=a[ww].substring(h+1);
                 a[ww]=aasli;
                
                 dir=a[ww].substring(0,h);
                 a[ww]=aasli;
                 h3=dir.lastIndexOf("/");
                 dir=a[ww].substring(h3+1,h);
                 a[ww]=aasli;
                 statement.executeUpdate("insert into  directory (url,id,name,directory) values ('"+a[ww]+"','"+(p+1)+"','"+file+"','"+dir+"') ");
p++;
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
public static void main(String arg[])
{
    dirindex m=new dirindex();

}
 public int  getfilenumber(String a)    {


    File file;
    file = new File(a);
    if (file.isDirectory()) {

  String list[]= file.list();
      for (int i = 0; i < list.length; i++)
         getfilenumber(a + File.separatorChar + list[i]);

      }else{
        f++;
      }

    return f;

}
/*
  Process all files and directories under dir
    public static void visitAllDirsAndFiles(File dir) {
        process(dir);

        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                visitAllDirsAndFiles(new File(dir, children[i]));
            }
        }
    }

    // Process only directories under dir
    public static void visitAllDirs(File dir) {
        if (dir.isDirectory()) {
            process(dir);

            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                visitAllDirs(new File(dir, children[i]));
            }
        }
    }

    // Process only files under dir
    public static void visitAllFiles(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                visitAllFiles(new File(dir, children[i]));
            }
        } else {
            process(dir);
        }
    }

 *
 /
 *
 */
}