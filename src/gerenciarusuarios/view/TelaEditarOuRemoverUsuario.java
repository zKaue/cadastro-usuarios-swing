package gerenciarusuarios.view;

import java.awt.Color;
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
import gerenciarusuarios.util.ImagemUtil;
import gerenciarusuarios.util.Placeholder;
import gerenciarusuarios.util.TabelaUtil;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class TelaEditarOuRemoverUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tbUsuario;
	private JTextField txtColetarId;
	private UsuarioTableModel usuarioTableModel;
	
	@SuppressWarnings("unused")
	public TelaEditarOuRemoverUsuario(List<Usuario> usuarios) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 550);
		setLocationRelativeTo(null);
		setTitle("Editar ou Remover Usuário");

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextField lblColocarFoco = new JTextField();
		lblColocarFoco.setBounds(0, 0, 1, 1);
		getContentPane().add(lblColocarFoco);
		lblColocarFoco.setColumns(10);
		lblColocarFoco.requestFocusInWindow();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 40, 514, 460);
		contentPane.add(scrollPane);

		usuarioTableModel = new UsuarioTableModel();
		usuarioTableModel.setUsuarios(usuarios);

		tbUsuario = new JTable(usuarioTableModel);
		scrollPane.setViewportView(tbUsuario);
		tbUsuario.setFocusable(false);

		tbUsuario.setShowGrid(true);
		tbUsuario.setIntercellSpacing(new java.awt.Dimension(1, 1));
		tbUsuario.setGridColor(new Color(100, 100, 100));
		TabelaUtil.formatarTabelaUsuario(tbUsuario);

		ImageIcon backIcon = ImagemUtil.carregarIconeRedimensionado("/gerenciarusuarios/images/backIcon.png", 25, 25);
		JButton btnVoltar = new JButton(backIcon);
		btnVoltar.setToolTipText("Voltar para o menu");
		btnVoltar.addActionListener(e -> {
			TelaMenu telaMenu = new TelaMenu();
			telaMenu.setVisible(true);
			dispose();
		});
		btnVoltar.setBounds(10, 10, 89, 23);
		contentPane.add(btnVoltar);

		txtColetarId = new JTextField();
		txtColetarId.setBounds(104, 10, 302, 23);
		contentPane.add(txtColetarId);
		txtColetarId.setColumns(10);
		Placeholder.setPlaceholder(txtColetarId, "Digite o ID do Usuário que Deseja Editar ou Remover");

		ImageIcon findIcon = ImagemUtil.carregarIconeRedimensionado("/gerenciarusuarios/images/findIcon.png", 23, 20);
		JButton btnPesquisarId = new JButton(findIcon);
		btnPesquisarId.setToolTipText("Buscar usuário pelo ID");
		btnPesquisarId.addActionListener(e -> {
			try {
				String textoId = txtColetarId.getText().trim();
				if (textoId.isEmpty() || textoId.equals("Digite o ID do Usuário que Deseja Editar ou Remover")) {
					JOptionPane.showMessageDialog(null, "Digite um ID para buscar", "Aviso",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				int id = Integer.parseInt(textoId);
				UsuarioRepository usuarioRepository = new UsuarioRepository();
				Usuario usuario = usuarioRepository.retornaPeloId(id);

				if (usuario != null) {
					usuarioTableModel = new UsuarioTableModel();
					usuarioTableModel.adicionar(usuario);
					tbUsuario.setModel(usuarioTableModel);
					TabelaUtil.formatarTabelaUsuario(tbUsuario);
				} else {
					JOptionPane.showMessageDialog(null, "Usuário não encontrado para o ID: " + id);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "O campo ID deve conter apenas números.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		btnPesquisarId.setBounds(410, 10, 25, 23);
		contentPane.add(btnPesquisarId);

		ImageIcon refreshIcon = ImagemUtil.carregarIconeRedimensionado("/gerenciarusuarios/images/refreshIcon.png", 23,
				21);
		JButton btnMostrarTodos = new JButton(refreshIcon);
		btnMostrarTodos.setToolTipText("Mostrar todos os usuários");
		btnMostrarTodos.addActionListener(e -> {
			UsuarioRepository usuarioRepository = new UsuarioRepository();
			List<Usuario> usuariosAtualizados = usuarioRepository.retornaTodos();

			usuarioTableModel = new UsuarioTableModel();
			usuarioTableModel.setUsuarios(usuariosAtualizados);
			tbUsuario.setModel(usuarioTableModel);
			TabelaUtil.formatarTabelaUsuario(tbUsuario);

			if (usuariosAtualizados.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Não foi encontrado nenhum usuário na base de dados!");
				return;
			}
			Placeholder.setPlaceholder(txtColetarId, "Digite o ID do Usuário que Deseja Editar ou Remover");
			JOptionPane.showMessageDialog(null, "Todos os usuários foram carregados.");
		});
		btnMostrarTodos.setBounds(439, 10, 25, 23);
		contentPane.add(btnMostrarTodos);

		ImageIcon editIcon = ImagemUtil.carregarIconeRedimensionado("/gerenciarusuarios/images/editIcon.png", 19, 19);
		JButton btnEditarUsuario = new JButton(editIcon);
		btnEditarUsuario.setEnabled(false);
		btnEditarUsuario.addActionListener(_ -> {
			int linhaSelecionada = tbUsuario.getSelectedRow();

			if (linhaSelecionada != -1) {
				Usuario usuarioSelecionado = usuarioTableModel.getUsuarios().get(linhaSelecionada);
				TelaEditarUsuario telaEditarUsuario = new TelaEditarUsuario(usuarioSelecionado);
				telaEditarUsuario.setVisible(true);
				dispose();
			}
		});
		btnEditarUsuario.setToolTipText("Editar usuário selecionado");
		btnEditarUsuario.setBounds(468, 10, 25, 23);
		contentPane.add(btnEditarUsuario);

		ImageIcon removeIcon = ImagemUtil.carregarIconeRedimensionado("/gerenciarusuarios/images/removeIcon.png", 19,
				19);
		JButton btnRemoverUsuario = new JButton(removeIcon);
		btnRemoverUsuario.addActionListener(e -> {
			int linhaSelecionada = tbUsuario.getSelectedRow();

			if (linhaSelecionada != -1) {
				Usuario usuarioSelecionado = usuarioTableModel.getUsuarios().get(linhaSelecionada);

				int resposta = JOptionPane.showConfirmDialog(null,
						"Tem certeza que deseja remover o usuário \"" + usuarioSelecionado.getNome() + "\"?", "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
				//Usar \" faz com que a aspa apareça no texto, e não feche a string

				if (resposta == JOptionPane.YES_OPTION) {
					UsuarioRepository usuarioRepository = new UsuarioRepository();
					usuarioRepository.excluir(usuarioSelecionado);

					List<Usuario> usuariosAtualizados = usuarioRepository.retornaTodos();
					usuarioTableModel = new UsuarioTableModel();
					usuarioTableModel.setUsuarios(usuariosAtualizados);
					tbUsuario.setModel(usuarioTableModel);
					TabelaUtil.formatarTabelaUsuario(tbUsuario);

					JOptionPane.showMessageDialog(null, "Usuário removido com sucesso.");
				}
			}
		});
		btnRemoverUsuario.setToolTipText("Remover usuário selecionado");
		btnRemoverUsuario.setEnabled(false);
		btnRemoverUsuario.setBounds(497, 10, 25, 23);
		contentPane.add(btnRemoverUsuario);

		// Ativar os botões somente quando um usuário for selecionado
		tbUsuario.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int linhaSelecionada = tbUsuario.getSelectedRow();
				btnRemoverUsuario.setEnabled(linhaSelecionada != -1);
				btnEditarUsuario.setEnabled(linhaSelecionada != -1);
			}
		});
	}
	
	public TelaEditarOuRemoverUsuario(int idPesquisado) {
		
	    this(new UsuarioRepository().retornaTodos());
	    txtColetarId.setText(String.valueOf(idPesquisado));
	    
	    try {
	        int id = idPesquisado;
	        UsuarioRepository usuarioRepository = new UsuarioRepository();
	        Usuario usuario = usuarioRepository.retornaPeloId(id);
	        if (usuario != null) {
	            usuarioTableModel = new UsuarioTableModel();
	            usuarioTableModel.adicionar(usuario);
	            tbUsuario.setModel(usuarioTableModel);
	            TabelaUtil.formatarTabelaUsuario(tbUsuario);
	        } else {
	            JOptionPane.showMessageDialog(null, "Usuário não encontrado para o ID: " + id);
	        }
	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(null, "Erro ao buscar usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
}
