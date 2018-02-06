/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import vo.Material;
import vo.Pessoa;

/**
 *
 * @author Henrique
 */
public class GerarPDF {

    private PdfPTable gerarTabelaSolicitacaoEmprestimo(List<Material> list) {
        // a table with three columns
        PdfPTable table = new PdfPTable(3);
        // the cell object
        PdfPCell cell;
        table.addCell("QUANTIDADE");
        table.addCell("MATERIAL");
        table.addCell("CATEGORIA");
        table.completeRow();
        for (int i = 0; i < list.size(); i++) {
            table.addCell("" + list.get(i).getQuantidadeSolicitada() + " " + list.get(i).getUnidadeMedida());
            table.addCell(list.get(i).getDescricao());
            table.addCell(list.get(i).getCategoriaNome());
            table.completeRow();
        }

        return table;
    }

    public void solicitarEmprestimo(String url, List<Material> lista, String finalidade, String data, String Observacao, Pessoa usuario) throws Exception {

        Date dt = new Date();
        String pattern = "dd/MM/yyyy";
        String dtSolicitacao = new SimpleDateFormat(pattern).format(dt);

// criação do documento
        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(url + "/ListaMateriais" + dt.getTime() + ".pdf"));
        document.open();
        document.addCreationDate();
        String caminho = new File("./src/utilitarios/icones/icone.png").getCanonicalPath();
        Image figura = Image.getInstance(caminho);
        figura.scaleToFit(100, 100);
        figura.setAlignment(1);
        document.add(figura);

        Paragraph p = new Paragraph("SystemAutoNet - Lista de Materiais");
        p.setAlignment("center");
        document.add(p);
        document.add(new Paragraph(" "));
        document.add(gerarTabelaSolicitacaoEmprestimo(lista));
        document.add(new Paragraph(" "));

        Paragraph dados = new Paragraph("\nAluno: " + usuario.getNome().toUpperCase() + "\nNº Matricula: " + usuario.getNum_matricula() + "\nFinalidade: " + finalidade + "\nObservações: " + Observacao + "\nData para Empréstimo: " + data + "\nData da Solicitação: " + dtSolicitacao);
        dados.getIndentationLeft();
        document.add(dados);

        document.close();
    }

}
