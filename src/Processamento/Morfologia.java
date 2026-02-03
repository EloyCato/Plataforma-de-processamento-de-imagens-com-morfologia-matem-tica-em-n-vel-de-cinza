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
import java.lang.Math;

/**
 *
 * @author Eloy
 */
public class Morfologia {

    public static BufferedImage dilatacaoNivelDeCinza(BufferedImage imagem_original, ArrayList<Point> elementoEstruturante, int iteracoes) {
        long inicio = System.currentTimeMillis();
        ArrayList<Integer> vizinhos = new ArrayList<>();
        BufferedImage novaImagem = new BufferedImage(imagem_original.getWidth(), imagem_original.getHeight(), 2);
        BufferedImage buffer = copyImage(imagem_original);
        Color c;
        int posX, posY;
        for (int k = 0; k < iteracoes; k++) {
            for (int i = 0; i < imagem_original.getWidth(); i++) {
                for (int j = 0; j < imagem_original.getHeight(); j++) {
                    for (Point index : elementoEstruturante) {
                        if (i + index.getX() >= 0 && j + index.getY() >= 0) {
                            if (i + index.getX() < imagem_original.getWidth() && j + index.getY() < imagem_original.getHeight()) {
                                posX = i + (int) index.getX();
                                posY = j + (int) index.getY();
                                c = new Color(buffer.getRGB(posX, posY));
                                vizinhos.add(c.getBlue());
                            }
                        }
                    }
                    int maximo = Collections.max(vizinhos);
                    c = new Color(maximo, maximo, maximo);
                    novaImagem.setRGB(i, j, c.getRGB());
                    vizinhos.clear();
                }
            }
            buffer = copyImage(novaImagem);
        }
        long fim = System.currentTimeMillis();
        long tempoDecorrido = fim - inicio;
        System.out.println("DILATACAO Tempo decorrido:"+tempoDecorrido);
        return novaImagem;
    }

    public static BufferedImage erosaoNivelDeCinza(BufferedImage imagem_original, ArrayList<Point> elementoEstruturante, int iteracoes) {
        long inicio = System.currentTimeMillis();
        ArrayList<Integer> vizinhos = new ArrayList<>();
        BufferedImage novaImagem = new BufferedImage(imagem_original.getWidth(), imagem_original.getHeight(), 2);
        BufferedImage buffer = imagem_original;
        Color c;
        int posX, posY;
        for (int k = 0; k < iteracoes; k++) {
            for (int i = 0; i < imagem_original.getWidth(); i++) {
                for (int j = 0; j < imagem_original.getHeight(); j++) {
                    for (Point index : elementoEstruturante) {
                        if (i + index.getX() >= 0 && j + index.getY() >= 0) {
                            if (i + index.getX() < imagem_original.getWidth() && j + index.getY() < imagem_original.getHeight()) {
                                posX = i + (int) index.getX();
                                posY = j + (int) index.getY();
                                c = new Color(buffer.getRGB(posX, posY));
                                vizinhos.add(c.getBlue());
                            }
                        }
                    }
                    int minimo = Collections.min(vizinhos);
                    c = new Color(minimo, minimo, minimo);
                    novaImagem.setRGB(i, j, c.getRGB());
                    vizinhos.clear();
                }
            }
            buffer = copyImage(novaImagem);

        }
        long fim =  System.currentTimeMillis();
        long tempoDecorrido = fim - inicio;
        System.out.println("EROSAO Tempo decorrido:"+tempoDecorrido);
        return novaImagem;
    }

    public static BufferedImage aberturaNivelDeCinza(BufferedImage imagem_original, ArrayList<Point> elementoEstruturante, int iteracoes) {
        long inicio = System.currentTimeMillis();
        BufferedImage novaImagem;

        novaImagem = erosaoNivelDeCinza(imagem_original, elementoEstruturante, iteracoes);
        novaImagem = dilatacaoNivelDeCinza(novaImagem, elementoEstruturante, iteracoes);
        long fim = System.currentTimeMillis();
        long tempoDecorrido = fim - inicio;
        System.out.println("ABERTURA=Tempo decorrido:"+tempoDecorrido);
        return novaImagem;
    }

    public static BufferedImage fechamentoNivelDeCinza(BufferedImage imagem_original, ArrayList<Point> elementoEstruturante, int iteracoes) {
        long inicio = System.currentTimeMillis();
        BufferedImage novaImagem;

        novaImagem = dilatacaoNivelDeCinza(imagem_original, elementoEstruturante, iteracoes);
        novaImagem = erosaoNivelDeCinza(novaImagem, elementoEstruturante, iteracoes);
        long fim = System.currentTimeMillis();
        long tempoDecorrido = fim - inicio;
        System.out.println("FECHAMENTO Tempo decorrido:"+tempoDecorrido);
        return novaImagem;
    }

