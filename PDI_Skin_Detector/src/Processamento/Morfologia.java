/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Processamento;

import java.awt.image.BufferedImage;
import java.awt.Point;
import java.util.Collections;
import java.util.ArrayList;
import java.awt.Color;
/**
 *
 * @author Eloy
 */
public class Morfologia {
    
    public static BufferedImage dilatacaoNivelDeCinza(BufferedImage imagem_original, ArrayList<Point> elementoEstruturante){
        ArrayList<Integer> vizinhos = new ArrayList<>();
        BufferedImage novaImagem = new BufferedImage(imagem_original.getWidth(),imagem_original.getHeight(),13);
        Color c;
        int posX,posY;
        for(int i=0 ; i<imagem_original.getWidth() ; i++){
            for(int j=0 ; j<imagem_original.getHeight() ; j++){
                for(Point index : elementoEstruturante){
                    if(i + index.getX()>=0 && j +index.getY()>=0){
                        if(i + index.getX()<imagem_original.getWidth() && j +index.getY()<imagem_original.getHeight()){
                            posX=i+(int)index.getX();
                            posY=j+(int)index.getY();
                            c = new Color(imagem_original.getRGB(posX,posY));
                            vizinhos.add(c.getBlue());
                        }
                    }
                }
                int maximo = Collections.max(vizinhos);
                c = new Color(maximo,maximo,maximo);
                novaImagem.setRGB(i,j,c.getRGB());
                vizinhos.clear();
            }
        }
        return novaImagem;
    }
    
    public static BufferedImage erosaoNivelDeCinza(BufferedImage imagem_original, ArrayList<Point> elementoEstruturante){
        ArrayList<Integer> vizinhos = new ArrayList<>();
        BufferedImage novaImagem = new BufferedImage(imagem_original.getWidth(),imagem_original.getHeight(),13);
        Color c;
        int posX,posY;
        for(int i=0 ; i<imagem_original.getWidth() ; i++){
            for(int j=0 ; j<imagem_original.getHeight() ; j++){
                for(Point index : elementoEstruturante){
                    if(i + index.getX()>=0 && j +index.getY()>=0){
                        if(i + index.getX()<imagem_original.getWidth() && j +index.getY()<imagem_original.getHeight()){
                            posX=i+(int)index.getX();
                            posY=j+(int)index.getY();
                            c = new Color(imagem_original.getRGB(posX,posY));
                            vizinhos.add(c.getBlue());
                        }
                    }
                }
                int minimo = Collections.min(vizinhos);
                c = new Color(minimo,minimo,minimo);
                novaImagem.setRGB(i,j,c.getRGB());
                vizinhos.clear();
            }
        }
        return novaImagem;
    }
    
    public static BufferedImage aberturaNivelDeCinza(BufferedImage imagem_original, ArrayList<Point> elementoEstruturante){
        BufferedImage novaImagem;
        
        novaImagem = erosaoNivelDeCinza(imagem_original,elementoEstruturante);
        novaImagem = dilatacaoNivelDeCinza(novaImagem,elementoEstruturante);
        return novaImagem;
    }
    
    public static BufferedImage fechamentoNivelDeCinza(BufferedImage imagem_original, ArrayList<Point> elementoEstruturante){
        BufferedImage novaImagem;
        
        novaImagem = dilatacaoNivelDeCinza(imagem_original,elementoEstruturante);
        novaImagem = erosaoNivelDeCinza(novaImagem,elementoEstruturante);
        
        return novaImagem;
    }
    
    public static BufferedImage tophatAbertura(BufferedImage imagem_original, ArrayList<Point> elementoEstruturante){
        BufferedImage novaImagem;
        int diferenca, pix1,pix2;
        Color c;
        
        novaImagem = aberturaNivelDeCinza(imagem_original, elementoEstruturante);
        for(int i=0 ; i<imagem_original.getWidth() ; i++){
            for(int j=0 ; j<imagem_original.getHeight() ; j++){
                pix1 = new Color(imagem_original.getRGB(i, j)).getBlue();
                pix2 = new Color(novaImagem.getRGB(i, j)).getBlue();
                diferenca =  pix1 - pix2;
                c = new Color(diferenca,diferenca,diferenca);
                novaImagem.setRGB(i, j, c.getBlue());
            }
        }
        return novaImagem;
    }
    
    public static BufferedImage tophatFechamento(BufferedImage imagem_original, ArrayList<Point> elementoEstruturante){
        BufferedImage novaImagem;
        int diferenca, pix1,pix2;
        Color c;
        
        novaImagem = fechamentoNivelDeCinza(imagem_original, elementoEstruturante);
        for(int i=0 ; i<imagem_original.getWidth() ; i++){
            for(int j=0 ; j<imagem_original.getHeight() ; j++){
                pix1 = new Color(imagem_original.getRGB(i, j)).getBlue();
                pix2 = new Color(novaImagem.getRGB(i, j)).getBlue();
                diferenca =  pix2 - pix1;
                c = new Color(diferenca,diferenca,diferenca);
                novaImagem.setRGB(i, j, c.getBlue());
            }
        }
        return novaImagem;
    }
}
