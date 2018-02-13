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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import vo.EmprestimoEstoqueMaterial;
import vo.Material;
import vo.Pessoa;

/**
 *
 * @author Henrique
 */
public class GerarPDF {

    private PdfPTable gerarTabelaRelacaoMaterial(List<EmprestimoEstoqueMaterial> list) {

        PdfPTable table = new PdfPTable(5);
        PdfPCell cell;
        table.addCell("QUANTIDADE");
        table.addCell("MATERIAL");
        table.addCell("CATEGORIA");
        table.addCell("ESTOQUE");
        table.addCell("OBS.");
        table.completeRow();

        for (int i = 0; i < list.size(); i++) {
            table.addCell("" + list.get(i).getQtd_emprestada() + " " + list.get(i).getId_material().getId_tipo_unidade().getSigla());
            table.addCell(list.get(i).getId_material().getDescricao());
            table.addCell(list.get(i).getId_material().getId_categoria().getDescricao());
            table.addCell(list.get(i).getId_estoquematerial().getId_departamento().getDescricao());
            table.addCell(list.get(i).getObservacao());
            table.completeRow();
        }

        return table;
    }

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

    public void comprovanteEntrega(List<EmprestimoEstoqueMaterial> list, String url, Pessoa solicitante, Pessoa autorizador) throws Exception {

        Document document = new Document();
        Date dt = new Date();

        PdfWriter.getInstance(document, new FileOutputStream(url + "/ComprovanteEntrega" + dt.getTime() + ".pdf"));
        document.open();
        document.addCreationDate();
        String caminho = new File("./src/utilitarios/icones/icone.png").getCanonicalPath();
        Image figura = Image.getInstance(caminho);
        figura.scaleToFit(100, 100);
        figura.setAlignment(1);
        document.add(figura);

        Paragraph p = new Paragraph("SystemAutoNet - Comprovante de Entrega de Materiais");
        p.setAlignment("center");
        document.add(p);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        Paragraph c = new Paragraph("Lista de Materiais");
        c.setAlignment("center");
        document.add(new Paragraph(" "));

        List<Material> material = new ArrayList<>();
        for (EmprestimoEstoqueMaterial vo : list) {
            Material mat = vo.getId_material();
            mat.setQuantidadeSolicitada(vo.getQtd_emprestada());

            material.add(mat);
        }
        document.add(gerarTabelaSolicitacaoEmprestimo(material));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        String termoCiencia = "Eu " + solicitante.getNome() + ", CPF Nº: " + solicitante.getCpf() + " e portador do Nº Matrícula: " + solicitante.getNum_matricula() + ", reconheço que "
                + "recebi TODOS os materiais listado no documento acima e prometo utilizar os devidos materiais com responsabilidade "
                + "e a devolução de todos os materiais.\n\n\n\n"
                + "Cuiabá - MT, _____/_____/__________ \n\n"
                + "_______________________________________________\n" + solicitante.getNome() + "\n"
                + "_______________________________________________\n" + autorizador.getNome() + "\n"
                + "_______________________________________________\nTestemunha:\n"
                + "CPF Testemunha\n\n\n";

        Paragraph t = new Paragraph(termoCiencia);
        t.setAlignment("justify");
        document.add(t);
        Paragraph d = new Paragraph("Documento gerado automáticamente - SystemAutoNet 1.0");
        d.setAlignment("center");
        document.add(d);
        document.add(new Paragraph(" "));
        document.close();

    }

    public void analisarEmprestimo(List<EmprestimoEstoqueMaterial> list, String url) throws Exception {
        Document document = new Document();
        Date dt = new Date();

        PdfWriter.getInstance(document, new FileOutputStream(url + "/ListagemMaterialEstoque" + dt.getTime() + ".pdf"));
        document.open();
        document.addCreationDate();
        String caminho = new File("./src/utilitarios/icones/icone.png").getCanonicalPath();
        Image figura = Image.getInstance(caminho);
        figura.scaleToFit(100, 100);
        figura.setAlignment(1);
        document.add(figura);

        Paragraph p = new Paragraph("SystemAutoNet - Relação de Material/Estoque empréstimo");
        p.setAlignment("center");
        document.add(p);
        document.add(new Paragraph(" "));
        document.add(gerarTabelaRelacaoMaterial(list));
        document.add(new Paragraph(" "));
        document.close();
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
