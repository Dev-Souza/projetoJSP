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

		if (objeto.isNovo()) {//Grava um novo

			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo) VALUES (?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement insertUsuario = connection.prepareStatement(sql);

			insertUsuario.setString(1, objeto.getLogin());
			insertUsuario.setString(2, objeto.getSenha());
			insertUsuario.setString(3, objeto.getNome());
			insertUsuario.setString(4, objeto.getEmail());
			insertUsuario.setLong(5, userLogado);
			insertUsuario.setString(6, objeto.getPerfil());
			insertUsuario.setString(7, objeto.getSexo());
			insertUsuario.execute();// Executa a instrução SQL
			connection.commit();
			
			//Caso venha uma foto insira ela
			if(objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				sql = "UPDATE model_login SET fotouser = ?, extensaofotouser = ? WHERE login = ?";
				insertUsuario = connection.prepareStatement(sql);
				insertUsuario.setString(1, objeto.getFotouser());
				insertUsuario.setString(2, objeto.getExtensaofotouser());
				insertUsuario.setString(3, objeto.getLogin());
				insertUsuario.execute();// Executa a instrução SQL
				connection.commit();
			}
		}else {
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=? WHERE id = "+objeto.getId()+";";
			
			PreparedStatement updateUsuario = connection.prepareStatement(sql);
			
			updateUsuario.setString(1, objeto.getLogin());
			updateUsuario.setString(2, objeto.getSenha());
			updateUsuario.setString(3, objeto.getNome());
			updateUsuario.setString(4, objeto.getEmail());
			updateUsuario.setString(5, objeto.getPerfil());
			updateUsuario.setString(6, objeto.getSexo());
			
			updateUsuario.executeUpdate();
			
			connection.commit();
			
			//Caso venha uma foto no meu update insira ela
			if(objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
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

		String sql = "SELECT * FROM model_login WHERE upper(login) = upper(?) AND useradmin is false AND usuario_id = " + userLogado;
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
			modelLogin.setFotouser(resultado.getString("fotouser"));
		}

		return modelLogin;
	}
	
	public ModelLogin buscarUsuarioPorId(String id, Long userLogado) throws Exception{
		
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
	
	public void deletarUser(String idUser) throws Exception{
		
		String sql = "DELETE FROM public.model_login WHERE id = ? AND useradmin is false;";
		PreparedStatement deletar = connection.prepareStatement(sql);
		deletar.setLong(1, Long.parseLong(idUser));
		deletar.executeUpdate(); //Executar nossa query
		
		connection.commit();
	}
	
public List<ModelLogin> buscarUsuarioAjax(Long userLogado) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login WHERE useradmin is false AND usuario_id = " + userLogado;
		PreparedStatement busca = connection.prepareStatement(sql);
		
		//Armazena minha busca nesse resultado
		ResultSet resultado = busca.executeQuery();
		
		while (resultado.next()) {//Percorre minhas linhas enquanto tem resultado detro delas
			
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
			
		}
		return retorno;
	}

	public List<ModelLogin> buscarUsuarioAjax(String nome, Long userLogado) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login WHERE upper(nome) LIKE upper(?) AND useradmin is false AND usuario_id = ?";
		PreparedStatement busca = connection.prepareStatement(sql);
		busca.setString(1, "%" +nome+ "%");
		busca.setLong(2, userLogado);
		
		//Armazena minha busca nesse resultado
		ResultSet resultado = busca.executeQuery();
		
		while (resultado.next()) {//Percorre minhas linhas enquanto tem resultado detro delas
			
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
			
		}
		return retorno;
	}

}