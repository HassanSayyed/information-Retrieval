
import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;



public class InfoRetrieval {

public static void main(String[] args) throws FileNotFoundException, IOException, SecurityException{
	
		

        String filePathString, result = null;

        PrintWriter printWriter;
        String  stpFileName = "";  

        File dataFolder = new File("C:\\\\Users\\\\LENOVO\\\\Desktop\\\\IR\\\\data");
        
        File[] dataFiles = dataFolder.listFiles();
        
        
       
        
        
        for (File f : dataFiles) {
        	
            System.out.println(f.getName());
            
             stpFileName =  f.getName().split("[.]")[0]  + ".txt";


            filePathString = fileMethods.fileToSentence("C:\\Users\\LENOVO\\Desktop\\IR\\data\\" + f.getName());
            
            
            result = StringStopWordsMethods.sentenceWithoutStopListWords(filePathString);
            
            
            File myObj = new File("C:\\Users\\LENOVO\\Desktop\\IR\\dataStpResults\\"+ stpFileName);
            myObj.createNewFile();
            
            printWriter = new PrintWriter("C:\\Users\\LENOVO\\Desktop\\IR\\dataStpResults\\" +  stpFileName , "UTF-8");
            printWriter.print(result.replaceAll("\\s+", System.getProperty("line.separator")));      
            printWriter.close();
            
            
            
                            }
        
        System.out.println("------------------------Phase 1-------------------");
        System.out.println("**************************************************");
        
        
        
        
        File stpFolder = new File("C:\\\\Users\\\\LENOVO\\\\Desktop\\\\IR\\\\dataStpResults");
        File[] stpFiles = stpFolder.listFiles();
        
        for(File f : stpFiles ) {
        	
        	
            String sfxFileName = f.getName().split("[.]")[0] + ".txt";

        	
        	//System.out.println("*/**/*// "+ f.getName()+" **////");
        	LinkedList<String> stpFileStringLinkedList = fileMethods.textFileToStringList("C:\\Users\\LENOVO\\Desktop\\IR\\dataStpResults\\"+f.getName());
        	//System.out.println("@@"+ stpFileStringLinkedList.toString());
        	
        	Stemmer proterStemmer = new Stemmer();
            String sfxResult = proterStemmer.completeStem(stpFileStringLinkedList);
            
            File myObj = new File("C:\\Users\\LENOVO\\Desktop\\IR\\dataSfxResults\\"+ sfxFileName);
            myObj.createNewFile();
            
            printWriter = new PrintWriter("C:\\Users\\LENOVO\\Desktop\\IR\\dataSfxResults\\" +  sfxFileName , "UTF-8");

            printWriter.print(sfxResult);
           
            printWriter.close();
        }
        
        
        System.out.println("------------------------Phase 2-------------------");
        
        
        
        File sfxFolder = new File("C:\\\\Users\\\\LENOVO\\\\Desktop\\\\IR\\\\dataStpResults");
        File[] sfxFiles = stpFolder.listFiles();

        
	}
	
	
	



}
    





