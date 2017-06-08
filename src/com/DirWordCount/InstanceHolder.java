/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DirWordCount;

/**
 *
 * @author alexa
 */
public class InstanceHolder implements Buffer
{
    private int fileWordcount;// variable to store number of instance of word that appears in a thread
    
    private boolean occupied = false;// whether the FileHandler is occupied
    
    // place value into FileHandler
    public synchronized void set( int instances ) throws InterruptedException
    {
        /*
            while there are no empty location, place thread in waiting state, 
            this is to make sure that all the locations aren't being used to 
            allow the threading calling this method to access it
        */
        while(occupied)
        {
            wait();// thread is put in waiting state
        }// end while
        
        fileWordcount = instances;// whatever the value in the 
        
        /* 
            Indicates another FileReader can't store value
            waits for word reader to retrive value
        */ 
        occupied = true;
        
        notifyAll();// tells all thread(s) to enter runnable state 
    }// end set()
    
    // returns value from wordcount
    public synchronized int get() throws InterruptedException
    {
        /* 
            while no data to read, place thread in waiting state,
            this is to male sure that the FileReader class writes to this class
            before the WordReader gets the instances in this class
        */
        while( !occupied )
        {
            wait();// put current thread to sleep 
        }// end while
        
        /* 
            indicates that fileReader can store another wordcount
            WordReader just stored the wordcount of the last FileReader
        */ 
        occupied = false;
        
        notifyAll();// tells waiting threads to enter runnable
        
        return fileWordcount;
        
    }// end get()
}// end InstanceHolder class
