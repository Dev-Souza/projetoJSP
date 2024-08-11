package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.export.XlsxReportConfiguration;
import net.sf.jasperreports.poi.export.JRXlsExporter;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ReportUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	public byte[] geraRelatorioPDF(List listaDados, String nomeRelatorio, ServletContext servletContext)
			throws Exception {

		// Cria a lista de dados que vem do nosso SQL da consulta feita
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);

		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";

		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap(), jrbcds);

		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}

	public byte[] geraRelatorioPDF(List listaDados, String nomeRelatorio, HashMap<String, Object> params,
			ServletContext servletContext) throws Exception {

		// Cria a lista de dados que vem do nosso SQL da consulta feita
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);

		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";

		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, jrbcds);

		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}

	public byte[] geraRelatorioExcel(List listaDados, String nomeRelatorio, HashMap<String, Object> params,
	        ServletContext servletContext) throws Exception {

	    JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);
	    String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";

	    JasperPrint impressoraJasper;
	    try {
	        impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, jrbcds);
	    } catch (JRException e) {
	        e.printStackTrace();
	        throw new Exception("Erro ao preencher o relatório: " + e.getMessage(), e);
	    }

	    JRXlsExporter exporter = new JRXlsExporter();
	    exporter.setExporterInput(new SimpleExporterInput(impressoraJasper));

	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));

	    SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
	    configuration.setOnePagePerSheet(false); // Teste com uma configuração diferente
	    configuration.setDetectCellType(true);

	    exporter.setConfiguration(configuration);

	    try {
	        exporter.exportReport();
	    } catch (JRException e) {
	        e.printStackTrace();
	        throw new Exception("Erro ao exportar o relatório para Excel: " + e.getMessage(), e);
	    }

	    return byteArrayOutputStream.toByteArray();
	}
}
