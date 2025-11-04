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
public class HaulingPoint extends Point {
    private int carry;
    
    public HaulingPoint(int x, int y, int carry){
        super(x,y);
        this.carry = carry;
    }
    
    public int getCarry(){
        return this.carry;
    }
}
