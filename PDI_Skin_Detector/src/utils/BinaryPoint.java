/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Point;

/**
 *
 * @author Eloy
 */
public class BinaryPoint extends Point {
    private boolean carry;
    
    public BinaryPoint(int x, int y, boolean carry){
        super(x,y);
        this.carry = carry;
    }
    
    public boolean getCarry(){
        return this.carry;
    }
}
