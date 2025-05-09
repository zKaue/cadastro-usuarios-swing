package gerenciarusuarios.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gerenciarusuarios.model.Usuario;
import gerenciarusuarios.repository.UsuarioRepository;
import gerenciarusuarios.table.model.UsuarioTableModel;
import gerenciarusuarios.util.Formatador;
import gerenciarusuarios.util.ImagemUtil;
import gerenciarusuarios.util.Placeholder;
import gerenciarusuarios.util.TabelaUtil;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class TelaListarUsuarios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tbUsuario;
	private JTextField txtBuscaInicialOuCpf;

	public TelaListarUsuarios(List<Usuario> usuarios) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 550);
		setLocationRelativeTo(null);
		setTitle("Listar Usuários");

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextField lblColocarFoco;
        lblColocarFoco = new JTextField();
        lblColocarFoco.setBounds(0, 0, 1, 1);
        getContentPane().add(lblColocarFoco);
        lblColocarFoco.setColumns(10);
        lblColocarFoco.requestFocusInWindow();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 40, 514, 460);
		contentPane.add(scrollPane);

		// Cria a estrutura do model da tabela
		UsuarioTableModel usuarioTableModel = new UsuarioTableModel();
		usuarioTableModel.setUsuarios(usuarios);

		// Atribui a estrutura da tabela ao JTable
		tbUsuario = new JTable(usuarioTableModel);
		scrollPane.setViewportView(tbUsuario);
		tbUsuario.setFocusable(false);

		tbUsuario.setShowGrid(true);
		tbUsuario.setIntercellSpacing(new java.awt.Dimension(1, 1)); //MARGEM ENTRA COLUNAS
		tbUsuario.setGridColor(new Color(100, 100, 100));
		
		TabelaUtil.formatarTabelaUsuario(tbUsuario);
		
		ImageIcon backIcon = ImagemUtil.carregarIconeRedimensionado("/gerenciarusuarios/images/backIcon.png", 25, 25);	
		JButton btnVoltar = new JButton(backIcon);
		btnVoltar.setToolTipText("Voltar para o menu");
	    btnVoltar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		TelaMenu telaMenu = new TelaMenu();
	    		telaMenu.setVisible(true);
	    		dispose();
	    	}
	    });
	    btnVoltar.setBounds(10, 10, 89, 23);
	    contentPane.add(btnVoltar);
	    
	    txtBuscaInicialOuCpf = new JTextField();
	    txtBuscaInicialOuCpf.setColumns(10);
	    txtBuscaInicialOuCpf.setBounds(104, 10, 389, 23);
	    contentPane.add(txtBuscaInicialOuCpf);
	    Placeholder.setPlaceholder(txtBuscaInicialOuCpf, "Digite a inicial do nome ou o CPF para buscar");
	    
	    ImageIcon findIcon = ImagemUtil.carregarIconeRedimensionado("/gerenciarusuarios/images/findIcon.png", 23, 20);
	    JButton btnBuscarUsuario = new JButton(findIcon);
	    btnBuscarUsuario.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String coletar = txtBuscaInicialOuCpf.getText().trim();
	            
	            if (coletar.isEmpty() || coletar.equals("Digite a inicial do nome ou o CPF para buscar")) {
	                UsuarioRepository usuarioRepository = new UsuarioRepository();
	                List<Usuario> todosUsuarios = usuarioRepository.retornaTodos();
	                if (todosUsuarios == null || todosUsuarios.isEmpty()) {
	                    JOptionPane.showMessageDialog(null, "Nenhum usuário cadastrado.");
	                } else {
	                    UsuarioTableModel novoModel = new UsuarioTableModel();
	                    novoModel.setUsuarios(todosUsuarios);
	                    tbUsuario.setModel(novoModel);
	                    TabelaUtil.formatarTabelaUsuario(tbUsuario);
	                }
	                return;
	            }
	            
	            UsuarioRepository usuarioRepository = new UsuarioRepository();
	            List<Usuario> usuariosEncontrados = null;
	            
	            try {
	                Long.parseLong(coletar);
	                if (coletar.length() == 11) {
	                    Usuario usuarioEncontrado = usuarioRepository.retornaPeloCpf(coletar);
	                    if (usuarioEncontrado != null) {
	                        usuariosEncontrados = List.of(usuarioEncontrado);
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Nenhum usuário encontrado para o CPF: " + Formatador.formatarCpf(coletar));
	                        return;
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(null, "CPF deve ter 11 dígitos.", "Erro", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	            } catch (NumberFormatException ex) {
	                usuariosEncontrados = usuarioRepository.retornaPelaInicial(coletar);
	                if (usuariosEncontrados == null || usuariosEncontrados.isEmpty()) {
	                    JOptionPane.showMessageDialog(null, "Nenhum usuário encontrado para a inicial: " + coletar);
	                    return;
	                }
	            }
	            UsuarioTableModel novoModel = new UsuarioTableModel();
	            novoModel.setUsuarios(usuariosEncontrados);
	            tbUsuario.setModel(novoModel);
	            TabelaUtil.formatarTabelaUsuario(tbUsuario);
	        }
	    });

	    btnBuscarUsuario.setToolTipText("Buscar usuário pelo CPF ou Inicial do nome");
	    btnBuscarUsuario.setBounds(497, 10, 25, 23);
	    contentPane.add(btnBuscarUsuario);	
	}
}
