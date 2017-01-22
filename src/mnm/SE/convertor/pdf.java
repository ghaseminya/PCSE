/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mnm.SE.convertor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;


/**
 *
 * @author mnm
 */
public class pdf {
    public  void pdf(String path,String temppath)
    {
        FileOutputStream fout;
        File file;
        boolean success = (new File(temppath+"/temp")).mkdirs();
        //if (success) {
        file =new File(temppath+"/temp/01tempmnm");
        if(file.isFile()){file.delete();}
        try
        {
            file = new File(path);
            if(!file.isFile()){
            System.out.println("Error: Can't open input file :\t"+path);
            }
            file=new File(temppath+"/temp");
            if(!file.isDirectory()){
            System.out.println("Error: Can't open temp dir :\t"+temppath+"/temp");
            }
            fout = new FileOutputStream ("Runnermnm");
            PrintStream mnm=new PrintStream(fout);
            mnm.println ("#!/bin/sh");
            mnm.println ("pdftotext "+path+" "+temppath+"/temp/01tempmnm");
            
            fout.close();
		}
		catch (IOException e)
		{
			System.err.println ("Unable to write to file");
			System.exit(-1);
		}
        try
        {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("sh Runnermnm");
            pr.waitFor();
            System.out.println("created value "+pr.exitValue());
        } catch(Exception e)
        {
            System.out.println(e.toString());
            e.printStackTrace();
        }
     }

}


