package gerenciarusuarios.util;

import javax.swing.*;
import java.awt.*;

public class ImagemUtil {

    public static ImageIcon carregarIconeRedimensionado(String caminho, int largura, int altura) {
    	
        java.net.URL url = ImagemUtil.class.getResource(caminho);
        if (url == null) {
            System.err.println("Imagem não encontrada: " + caminho);
            return null;
        }

        ImageIcon iconeOriginal = new ImageIcon(url);
        Image imagemRedimensionada = iconeOriginal.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        return new ImageIcon(imagemRedimensionada);
        
        /*
	    Carrega (ImageIcon)
	    Redimensiona (Image)
	    Usa (ImageIcon novo)
	    */
        
    }
}
