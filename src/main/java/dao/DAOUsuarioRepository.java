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

	public ModelLogin gravarUsuario(ModelLogin objeto) throws Exception {

		if (objeto.isNovo()) {//Grava um novo

			String sql = "INSERT INTO model_login(login, senha, nome, email) VALUES (?, ?, ?, ?);";
			PreparedStatement insertUsuario = connection.prepareStatement(sql);

			insertUsuario.setString(1, objeto.getLogin());
			insertUsuario.setString(2, objeto.getSenha());
			insertUsuario.setString(3, objeto.getNome());
			insertUsuario.setString(4, objeto.getEmail());
			insertUsuario.execute();// Executa a instrução SQL
			connection.commit();
		}else {
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=? WHERE id = "+objeto.getId()+";";
			
			PreparedStatement updateUsuario = connection.prepareStatement(sql);
			
			updateUsuario.setString(1, objeto.getLogin());
			updateUsuario.setString(2, objeto.getSenha());
			updateUsuario.setString(3, objeto.getNome());
			updateUsuario.setString(4, objeto.getEmail());
			
			updateUsuario.executeUpdate();
			
			connection.commit();
		}
		return this.consultarUsuario(objeto.getLogin());
	}

	public ModelLogin consultarUsuario(String login) throws Exception {

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
		
		String sql = "DELETE FROM public.model_login WHERE id = ?;";
		PreparedStatement deletar = connection.prepareStatement(sql);
		deletar.setLong(1, Long.parseLong(idUser));
		deletar.executeUpdate(); //Executar nossa query
		
		connection.commit();
	}

	public List<ModelLogin> buscarUsuarioAjax(String nome) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login WHERE upper(nome) LIKE upper(?)";
		PreparedStatement busca = connection.prepareStatement(sql);
		busca.setString(1, "%" +nome+ "%");
		
		//Armazena minha busca nesse resultado
		ResultSet resultado = busca.executeQuery();
		
		while (resultado.next()) {//Percorre minhas linhas enquanto tem resultado detro delas
			
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			
			retorno.add(modelLogin);
			
		}
		return retorno;
	}

}