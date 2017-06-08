/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DirWordCount;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author alexa
 */
public class FileReader implements Runnable
{
    private final Buffer sharedResource; // will store ref to shared object
    private static Scanner in; // to scan the files
    private static String word2search;// will hold the word being searched
    private static int instances;// stores number of instances of word
    private static ArrayList<File> files2Search;// stores arrays
    
    // constructor
    public FileReader(Buffer shared, ArrayList<File> files, String word)
    {
        sharedResource = shared;// reference to shared object
        files2Search = files;// arraylist of file objects created in main
        word2search = word;// refernce to word being searched
    }// end FileReader constructor
    
    @Override
    public void run()
    {
        
        try{
        Thread.sleep(2000);
        }catch(Throwable e){
            
        }
        
        // loops through each file in list
        for(int count = 0; count < files2Search.size(); count++)
        {
            
            readFile(files2Search.get(count)); // first uses the scanner to read
            
            getInstances();// the amount of instances if found and stores
            
            closeReader();// closes the file
            
            instances = 0;// sets instances back to 0 so each file can store their own instances
        }
    }
    
    // Function to read the files contents
    public void readFile(File filein)
    {
        try
        {
            /*  
                file is passed into the scanner object, which specifies 
                the scanner will read from that file
            */  
            in = new Scanner(filein).useDelimiter("\\s+");
        }// end try
        catch(FileNotFoundException fnfe)
        {
            System.err.println( "Error reading files" );// outputs message once caught
        }// end catch
    }// end ReadFil()
    
    // Method to get instances and send it to the buffer 
    public void getInstances()
    {
        
        String word = "";// will temp hold a word
        
        ArrayList<String> words = new ArrayList<String>();// holds array of words

        try
        {
            // as long as there's another line 
            while(in.hasNext())
            {
                
                word = in.next();// word is set to a word in the file 
                
                words.add(word);// that word is added to the array
            }// end while

            // loops round the array of words
            for(int i = 0; i < words.size(); i++)
            {
                
                String temp;// temp holds word of current loop 
                
                /*
                    Both the word being searched and the word in the arraylist
                    are converted to lowercase fo accuracy
                */
                
                temp = words.get(i).toLowerCase();
                
                word2search.toLowerCase();
                
                // condition to see if the word in parenthesis matches any in the list
                if(temp.equals(word2search))
                {
                    // increments once that the word is present
                    instances ++;
                }// end if 
            }// end for 
        }// end try
        catch(NoSuchElementException nsee)
        {
            System.err.println("File improperly formed.");
            in.close();// closes file
            System.exit(1);// ends program
        }// end catch
        catch(IllegalStateException ise)
        {
            System.err.println("Error reading from file");
            in.close();// closes file
            System.exit(1);// ends program
        }// end catch

        try
        {
            sharedResource.set(instances);// stores the number of instances in the File Handler class 
        }// end try
        catch(InterruptedException ex)
        {
            ex.printStackTrace();
        }// end catch
    }// end getInstances()

    // simply closes the file
    public void closeReader()
    {
        // scanner doesn't find null
        if(in != null)
        {
            in.close();
        }// end if
    }// end cloaseReader
    
}// end FileReader class
