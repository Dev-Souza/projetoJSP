package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;

public class DaoVersionadorBanco implements Serializable{

	private static final long serialVersionUID = 1L;

	private Connection connection;
	
	public DaoVersionadorBanco() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public void gravaArquivoSqlRodado (String nome_file) throws Exception{
		
		String sql = "INSERT INTO versionadorbanco(arquivo_sql) VALUES (?)";
		PreparedStatement gravarArquivo = connection.prepareStatement(sql);
		gravarArquivo.setString(1, nome_file);
		gravarArquivo.execute();
	}
	
	public boolean arquivoSqlRodado (String nome_do_arquivo) throws Exception{
	
		String sql = "SELECT COUNT(1) > 0 as rodado FROM versionadorbanco WHERE arquivo_sql = ?";
		PreparedStatement buscarArquivo = connection.prepareStatement(sql);
		buscarArquivo.setString(1, nome_do_arquivo);
		
		ResultSet resultado = buscarArquivo.executeQuery();
		
		resultado.next();
		
		return resultado.getBoolean("rodado");
	}
}
