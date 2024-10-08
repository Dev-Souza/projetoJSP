package filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.oracle.wls.shaded.java_cup.runtime.Scanner;
import com.oracle.wls.shaded.java_cup.runtime.Symbol;

import connection.SingleConnectionBanco;
import dao.DaoVersionadorBanco;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/principal/*" }) /* Interceptas todas as requisiçoes que vierem do projeto ou mapeamento */
public class FilterAutenticacao implements Filter {

	private static Connection connection;

	public FilterAutenticacao() {
	}

	/* Encerra os processo quando o servidor é parado */
	/* Mataria os processo de conexão com banco */
	public void destroy() {
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Intercepta as requisicoes e a as respostas no sistema */
	/* Tudo que fizer no sistema vai fazer por aqui */
	/* Validação de autenticao */
	/* Dar commit e rolback de transaçoes do banco */
	/* Validar e fazer redirecionamento de paginas */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();

			String usuarioLogado = (String) session.getAttribute("usuario");

			String urlParaAutenticar = req.getServletPath();/* Url que está sendo acessada */

			/* Validar se está logado senão redireciona para a tela de login */
			if (usuarioLogado == null
					&& !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLoginn")) {/* Não está logado */

				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o login!");
				redireciona.forward(request, response);
				return; /* Para a execução e redireciona para o login */

			} else {
				chain.doFilter(request, response);
			}

			connection.commit(); // Deu tudo ceto, então comita as alterações no banco de dados

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	/* Inicia os processo ou recursos quando o servidor sobre o projeto */
	// inicar a conexão com o banco
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnectionBanco.getConnection();

		DaoVersionadorBanco banco = new DaoVersionadorBanco();

		String caminhoPastaSQL = fConfig.getServletContext().getRealPath("versionadorbancosql") + File.separator;

		File[] filesSql = new File(caminhoPastaSQL).listFiles();

		try {
			for (File file : filesSql) {
				boolean arquivoJaRodado = banco.arquivoSqlRodado(file.getName());
				
				if(!arquivoJaRodado) {
					
					FileInputStream entradaArquivo = new FileInputStream(file);
					
					java.util.Scanner lerArquivo = new java.util.Scanner(entradaArquivo, "UTF-8");
					
					StringBuilder sql = new StringBuilder();
					
					while(lerArquivo.hasNext()) {
						
						sql.append(lerArquivo.nextLine());
						sql.append("\n");
					}
					
					connection.prepareStatement(sql.toString()).execute();
					banco.gravaArquivoSqlRodado(file.getName());
					connection.commit();
					lerArquivo.close();
				}
			}
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

}
