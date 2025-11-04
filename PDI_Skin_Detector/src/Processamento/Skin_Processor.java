/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Processamento;

import java.awt.Color;
import java.awt.image.BufferedImage;
import utils.Parametros_Deteccao_RGB;
import utils.Parametros_Deteccao_YCgCr;

/**
 *
 * @author Math
 */
public class Skin_Processor {
    
    
    public static BufferedImage RGB_Norm(BufferedImage imagemOriginal, Parametros_Deteccao_RGB params){
        
        System.out.println("Processando rgb...");
        System.out.println("Criterio 1: " + params.usarCriterio1 + "| Min: " + params.criterio1Min + " Max: " + params.criterio1Max + "\n"
                        + "Criterio 2: " + params.usarCriterio2 + "| Min: " + params.criterio2Min + " Max: " + params.criterio2Max + "\n"
                        + "Criterio 3: " + params.usarCriterio3 + "| Min: " + params.criterio3Min + " Max: " + params.criterio3Max + "");
        
        
        int width = imagemOriginal.getWidth();
        int height = imagemOriginal.getHeight();
        
        BufferedImage imagemProcessada = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for(int h = 0; h < height; h++) {
            for(int w = 0; w < width; w++){
                int rgb = imagemOriginal.getRGB(w, h);
                Color color = new Color(rgb);
                
                int R = color.getRed();
                int G = color.getGreen();
                int B = color.getBlue();
                
                double r_norm, g_norm, b_norm;
                int soma = R + G + B;
                if(soma == 0){
                    r_norm = g_norm = b_norm = 1.0/3.0;
                }
                else{
                    r_norm = (double) R / soma;
                    g_norm = (double) G / soma;
                    b_norm = (double) B / soma;
                }
               
                boolean pass = true;
                
                if(params.usarCriterio1){
                    double criterio_value = (g_norm == 0) ? Double.MAX_VALUE : r_norm/g_norm;
                    pass &= (criterio_value > params.criterio1Min && criterio_value < params.criterio1Max);
                }
                
                if(params.usarCriterio2){
                    double criterio_value = r_norm*b_norm / ((r_norm + g_norm + b_norm)*(r_norm + g_norm + b_norm));
                    pass &= (criterio_value > params.criterio2Min && criterio_value < params.criterio2Max);
                }
                
                if(params.usarCriterio3){
                    double criterio_value = r_norm*g_norm / ((r_norm + g_norm + b_norm)*(r_norm + g_norm + b_norm));
                    pass &= (criterio_value > params.criterio3Min && criterio_value < params.criterio3Max);
                }
                
                if(pass){
                    imagemProcessada.setRGB(w, h, rgb);
                }
                else{
                    imagemProcessada.setRGB(w, h, Color.BLACK.getRGB());
                }
            }
        }
        
        System.out.println("Retornando Imagem....\n");
        
        return imagemProcessada;
    }
    
    public static BufferedImage YCgCr(BufferedImage imagemOriginal, Parametros_Deteccao_YCgCr params){
        
        System.out.println("Processando YCgCr...");
        System.out.println("Criterio 1: " + params.usarCriterio1 + "| Min: " + params.criterio1Min + " Max: " + params.criterio1Max + "\n"
                        + "Criterio 2: " + params.usarCriterio2 + "| Min: " + params.criterio2Min + " Max: " + params.criterio2Max + "");
        
        
        int width = imagemOriginal.getWidth();
        int height = imagemOriginal.getHeight();
        
        BufferedImage imagemProcessada = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for(int h = 0; h < height; h++) {
            for(int w = 0; w < width; w++){
                int rgb = imagemOriginal.getRGB(w, h);
                Color color = new Color(rgb);
                
                int R = color.getRed();
                int G = color.getGreen();
                int B = color.getBlue();
                
                double[] ycgcr = converterRGBparaYCgCr(R,G,B);
                double Cg, Cr;
                
                Cg = ycgcr[1];
                Cr = ycgcr[2];
               
                boolean pass = true;
                
                if(params.usarCriterio1){
                    double criterio_value = Cg;
                    pass &= (criterio_value >= params.criterio1Min && criterio_value <= params.criterio1Max);
                }
                
                if(params.usarCriterio2){
                    double criterio_value = Cr;
                    pass &= (criterio_value >= params.criterio2Min && criterio_value <= params.criterio2Max);
                }
                
                if(pass){
                    imagemProcessada.setRGB(w, h, rgb);
                }
                else{
                    imagemProcessada.setRGB(w, h, Color.BLACK.getRGB());
                }
            }
        }
        
        System.out.println("Retornando Imagem....");
        
        
        return imagemProcessada;
    }
    
    public static double[] converterRGBparaYCgCr(int R, int G, int B) {

        double r_norm = R / 255.0;
        double g_norm = G / 255.0;
        double b_norm = B / 255.0;
        
        double Y  =  65.481 * r_norm + 128.553 * g_norm + 24.966 * b_norm +  16;
        double Cg = -81.085 * r_norm + 112.000 * g_norm - 30.915 * b_norm + 128;
        double Cr = 112.000 * r_norm -  93.786 * g_norm - 18.214 * b_norm + 128;

        return new double[] { Y, Cg, Cr };
    }
    
}
