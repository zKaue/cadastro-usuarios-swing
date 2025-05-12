package gerenciarusuarios.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import gerenciarusuarios.model.Usuario;


public class UsuarioTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	// Nome das colunas
	private String[] colunas = {"ID","CPF", "Nome", "Idade"};
	
	// Conteúdo que será exibido na coluna
	private List<Usuario> usuarios;
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
	    this.usuarios.clear();
	    this.usuarios.addAll(usuarios);
	    fireTableDataChanged();
	}

	public UsuarioTableModel() {
		this.usuarios = new ArrayList<>();
	}
	
	// Método que retorna o número de linhas da tabela
	@Override
	public int getRowCount() {
		return usuarios.size();
	}

	// Método que retorna o número de colunas da tabela
	@Override
	public int getColumnCount() {
		return colunas.length;
	}
	
	// Método que retorna o nome da coluna de um determinado index
    @Override
    public String getColumnName(int columnIndex){
      return colunas[columnIndex];
    }

	// Método que permite ou não a edição de uma célula
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void limpar() {
        usuarios.clear();
        fireTableDataChanged();
    }
    
    public void adicionar(Usuario usuario) {
        usuarios.add(usuario);
        fireTableDataChanged();
    }

    public void remover(Usuario usuario) {
    	usuarios.remove(usuario);
    	fireTableDataChanged();
    }
    
    public void remover(int indice) {
    	usuarios.remove(indice);
    	fireTableDataChanged();
    }
    
	// Método que retorna o valor de uma célula da tabela
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
        Usuario usuario = usuarios.get(rowIndex);
        String conteudoCelula = null;
        switch(columnIndex){
            case 0:
            	conteudoCelula = String.valueOf(usuario.getId());
            	break;
            case 1:
            	conteudoCelula = gerenciarusuarios.util.Formatador.formatarCpf(usuario.getCpf());
            	break;
            case 2 :
            	conteudoCelula = usuario.getNome();
            	break;
            case 3 :
            	conteudoCelula = String.valueOf(usuario.getIdade());
            	break;
            default:
            	System.err.println("Índice inválido");
        }
        return conteudoCelula;
	}



}
