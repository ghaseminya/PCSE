package mnm.SE.gui;
import mnm.SE.main.typerecognize;
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
/** Uses a SwingWorker to perform a time-consuming (and utterly fake) task. */
//import javax.swing.SwingWorker;

public class LongTask {
    private int lengthOfTask;
    private int current = 0;
    private String statMessage;

static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DATABASE_URL = "jdbc:mysql://localhost/SE?characterEncoding=UTF-8";
 public ResultSet resultSet;
 private String tempdir;


    LongTask(int a) {
        //Compute length of task...
        //In a real program, this would figure out
        //the number of bytes to read or whatever.
        lengthOfTask = a;
    }

    /**
     * Called from ProgressBarDemo to start the task.
     */
    void go() {
        current = 1;
        final SwingWorker worker = new SwingWorker() {

            public Object construct() {
                return new ActualTask();
            }
        };
    }

    /**
     * Called from ProgressBarDemo to find out how much work needs
     * to be done.
     */
    int getLengthOfTask() {
        return lengthOfTask;
    }

    /**
     * Called from ProgressBarDemo to find out how much has been done.
     */
    int getCurrent() {
        return current;
    }

    void stop() {
        current = lengthOfTask;
    }

    /**
     * Called from ProgressBarDemo to find out if the task has completed.
     */
    boolean done() {
        if (current >= lengthOfTask)
            return true;
        else
            return false;
    }

    String getMessage() {
        return statMessage;
    }

    /**
     * The actual long running task.  This runs in a SwingWorker thread.
     */
    class ActualTask {
        ActualTask () {
            //Fake a long task,
            //making a random amount of progress every second.
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

statMessage = "Completed Indexing of " + current +": "+url;
                current++;
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



            /*while (current < lengthOfTask) {
                try {
                    Thread.sleep(1000); //sleep for a second
                    current += Math.random() * 100; //make some progress
                    if (current > lengthOfTask) {
                        current = lengthOfTask;
                    }
                    System.out.println(current);
                    statMessage = "Completed " + current +
                                  " out of " + lengthOfTask + ".";
                } catch (InterruptedException e) {}
            }*/
        }
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
}