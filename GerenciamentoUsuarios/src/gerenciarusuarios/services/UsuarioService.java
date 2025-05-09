package gerenciarusuarios.services;

import gerenciarusuarios.model.Usuario;
import gerenciarusuarios.repository.UsuarioRepository;

public class UsuarioService {

    public void cadastrar(Usuario usuario) throws Exception {
    	
        validarId(usuario.getId());
        validarNome(usuario.getNome());
        validarCpf(usuario.getCpf());
        validarIdade(usuario.getIdade());
        
        UsuarioRepository usuarioRepository = new UsuarioRepository();

        if (usuarioRepository.consultarId(usuario.getId())) {
            throw new Exception("O ID informado já existe no sistema.");
        } else if (usuarioRepository.consultarCpf(usuario.getCpf())) {
            throw new Exception("O CPF informado já está cadastrado.");
        }
        
        usuarioRepository.inserir(usuario);
    }
    
    public void atualizar(Usuario usuario) throws Exception {
    	
        validarNome(usuario.getNome());
        validarCpf(usuario.getCpf());
        validarIdade(usuario.getIdade());

        UsuarioRepository usuarioRepository = new UsuarioRepository();
        Usuario usuarioExistente = usuarioRepository.retornaPeloCpf(usuario.getCpf());

	     if (usuarioExistente != null && usuarioExistente.getId() != usuario.getId()) {
	         throw new Exception("O CPF informado já está cadastrado.");
	     }
        
        usuarioRepository.atualizar(usuario);
    	
    }

    private void validarId(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("Id inválido.");
        }
    }

    private void validarNome(String nome) throws Exception {
        if (nome == null) {
            throw new Exception("Nome não pode ser nulo.");
        } else if (nome.trim().isBlank()) {
            throw new Exception("Nome não pode estar em branco.");
        } else if (nome.length() > 100) {
            throw new Exception("Nome não pode estar em branco (máx. 100 caracteres).");
        } else if (!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s'-]+$")) {
            throw new Exception("Nome inválido! Não use números ou símbolos especiais.");
        }
    }

    private void validarCpf(String cpf) throws Exception {
        if (cpf.trim().isBlank()) {
            throw new Exception("CPF não pode estar em branco.");
        } else if (!cpf.matches("^[0-9]{11}$")) {
            throw new Exception("O CPF deve conter exatamente 11 dígitos numéricos.");
        }
    }

    private void validarIdade(int idade) throws Exception {
    	if (idade <= 0 || idade > 120) {
            throw new Exception("Idade inválida. Informe um valor entre 1 e 120 anos.");
        }
    }
}

