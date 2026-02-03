/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 *
 * @author Math
 */
public class ImgWindow extends JInternalFrame{
    private BufferedImage img;
    private JLabel labelImg;
    private String originalName;
    
    public ImgWindow(BufferedImage img, String title, String sufixo){
        super(title+sufixo, true, true, true, true);
        this.img = img;
        this.originalName = title+sufixo;        
        
        this.labelImg = new JLabel(new ImageIcon(img));
        
        JScrollPane scroll = new JScrollPane(labelImg);
        this.add(scroll);
        this.pack();
        this.setVisible(true);
    }
    
    
    public BufferedImage getImg(){
        return img;
    }
    
    public void setImg(BufferedImage img){
        this.img = img;
        labelImg.setIcon(new ImageIcon(img));
        this.revalidate();
        this.repaint();
    }
    
    public String getOriginalName(){
        return originalName;
    }
}
