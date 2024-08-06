package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelTelefone;

public class DAOTelefoneRepository {

	private Connection connection;
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	public DAOTelefoneRepository() {
		
		connection = SingleConnectionBanco.getConnection();
	}
	
	public void gravarTelefone(ModelTelefone modelTelefone) throws Exception{
		
		String sql = "INSERT INTO telefone(numero, usuario_pai_id, usuario_cad_id) VALUES (?,?,?)";
		
		PreparedStatement insert = connection.prepareStatement(sql);
		insert.setString(1, modelTelefone.getNumero());
		insert.setLong(2, modelTelefone.getUsuario_pai_id().getId());
		insert.setLong(3, modelTelefone.getUsuario_cad_id().getId());
		
		//Executa e salva no banco
		insert.execute();
		connection.commit();
		
	}
	
	public List<ModelTelefone> listarTelefones(Long idUserPai) throws Exception{
		
		List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();
		
		String sql = "SELECT * FROM telefone WHERE usuario_pai_id = ?";
		
		PreparedStatement listar = connection.prepareStatement(sql);
		listar.setLong(1, idUserPai);
	
		ResultSet resultado = listar.executeQuery();
		
		while (resultado.next()) {
			ModelTelefone modelTelefone = new ModelTelefone();
			modelTelefone.setId(resultado.getLong("id"));
			modelTelefone.setNumero(resultado.getString("numero"));
			modelTelefone.setUsuario_cad_id(daoUsuarioRepository.buscarUsuarioPorId(resultado.getLong("usuario_cad_id")));
			modelTelefone.setUsuario_pai_id(daoUsuarioRepository.buscarUsuarioPorId(resultado.getLong("usuario_pai_id")));
			
			retorno.add(modelTelefone);
		}
		
		return retorno;
	}
	
	public void deletarTelefone(Long id) throws Exception{
		
		String sql = "DELETE FROM telefone WHERE id = ?";
		
		PreparedStatement delete = connection.prepareStatement(sql);
		delete.setLong(1, id);
		
		delete.executeUpdate();
		connection.commit();
	}
	
}
