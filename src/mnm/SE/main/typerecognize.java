/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *
 */

package mnm.SE.main;
import java.io.File;

import org.w3c.dom.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
/**
 *
 * @author mnm
 */
public class typerecognize {
public int typerecognize(String type){
       try {
    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File("filetype.xml"));

            // normalize text representation
            doc.getDocumentElement ().normalize ();
            NodeList listOftype = doc.getElementsByTagName("filetype");
            int totaltype = listOftype.getLength();


            for(int s=0; s<listOftype.getLength() ; s++){


                Node ftNode = listOftype.item(s);
                if(ftNode.getNodeType() == Node.ELEMENT_NODE){


                    Element ftElement = (Element)ftNode;

                    //-------
                   NodeList nList = ftElement.getElementsByTagName("name");
                    Element nElement = (Element)nList.item(0);
                    NodeList tnList = nElement.getChildNodes();
                    String name=((Node)tnList.item(0)).getNodeValue().trim();

                    NodeList iList = ftElement.getElementsByTagName("id");
                    Element iElement = (Element)iList.item(0);
                    NodeList tiList = iElement.getChildNodes();
                    String id=((Node)tiList.item(0)).getNodeValue().trim();


                    NodeList ftList = ftElement.getElementsByTagName("type");
                    Element firsttypeElement1 = (Element)ftList.item(0);
                    NodeList ttList = firsttypeElement1.getChildNodes();
                    if(type.equals(((Node)ttList.item(0)).getNodeValue().trim())){
                     
                     
                     
                     

                        return Integer.parseInt(id);
                    }



                }//end of if clause


            }//end of for loop with s var


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
        //System.exit (0);
       return 0;

}

public void addtype(String type,String name){
     String xmlFilePath = "filetype.xml";
     String id="";
try{




    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File("filetype.xml"));

            // normalize text representation
            doc.getDocumentElement ().normalize ();
            NodeList listOftype = doc.getElementsByTagName("filetype");
            int totaltype = listOftype.getLength();
            Node ftNode = listOftype.item(listOftype.getLength()-1);
            if(ftNode.getNodeType() == Node.ELEMENT_NODE){
                    Element ftElement = (Element)ftNode;
                   NodeList nList = ftElement.getElementsByTagName("id");
                    Element nElement = (Element)nList.item(0);
                    NodeList tnList = nElement.getChildNodes();
                    id=((Node)tnList.item(0)).getNodeValue().trim();
                }






     File file = new File("filetype.xml");
	 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	 docBuilder = factory.newDocumentBuilder();
     doc = docBuilder.parse(file);
     Element childElement= doc.createElement("filetype");
     Element root =doc.getDocumentElement();
     childElement.setAttribute("type",type);
     childElement.setAttribute("name",name);
     childElement.setAttribute("id",id);
     root.appendChild(childElement);
     TransformerFactory transfac = TransformerFactory.newInstance();
     Transformer trans = transfac.newTransformer();
     StringWriter sw = new StringWriter();
    StreamResult result = new StreamResult(sw);
    DOMSource source = new DOMSource(doc);
    trans.transform(source, result);
    String xmlString = sw.toString();

    //Saving the XML content to File
    OutputStream f0;
    byte buf[] = xmlString.getBytes();
    f0 = new FileOutputStream("filetype.xml");
    for(int i=0;i<buf .length;i++) {
 	f0.write(buf[i]);
    }
	f0.close();
	buf = null;
}catch (Exception e) {
    e.printStackTrace();
}
}
public static void main(String arg[])
{
    typerecognize n=new typerecognize();
    System.out.print(n.typerecognize("pdf"));
}
}

