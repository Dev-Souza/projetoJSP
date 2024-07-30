package servlets;

import java.io.IOException;
import java.util.List;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;

@MultipartConfig
@WebServlet(urlPatterns = {"/ServletUsuarioControlleer"})
public class ServletUsuarioController extends ServletGenericUtil{
	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public ServletUsuarioController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			String msg = "Operação realizada com sucesso!";
			String acao = request.getParameter("acao");

			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {

				String idUser = request.getParameter("id");
				// Operação para deletar usuário
				daoUsuarioRepository.deletarUser(idUser);
				
				//Para recaregar todas as vezes meu usuário
				List<ModelLogin> modelLogins = daoUsuarioRepository.buscarUsuarioAjax(super.getUserLogado(request));

				msg = "Excluido com sucesso!";
				request.setAttribute("msg", msg);
				//Atributo do recarregamento
				request.setAttribute("modelLogins", modelLogins);
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {

				String idUser = request.getParameter("id");
				// Operação para deletar usuário
				daoUsuarioRepository.deletarUser(idUser);

				msg = "Excluido com sucesso!";
				response.getWriter().write(msg);
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarajax")) {
			
				String nomeBuscado = request.getParameter("nomeBusca");
				
				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.buscarUsuarioAjax(nomeBuscado, super.getUserLogado(request));
				
				//Dependencia Jackson para transformar em json o meu objeto
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(dadosJsonUser);
				
				response.getWriter().write(json);
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
			
				String idUser = request.getParameter("id");
				
				ModelLogin modelLogin = daoUsuarioRepository.buscarUsuarioPorId(idUser, super.getUserLogado(request));
				//Para recaregar todas as vezes meu usuário
				List<ModelLogin> modelLogins = daoUsuarioRepository.buscarUsuarioAjax(super.getUserLogado(request));
				
				
				request.setAttribute("msg", "Usuário em edição");
				request.setAttribute("modelLogin", modelLogin);
				//Atributo do recarregamento
				request.setAttribute("modelLogins", modelLogins);
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.buscarUsuarioAjax(super.getUserLogado(request));
				
				request.setAttribute("msg", "Usuário carregados");
				request.setAttribute("modelLogins", modelLogins);
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String msg = "Operação realizada com sucesso!";

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			
			//Para pegar minha imagem do formulário
			if (ServletFileUpload.isMultipartContent((javax.servlet.http.HttpServletRequest) request)) {
				Part part = request.getPart("fileFoto"); //Pega foto da tela
				byte[] foto = IOUtils.toByteArray(part.getInputStream());; //Converte imagem para byte
				String imagemBase64 = new Base64().encodeBase64String(foto);
				System.out.println(imagemBase64);
			}

			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);

			// Rotina que verifica se já existe usuário com este login informado
			if (daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg = "Já existe um usuário com este login, informe outro login";
			} else {
				if (modelLogin.isNovo()) {
					msg = "Gravado com sucesso!";
				} else {
					msg = "Atualizado com sucesso!";
				}
				// Operação para gravar no banco
				modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin, super.getUserLogado(request));
			}
			
			//Para recarregar todas as vezes meus usuários
			List<ModelLogin> modelLogins = daoUsuarioRepository.buscarUsuarioAjax(super.getUserLogado(request));
			
			request.setAttribute("msg", msg);
			request.setAttribute("modelLogin", modelLogin);
			//Atributo do recarregamento
			request.setAttribute("modelLogins", modelLogins);
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

}
