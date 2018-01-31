/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.emprestimo.solicita;

import classesAuxiliares.ClasseDoSistemaEstatico;
import classesAuxiliares.NegociosEstaticos;
import controller.PrincipalController;
import enumm.StatusEmprestimo;
import gui.SystemAutonet;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import utilitarios.Alertas;
import vo.Emprestimo;
import vo.EmprestimoEstoqueMaterial;
import vo.EstoqueMaterial;
import vo.Material;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class SolicitaEmprestimoController {

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnAdicionarMaterial;

    @FXML
    private TabPane tabPanePrincipal;

    @FXML
    private TableView<Material> tblBuscaMateriais;

    @FXML
    private Button btnExclur;

    @FXML
    private TableColumn<Material, String> tbcCategoriaListaMaterial;

    @FXML
    private TableView<Material> tblListaMateriais;

    @FXML
    private TextField txtFinalidade;

    @FXML
    private TableColumn<Material, String> tbcMaterialBuscaMaterial;

    @FXML
    private TableColumn<Material, String> tbcDescricaoListaMaterial;

    @FXML
    private TextField txtBuscador;

    @FXML
    private Button btnEditar;

    @FXML
    private Tab tabListaMaterial;

    @FXML
    private TableColumn<Material, Number> tbcQuantidadeDisponivelBuscaMaterial;

    @FXML
    private TableColumn<Material, Integer> tbcQuantidadeSolicitadaListaMaterial;

    @FXML
    private Tab tabBuscarMaterial;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextArea txtObservacao;

    @FXML
    private Label finalidadeObrigatorio;

    @FXML
    private DatePicker dtpDataEmprestimo;

    @FXML
    private Label dataObrigatorio;

    @FXML
    private TableColumn<Material, String> tbcCategoriaBuscaMaterial;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnSolicitar;

    ObservableList<Material> altertab = FXCollections.observableArrayList();

    @FXML
    void btnSolicitarOnAction(ActionEvent event) {

        if (tblListaMateriais.getItems() == null) {
            return;
        }

        Date data = new Date();
        Emprestimo emp = new Emprestimo();

        Instant instant = dtpDataEmprestimo.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        emp.setDt_emprestimo(Date.from(instant));

        emp.setFinalidade(txtFinalidade.getText());
        emp.setObservacao(txtObservacao.getText());
        emp.setStatus_emprestimo(StatusEmprestimo.ESPERANDO_ANALISE);

        emp.setId_pessoa_solicita(ClasseDoSistemaEstatico.getPessoa());
        
        try {
            System.out.println("Solicitado " + ClasseDoSistemaEstatico.getPessoa().getNome());
            emp = NegociosEstaticos.getNegocioEmprestimo().salvar(emp);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(emp.getId());

        for (int i = 0; i < altertab.size(); i++) {
            EmprestimoEstoqueMaterial eem = new EmprestimoEstoqueMaterial();
            // eem.setDt_devolucao(data);// obs rever este ponto
            eem.setId_emprestimo(emp);
            //   eem.setObservacao("dsada");
            eem.setQtd_emprestada(altertab.get(i).getQuantidadeSolicitada());
            eem.setQtd_devolvida(0);
            eem.setId_material(altertab.get(i));

            try {
                NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().salvar(eem);
                eem = null;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }

        emp = null;

        try {
            Parent root;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

//        NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().salvar(eem);
//        EmprestimoEstoqueMaterial emp = new EmprestimoEstoqueMaterial();
    }

    @FXML
    void btnCancelarOnAction(ActionEvent event) {

        try {
            Parent root;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void btnVoltarOnAction(ActionEvent event) {

        tabBuscarMaterial.setDisable(false);
        tabListaMaterial.setDisable(true);
        tabPanePrincipal.getSelectionModel().select(tabBuscarMaterial);

    }

    @FXML
    void btnAdicionarOnAction(ActionEvent event) {
        tabListaMaterial.setDisable(false);
        tabPanePrincipal.getSelectionModel().select(tabListaMaterial);
        tabBuscarMaterial.setDisable(true);

    }

    void verificaLista(Material mat) {

        if (altertab.size() > 0) {

            for (int i = 0; i < altertab.size(); i++) {
                if (mat.getDescricao() == altertab.get(i).getDescricao()) {
                    if ((mat.getQuantidadeSolicitada() + altertab.get(i).getQuantidadeSolicitada()) <= mat.getQuantidadeDisponivel().intValue()) {
                        //altertab.get(i).setQuantidadeSolicitada(mat.getQuantidadeSolicitada() + altertab.get(i).getQuantidadeSolicitada());
                        mat.setQuantidadeSolicitada(mat.getQuantidadeSolicitada() + altertab.get(i).getQuantidadeSolicitada());
                        altertab.set(i, mat);

                    }
                    return;
                }
            }
        }
        altertab.add(mat);
    }

    boolean set_quantidade() {
        Material mat = new Material();
        mat = tblBuscaMateriais.getSelectionModel().getSelectedItem();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Informe a quantidade");
//        // dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Informe a quantidade de material:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            int resultado = Integer.parseInt(result.get());
            if (resultado <= mat.getQuantidadeDisponivel().intValue() && resultado > 0) {
                mat.setQuantidadeSolicitada(resultado);
                verificaLista(mat);
                //altertab.add(mat);
                return true;
            } else {
                  Alertas al = new Alertas();
                  al.alerta(Alert.AlertType.ERROR, "Erro", "Quantidade inválida");
                return false;
            }

        }
        return false;
    }

    @FXML
    void btnAdicionarMaterialOnAction(ActionEvent event) {

        // fazer o tratamento da seleção
        if (set_quantidade()) {
            tblListaMateriais.setItems(altertab);
            completar_ListaMaterial(altertab);
            btnVoltarOnAction(event);

        } else {
            return;
        }
        // altertab.add(tblBuscaMateriais.getSelectionModel().getSelectedItem());

    }

    void completar_ListaMaterial(List<Material> list) {

        this.tbcCategoriaListaMaterial.setCellValueFactory(new PropertyValueFactory<Material, String>("CategoriaNome"));
        this.tbcDescricaoListaMaterial.setCellValueFactory(new PropertyValueFactory<Material, String>("descricao"));
        this.tbcQuantidadeSolicitadaListaMaterial.setCellValueFactory(new PropertyValueFactory<Material, Integer>("quantidadeSolicitada"));
        tblListaMateriais.setItems(altertab);
    }

    @FXML
    void btnEditarOnAction(ActionEvent event) {

    }

    @FXML
    void btnExclurOnAction(ActionEvent event) {

    }

    @FXML
    void btnBuscarOnAction(ActionEvent event) {

    }

    private void completarTabela(List<Material> lista) {

        ObservableList<Material> dado = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i));
        }
        this.tbcCategoriaBuscaMaterial.setCellValueFactory(new PropertyValueFactory<Material, String>("CategoriaNome"));
        this.tbcMaterialBuscaMaterial.setCellValueFactory(new PropertyValueFactory<Material, String>("descricao"));
        this.tbcQuantidadeDisponivelBuscaMaterial.setCellValueFactory(new PropertyValueFactory<Material, Number>("quantidade"));
        this.tblBuscaMateriais.setItems(dado);

    }

    public void initialize() {
        List<Material> lista = NegociosEstaticos.getNegocioMaterial().buscarTodos();
        completarTabela(lista);

        tabListaMaterial.setDisable(true);
        dataObrigatorio.setVisible(false);
        finalidadeObrigatorio.setVisible(false);
    }

}
