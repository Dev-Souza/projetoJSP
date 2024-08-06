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

		if (objeto.isNovo()) {/* Grava um novo */

			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero, datanascimento)  VALUES (?, ?, ?, ?, ?, ?,?, ?, ?, ?,?,?,?,?);";
			PreparedStatement preparedSql = connection.prepareStatement(sql);

			preparedSql.setString(1, objeto.getLogin());
			preparedSql.setString(2, objeto.getSenha());
			preparedSql.setString(3, objeto.getNome());
			preparedSql.setString(4, objeto.getEmail());
			preparedSql.setLong(5, userLogado);
			preparedSql.setString(6, objeto.getPerfil());
			preparedSql.setString(7, objeto.getSexo());

			preparedSql.setString(8, objeto.getCep());
			preparedSql.setString(9, objeto.getLogradouro());
			preparedSql.setString(10, objeto.getBairro());
			preparedSql.setString(11, objeto.getLocalidade());
			preparedSql.setString(12, objeto.getUf());
			preparedSql.setString(13, objeto.getNumero());
			preparedSql.setDate(14, objeto.getDatanascimento());

			preparedSql.execute();
			connection.commit();

			if (objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				sql = "update model_login set fotouser =?, extensaofotouser=? where login =?";

				preparedSql = connection.prepareStatement(sql);
				preparedSql.setString(1, objeto.getFotouser());
				preparedSql.setString(2, objeto.getExtensaofotouser());
				preparedSql.setString(3, objeto.getLogin());

				preparedSql.execute();
				connection.commit();
			}
		} else {
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro =?, localidade=?, uf=?, numero =?, datanascimento =? WHERE id =  "
					+ objeto.getId() + ";";

			PreparedStatement prepareSql = connection.prepareStatement(sql);

			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2, objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());
			prepareSql.setString(5, objeto.getPerfil());
			prepareSql.setString(6, objeto.getSexo());
			prepareSql.setString(7, objeto.getCep());
			prepareSql.setString(8, objeto.getLogradouro());
			prepareSql.setString(9, objeto.getBairro());
			prepareSql.setString(10, objeto.getLocalidade());
			prepareSql.setString(11, objeto.getUf());
			prepareSql.setString(12, objeto.getNumero());
			prepareSql.setDate(13, objeto.getDatanascimento());

			prepareSql.executeUpdate();
			connection.commit();

			if (objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				sql = "update model_login set fotouser =?, extensaofotouser=? where id =?";

				prepareSql = connection.prepareStatement(sql);

				prepareSql.setString(1, objeto.getFotouser());
				prepareSql.setString(2, objeto.getExtensaofotouser());
				prepareSql.setLong(3, objeto.getId());

				prepareSql.execute();

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

	public ModelLogin buscarUsuarioPorId(Long id) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE id = ? AND useradmin is false";
		PreparedStatement buscarUsuarioPorId = connection.prepareStatement(sql);
		buscarUsuarioPorId.setLong(1, id);

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
			modelLogin.setDatanascimento(resultado.getDate("datanascimento"));

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

	public int totalPagina(Long userLogado) throws Exception {

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
			pagina++;
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

	public List<ModelLogin> buscarUsuarioAjaxOffSet(String nome, Long userLogado, int offset) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "SELECT * FROM model_login WHERE upper(nome) LIKE upper(?) AND useradmin is false AND usuario_id = ? offset "
				+ offset + " limit 5";
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

	public int consultaUsuarioLisTotalPaginaPaginacao(String nome, Long userLogado) throws Exception {

		String sql = "SELECT COUNT(1) as total FROM model_login WHERE upper(nome) LIKE upper(?) AND useradmin is false AND usuario_id = ?";

		PreparedStatement busca = connection.prepareStatement(sql);
		busca.setString(1, "%" + nome + "%");
		busca.setLong(2, userLogado);

		// Armazena minha busca nesse resultado
		ResultSet resultado = busca.executeQuery();

		resultado.next();

		Double cadastros = resultado.getDouble("total");

		Double porpagina = 5.0;

		Double pagina = cadastros / porpagina;

		Double resto = pagina % 2;

		if (resto > 0) {
			pagina++;
		}

		return pagina.intValue();
	}

}