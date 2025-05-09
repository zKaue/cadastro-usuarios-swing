package gerenciarusuarios.util;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

public class TabelaUtil {

    public static void formatarTabelaUsuario(JTable tabela) {
        TableColumnModel coluna = tabela.getColumnModel();
        coluna.getColumn(0).setPreferredWidth(57);   // ID
        coluna.getColumn(1).setPreferredWidth(65);   // CPF
        coluna.getColumn(2).setPreferredWidth(250);  // Nome
        coluna.getColumn(3).setPreferredWidth(15);   // Idade
    }

}