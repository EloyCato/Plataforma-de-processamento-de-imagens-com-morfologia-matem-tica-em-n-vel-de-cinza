package Processamento;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * @author André Couto - 2021/2
 */
public class Redness_Index_ {
    
    /*  Algoritmo para deteccao de Redness Index em
        plantas, desenvolvido por André Couto - 2021/2. */
    public static BufferedImage riRedPlant_(BufferedImage Imagem) {
            
        double max = -Double.MAX_VALUE;
        double min = Double.MAX_VALUE;
        double RI;

        /*  Loop para percorrer os pixels da imagem (Coluna x Linha), e
            calcular os indices maximo e minimo de Redness na imagem.   */
        for(int i = 0; i < Imagem.getWidth(); i++) {
            for(int j = 0; j < Imagem.getHeight(); j++) {

                // Niveis de cor de cada pixel.
                Color c = new Color(Imagem.getRGB(i, j));
                double R = new Double(c.getRed());
                double G = new Double(c.getGreen());

                // Aplicacao do Metodo.
                RI = (double)(R - G) / (R + G);
                if (RI < min) min = RI;
                if (RI > max) max = RI;
            }
        }

        
        /*  Loop para percorrer os pixels da imagem (Coluna x Linha),
            e normalizar os niveis de cor da imagem.    */
        for(int i = 0; i < Imagem.getWidth(); i++) {
            for(int j = 0; j < Imagem.getHeight(); j++) {

                // Niveis de cor de cada pixel.
                Color c = new Color(Imagem.getRGB(i, j));
                double R = new Double(c.getRed());
                double G = new Double(c.getGreen());

                // Aplicacao do metodo.
                RI = (double)(R - G) / (R + G);
                
                if(((R + G) <= 0)){ 

                    Color Novo = new Color(0, 0, 0);
                    Imagem.setRGB(i, j, Novo.getRGB());
                } else {

                    // Normalizacao dos niveis de cor.
                    double riNormalizado = 255 * ((RI - min) / (max - min));
                    int riBW = (int) riNormalizado;

                    Color Novo = new Color(riBW, riBW, riBW);
                    Imagem.setRGB(i, j, Novo.getRGB());
                }
            }
        }

        return Imagem;
    }

    
    /*
                        // Calculo opcional para Normalização,
                        // por se encaixar melhor em determinadas ocasiões

    RI =  RI * 255;

    if((RI) < 0){
                           Color newColor = new Color(0, 0, 0);
                           imagem.setRGB(i, j, newColor.getRGB());
                        } else if(RI > 255){
                           Color newColor = new Color((int)(RI), (int)(RI), (int)(RI));
                           imagem.setRGB(i, j, newColor.getRGB());
                        } else {
                           Color newColor = new Color((int) RI, (int) RI, (int) RI);
                           imagem.setRGB(i, j, newColor.getRGB());
                        }
    */
}