    public static BufferedImage tophatAbertura(BufferedImage imagem_original, ArrayList<Point> elementoEstruturante, int iteracoes) {
        BufferedImage novaImagem;
        int diferenca, pix1, pix2;
        Color c;

        novaImagem = aberturaNivelDeCinza(imagem_original, elementoEstruturante, iteracoes);
        for (int i = 0; i < imagem_original.getWidth(); i++) {
            for (int j = 0; j < imagem_original.getHeight(); j++) {
                pix1 = new Color(imagem_original.getRGB(i, j)).getBlue();
                pix2 = new Color(novaImagem.getRGB(i, j)).getBlue();
                diferenca = pix1 - pix2;

                diferenca = (diferenca < 0) ? 0 : diferenca;
                diferenca = (diferenca > 255) ? 255 : diferenca;
                c = new Color(diferenca, diferenca, diferenca);
                novaImagem.setRGB(i, j, c.getRGB());
            }
        }
        return novaImagem;
    }

    public static BufferedImage tophatFechamento(BufferedImage imagem_original, ArrayList<Point> elementoEstruturante, int iteracoes) {
        BufferedImage novaImagem;
        int diferenca, pix1, pix2;
        Color c;

        novaImagem = fechamentoNivelDeCinza(imagem_original, elementoEstruturante, iteracoes);
        for (int i = 0; i < imagem_original.getWidth(); i++) {
            for (int j = 0; j < imagem_original.getHeight(); j++) {
                pix1 = new Color(imagem_original.getRGB(i, j)).getBlue();
                pix2 = new Color(novaImagem.getRGB(i, j)).getBlue();
                diferenca = pix2 - pix1;

                diferenca = (diferenca < 0) ? 0 : diferenca;
                diferenca = (diferenca > 255) ? 255 : diferenca;
                c = new Color(diferenca, diferenca, diferenca);
                novaImagem.setRGB(i, j, c.getRGB());
            }
        }
        return novaImagem;
    }

