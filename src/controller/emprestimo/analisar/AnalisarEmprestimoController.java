/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.emprestimo.analisar;

import classesAuxiliares.NegociosEstaticos;
import classesAuxiliares.TblEmprestimoEstoque;
import classesAuxiliares.TblPessoaEmprestimo;
import controller.PrincipalController;
import gui.SystemAutonet;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import vo.Emprestimo;
import vo.EmprestimoEstoqueMaterial;
import vo.Pessoa;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class AnalisarEmprestimoController implements Initializable {

    @FXML
    private Button btnRecusar;

    @FXML
    private Button btnAdicionarLocal;

    @FXML
    private Button btnEditarLocal;

    @FXML
    private Button btnSelecionar;

    @FXML
    private TableView<TblPessoaEmprestimo> tblAguardandoAnalise;

    @FXML
    private TableColumn<?, ?> tbcDisponibilidadeAnalise;

    @FXML
    private TableColumn<?, ?> tbcQuantidadeSolicitadaSeparado;

    @FXML
    private TextField txtQtdSolicitada;

    @FXML
    private TextField txtMaterial;

    @FXML
    private TableColumn<?, ?> tbcQtdSolicitadaBusca;

    @FXML
    private Button btnAprovar;

    @FXML
    private Button btnVoltarAnalise;

    @FXML
    private Button btnAnalisar;

    @FXML
    private Button btnSalvar;

    @FXML
    private TableColumn<?, ?> tbcDescricaoSeparado;

    @FXML
    private Tab tabBuscarMaterial;

    @FXML
    private TableColumn<?, ?> tbcMaterialSeparado;

    @FXML
    private TableView<?> tblPrincipalBuscarMaterial;

    @FXML
    private TableColumn<?, ?> tbcQuantidadeDisponivelSeparado;

    @FXML
    private TableColumn<TblPessoaEmprestimo, String> tbcDtEmprestimo;

    @FXML
    private TableColumn<?, ?> tbcMaterialAnalise;

    @FXML
    private Label lblDtEmprestimo;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnVoltarBuscar;

    @FXML
    private Label lblObservacao;

    @FXML
    private Tab tabItensSeparado;

    @FXML
    private TabPane TabPanePrincipal;

    @FXML
    private Button btnVoltarLista;

    @FXML
    private TableColumn<?, ?> tbcCategoriaAnalise;

    @FXML
    private Label lblFinalidade;

    @FXML
    private Button btnRemoverLocal;

    @FXML
    private TableColumn<TblPessoaEmprestimo, String> tbcFinalidade;

    @FXML
    private Tab tabListaEmprestimo;

    @FXML
    private TableColumn<?, ?> tbcDescricaoBusca;

    @FXML
    private TableColumn<?, ?> tbcQuantidadeAnalise;

    @FXML
    private TableColumn<?, ?> tbcQuantidadeBusca;

    @FXML
    private TableColumn<TblPessoaEmprestimo, String> tbcPessoa;

    @FXML
    private Tab tabAnaliseEmprestimo;

    List<TblPessoaEmprestimo> ListaOf = new ArrayList();
    List<TblEmprestimoEstoque> ListaPessoaMaterial = new ArrayList();

    @FXML
    void btnAnalisarOnAction(ActionEvent event) {

    }

    @FXML
    void btnVoltarListaOnAction(ActionEvent event) {
         try {
            Parent root;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void btnAprovarOnAction(ActionEvent event) {

    }

    @FXML
    void btnRecusarOnAction(ActionEvent event) {

    }

    @FXML
    void btnVoltarAnaliseOnAction(ActionEvent event) {

    }

    @FXML
    void btnSalvarOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelarOnAction(ActionEvent event) {

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
    void btnSelecionarOnAction(ActionEvent event) {

    }

    @FXML
    void btnVoltarBuscarOnAction(ActionEvent event) {

    }

    private void completarTblAguardandoAnalise(List<TblPessoaEmprestimo> lista) {

        ObservableList<TblPessoaEmprestimo> dado = FXCollections.observableArrayList();

        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i));
        }

        this.tbcPessoa.setCellValueFactory(new PropertyValueFactory<TblPessoaEmprestimo, String>("Nome"));
        this.tbcDtEmprestimo.setCellValueFactory(new PropertyValueFactory<TblPessoaEmprestimo, String>("EmprestimoDt"));
        this.tbcFinalidade.setCellValueFactory(new PropertyValueFactory<TblPessoaEmprestimo, String>("Finalidade"));
        this.tblAguardandoAnalise.setItems(dado);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        TabPanePrincipal.getSelectionModel().select(tabListaEmprestimo);
        tabListaEmprestimo.setDisable(false);
        tabAnaliseEmprestimo.setDisable(true);
        tabItensSeparado.setDisable(true);
        tabBuscarMaterial.setDisable(true);
        tabBuscarMaterial.setDisable(true);

        List<EmprestimoEstoqueMaterial> empm = new ArrayList();

        List<Pessoa> listpessoa = NegociosEstaticos.getNegocioPessoa().buscarTodos();
        for (Pessoa vo : listpessoa) {
            List<Emprestimo> emp = NegociosEstaticos.getNegocioEmprestimo().buscarPorIdPessoaStatusESPERANDO_ANALISE(vo);
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
        completarTblAguardandoAnalise(ListaOf);
    }

}
