/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mnm.SE.main;
import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import mnm.SE.convertor.*;
import mnm.SE.index.WordCounter;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author mnm
 */
public class mnmmain {
static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DATABASE_URL = "jdbc:mysql://localhost/SE?characterEncoding=UTF-8";
 public ResultSet resultSet;
 private String tempdir;

 
    public void start(){
tempdir=tempfetcher();
     Connection connection = null; // manages connection
        Statement statement = null; // query statement
        try {
            Class.forName(JDBC_DRIVER); // load database driver class
            connection =
                    DriverManager.getConnection(DATABASE_URL, "mnm", "mnm");
            statement = connection.createStatement();
          ResultSet resultSet = statement.executeQuery("SELECT name,url,id  FROM directory where indexed='false' ");
          while (resultSet.next()) {
                String a=resultSet.getObject(1).toString();
                
                a=a.substring(a.lastIndexOf(".")+1);
                
                a=a.toLowerCase();
                
                String url=resultSet.getObject(2).toString();
                String id=resultSet.getObject(3).toString();
                startchild(a, url, id);
              //JOptionPane.showMessageDialog(null,"file with this id is finished : "+id,"message",JOptionPane.INFORMATION_MESSAGE);
                System.out.println("the file with  id : "+id+" has indexed");

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
public String tempfetcher(){
       try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File("setting.xml"));

            // normalize text representation
            doc.getDocumentElement ().normalize ();
            NodeList listOftype = doc.getElementsByTagName("temp");
            int totaltype = listOftype.getLength();
            Node ftNode = listOftype.item(0);
            if(ftNode.getNodeType() == Node.ELEMENT_NODE){
                Element ftElement = (Element)ftNode;
                NodeList nList = ftElement.getElementsByTagName("dir");
                Element nElement = (Element)nList.item(0);
                NodeList tnList = nElement.getChildNodes();
                String name=((Node)tnList.item(0)).getNodeValue().trim();
                return name;
            }

        }catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line "
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        t.printStackTrace ();
        }
       return null;
}
public void startchild(String name,String url,String id)
{

                int typeid=new typerecognize().typerecognize(name);//return the type code
                
              
                new runner().runner(url, typeid,tempdir);//run sertain convertor by tyoe of file

 
                new WordCounter().main(tempdir+"/temp",id);//indexing the converted file


                Connection connection = null; // manages connection
        Statement statement = null; // query statement
        try {
            Class.forName(JDBC_DRIVER); // load database driver class
            connection =
                    DriverManager.getConnection(DATABASE_URL, "mnm", "mnm");
            statement = connection.createStatement();
          statement.executeUpdate("update directory set indexed=true where id='"+id+"' ");
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
}
public static void main(String arg[]){
    new mnmmain().start();
}
}
