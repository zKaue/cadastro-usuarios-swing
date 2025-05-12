package gerenciarusuarios.main;

import javax.swing.UIManager;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;

import gerenciarusuarios.view.TelaMenu;

public class Main {
    public static void main(String[] args) {
    	
        try {
        	UIManager.setLookAndFeel(new FlatArcDarkIJTheme());
        } catch (Exception e) {
            System.err.println("Erro ao aplicar o tema: " + e.getMessage());
        }

        TelaMenu telaMenu = new TelaMenu();
        telaMenu.setVisible(true);
    }
}
