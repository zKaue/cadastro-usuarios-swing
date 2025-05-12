package gerenciarusuarios.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gerenciarusuarios.model.Usuario;
import gerenciarusuarios.services.UsuarioService;
import gerenciarusuarios.util.Formatador;
import gerenciarusuarios.util.ImagemUtil;
import gerenciarusuarios.util.Placeholder;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;

public class TelaEditarUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtIdUsuario;
	private JTextField txtNomeUsuario;
	private JTextField txtCpf;
	private JTextField txtIdade;

	public TelaEditarUsuario(Usuario usuario) {
		setTitle("Editar Usuário");
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
	    
	    txtIdUsuario = new JTextField();
	    txtIdUsuario.setEnabled(false);
	    txtIdUsuario.setBounds(129, 97, 269, 38);
	    contentPane.add(txtIdUsuario);
	    txtIdUsuario.setColumns(10);
	    txtIdUsuario.setText(String.valueOf(usuario.getId()));
	    
	    txtNomeUsuario = new JTextField();
	    txtNomeUsuario.setColumns(10);
	    txtNomeUsuario.setBounds(129, 153, 269, 38);
	    contentPane.add(txtNomeUsuario);
	    txtNomeUsuario.setText(usuario.getNome());
	    
	    txtNomeUsuario.addFocusListener(new FocusAdapter() {
	        @Override
	        public void focusLost(FocusEvent e) {
	            if (txtNomeUsuario.getText().trim().isEmpty()) {
	                Placeholder.setPlaceholder(txtNomeUsuario, "Informe o seu Nome");
	            }
	        }
	    });

	    txtCpf = new JTextField();
	    txtCpf.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
            	
            	String cpfNumerico = txtCpf.getText().replaceAll("[^0-9]", "");

            	if (cpfNumerico.length() > 11) {
            	    cpfNumerico = cpfNumerico.substring(0, 11);
            	}

            	StringBuilder sb = new StringBuilder();
            	for (int i = 0; i < cpfNumerico.length(); i++) {
            	    if (i == 3 || i == 6) {
            	        sb.append(".");
            	    } else if (i == 9) {
            	        sb.append("-");
            	    }
            	    sb.append(cpfNumerico.charAt(i));
            	}

            	txtCpf.setText(sb.toString());
            	txtCpf.setCaretPosition(sb.length());
            }
        });
	    
	    txtCpf.setColumns(10);
	    txtCpf.setBounds(129, 211, 269, 38);
	    contentPane.add(txtCpf);
	    txtCpf.setText(Formatador.formatarCpf(usuario.getCpf()));
	    
	    txtCpf.addFocusListener(new FocusAdapter() {
	        @Override
	        public void focusLost(FocusEvent e) {
	            if (txtCpf.getText().trim().isEmpty() || txtCpf.getText().equals("000.000.000-00")) {
	                Placeholder.setPlaceholder(txtCpf, "000.000.000-00");
	            }
	        }
	    });
	    
	    txtIdade = new JTextField();
	    txtIdade.setColumns(10);
	    txtIdade.setBounds(129, 270, 269, 38);
	    contentPane.add(txtIdade);
	    txtIdade.setText(String.valueOf(usuario.getIdade()));
	    
	    txtIdade.addFocusListener(new FocusAdapter() {
	        @Override
	        public void focusLost(FocusEvent e) {
	            if (txtIdade.getText().trim().isEmpty()) {
	                Placeholder.setPlaceholder(txtIdade, "Informe a sua Idade");
	            }
	        }
	    });
	    
	    JButton btnEditarUsuario = new JButton("Editar Usuário");
	    btnEditarUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
	    btnEditarUsuario.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            if (txtNomeUsuario.getText().equals("Informe o seu Nome")) {
	                JOptionPane.showMessageDialog(null, "O campo Nome não pode estar vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
	            } else if (txtCpf.getText().equals("000.000.000-00")) {
	                JOptionPane.showMessageDialog(null, "O campo CPF não pode estar vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
	            } else if (txtIdade.getText().equals("Informe a sua Idade")) {
	                JOptionPane.showMessageDialog(null, "O campo Idade não pode estar vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
	            } else {
	            	
	                int id, idade;

	                try {
	                    id = Integer.parseInt(txtIdUsuario.getText().trim());
	                    idade = Integer.parseInt(txtIdade.getText().trim());
	                } catch (NumberFormatException ex) {
	                    JOptionPane.showMessageDialog(null, "A Idade devem conter apenas números!", "Erro", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }

	                String nome = txtNomeUsuario.getText().trim();
	                String cpf = Formatador.limparCpf(txtCpf.getText());
	                
	                if (nome.equals(usuario.getNome()) && cpf.equals(usuario.getCpf()) && idade == usuario.getIdade()) {
	                        JOptionPane.showMessageDialog(null, "Nenhuma informação foi alterada.");
	                        return;
	                }
	                
	                Usuario usuarioAtualizado = new Usuario(id, nome, cpf, idade);

	                try {
	                    UsuarioService usuarioService = new UsuarioService();
	                    usuarioService.atualizar(usuarioAtualizado);
	                    
	                    // Sincroniza o objeto passado por parâmetro com os dados atualizados
	                    usuario.setNome(nome);
	                    usuario.setCpf(cpf);
	                    usuario.setIdade(idade);
	                    
	                    JOptionPane.showMessageDialog(null, "Usuário editado com sucesso!");
	                } catch (Exception e2) {
	                    JOptionPane.showMessageDialog(null, e2.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        }
	    });
	    btnEditarUsuario.setBounds(129, 319, 269, 38);
	    contentPane.add(btnEditarUsuario);
	    
	    ImageIcon backIcon = ImagemUtil.carregarIconeRedimensionado("/gerenciarusuarios/images/backIcon.png", 25, 25);
		JButton btnVoltar = new JButton(backIcon);
		btnVoltar.setToolTipText("Voltar para a tela anterior");
		btnVoltar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        TelaEditarOuRemoverUsuario tela = new TelaEditarOuRemoverUsuario(usuario.getId());
		        tela.setVisible(true);
		        dispose();
		    }
		});
	    btnVoltar.setBounds(10, 10, 89, 23);
	    contentPane.add(btnVoltar);
	    
	    JLabel lblIdUsuario = new JLabel("ID do Usuário:");
	    lblIdUsuario.setBounds(129, 82, 120, 14);
	    contentPane.add(lblIdUsuario);

	    JLabel lblNomeUsuario = new JLabel("Nome:");
	    lblNomeUsuario.setBounds(129, 138, 120, 14);
	    contentPane.add(lblNomeUsuario);

	    JLabel lblCpf = new JLabel("CPF:");
	    lblCpf.setBounds(129, 196, 120, 14);
	    contentPane.add(lblCpf);

	    JLabel lblIdade = new JLabel("Idade:");
	    lblIdade.setBounds(129, 257, 120, 14);
	    contentPane.add(lblIdade);
	}
}
