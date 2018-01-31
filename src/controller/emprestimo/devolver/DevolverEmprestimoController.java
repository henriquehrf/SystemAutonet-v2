/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.emprestimo.devolver;

import classesAuxiliares.NegociosEstaticos;
import classesAuxiliares.TblEmprestimoEstoque;
import classesAuxiliares.TblPessoaEmprestimo;
import controller.PrincipalController;
import gui.SystemAutonet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import vo.Emprestimo;
import vo.EmprestimoEstoqueMaterial;
import vo.Pessoa;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class DevolverEmprestimoController {

    @FXML
    private Button btnBaixarItensEmprestimo;

    @FXML
    private Button btnAdicionarLocal;

    @FXML
    private Button btnBuscarLocalizarEstoque;

    @FXML
    private Button btnCancelarInformarEstoque;

    @FXML
    private Tab tabBuscarMaterial;

    @FXML
    private TableColumn<TblPessoaEmprestimo, String> tbcFinalidadeBuscarPorEmprestimo;

    @FXML
    private Button btnBuscarEmprestimo;

    @FXML
    private TextArea txtObservacao;

    @FXML
    private TableColumn<Pessoa, String> tbcMaterialInformarEstoque;

    @FXML
    private DatePicker dtpDtFinal;

    @FXML
    private TableColumn<TblPessoaEmprestimo, String> tbcPessoaBuscarEmprestimo;

    @FXML
    private TableColumn<TblPessoaEmprestimo, String> tbcResponsavelLocalizarEstoque;

    @FXML
    private Button btnDevolverItensEmprestimo;

    @FXML
    private Button btnVoltarItensEmprestimo;

    @FXML
    private TableColumn<TblPessoaEmprestimo, String> tbcDtEmprestimoBuscarEmprestimo;

    @FXML
    private TextField txtBuscadorEmprestimo;

    @FXML
    private TextField txtBuscadorLocalizarEstoque;

    @FXML
    private Button btnConsultarBuscarEmprestimo;

    @FXML
    private TableColumn<?, ?> tbcNumeroLocalizarEstoque;

    @FXML
    private Button btnEditarLocal;

    @FXML
    private ToggleGroup Filtro;

    @FXML
    private TableColumn<TblPessoaEmprestimo, String> tbcCategoriaItensEmprestimo;

    @FXML
    private TableColumn<?, ?> tbcDescricaoLocalInformarEstoque;

    @FXML
    private TableColumn<TblPessoaEmprestimo, String> tbcMaterialItensEmprestimo;

    @FXML
    private TableColumn<TblPessoaEmprestimo, Number> tbcQtdItensEmprestimo;

    @FXML
    private Button btnSalvarInformarEstoque;

    @FXML
    private TableView<?> tblPrincipalBuscarMaterial;

    @FXML
    private Button btnSelecionarLocalizarEstoque;

    @FXML
    private RadioButton rdbDescricao;

    @FXML
    private Button btnVoltarObservacao;

    @FXML
    private Label lblDescricao;

    @FXML
    private TableColumn<?, ?> tbcDescricaoLocalizarEstoque;

    @FXML
    private RadioButton rdbNumero;

    @FXML
    private Tab tabItensEmprestimo;

    @FXML
    private Tab tabObservacao;

    @FXML
    private TableView<TblEmprestimoEstoque> tblPrincipalItensEmprestimo;

    @FXML
    private Button btnVoltarBuscarEmprestimo;

    @FXML
    private DatePicker dtpDtInicial;

    @FXML
    private Tab tabBuscarEmprestimo;

    @FXML
    private TableView<TblPessoaEmprestimo> tblPrincipalBuscarEmprestimo;

    @FXML
    private Button btnVoltarLocalizarEstoque;

    @FXML
    private Button btnRemoverLocal;

    @FXML
    private Button btnImprimirInformarEstoque;

    @FXML
    private TableColumn<?, ?> tbcDepartamentoLocalizarEstoque;

    @FXML
    private Tab tabInformarEstoque;

    @FXML
    private Button btnSalvarObservacao;

    @FXML
    private TabPane PanePrincipal;

    @FXML
    private RadioButton rdbPessoaResponsavel;

    @FXML
    private TableColumn<?, ?> tbcQtdInformarEstoque;

    @FXML
    private TableView<?> tblPrincipalInformarEstoque;

    List<TblPessoaEmprestimo> ListaOf = new ArrayList();
    List<TblEmprestimoEstoque> ListaPessoaMaterial = new ArrayList();

    @FXML
    void btnBuscarEmprestimoOnAction(ActionEvent event) {
        List<Pessoa> listpessoa = new ArrayList();
        Pessoa p = new Pessoa();
        p.setNome(txtBuscadorEmprestimo.getText());

        listpessoa = NegociosEstaticos.getNegocioPessoa().buscarPorNome(p);
        List<EmprestimoEstoqueMaterial> empm = new ArrayList();

        for (Pessoa vo : listpessoa) {
            List<Emprestimo> emp = NegociosEstaticos.getNegocioEmprestimo().buscarPorIdPessoa(vo);
            for (Emprestimo voEmp : emp) {
                empm = NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().consultarPorNaoDevolvido(voEmp);
                if (empm.size() > 0) {
                    TblPessoaEmprestimo tb = new TblPessoaEmprestimo();
                    tb.setEmprestimo(voEmp);
                    tb.setPessoa(vo);
                    ListaOf.add(tb);
                    // listEmprestimoNaoDevolvido.add(voEmp);

                }
            }

        }
        completarTabelaTblPrincipalBuscarEmprestimo(ListaOf);
    }

    @FXML
    void dtpDtInicialOnAction(ActionEvent event) {

    }

    @FXML
    void dtpDtFinalOnAction(ActionEvent event) {

    }

    @FXML
    void btnDevolverItensEmprestimoOnAction(ActionEvent event) {

    }

    @FXML
    void btnBaixarItensEmprestimoOnAction(ActionEvent event) {

    }

    @FXML
    void btnVoltarItensEmprestimoOnAction(ActionEvent event) {
        PanePrincipal.getSelectionModel().select(tabBuscarEmprestimo);
        tabBuscarEmprestimo.setDisable(false);
        tabItensEmprestimo.setDisable(true);
        tabInformarEstoque.setDisable(true);
        tabBuscarMaterial.setDisable(true);
        tabObservacao.setDisable(true);
    }

    @FXML
    void btnSalvarInformarEstoqueOnAction(ActionEvent event) {

    }

    @FXML
    void btnImprimirInformarEstoqueOnAction(ActionEvent event) {

    }

    @FXML
    void btnVoltarBuscarEmprestimo_onAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void btnCancelarInformarEstoqueOnAction(ActionEvent event) {

    }

    @FXML
    void btnAdicionarLocalOnAction(ActionEvent event) {

    }

    @FXML
    void btnEditarLocalOnAction(ActionEvent event) {

    }

    @FXML
    void btnRemoverLocalOnAction(ActionEvent event) {

    }

    @FXML
    void btnSelecionarLocalizarEstoqueOnAction(ActionEvent event) {

    }

    @FXML
    void btnVoltarLocalizarEstoqueOnAction(ActionEvent event) {

    }

    @FXML
    void btnBuscarLocalizarEstoqueOnAction(ActionEvent event) {

    }

    @FXML
    void btnSalvarObservacaoOnAction(ActionEvent event) {

    }

    @FXML
    void btnVoltarObservacaoOnAction(ActionEvent event) {

    }

    @FXML
    void btnConsultarBuscarEmprestimo_onAction(ActionEvent event) {

        ListaPessoaMaterial.clear();
        for (TblPessoaEmprestimo vo : ListaOf) {
            if (vo.getEmprestimo() == tblPrincipalBuscarEmprestimo.getSelectionModel().getSelectedItem().getEmprestimo()) {
                List<EmprestimoEstoqueMaterial> list = NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().consultarPorNaoDevolvido(vo.getEmprestimo());

                for (EmprestimoEstoqueMaterial voe : list) {
                    TblEmprestimoEstoque EE = new TblEmprestimoEstoque();
                    EE.setEmp(vo.getEmprestimo());
                    EE.setEmpEstoque(voe);
                    ListaPessoaMaterial.add(EE);
                }

            }
        }
        completartblPrincipalItensEmprestimo(ListaPessoaMaterial);
        tabBuscarEmprestimo.setDisable(true);
        tabItensEmprestimo.setDisable(false);
        tabInformarEstoque.setDisable(true);
        tabBuscarMaterial.setDisable(true);
        tabObservacao.setDisable(true);
        PanePrincipal.getSelectionModel().select(tabItensEmprestimo);

    }

    public void initialize() {
        PanePrincipal.getSelectionModel().select(tabBuscarEmprestimo);
        tabBuscarEmprestimo.setDisable(false);
        tabItensEmprestimo.setDisable(true);
        tabInformarEstoque.setDisable(true);
        tabBuscarMaterial.setDisable(true);
        tabObservacao.setDisable(true);

//        List<Emprestimo> listEmprestimoNaoDevolvido = new ArrayList();
        List<EmprestimoEstoqueMaterial> empm = new ArrayList();

        List<Pessoa> listpessoa = NegociosEstaticos.getNegocioPessoa().buscarTodos();
        for (Pessoa vo : listpessoa) {
            List<Emprestimo> emp = NegociosEstaticos.getNegocioEmprestimo().buscarPorIdPessoaStatusRetirado(vo);
            for (Emprestimo voEmp : emp) {
                empm = NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().consultarPorNaoDevolvido(voEmp);
                if (empm.size() > 0) {
                    TblPessoaEmprestimo tb = new TblPessoaEmprestimo();
                    tb.setEmprestimo(voEmp);
                    tb.setPessoa(vo);
                    ListaOf.add(tb);
                    // listEmprestimoNaoDevolvido.add(voEmp);

                }
            }

        }

        completarTabelaTblPrincipalBuscarEmprestimo(ListaOf);
        // TODO
    }

    private void completartblPrincipalItensEmprestimo(List<TblEmprestimoEstoque> lista) {

        ObservableList<TblEmprestimoEstoque> dado = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i));
        }

        this.tbcMaterialItensEmprestimo.setCellValueFactory(new PropertyValueFactory<TblPessoaEmprestimo, String>("Material"));
        this.tbcQtdItensEmprestimo.setCellValueFactory(new PropertyValueFactory<TblPessoaEmprestimo, Number>("Quantidade"));
        this.tbcCategoriaItensEmprestimo.setCellValueFactory(new PropertyValueFactory<TblPessoaEmprestimo, String>("Categoria"));
        this.tblPrincipalItensEmprestimo.setItems(dado);

    }

    private void completarTabelaTblPrincipalBuscarEmprestimo(List<TblPessoaEmprestimo> lista) {
        ObservableList<TblPessoaEmprestimo> dado = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i));
        }
        System.out.println(lista.size());

        this.tbcPessoaBuscarEmprestimo.setCellValueFactory(new PropertyValueFactory<TblPessoaEmprestimo, String>("Nome"));
        this.tbcDtEmprestimoBuscarEmprestimo.setCellValueFactory(new PropertyValueFactory<TblPessoaEmprestimo, String>("EmprestimoDt"));
        this.tbcFinalidadeBuscarPorEmprestimo.setCellValueFactory(new PropertyValueFactory<TblPessoaEmprestimo, String>("Finalidade"));
        this.tblPrincipalBuscarEmprestimo.setItems(dado);
    }

}
