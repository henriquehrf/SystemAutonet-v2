/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.estoque.baixa;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class BaixaMaterialController  {
    
    @FXML
    private Button btnAdicionar;

    @FXML
    private TabPane tabPanePrincipal;

    @FXML
    private Button btnExclur;

    @FXML
    private TableColumn<?, ?> tbcCategoriaListaMaterial;

    @FXML
    private Button btnBaixar;

    @FXML
    private TableColumn<?, ?> tbcDescricaoListaMaterial;

    @FXML
    private TextField txtBuscador;

    @FXML
    private TableColumn<?, ?> tbcQuantidadeDisponivelBuscaMaterial;

    @FXML
    private TableColumn<?, ?> tbcQuantidadeSolicitadaListaMaterial;

    @FXML
    private Tab tabBuscarMaterial;

    @FXML
    private ComboBox<?> cbmTipoBaixa;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextArea txtObservacao;

    @FXML
    private Button btnAdicionarMaterial;

    @FXML
    private TextArea txtObservacao1;

    @FXML
    private Button btnVoltarObservacao;

    @FXML
    private Label lblDescricao;

    @FXML
    private TableView<?> tblBuscaMateriais;

    @FXML
    private Tab tabObservacao;

    @FXML
    private TableView<?> tblListaMateriais;

    @FXML
    private TextField txtFinalidade;

    @FXML
    private TableColumn<?, ?> tbcMaterialBuscaMaterial;

    @FXML
    private Button btnEditar;

    @FXML
    private Tab tabListaMaterial;

    @FXML
    private Button btnSalvarObservacao;

    @FXML
    private DatePicker dtpDataEmprestimo;

    @FXML
    private TableColumn<?, ?> tbcLocalBuscaMaterial;

    @FXML
    private Button btnBuscar;

    @FXML
    void btnAdicionarOnAction(ActionEvent event) {

    }

    @FXML
    void btnEditarOnAction(ActionEvent event) {

    }

    @FXML
    void btnExclurOnAction(ActionEvent event) {

    }

    @FXML
    void btnBaixarOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelarOnAction(ActionEvent event) {

    }

    @FXML
    void btnAdicionarMaterialOnAction(ActionEvent event) {

    }

    @FXML
    void btnVoltarOnAction(ActionEvent event) {

    }

    @FXML
    void btnBuscarOnAction(ActionEvent event) {

    }

    @FXML
    void btnSalvarObservacaoOnAction(ActionEvent event) {

    }

    @FXML
    void btnVoltarObservacaoOnAction(ActionEvent event) {

    }

 
    public void initialize() {
        // TODO
    }    
    
}
