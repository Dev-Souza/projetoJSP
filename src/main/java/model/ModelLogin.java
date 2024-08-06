package model;

import java.io.Serializable;
import java.sql.Date;

public class ModelLogin implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private String email;
	private String login;
	private String senha;
	private Date datanascimento;
	//Vindo do meu banco para ver se o usuário que está logando é admin
	private boolean useradmin;
	private String perfil;
	private String sexo;
	private String fotouser;
	private String extensaofotouser;
	//Atributos para endereço
	private String cep;
	private String logradouro;
	private String bairro;
	private String localidade;
	private String uf;
	private String numero;
	
	public boolean isNovo() {
		
		if (this.id == null) {
			return true; //Inserir novo
		}else if (this.id != null && this.id > 0) {
			return false; //Atualizar
		}
		
		return id == null;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isUseradmin() {
		return useradmin;
	}

	public void setUseradmin(boolean useradmin) {
		this.useradmin = useradmin;
	}
	
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public String getPerfil() {
		return perfil;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public String getSexo() {
		return sexo;
	}
	
	public String getExtensaofotouser() {
		return extensaofotouser;
	}
	
	public void setExtensaofotouser(String extensaofotouser) {
		this.extensaofotouser = extensaofotouser;
	}
	
	public String getFotouser() {
		return fotouser;
	}
	
	public void setFotouser(String fotouser) {
		this.fotouser = fotouser;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getDatanascimento() {
		return datanascimento;
	}

	public void setDatanascimento(Date datanascimento) {
		this.datanascimento = datanascimento;
	}
}
