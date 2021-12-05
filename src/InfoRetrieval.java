
import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import java.util.LinkedList;
import java.util.List;





public class InfoRetrieval {

public static void main(String[] args) throws FileNotFoundException, IOException, SecurityException{
	
		

        String dataFileString, stpResult = null;

        PrintWriter printWriter;
        String  stpFileName = "";  

        File dataFolder = new File("C:\\\\Users\\\\LENOVO\\\\Desktop\\\\IR\\\\data");
        File stpFolder = new File("C:\\\\Users\\\\LENOVO\\\\Desktop\\\\IR\\\\dataStpResults");
        File sfxFolder = new File("C:\\\\Users\\\\LENOVO\\\\Desktop\\\\IR\\\\dataSfxResults");
        
        
        
        File[] dataFiles = dataFolder.listFiles();
        
        
       
        fileMethods.deleteAllFilesFromDirectory(stpFolder);
        
        for (File f : dataFiles) {
        	
            System.out.println(f.getName());
            
             stpFileName =  f.getName().split("[.]")[0]  + ".txt";


            dataFileString = fileMethods.fileToSentence("C:\\Users\\LENOVO\\Desktop\\IR\\data\\" + f.getName());
            
            
            stpResult = StringStopWordsMethods.sentenceWithoutStopListWords(dataFileString);
            
            
            File myObj = new File("C:\\Users\\LENOVO\\Desktop\\IR\\dataStpResults\\"+ stpFileName);
            myObj.createNewFile();
            
            printWriter = new PrintWriter("C:\\Users\\LENOVO\\Desktop\\IR\\dataStpResults\\" +  stpFileName , "UTF-8");
            printWriter.print(stpResult.replaceAll("\\s+", System.getProperty("line.separator")));      
            printWriter.close();
            
            
            
                            }
        
        System.out.println("------------------------Phase 1-------------------");
        System.out.println("**************************************************");
        
        
        
        
       
        File[] stpFiles = stpFolder.listFiles();
        
        fileMethods.deleteAllFilesFromDirectory(sfxFolder);
        
        for(File f : stpFiles ) {
        	
        	
            String sfxFileName = f.getName().split("[.]")[0] + ".txt";

        	
        	//System.out.println("*/**/*// "+ f.getName()+" **////");
        	LinkedList<String> stpFileStringLinkedList = fileMethods.textFileToStringList("C:\\Users\\LENOVO\\Desktop\\IR\\dataStpResults\\"+f.getName());
        	//System.out.println("@@"+ stpFileStringLinkedList.toString());
        	
            String sfxResult = new Stemmer().completeStem(stpFileStringLinkedList);
            
            File myObj = new File("C:\\Users\\LENOVO\\Desktop\\IR\\dataSfxResults\\"+ sfxFileName);
            myObj.createNewFile();
            
            printWriter = new PrintWriter("C:\\Users\\LENOVO\\Desktop\\IR\\dataSfxResults\\" +  sfxFileName , "UTF-8");

            printWriter.print(sfxResult);
           
            printWriter.close();
        }
        
        
        System.out.println("------------------------Phase 2-------------------");
        System.out.println("**************************************************");
        
        
        
        
        File[] sfxFiles = sfxFolder.listFiles();
       
        HashSet<String> allWordsHashSet = new HashSet<>();
        
        for(File f: sfxFiles) {
        	LinkedList<String> allFileWords = fileMethods.textFileToStringList("C:\\Users\\LENOVO\\Desktop\\IR\\dataSfxResults\\"+f.getName());
        	
        	for(String word : allFileWords) {
        		allWordsHashSet.add(word);
        	}
        
        }
        
        ArrayList<String> allWordsWithoutRepList = new ArrayList<>(allWordsHashSet);
        Collections.sort(allWordsWithoutRepList);
        
        
        double[][] matrix = new double[sfxFiles.length][allWordsWithoutRepList.size()];
        Arrays.stream(matrix).forEach(a -> Arrays.fill(a, 0));
        
       /*Fill the matrix row by row each iteration*/
        
        for (int x=0; x < sfxFiles.length; x++) {
        	int[] tempRow = fileMethods.checkOccurenceOfwordsListInTextFile("C:\\Users\\LENOVO\\Desktop\\IR\\dataSfxResults\\"+sfxFiles[x].getName(),allWordsWithoutRepList);
        	
        	for (int i=0; i <allWordsWithoutRepList.size();i++) {
        		matrix[x][i] = tempRow[i];
        	}
        }
        
        int[] docFreq = new int[allWordsWithoutRepList.size()];
        
        for(int w =0; w < allWordsWithoutRepList.size(); w++) {
        	int dFcount =0;
        	for(int f =0; f<sfxFiles.length; f++) {
        		if(matrix[f][w]!=0)dFcount++;
        	}
        	docFreq[w]= dFcount;
        }
        
        List<String> newStopWordsList = new ArrayList<>();
        
        System.out.println("******");
        for (int i: docFreq) {
        	if(i==sfxFiles.length) {
        		newStopWordsList.add(allWordsWithoutRepList.get(i));
        		System.out.println("STP word found : "+allWordsWithoutRepList.get(i));
        	}
        }
        
        
        System.out.println("------------------------Phase 3-------------------");
        System.out.println("**************************************************");
        
        
	}
	
	
	



}
    





