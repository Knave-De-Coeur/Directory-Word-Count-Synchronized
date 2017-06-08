/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DirWordCount;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author alexa
 */
public class InstanceGrabber implements Runnable
{
    private static ArrayList<Integer> instances  = new ArrayList<>();// holds the instances of different files
    private final Buffer sharedResource;// will hold reference to shared object
    private static ArrayList<File> _File;// will hold the files being searched
    private static int totInstances;// will hold the total times the word pops up in a directory
    
    // constructor
    public InstanceGrabber( Buffer shared, ArrayList<File> files)
    {
        sharedResource = shared;// shared object
        _File = files;// files being searched
    }// end constructor
    
    @Override
    public void run()
    {
        int value;
        // loops as many times as the size of the files array
        for(int count = 0; count < _File.size(); count++)
        {
            try
            {
                 value = sharedResource.get();// will get the value of the instances of a particular file
                instances.add(value);// put in the array
                totInstances += value;// total is incremented by the value of temp
            }// end try
            catch(InterruptedException ex)
            {
                System.err.println("Error happening in  word reader: " + ex.getMessage());
            }// end catch
            //System.out.println("All wordreader threads have ended");
        }// end for
        
        // Once the loop ends the files and their instances are outputted
        for(int i = 0; i < _File.size(); i++)
        {
            System.out.println("Searched file: "+ _File.get(i).getName() + " found: " + instances.get(i));
        }
        System.out.println("Total instances: " + totInstances);
        System.out.println("************************************");
    }// end run
}// end WordGrabber class
