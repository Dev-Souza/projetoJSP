package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;
import model.ModelTelefone;

import java.io.IOException;
import java.util.List;

import dao.DAOTelefoneRepository;
import dao.DAOUsuarioRepository;

@WebServlet("/ServletTelefonee")
public class ServletTelefone extends ServletGenericUtil {

	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	private DAOTelefoneRepository daoTelefoneRepository = new DAOTelefoneRepository();

	public ServletTelefone() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("excluir")) {
				String idFone = request.getParameter("id");
				
				daoTelefoneRepository.deletarTelefone(Long.parseLong(idFone));

				//Para retornar o objetos na tela novamente
				String userpai = request.getParameter("userpai");
				ModelLogin modelLogin = daoUsuarioRepository.buscarUsuarioPorId(Long.parseLong(userpai));
				List<ModelTelefone> modelTelefones = daoTelefoneRepository.listarTelefones(modelLogin.getId());
				request.setAttribute("modelTelefones", modelTelefones);
				request.setAttribute("modelLogin", modelLogin);
				request.setAttribute("msg", "Telefone excluído com sucesso!");
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
				
				return;
			}
			
			String idUser = request.getParameter("idUser");

			if (idUser != null && !idUser.isEmpty()) {

				ModelLogin modelLogin = daoUsuarioRepository.buscarUsuarioPorId(Long.parseLong(idUser));

				List<ModelTelefone> modelTelefones = daoTelefoneRepository.listarTelefones(modelLogin.getId());
				
				request.setAttribute("modelTelefones", modelTelefones);
				request.setAttribute("modelLogin", modelLogin);
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);

			} else {
				List<ModelLogin> modelLogins = daoUsuarioRepository.buscarUsuarioAjax(super.getUserLogado(request));
				// Atributo do recarregamento
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String usuario_pai_id = request.getParameter("id");
			String numero = request.getParameter("numero");

			ModelTelefone modelTelefone = new ModelTelefone();

			modelTelefone.setNumero(numero);
			modelTelefone.setUsuario_pai_id(daoUsuarioRepository.buscarUsuarioPorId(Long.parseLong(usuario_pai_id)));
			modelTelefone.setUsuario_cad_id(super.getUserLogadoObject(request));

			daoTelefoneRepository.gravarTelefone(modelTelefone);
			
			List<ModelTelefone> modelTelefones = daoTelefoneRepository.listarTelefones(Long.parseLong(usuario_pai_id));
			
			//Retornando o objeto pai
			ModelLogin modelLogin = daoUsuarioRepository.buscarUsuarioPorId(Long.parseLong(usuario_pai_id));
			request.setAttribute("modelLogin", modelLogin);
			
			request.setAttribute("modelTelefones", modelTelefones);
			request.setAttribute("msg", "Telefone gravado com sucesso!");
			request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
