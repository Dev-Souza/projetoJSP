package servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

import beandto.BeanDtoGraficoSalarioUser;
import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;
import util.ReportUtil;

@MultipartConfig
@WebServlet(urlPatterns = { "/ServletUsuarioControlleer" })
public class ServletUsuarioController extends ServletGenericUtil {
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

				// Para recaregar todas as vezes meu usuário
				List<ModelLogin> modelLogins = daoUsuarioRepository.buscarUsuarioAjax(super.getUserLogado(request));

				msg = "Excluido com sucesso!";
				request.setAttribute("msg", msg);
				// Atributo do recarregamento
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {

				String idUser = request.getParameter("id");
				// Operação para deletar usuário
				daoUsuarioRepository.deletarUser(idUser);

				msg = "Excluido com sucesso!";
				response.getWriter().write(msg);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarajax")) {

				String nomeBuscado = request.getParameter("nomeBusca");

				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.buscarUsuarioAjax(nomeBuscado,
						super.getUserLogado(request));

				// Dependencia Jackson para transformar em json o meu objeto
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(dadosJsonUser);

				response.addHeader("totalPagina", "" + daoUsuarioRepository
						.consultaUsuarioLisTotalPaginaPaginacao(nomeBuscado, super.getUserLogado(request)));
				response.getWriter().write(json);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {

				String nomeBuscado = request.getParameter("nomeBusca");
				String pagina = request.getParameter("pagina");

				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.buscarUsuarioAjaxOffSet(nomeBuscado,
						super.getUserLogado(request), Integer.parseInt(pagina));

				// Dependencia Jackson para transformar em json o meu objeto
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(dadosJsonUser);

				response.addHeader("totalPagina", "" + daoUsuarioRepository
						.consultaUsuarioLisTotalPaginaPaginacao(nomeBuscado, super.getUserLogado(request)));
				response.getWriter().write(json);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {

				String idUser = request.getParameter("id");

				ModelLogin modelLogin = daoUsuarioRepository.buscarUsuarioPorId(idUser, super.getUserLogado(request));
				// Para recaregar todas as vezes meu usuário
				List<ModelLogin> modelLogins = daoUsuarioRepository.buscarUsuarioAjax(super.getUserLogado(request));

				request.setAttribute("msg", "Usuário em edição");
				request.setAttribute("modelLogin", modelLogin);
				// Atributo do recarregamento
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {

				List<ModelLogin> modelLogins = daoUsuarioRepository.buscarUsuarioAjax(super.getUserLogado(request));

				request.setAttribute("msg", "Usuário carregados");
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {

				String idUser = request.getParameter("id");

				ModelLogin modelLogin = daoUsuarioRepository.buscarUsuarioPorId(idUser, super.getUserLogado(request));
				if (modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {

					response.setHeader("Content-Disposition",
							"attachment;filename=arquivo." + modelLogin.getExtensaofotouser());
					response.getOutputStream()
							.write(new Base64().decodeBase64(modelLogin.getFotouser().split("\\,")[1]));
				}
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {

				Integer offset = Integer.parseInt(request.getParameter("pagina"));

				List<ModelLogin> modelLogins = daoUsuarioRepository
						.buscarUsuarioAjaxPaginada(this.getUserLogado(request), offset);

				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")) {

				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");

				if (dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {
					request.setAttribute("listarUser",
							daoUsuarioRepository.buscarUsuarioAjaxRel(super.getUserLogado(request)));
				} else {
					request.setAttribute("listarUser", daoUsuarioRepository
							.buscarUsuarioAjaxRel(super.getUserLogado(request), dataInicial, dataFinal));
				}

				request.setAttribute("dataInicial", dataInicial);
				request.setAttribute("dataFinal", dataFinal);
				request.getRequestDispatcher("principal/reluser.jsp").forward(request, response);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioPDF")
					|| acao.equalsIgnoreCase("imprimirRelatorioExcel")) {

				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");

				List<ModelLogin> modelLogins = null;

				if (dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {
					modelLogins = daoUsuarioRepository.buscarUsuarioAjaxRel(super.getUserLogado(request));
				} else {
					modelLogins = daoUsuarioRepository.buscarUsuarioAjaxRel(super.getUserLogado(request), dataInicial,
							dataFinal);
				}

				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorio") + File.separator);

				byte[] relatorio = null;
				String extensao = "";
				if (acao.equalsIgnoreCase("imprimirRelatorioPDF")) {
					relatorio = new ReportUtil().geraRelatorioPDF(modelLogins, "relatorio-user-jsp", params, request.getServletContext());
					extensao = "pdf";
				}else if (acao.equalsIgnoreCase("imprimirRelatorioExcel")) {
						relatorio = new ReportUtil().geraRelatorioExcel(modelLogins, "relatorio-user-jsp", params, request.getServletContext());
						extensao = "xls";
				}
				response.setHeader("Content-Disposition", "attachment;filename=arquivo." + extensao);
				response.getOutputStream().write(relatorio);
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("graficoSalario")){
				
				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");

				if (dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {
					
					BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = daoUsuarioRepository.montarGraficoMediaSalario(super.getUserLogado(request));
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(beanDtoGraficoSalarioUser);
					response.getWriter().write(json);
				} else {
					
					BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = daoUsuarioRepository.montarGraficoMediaSalario(super.getUserLogado(request), dataInicial, dataFinal);
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(beanDtoGraficoSalarioUser);
					response.getWriter().write(json);
				}
				
			}else {
				List<ModelLogin> modelLogins = daoUsuarioRepository.buscarUsuarioAjax(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
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
			String cep = request.getParameter("cep");
			String logradouro = request.getParameter("logradouro");
			String bairro = request.getParameter("bairro");
			String localidade = request.getParameter("localidade");
			String uf = request.getParameter("uf");
			String numero = request.getParameter("numero");
			String datanascimento = request.getParameter("datanascimento");
			String rendaMensal = request.getParameter("rendamensal");
			// Tratando de como está vindo a renda
			rendaMensal = rendaMensal.split("\\ ")[1].replaceAll("\\.", "").replaceAll("\\,", ".");

			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);
			modelLogin.setCep(cep);
			modelLogin.setLogradouro(logradouro);
			modelLogin.setBairro(bairro);
			modelLogin.setLocalidade(localidade);
			modelLogin.setUf(uf);
			modelLogin.setNumero(numero);
			// Converte essa data para salvar no banco
			modelLogin.setDatanascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd")
					.format(new SimpleDateFormat("dd/mm/yyyy").parse(datanascimento))));
			modelLogin.setRendamensal(Double.valueOf(rendaMensal));

			// Verifica se a solicitação é multipart
			Part part = request.getPart("fileFoto"); // Pega a foto do formulário

			if (part != null && part.getSize() > 0) {
				byte[] foto = IOUtils.toByteArray(part.getInputStream()); // Converte a imagem para bytes
				String imagemBase64 = "data:image/" + part.getContentType().split("/")[1] + ";base64,"
						+ new Base64().encodeBase64String(foto);

				modelLogin.setFotouser(imagemBase64);
				modelLogin.setExtensaofotouser(part.getContentType().split("/")[1]);
			}

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

			// Para recarregar todas as vezes meus usuários
			List<ModelLogin> modelLogins = daoUsuarioRepository.buscarUsuarioAjax(super.getUserLogado(request));

			request.setAttribute("msg", msg);
			request.setAttribute("modelLogin", modelLogin);
			// Atributo do recarregamento
			request.setAttribute("modelLogins", modelLogins);
			request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

}
