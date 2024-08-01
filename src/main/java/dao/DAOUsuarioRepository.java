package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception {

		if (objeto.isNovo()) {// Grava um novo

			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement insertUsuario = connection.prepareStatement(sql);

			insertUsuario.setString(1, objeto.getLogin());
			insertUsuario.setString(2, objeto.getSenha());
			insertUsuario.setString(3, objeto.getNome());
			insertUsuario.setString(4, objeto.getEmail());
			insertUsuario.setLong(5, userLogado);
			insertUsuario.setString(6, objeto.getPerfil());
			insertUsuario.setString(7, objeto.getSexo());
			insertUsuario.setString(8, objeto.getCep());
			insertUsuario.setString(9, objeto.getLogradouro());
			insertUsuario.setString(10, objeto.getBairro());
			insertUsuario.setString(11, objeto.getLocalidade());
			insertUsuario.setString(12, objeto.getUf());
			insertUsuario.setString(13, objeto.getNumero());
			insertUsuario.execute();// Executa a instrução SQL
			connection.commit();

			// Caso venha uma foto insira ela
			if (objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				sql = "UPDATE model_login SET fotouser = ?, extensaofotouser = ? WHERE login = ?";
				insertUsuario = connection.prepareStatement(sql);
				insertUsuario.setString(1, objeto.getFotouser());
				insertUsuario.setString(2, objeto.getExtensaofotouser());
				insertUsuario.setString(3, objeto.getLogin());
				insertUsuario.execute();// Executa a instrução SQL
				connection.commit();
			}
		} else {
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=? WHERE id = "
					+ objeto.getId() + ";";

			PreparedStatement updateUsuario = connection.prepareStatement(sql);

			updateUsuario.setString(1, objeto.getLogin());
			updateUsuario.setString(2, objeto.getSenha());
			updateUsuario.setString(3, objeto.getNome());
			updateUsuario.setString(4, objeto.getEmail());
			updateUsuario.setString(5, objeto.getPerfil());
			updateUsuario.setString(6, objeto.getSexo());
			updateUsuario.setString(7, objeto.getCep());
			updateUsuario.setString(8, objeto.getLogradouro());
			updateUsuario.setString(9, objeto.getBairro());
			updateUsuario.setString(10, objeto.getLocalidade());
			updateUsuario.setString(11, objeto.getUf());
			updateUsuario.setString(12, objeto.getNumero());

			updateUsuario.executeUpdate();

			connection.commit();

			// Caso venha uma foto no meu update insira ela
			if (objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				sql = "UPDATE model_login SET fotouser = ?, extensaofotouser = ? WHERE id = ?";
				updateUsuario = connection.prepareStatement(sql);
				updateUsuario.setString(1, objeto.getFotouser());
				updateUsuario.setString(2, objeto.getExtensaofotouser());
				updateUsuario.setLong(3, objeto.getId());
				updateUsuario.execute();// Executa a instrução SQL
				connection.commit();
			}
		}
		return this.consultarUsuario(objeto.getLogin(), userLogado);
	}

	public ModelLogin consultarUsuario(String login, Long userLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE upper(login) = upper(?) AND useradmin is false AND usuario_id = "
				+ userLogado;
		PreparedStatement buscarUsuario = connection.prepareStatement(sql);
		buscarUsuario.setString(1, login);

		ResultSet resultado = buscarUsuario.executeQuery();

		while (resultado.next()) {// Enquanto tiver resultado
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
		}

		return modelLogin;
	}

	public ModelLogin consultarUsuario(String login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE upper(login) = upper(?) AND useradmin is false";
		PreparedStatement buscarUsuario = connection.prepareStatement(sql);
		buscarUsuario.setString(1, login);

		ResultSet resultado = buscarUsuario.executeQuery();

		while (resultado.next()) {// Enquanto tiver resultado
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
		}

		return modelLogin;
	}

	public ModelLogin consultarUsuarioLogado(String login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE upper(login) = upper(?)";
		PreparedStatement buscarUsuario = connection.prepareStatement(sql);
		buscarUsuario.setString(1, login);

		ResultSet resultado = buscarUsuario.executeQuery();

		while (resultado.next()) {// Enquanto tiver resultado
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));

		}

		return modelLogin;
	}

	public ModelLogin buscarUsuarioPorId(String id, Long userLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE id = ? AND useradmin is false AND usuario_id = ?";
		PreparedStatement buscarUsuarioPorId = connection.prepareStatement(sql);
		buscarUsuarioPorId.setLong(1, Long.parseLong(id));
		buscarUsuarioPorId.setLong(2, userLogado);

		ResultSet resultado = buscarUsuarioPorId.executeQuery();

		while (resultado.next()) {// Enquanto tiver resultado
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
		}

		return modelLogin;
	}

	public boolean validarLogin(String login) throws Exception {

		String sql = "SELECT COUNT(1) > 0 AS existe FROM model_login WHERE upper(login) = upper(?)";
		PreparedStatement loginExistente = connection.prepareStatement(sql);
		loginExistente.setString(1, login);

		ResultSet resultado = loginExistente.executeQuery();

		resultado.next();// Pra ele entrar nos resultados
		return resultado.getBoolean("existe");
	}

	public void deletarUser(String idUser) throws Exception {

		String sql = "DELETE FROM public.model_login WHERE id = ? AND useradmin is false;";
		PreparedStatement deletar = connection.prepareStatement(sql);
		deletar.setLong(1, Long.parseLong(idUser));
		deletar.executeUpdate(); // Executar nossa query

		connection.commit();
	}

	public List<ModelLogin> buscarUsuarioAjax(Long userLogado) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "SELECT * FROM model_login WHERE useradmin is false AND usuario_id = " + userLogado + " limit 5";
		PreparedStatement busca = connection.prepareStatement(sql);

		// Armazena minha busca nesse resultado
		ResultSet resultado = busca.executeQuery();

		while (resultado.next()) {// Percorre minhas linhas enquanto tem resultado detro delas

			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);

		}
		return retorno;
	}

	public List<ModelLogin> buscarUsuarioAjaxPaginada(Long userLogado, Integer offset) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "SELECT * FROM model_login WHERE usuario_id = ? ORDER BY nome OFFSET " + offset + " limit 5;";
		PreparedStatement busca = connection.prepareStatement(sql);
		busca.setLong(1, userLogado);

		// Armazena minha busca nesse resultado
		ResultSet resultado = busca.executeQuery();

		while (resultado.next()) {// Percorre minhas linhas enquanto tem resultado detro delas

			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);

		}
		return retorno;
	}
	
	public int totalPagina(Long userLogado) throws Exception{
		
		String sql = "SELECT count(1) as total FROM model_login WHERE usuario_id = ?";
		PreparedStatement total = connection.prepareStatement(sql);
		total.setLong(1, userLogado);

		// Armazena minha busca nesse resultado
		ResultSet resultado = total.executeQuery();
		
		resultado.next();
		
		Double cadastros = resultado.getDouble("total");
		
		Double porpagina = 5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2;
		
		if (resto > 0) {
			pagina ++;
		}
		
		return pagina.intValue();
	}

	public List<ModelLogin> buscarUsuarioAjax(String nome, Long userLogado) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "SELECT * FROM model_login WHERE upper(nome) LIKE upper(?) AND useradmin is false AND usuario_id = ? limit 5";
		PreparedStatement busca = connection.prepareStatement(sql);
		busca.setString(1, "%" + nome + "%");
		busca.setLong(2, userLogado);

		// Armazena minha busca nesse resultado
		ResultSet resultado = busca.executeQuery();

		while (resultado.next()) {// Percorre minhas linhas enquanto tem resultado detro delas

			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);

		}
		return retorno;
	}

}