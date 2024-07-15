package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOLoginRepository {

	private Connection connection;

	public DAOLoginRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception {

		String sql = "SELECT * FROM model_login WHERE upper(login) = upper(?) AND upper(senha) = upper(?)";
		
		PreparedStatement validarLogin = connection.prepareStatement(sql);
		validarLogin.setString(1, modelLogin.getLogin());
		validarLogin.setString(2, modelLogin.getSenha());

		ResultSet resultado = validarLogin.executeQuery();
		
		if(resultado.next()) {
			return true; //Autenticado
		}
		return false; //Não autenticado
	}
}
