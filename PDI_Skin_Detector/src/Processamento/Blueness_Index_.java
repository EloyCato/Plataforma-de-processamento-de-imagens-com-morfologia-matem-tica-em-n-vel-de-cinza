package Processamento;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * @author João Victor do Rozário Recla - 2022/2
 */
public class Blueness_Index_ {
    
    /*  Algoritmo para deteccao de Blueness Index, desenvolvido
        por João Victor do Rozário Recla - 2022/2.
    
        Adaptacao do metodo de Gabautz (para deteccao de olhos
        vermelhos) para a deteccao de Bluness Index em imagens.
    */
    public static BufferedImage Blueness_(BufferedImage Imagem) {
        
        double BI;                              // Indice de Blueness.
        int K = 14;                             // Indice K.
        double Index_max = Double.MIN_VALUE;    // Inidce maximo.
        double Index_min = Double.MAX_VALUE;    // Indice minimo.
        
        // Auxiliares para a aplicacao do metodo.
        double BI_A[][] = new double[Imagem.getWidth()][Imagem.getHeight()];
        double BI_B[][] = new double[Imagem.getWidth()][Imagem.getHeight()];
        
        
        /*  Loop para percorrer os pixels da imagem (Coluna x Linha), e
            calcular os indices maximo e minimo de Bluness na imagem.   */
        for(int i = 0; i < Imagem.getWidth(); i++) {
            for(int j = 0; j < Imagem.getHeight(); j++) {

                // Niveis de cor de cada pixel.
                Color C = new Color(Imagem.getRGB(i, j));
                double R = new Double(C.getRed());
                double G = new Double(C.getGreen());
                double B = new Double(C.getBlue());
                
                // Aplicacao do metodo.
                BI_A[i][j] = Math.pow(B, 2);
                BI_B[i][j] = Math.pow(R, 2) + Math.pow(G, 2) + K;
                BI = (double)(BI_A[i][j] / BI_B[i][j]);
                
                // Indices de Blueness.
                if(BI < Index_min) Index_min = BI;
                if(BI > Index_max) Index_max = BI;
            }
        }

        
        /*  Loop para percorrer os pixels da imagem (Coluna x Linha),
            e normalizar os niveis de cor da imagem.    */
        for(int i = 0; i < Imagem.getWidth(); i++) {
            for(int j = 0; j < Imagem.getHeight(); j++) {

                // Indice de Blueness do pixel(i, j).
                BI = (double)(BI_A[i][j] / BI_B[i][j]);
                
                if(BI_B[i][j] <= 0){

                    Color Novo = new Color(0, 0, 0);
                    Imagem.setRGB(i, j, Novo.getRGB());
                } else {
                    
                    // Normalizacao dos niveis de cor.
                    double BI_normalizado = ((BI - Index_min) / (Index_max - Index_min)) * 255;
                    int BI_color = (int) BI_normalizado;

                    Color Novo = new Color(BI_color, BI_color, BI_color);
                    Imagem.setRGB(i, j, Novo.getRGB());
                }
            }
        }

        return Imagem;
    }
}