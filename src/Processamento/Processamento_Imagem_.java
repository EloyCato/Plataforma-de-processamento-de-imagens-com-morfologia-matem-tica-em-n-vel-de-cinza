package Processamento;

import java.awt.image.BufferedImage;

/**
 * @author João Victor do Rozário Recla - 2022/2
 */
public class Processamento_Imagem_ {


	public static BufferedImage Segment_(BufferedImage Imagem, int Metodo) {
            
            // Aplicacao do algoritmo.
            switch(Metodo){
                
                case 1:  return Segmentacao_.Otsu_Binarization_(Imagem);
                case 2:  return Segmentacao_.Fuzzy_Huang_Binarization_(Imagem);
                default: return Imagem;
            }
	}
}
