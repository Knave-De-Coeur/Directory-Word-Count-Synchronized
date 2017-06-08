/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DirWordCount;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author alexa
 */
public class Main {

    private static ArrayList<File> files2Search = new ArrayList<>();
    private static File file;
    private static Scanner in = new Scanner(System.in);
    private static String directory;
    private static String word2Search;
    private static Buffer sharedResource;
    private static ExecutorService application;
    private static long started; 
    
    public static void main(String[] args) 
    {
        // simply checks if the correct amount of arguments were inputted
        if(args.length == 2)
        {
            directory = args[0];// first arg set as the file/directory
            
            word2Search = args[1];// second arg set as word to be search for
        }
        // any other amount of args will simply prompt user to input the data
        else
        {
            System.out.println("Enter word to search: ");
            
            word2Search = in.nextLine();// user inputs word

            System.out.println("Enter file or directory");
            
            directory = in.nextLine();// user inputs directory or file
        }
        
        file = new File(directory);// directory is saved in file object
        
        RegisterFiles(file);// converts into individual files and/or just stores in array
        
        application = Executors.newCachedThreadPool();// threadpool created 
        
        sharedResource = new InstanceHolder();// Synchronization buffer to store ints
                
        System.out.println("Files to read: " + files2Search.size());
        System.out.println("Searching for word: " + word2Search);
        
        started = System.currentTimeMillis();// stores time of execution
        
        application.execute(new FileReader(sharedResource, files2Search, word2Search));// executes producer to write the data to the buffer
        
        application.execute(new InstanceGrabber(sharedResource, files2Search));// executes consumer to read contents from buffer
        
        application.shutdown();// Shuts down the executor 
        try
        {
            boolean tasksEnded = application.awaitTermination(1, TimeUnit.MINUTES);
            // if task ended in time
            if( tasksEnded )
            {
                // outputs how log prgram took to complete all threads
                System.out.println("Copmleted in: " + (System.currentTimeMillis()-started) + "milliseconds");
            }
            else 
                System.out.println("Timed out while waiting for tasks to finish");
        }// end try
        catch(InterruptedException ex)
        {
            System.out.println("Interrupted while waiting for tasks to finish");
        }// end catch
    }// end main method
    
    // Function checks and adds files
    public static void RegisterFiles(File file)
    {
        // since file objects can be files or directories condition validates both
        if(file.isFile())
        {
            // if it is a file add it to the arraylist
            files2Search.add(file);
        }// end if
        else if(file.isDirectory())
        {
            // else loop through the directory and save each file in the list
            for(File tFile : file.listFiles())
            {
                RegisterFiles(tFile);
            }// end for
        }// end else if
    }// end RegisterFiles
    
}// end main class
