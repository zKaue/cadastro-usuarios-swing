package gerenciarusuarios.repository;

import gerenciarusuarios.model.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;


public class UsuarioRepository {
    private Connection conexao;
    private String CONNECTION_STRING = "jdbc:mysql://localhost:3306/Usuarios";
    private String USUARIO = "root";
    private String SENHA = "root";

    public UsuarioRepository() {
        try {
            this.conexao = DriverManager.getConnection(CONNECTION_STRING, USUARIO, SENHA);
            if (this.conexao.isClosed()) {
                System.out.println("Não foi possível estabelecer a conexão com o BD!");
            }
        } catch (SQLException e) {
            System.out.println("Não foi possível se conectar com o BD!");
            e.printStackTrace();
        }
    }

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO tb_usuarios (id, nome, cpf, idade) VALUES (?, ?, ?, ?);";
        try (PreparedStatement ps = this.conexao.prepareStatement(sql)) {
            ps.setInt(1, usuario.getId());
            ps.setString(2, usuario.getNome());
            ps.setString(3, usuario.getCpf());
            ps.setInt(4, usuario.getIdade());
            ps.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Usuário " + usuario.getNome()
                    + " não foi inserido no Banco de dados devido a duplicidade de dados!");
        } catch (SQLException e) {
            System.out.println("Usuário não foi inserido no banco de dados");
            e.printStackTrace();
        }
    }

    public void excluir(Usuario usuario) {
        String sql = "DELETE FROM tb_usuarios WHERE id = ?;";
        try (PreparedStatement ps = this.conexao.prepareStatement(sql)) {
            ps.setInt(1, usuario.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Usuario usuario) {
        String sql = "update tb_usuarios set nome=?, cpf = ? where id = ?";
        try {
            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setInt(3, usuario.getId());
            ps.execute();
        } catch (SQLException e) {
            System.out.println("NÃO FOI POSSIVEL ATUALIZAR O DADOS DO USUÁRIO!");
            e.printStackTrace();
        }
    }

    //Método que realiza uma nova conexão com o banco de dados para coletar os dados mais atualizados possível
    public List<Usuario> retornaTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, idade FROM tb_usuarios ORDER BY nome ASC";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USUARIO, SENHA);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setNome(rs.getString("nome"));
                usuario.setIdade(rs.getInt("idade"));
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuários");
            e.printStackTrace();
        }
        return usuarios;
    }


    public Usuario retornaPeloCpf(String cpf) {
        Usuario usuario = null;
        String sql = "SELECT id, nome, cpf, idade FROM tb_usuarios WHERE cpf = ?";

        try {
            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setNome(rs.getString("nome"));
                usuario.setIdade(rs.getInt("idade"));
            }
        } catch (SQLException e) {
            System.out.println("NÃO FOI POSSIVEL REALIZAR A PESQUISA!");
            e.printStackTrace();
        }
        return usuario;
    }
    
    public Usuario retornaPeloId(int id) {
        Usuario usuario = null;
        String sql = "SELECT id, nome, cpf, idade FROM tb_usuarios WHERE id = ?";

        try {
            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setNome(rs.getString("nome"));
                usuario.setIdade(rs.getInt("idade"));
            }
        } catch (SQLException e) {
            System.out.println("NÃO FOI POSSIVEL REALIZAR A PESQUISA!");
            e.printStackTrace();
        }
        return usuario;
    }

    public List<Usuario> retornaPelaInicial(String inicial) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, idade FROM tb_usuarios WHERE nome LIKE ? ORDER BY nome ASC";

        try {
            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ps.setString(1, inicial + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setNome(rs.getString("nome"));
                usuario.setIdade(rs.getInt("idade"));
                usuarios.add(usuario);
            }
            if(usuarios.isEmpty()) {
                System.out.println("NENHUM USUÁRIO COM A INICIAL: "+inicial + " ENCONTRADO!");
            }
        } catch (SQLException e) {
            System.out.println("NÃO FOI POSSIVEL REALIZAR A PESQUISA!");
            e.printStackTrace();
        }
        return usuarios;
    }

    public boolean consultarId(int id) {
        String sql = "SELECT COUNT(*) FROM tb_usuarios WHERE id = ?";
        try {
            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO VERIFICAR ID NO BANCO DE DADOS!");
            e.printStackTrace();
        }
        return false;
    }

    public boolean consultarCpf(String cpf) {
        String sql = "SELECT COUNT(*) FROM tb_usuarios WHERE cpf = ?";
        try {
            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("ERRO AO VERIFICAR CPF NO BANCO DE DADOS!");
            e.printStackTrace();
        }
        return false;
    }

}