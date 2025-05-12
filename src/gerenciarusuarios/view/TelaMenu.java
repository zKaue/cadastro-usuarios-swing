package gerenciarusuarios.view;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import gerenciarusuarios.model.Usuario;
import gerenciarusuarios.repository.UsuarioRepository;
import gerenciarusuarios.util.ImagemUtil;

public class TelaMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public TelaMenu() {
	    setTitle("Menu Principal");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 550, 550);
	    setLocationRelativeTo(null);

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

	    JLabel lblTitulo = new JLabel("Gerenciador de Usuários");
	    lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
	    lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
	    lblTitulo.setBounds(47, 71, 450, 40);
	    contentPane.add(lblTitulo);

	    JPanel painelBotoes = new JPanel();
	    painelBotoes.setBounds(116, 139, 300, 250);
	    painelBotoes.setLayout(new GridLayout(3, 1, 10, 10));
	    
	    JButton btnCadastrarUsuario = new JButton("Cadastrar Usuário");
	    btnCadastrarUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
	    btnCadastrarUsuario.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            TelaCadastro telaCadastro = new TelaCadastro();
	            telaCadastro.setVisible(true);
	            dispose();
	        }
	    });
	    painelBotoes.add(btnCadastrarUsuario);

	    JButton btnEditarOuRemoverUsuario = new JButton("Editar ou Remover Usuário");
	    btnEditarOuRemoverUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
	    btnEditarOuRemoverUsuario.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            UsuarioRepository usuarioRepository = new UsuarioRepository();
	            List<Usuario> usuarios = usuarioRepository.retornaTodos();
	            if (usuarios.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Nenhum usuário cadastrado na base de dados!");
	            } else {
	                TelaEditarOuRemoverUsuario telaEditarOuRemoverUsuario = new TelaEditarOuRemoverUsuario(usuarios);
	                telaEditarOuRemoverUsuario.setVisible(true);
	                dispose();
	            }
	        }
	    });
	    painelBotoes.add(btnEditarOuRemoverUsuario);

	    JButton btnConsultarTodos = new JButton("Consultar Usuários");
	    btnConsultarTodos.setFont(new Font("Arial", Font.PLAIN, 18));
	    btnConsultarTodos.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            UsuarioRepository usuarioRepository = new UsuarioRepository();
	            List<Usuario> usuarios = usuarioRepository.retornaTodos();
	            if (usuarios.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Nenhum usuário cadastrado na base de dados!");
	            } else {
	                TelaListarUsuarios telaListarUsuarios = new TelaListarUsuarios(usuarios);
	                telaListarUsuarios.setVisible(true);
	                dispose();
	            }
	        }
	    });
	    painelBotoes.add(btnConsultarTodos);

	    contentPane.add(painelBotoes);

	    ImageIcon exitIcon = ImagemUtil.carregarIconeRedimensionado("/gerenciarusuarios/images/exitIcon.png", 22, 18);
	    JButton btnSair = new JButton(exitIcon);
	    btnSair.setToolTipText("Sair do programa");
	    btnSair.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            dispose();
	        }
	    });
	    btnSair.setBounds(10, 10, 89, 23);
	    contentPane.add(btnSair);
	}
}
