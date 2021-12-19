
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
import java.util.Scanner;






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
        
        // detect new stop words
        List<String> newStopWordsList = new ArrayList<>();
        
        System.out.println(" ");
        for (int i: docFreq) {
        	if(i==sfxFiles.length) {
        		newStopWordsList.add(allWordsWithoutRepList.get(i));
        		System.out.println("STP word found : "+allWordsWithoutRepList.get(i));
        	}
        }
        
        
        // Calculate TFIDF = TF (term freq.) * IDF (inverse doc freq)
        
        for(int w=0; w < allWordsWithoutRepList.size(); w++) {
        	for(int f=0; f < sfxFiles.length; f++) {
        		matrix[f][w] = matrix[f][w] * Math.log10(sfxFiles.length/ Double.valueOf(docFreq[w]));
        	}
        }
        
        
        
        
        System.out.println("------------------------Phase 3-------------------");
        System.out.println("**************************************************");
        
        
        
        System.out.println(" Search query: ");
        Scanner inputScanner =new  Scanner(System.in);
        String query = StringStopWordsMethods.sentenceWithoutStopListWords(inputScanner.nextLine()); 
        inputScanner.close();
        
        LinkedList<String> queryLinkedList = new LinkedList<>(Arrays.asList(query.split("\\s+")));
        
        
        String[] queryWords = new Stemmer().completeStem(queryLinkedList).split("[\\n\\s+]");
      
        HashSet<String> queryHashSet = new HashSet<>();
        
        for(String q : queryWords) {
        	if(!q.isEmpty())
        	 queryHashSet.add(q);
        }
        
        // user query Term Frequency table (word : queryWordsWithoutRep , count : queryTermFreq)
        ArrayList<String> queryWordsWithoutRep = new ArrayList<String>(queryHashSet);
        int[] queryTermFreq = fileMethods.checkOccurenceOfWordsListInLinkedList(new LinkedList<>(Arrays.asList(queryWords)),queryWordsWithoutRep);
        
         
        double[] queryWordsInAllWordsWithoutRepTfidf = new double[allWordsWithoutRepList.size()];
        
        for(int w = 0; w<allWordsWithoutRepList.size(); w++) {
        	for(int q=0; q<queryWordsWithoutRep.size(); q++) {
        		
        		if(allWordsWithoutRepList.get(w).equals(queryWordsWithoutRep.get(q))) {
        			
        			queryWordsInAllWordsWithoutRepTfidf[w] = queryTermFreq[q] * Math.log10(sfxFiles.length/ Double.valueOf(docFreq[w]));
        			break;
        			
        		}
        	}
        }
        // query count in all words table ( word : allWordsWithoutRepList, count : queryWordsInAllWordsWithoutRepTfidf)
        
        
        /*
        for(int x=0; x< queryWordsInAllWordsWithoutRepTfidf.length;x++) {
        	System.out.println(" word "+allWordsWithoutRepList.get(x)+" : "+queryWordsInAllWordsWithoutRepTfidf[x]);
        }*/
        	
        // cosin ( Doc & querty )
        
        double[] cosinDocRank = new double[sfxFiles.length];
        
        
        double cosinNumerator;
        double cosinDenominator;
        double ti = 0;
        double tj = 0;
        
        for (int f=0; f< sfxFiles.length; f++) {
        	
        	cosinNumerator = 0;
        	cosinDenominator = 1;
        	
        	
        	
        	for(int w=0;w<allWordsWithoutRepList.size();w++) {
        		
        		cosinNumerator += matrix[f][w] * queryWordsInAllWordsWithoutRepTfidf[w];
        		ti += Math.pow( matrix[f][w] , 2);
        		tj += Math.pow( queryWordsInAllWordsWithoutRepTfidf[w] , 2);
        		cosinDenominator *= Math.sqrt(ti+tj);
        	}
        	
        	cosinDocRank[f] = Double.isNaN(cosinNumerator/cosinDenominator)? 0.0 : cosinNumerator/cosinDenominator;
        	
        	
        }
        
        for(int f=0;f<sfxFiles.length;f++) {
        	System.out.println("file "+sfxFiles[f].getName()+" cosin "+cosinDocRank[f]);
        }
        
        System.out.println("------------------------Phase 4-------------------");
        System.out.println("**************************************************");
        
	}
	
	




}
    





