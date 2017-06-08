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
public interface Buffer 
{
    
    public void set( int instances ) throws InterruptedException;// place int value into Buffer
    
    public int get() throws InterruptedException;// return int from Buffer
}