    public static BufferedImage copyImage(BufferedImage original) {
        BufferedImage copia = new BufferedImage(original.getWidth(), original.getHeight(), 2);

        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                copia.setRGB(i, j, original.getRGB(i, j));
            }
        }
        return copia;
    }

    public static Boolean checaCompatibilidade(BufferedImage mascara, BufferedImage marcador) {

        if (mascara.getHeight() == marcador.getHeight() && mascara.getWidth() == marcador.getWidth()) {
            return true;
        } else {
            return false;
        }

    }

    public static BufferedImage reconstrucaoRapida(BufferedImage mascara, BufferedImage marcador, ArrayList<Point> elementoEstruturante) {
//        BufferedImage copia = new BufferedImage(mascara.getWidth(), mascara.getHeight(), 2);
//        BufferedImage buffer = new BufferedImage(mascara.getWidth(), mascara.getHeight(), 2);
        BufferedImage bufferMarcador = Morfologia.copyImage(marcador);
        int posX, posY, dilatacao, pixelMascara, minimo;
        Color c;
        ArrayList<Integer> vizinhos = new ArrayList<>();
        ArrayList<Point> fifo = new ArrayList<>();
        //sentido raster
        for (int i = 0; i < (int) bufferMarcador.getWidth(); i++) {
            for (int j = 0; j < (int) bufferMarcador.getHeight(); j++) {
                for (int k = 0; k < elementoEstruturante.size() / 2; k++) {
                    if (i + elementoEstruturante.get(k).x >= 0 && j + elementoEstruturante.get(k).y >= 0) {
                        if (i + elementoEstruturante.get(k).x < bufferMarcador.getWidth() && j + elementoEstruturante.get(k).y < bufferMarcador.getHeight()) {
                            posX = i + elementoEstruturante.get(k).x;
                            posY = j + elementoEstruturante.get(k).y;
                            c = new Color(bufferMarcador.getRGB(posX, posY));
                            vizinhos.add(c.getBlue());
                        }
                    }
                }
                if(vizinhos.isEmpty()){
                    dilatacao = 255;
                }else{
                    dilatacao = Collections.max(vizinhos);
                }
                pixelMascara = new Color(mascara.getRGB(i, j)).getBlue();
                minimo = Math.min(pixelMascara, dilatacao);
                c = new Color(minimo, minimo, minimo);
                bufferMarcador.setRGB(i, j, c.getRGB());
                vizinhos.clear();
            }
        }
        //sentido antiraster
        for (int i = (int) bufferMarcador.getWidth() -1 ; i >= 0; i--) {
            for (int j = (int) bufferMarcador.getHeight()-1 ; j >= 0; j--) {
                for (int k = 1 + elementoEstruturante.size() / 2; k < elementoEstruturante.size(); k++) {
                    if (i + elementoEstruturante.get(k).x >= 0 && j + elementoEstruturante.get(k).y >= 0) {
                        if (i + elementoEstruturante.get(k).x < bufferMarcador.getWidth() && j + elementoEstruturante.get(k).y < bufferMarcador.getHeight()) {
                            posX = i + elementoEstruturante.get(k).x;
                            posY = j + elementoEstruturante.get(k).y;
                            c = new Color(bufferMarcador.getRGB(posX, posY));
                            vizinhos.add(c.getBlue());
                        }
                    }
                }
                if(vizinhos.isEmpty()){
                    dilatacao = 255;
                }else{
                    dilatacao = Collections.max(vizinhos);
                }

                pixelMascara = new Color(mascara.getRGB(i, j)).getBlue();
                minimo = Math.min(pixelMascara, dilatacao);
                c = new Color(minimo, minimo, minimo);
                bufferMarcador.setRGB(i, j, c.getRGB());
                vizinhos.clear();

                for (int k = 1 + elementoEstruturante.size() / 2; k < elementoEstruturante.size(); k++) {
                    if (i + elementoEstruturante.get(k).x >= 0 && j + elementoEstruturante.get(k).y >= 0) {
                        if (i + elementoEstruturante.get(k).x < bufferMarcador.getWidth() && j + elementoEstruturante.get(k).y < bufferMarcador.getHeight()) {
                            posX = i + elementoEstruturante.get(k).x;
                            posY = j + elementoEstruturante.get(k).y;
                            c = new Color(bufferMarcador.getRGB(posX, posY));
                            if (c.getBlue() < new Color(bufferMarcador.getRGB(i, j)).getBlue()) {
                                if (c.getBlue() < new Color(mascara.getRGB(posX, posY)).getBlue()) {
                                    fifo.add(new Point(i, j));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        // propagação

        while (fifo.isEmpty() == false) {
            Point pixel = fifo.get(0);

            for (Point index : elementoEstruturante) {
                if (pixel.x + index.x >= 0 && pixel.y + index.y >= 0) {
                    if (pixel.x + index.x < bufferMarcador.getWidth() && pixel.y + index.y < bufferMarcador.getHeight()) {
                        if (new Color(bufferMarcador.getRGB(pixel.x + index.x, pixel.y + index.y)).getBlue() < new Color(bufferMarcador.getRGB(pixel.x, pixel.y)).getBlue()) {
                            if(new Color(bufferMarcador.getRGB(pixel.x + index.x, pixel.y + index.y)).getBlue() != new Color(mascara.getRGB(pixel.x + index.x, pixel.y + index.y)).getBlue()){
                                minimo = Math.min(new Color(bufferMarcador.getRGB(pixel.x, pixel.y)).getBlue(),new Color (mascara.getRGB(index.x + pixel.x,index.y + pixel.y)).getBlue());
                                bufferMarcador.setRGB(pixel.x + index.x, pixel.y + index.y, new Color(minimo,minimo,minimo).getRGB());
                                fifo.add(new Point(pixel.x + index.x, pixel.y + index.y));
                            }

                        }
                    }
                }
            }
            fifo.remove(0);
        }
        return bufferMarcador;
    }
    
    public static BufferedImage reconstrucaoDualRapida(BufferedImage mascara, BufferedImage marcador, ArrayList<Point> elementoEstruturante) {
//        BufferedImage copia = new BufferedImage(mascara.getWidth(), mascara.getHeight(), 2);
//        BufferedImage buffer = new BufferedImage(mascara.getWidth(), mascara.getHeight(), 2);
        BufferedImage bufferMarcador = Morfologia.copyImage(marcador);
        int posX, posY, erosao, pixelMascara, maximo;
        Color c;
        ArrayList<Integer> vizinhos = new ArrayList<>();
        ArrayList<Point> fifo = new ArrayList<>();
        //sentido raster
        for (int i = 0; i < (int) bufferMarcador.getWidth(); i++) {
            for (int j = 0; j < (int) bufferMarcador.getHeight(); j++) {
                for (int k = 0; k < elementoEstruturante.size() / 2; k++) {
                    if (i + elementoEstruturante.get(k).x >= 0 && j + elementoEstruturante.get(k).y >= 0) {
                        if (i + elementoEstruturante.get(k).x < bufferMarcador.getWidth() && j + elementoEstruturante.get(k).y < bufferMarcador.getHeight()) {
                            posX = i + elementoEstruturante.get(k).x;
                            posY = j + elementoEstruturante.get(k).y;
                            c = new Color(bufferMarcador.getRGB(posX, posY));
                            vizinhos.add(c.getBlue());
                        }
                    }
                }
//                erosao = Collections.max(vizinhos);
                if(vizinhos.isEmpty()){
                    erosao = 0;
                }else{
                    erosao = Collections.min(vizinhos);
                }
                pixelMascara = new Color(bufferMarcador.getRGB(i, j)).getBlue();
                maximo = Math.max(pixelMascara, erosao);
                c = new Color(maximo, maximo, maximo);
                bufferMarcador.setRGB(i, j, c.getRGB());
                vizinhos.clear();
            }
        }
        //sentido antiraster
        for (int i = (int) bufferMarcador.getWidth() -1; i >= 0; i--) {
            for (int j = (int) bufferMarcador.getHeight() -1; j >= 0; j--) {
                for (int k = 1 + elementoEstruturante.size() / 2; k < elementoEstruturante.size(); k++) {
                    if (i + elementoEstruturante.get(k).x >= 0 && j + elementoEstruturante.get(k).y >= 0) {
                        if (i + elementoEstruturante.get(k).x < bufferMarcador.getWidth() && j + elementoEstruturante.get(k).y < bufferMarcador.getHeight()) {
                            posX = i + elementoEstruturante.get(k).x;
                            posY = j + elementoEstruturante.get(k).y;
                            c = new Color(bufferMarcador.getRGB(posX, posY));
                            vizinhos.add(c.getBlue());
                        }
                    }
                }
//                erosao = Collections.min(vizinhos);
                if(vizinhos.isEmpty()){
                    erosao = 0;
                }else{
                    erosao = Collections.min(vizinhos);
                }
                pixelMascara = new Color(bufferMarcador.getRGB(i, j)).getBlue();
                maximo = Math.max(pixelMascara, erosao);
                c = new Color(maximo, maximo, maximo);
                bufferMarcador.setRGB(i, j, c.getRGB());
                vizinhos.clear();

                for (int k = 1 + elementoEstruturante.size() / 2; k < elementoEstruturante.size(); k++) {
                    if (i + elementoEstruturante.get(k).x >= 0 && j + elementoEstruturante.get(k).y >= 0) {
                        if (i + elementoEstruturante.get(k).x < bufferMarcador.getWidth() && j + elementoEstruturante.get(k).y < bufferMarcador.getHeight()) {
                            posX = i + elementoEstruturante.get(k).x;
                            posY = j + elementoEstruturante.get(k).y;
                            c = new Color(bufferMarcador.getRGB(posX, posY));
                            if (c.getBlue() > new Color(bufferMarcador.getRGB(i, j)).getBlue()) {
                                if (c.getBlue() > new Color(mascara.getRGB(posX, posY)).getBlue()) {
                                    fifo.add(new Point(i, j));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        // propagação

        while (fifo.isEmpty() == false) {
            Point pixel = fifo.get(0);

            for (Point index : elementoEstruturante) {
                if (pixel.x + index.x >= 0 && pixel.y + index.y >= 0) {
                    if (pixel.x + index.x < bufferMarcador.getWidth() && pixel.y + index.y < bufferMarcador.getHeight()) {
                        if (new Color(bufferMarcador.getRGB(pixel.x + index.x, pixel.y + index.y)).getBlue() > new Color(bufferMarcador.getRGB(pixel.x, pixel.y)).getBlue()) {
                            if(new Color(bufferMarcador.getRGB(pixel.x + index.x, pixel.y + index.y)).getBlue() != new Color(mascara.getRGB(pixel.x + index.x, pixel.y + index.y)).getBlue()){
                                maximo = Math.max(new Color(bufferMarcador.getRGB(pixel.x, pixel.y)).getBlue(),new Color (mascara.getRGB(index.x + pixel.x,index.y + pixel.y)).getBlue());
                                bufferMarcador.setRGB(pixel.x + index.x, pixel.y + index.y, new Color(maximo,maximo,maximo).getRGB());
                                fifo.add(new Point(pixel.x + index.x, pixel.y + index.y));
                            }

                        }
                    }
                }
            }
            fifo.remove(0);
        }
        return bufferMarcador;
    }

}
