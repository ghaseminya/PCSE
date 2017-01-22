/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mnm.SE.directory;

import java.io.File;
import java.util.ArrayList;
    import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author mnm
 */


/**
 *
 * @author mnm
 */
public class dirmanager {
    String list[];


 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DATABASE_URL = "jdbc:mysql://localhost/SE?characterEncoding=UTF-8";
 public ResultSet resultSet; /** Creates new form NewJFrame1 */

    public void choise(String path,String a)
    {
        ArrayList<String> aresult=new ArrayList<String>();
        String result[];
        if(a.equals("0")){
        aresult.add(path);
        }
        if(a.equals("1")){
        result=recurseInDirFrom1(path);
        for(int i=0;i<result.length;i++)
            aresult.add(result[i]);
        }
        if(a.equals("2")){

            String aas[]=recurseInDirFrom1(path);
            for(int i=0;i<aas.length;i++)
            {
                String temp[]=recurseInDirFrom1(aas[i]);
                for(int h=0;h<temp.length;h++)
                    aresult.add(temp[h]);

            }
        }
        if(a.equals("3")){
        String aas[]=recurseInDirFrom1(path);
            for(int i=0;i<aas.length;i++)
            {
                String temp[]=recurseInDirFrom1(aas[i]);
                for(int h=0;h<temp.length;h++)
                {
                    String aas1[]=recurseInDirFrom1(temp[h]);
                    for(int i1=0;i1<aas1.length;i1++)
                    {
                        String temp1[]=recurseInDirFrom1(aas1[i1]);
                        for(int h1=0;h1<temp1.length;h1++)
                            aresult.add(temp1[h1]);
                    }
                }
            }
        }
        for(int i=0;i<aresult.size();i++)
        new dirindex().recurseDir(aresult.get(i));
}

 private  String[] recurseInDirFrom1(String dirItem) {
    File file;int d=0,f=0;
    String  dirfile[],dirdir[];
    file = new File(dirItem);
    list = file.list();
   
    for(int i=0;i<list.length;i++)
    {
       file=new File(dirItem+"/"+list[i]);
        if (file.isDirectory()) {
    d++;
    }else{
        f++;
    }}
    ////System.out.println("mnm is ok "+f+"  "+d+" "+list.length);
    dirfile=new String[f];
    dirdir=new String[d];
    d=0;f=0;
    for(int i=0;i<list.length;i++)
    {
        file=new File(dirItem+"/"+list[i]);
        if (file.isDirectory()) {
        dirdir[d]=dirItem+"/"+list[i];
        d++;
    }else{
        dirfile[f]=dirItem+"/"+list[i];
        f++;
    }}
     //for for recursive
    fileindexing(dirfile);
    return dirdir;
    }
      public void fileindexing(String a[]){


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
         //   //System.out.println("mnm is ok MASX"+p);
            for(int i=0;i<a.length;i++)
            {
                aasli=a[i];
                 h=a[i].lastIndexOf("/");
                 a[i]=aasli;
                 file=a[i].substring(h+1);
                 a[i]=aasli;
             //    //System.out.println("mnm is ok "+a[i]);
                 dir=a[i].substring(0,h);
                 a[i]=aasli;
                 h3=dir.lastIndexOf("/");
                 dir=a[i].substring(h3+1,h);
                 a[i]=aasli;
                 statement.executeUpdate("insert into  directory (url,id,name,directory) values ('"+a[i]+"','"+(i+p+1)+"','"+file+"','"+dir+"') ");
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

public static void main(String arf[])
{
    dirmanager m=new dirmanager();
    m.choise("/media/home/mnm/wine-1.0.1","3");
    //new dirindex().recurseDir("/media/home/mnm/wine-1.0.1");
}
}
