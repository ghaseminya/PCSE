/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mnm.SE.convertor;
/**
 *
 * @author mnm
 */
public class runner {
public boolean runner(String path,int a,String temppath)
{
 switch(a){
     case 1:{new pdf().pdf(path,temppath);break;}
     case 2:
     case 3:
     case 4:
     case 5:
     case 6:
         return false;

 }
 return false;
}
}
