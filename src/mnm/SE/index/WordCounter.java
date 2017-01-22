/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mnm.SE.index;

/**
 *
 * @author mnm
 */


import mnm.SE.database.database;
import java.io.*;
import java.util.*;

public class WordCounter {
private  Map<String ,Integer> words;
    TextReader in;
   
 database mnm=new database();
   public  void main(String arg,String id){
      openFiles(arg);

      //TreeMap words;
      //words = new TreeMap();
      words =new HashMap<String, Integer>();
      readWords(in,id);
       }

    void openFiles(String args) {
      
      try {
         in = new TextReader(new FileReader(args+"/01tempmnm"));
      }
      catch (IOException e) {
         System.out.println("Error for open file in wordcounter: Can't open input file " + args+"/01tempmnm");
         System.exit(1);
      }
     
      
   } // end openFiles()
    void readWords(TextReader inStream, String id) {

       try {
         while (true) {
            while (! inStream.eof() && ! Character.isLetter(inStream.peek()))
               inStream.getAnyChar();  
            if (inStream.eof())
               break;  
            String word = inStream.getAlpha();
            word = word.toLowerCase();
            //System.out.println(word);
           /* if( mnm.test(word,id)){
                mnm.insert(word,1,id);
                }else{
                mnm.update(word,id);
                }*/
           //WordData data = (WordData)words.get(word);

            if (words.containsKey(word)) {
             int count =words.get(word);
                words.put(word, count+1 );

            }
            else {
               words.put(word, 1 );


            }


         }

         mnm.insertrelease(words, id);
      }
      catch (TextReader.Error e) {
         System.out.println("An error occurred while reading the data.");
         System.out.println(e.toString());
         System.exit(1);
      }

   } 
} 


