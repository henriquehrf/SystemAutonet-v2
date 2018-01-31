/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.estoque.entrada;

import classesAuxiliares.NegociosEstaticos;
import classesAuxiliares.Validar;
import controller.PrincipalController;
import controller.cadastro.Cadastro.CadastroFornecedorController;
import controller.cadastro.Cadastro.CadastroMaterialController;
import controller.cadastro.Cadastro.CadastroSalaBlocoController;
import gui.SystemAutonet;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import utilitarios.Alertas;
import utilitarios.LerMessage;
import utilitarios.Mask;
import vo.Entrada;
import vo.EntradaMaterial;
import vo.EstoqueMaterial;
import vo.Fornecedor;
import vo.Local;
import vo.Material;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class EntradaMaterialController implements Initializable {
    
    @FXML
    private TabPane TabPai;
    
    @FXML
    private Label numNFObrigatorio;
    
    @FXML
    private Label dtEntradaObrigatorio;
    
    @FXML
    private Label valorNFObrigatorio;
    
    @FXML
    private RadioButton rdbPessoaResponsavelFornecedor;
    
    @FXML
    private Button btnDarEntrada;
    
    @FXML
    private Button btnVoltarBuscarFornecedor;
    
    @FXML
    private Button btnAdicionarFornecedor;
    @FXML
    private Button btncancelarEntrada;
    
    @FXML
    private Button btnAddMaterial;
    
    @FXML
    private Button btnAddFornecedor;
    
    @FXML
    // arrumar
    private TableView<EntradaMaterial> tblEntradaMaterial;
    
    @FXML
    private Tab tabMaterial;
    
    @FXML
    private TextField txtValorNF;
    
    @FXML
    private TextField txtCNPJ;
    
    @FXML
    private TableColumn<Fornecedor, String> tbcPessoaResponsavel;
    
    @FXML
    private TextField txtBuscadorLocal;
    
    @FXML
    private TableColumn<EntradaMaterial, String> tbcMaterial;
    
    @FXML
    private RadioButton rdbNumeroLocal;
    
    @FXML
    private TableColumn<EntradaMaterial, String> tbcValorUnitario;
    
    @FXML
    private Button btnBuscarMaterial;
    
    @FXML
    private TableColumn<EntradaMaterial, Integer> tbcQtd;
    
    @FXML
    private TextField txtValorMaterialTotalGeral;
    
    @FXML
    private TextField txtQtdMaterial;
    
    @FXML
    private Tab tabLocal;
    
    @FXML
    private TableColumn<Local, Integer> tbcNumeroBuscarLocal;
    
    @FXML
    private TableColumn<Local, String> tbcPessoaResponsavelBuscarLocal;
    
    @FXML
    private TableColumn<Fornecedor, String> tbcNomeFantasia;
    
    @FXML
    private Button btnEditarFornecedor;
    
    @FXML
    private DatePicker dtEntrada;
    
    @FXML
    private Button btnAdicionarBuscarFornecedor;
    
    @FXML
    private RadioButton rdbRazaoSocialFornecedor;
    
    @FXML
    private TableColumn<Material, Integer> tbcQuantidadeBuscaMaterial;
    
    @FXML
    private Button btnBuscar;
    
    @FXML
    private Button btnVoltarBuscaMaterial;
    
    @FXML
    private TextField txtFornecedor;
    
    @FXML
    private Tab tabEntrada;
    
    @FXML
    private ToggleGroup FiltroLocal;
    
    @FXML
    private TableView<Local> tblBuscarLocal;
    
    @FXML
    private ToggleGroup Filtro;
    
    @FXML
    private TextField txtNumNF;
    
    @FXML
    private TableView<Fornecedor> tblBuscarFornecedor;
    
    @FXML
    private TableColumn<EntradaMaterial, String> tbcLocal;
    
    @FXML
    private Button btnVoltarBuscarLocal;
    
    @FXML
    private RadioButton rdbNomeFantasiaFornecedor;
    
    @FXML
    private TableView<Material> tblBuscaMaterial;
    
    @FXML
    private Button btnAdicionarTabelaEntrada;
    
    @FXML
    private TextField txtBuscadorMaterial;
    
    @FXML
    private TableColumn<Local, String> tbcDescricaoBuscarLocal;
    
    @FXML
    private Button btnAdicionarBuscaMaterial;
    
    @FXML
    private Button btnBuscarLocal;
    
    @FXML
    private RadioButton rdbDescricaoLocal;
    
    @FXML
    private TableColumn<Material, String> tbcMaterialBuscaMaterial;
    
    @FXML
    private TableColumn<Fornecedor, String> tbcRazaoSocial;
    
    @FXML
    private TableColumn<EntradaMaterial, Float> tbcValorTotal;
    
    @FXML
    private Tab tabFornecedor;
    
    @FXML
    private Button btnRemoverTabelaEntrada;
    
    @FXML
    private Button btnAdicionarBuscarLocal;
    
    @FXML
    private TextField txtBuscadorFornecedor;
    
    @FXML
    private RadioButton rdbPessoaRespBuscaLocal;
    
    @FXML
    private TableColumn<Fornecedor, String> tbcCNPJ;
    
    @FXML
    private TableColumn<EntradaMaterial, Float> tbcValorMaterial;
    
    @FXML
    private Button btnAddLocal;
    
    @FXML
    private TableColumn<Material, String> tbcCategoriaBuscaMaterial;
    
    @FXML
    private RadioButton rdbCNPJFornecedor;

    // Ajusta este campo para o VO
    ArrayList<EntradaMaterial> Itens = new ArrayList<>();
    EntradaMaterial ent = new EntradaMaterial();
    Entrada entrada;
    Boolean materialEdit = false;
    int indexMaterial = 0;
    Boolean localEdit = false;
    int indexLocal = 0;
    public static Material materalAdd = null;
    public static Local localAdd = null;
    public static Fornecedor fornecedorAdd = null;
    Mask mask = new Mask();
    
    public EntradaMaterialController() {
        materalAdd = null;
        localAdd = null;
    }
    
    @FXML
    void btnDarEntradaOnAction(ActionEvent event) {

        // habilitar o banco de dados
        if (tblEntradaMaterial.getItems().size() > 0 && !txtNumNF.getText().isEmpty() && dtEntrada.getValue() != null && !txtValorNF.getText().isEmpty() && mask.Monetaria_To_Double(txtValorNF.getText())
                == mask.Monetaria_To_Double(txtValorMaterialTotalGeral.getText())) {
            
            entrada.setNumero_nf(Integer.parseInt(txtNumNF.getText()));
            
            Instant instant = dtEntrada.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            entrada.setDt_NF(Date.from(instant));
            entrada.setDt_entrada(new Date());
            entrada.setValor_total((float) mask.Monetaria_To_Double(txtValorMaterialTotalGeral.getText()));
            
            try {
                Entrada entrada2 = NegociosEstaticos.getNegocioEntrada().salvar(entrada);
                
                for (EntradaMaterial vo : Itens) {
                    EstoqueMaterial estoqueMaterial = new EstoqueMaterial();
                    vo.setId_entrada(entrada2);
                    NegociosEstaticos.getNegocioEntradaMaterial().salvar(vo);
                    
                    estoqueMaterial.setId_material(vo.getId_material());
                    estoqueMaterial.setId_departamento(vo.getLocal());
                    
                    estoqueMaterial = NegociosEstaticos.getNegocioEstoqueMateria().BuscarPorIdMaterialIdLocal(estoqueMaterial);
                    estoqueMaterial.setQuantidade(vo.getQuantidade_material());
                    
                    NegociosEstaticos.getNegocioEstoqueMateria().salvar(estoqueMaterial);
                    
                    vo.getId_material().setQuantidade(vo.getId_material().getQuantidade() + vo.getQuantidade_material());
                    NegociosEstaticos.getNegocioMaterial().salvar(vo.getId_material(), vo.getId_material());
                    estoqueMaterial = null;
                }
                Alertas alert = new Alertas();
                Boolean result = alert.alerta(Alert.AlertType.CONFIRMATION, "Operação realizada com sucesso!", "Todos os registros foram salvo, deseja realizar uma nova entrada?", "Sim", "Não");
                
                if (result) {
                    try {
                        Parent root;
                        root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/estoque/Entrada/EntradaMaterial.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                        SystemAutonet.SCENE.setRoot(root);
                        
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                } else {
                    Parent root;
                    root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                    SystemAutonet.SCENE.setRoot(root);
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            btnDarEntrada.setDisable(false);
            numNFObrigatorio.setVisible(false);
            dtEntradaObrigatorio.setVisible(false);
            valorNFObrigatorio.setVisible(false);
            txtNumNF.setStyle("-fx-border-color:darkgrey;");
            dtEntrada.setStyle("-fx-border-color:darkgrey;");
            txtValorNF.setStyle("-fx-border-color:darkgrey;");
            
            Alertas alert = new Alertas();
            String msg = "É obrigatório o preenchimento do(s) campo(s)\n";
            
            if (txtNumNF.getText().isEmpty()) {
                numNFObrigatorio.setVisible(true);
                msg += "Número da NF\n";
                txtNumNF.setStyle("-fx-border-color:red;");
            }
            if (dtEntrada.getValue() == null) {
                dtEntradaObrigatorio.setVisible(true);
                msg += "Data da NF\n";
                dtEntrada.setStyle("-fx-border-color:red;");
            }
            if (txtValorNF.getText().isEmpty()) {
                valorNFObrigatorio.setVisible(true);
                msg += "Valor da NF\n";
                txtValorNF.setStyle("-fx-border-color:red;");
            }
            
            if (txtFornecedor.getText().isEmpty()) {
                msg += "Informar um fornecedor\n";
            }
            
            if (tblEntradaMaterial.getItems().size() <= 0) {
                msg += "Adicionar um material\n";
            }
            if (!txtValorNF.getText().isEmpty() && !txtValorMaterialTotalGeral.getText().isEmpty()) {
                if (mask.Monetaria_To_Double(txtValorNF.getText()) != mask.Monetaria_To_Double(txtValorMaterialTotalGeral.getText())) {
                    valorNFObrigatorio.setVisible(true);
                    msg = "Valor da NF difere do valor da entrada";
                }
            }
            
            alert.alerta(Alert.AlertType.ERROR, "Erro ao salvar", msg);
            
        }
        
    }
    
    @FXML
    void btnAdicionarFornecedorOnAction(ActionEvent event) {

//        if (txtNumNF.getText().isEmpty() || dtEntrada.getValue() == null || txtValorNF.getText().isEmpty()) {
//            System.out.println(" forneca os dados anteriores");
//        } else {
        tabFornecedor.setDisable(false);
        tabEntrada.setDisable(true);
        TabPai.getSelectionModel().select(tabFornecedor);
        //      }
    }
    
    @FXML
    void btnEditarFornecedorOnAction(ActionEvent event) {
        btnAdicionarFornecedorOnAction(event);
        
    }
    
    @FXML
    void btnAdicionarTabelaEntradaOnAction(ActionEvent event) {
        
        TabPai.getSelectionModel().select(tabMaterial);
        tabEntrada.setDisable(true);
        tabMaterial.setDisable(false);
    }
    
    private boolean DadosEntrada() {
        
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Dados para entrada do material");
        dialog.setHeaderText("Forneça os dados necessários para a entrada do material");

// Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField qtd = new TextField();
        qtd.setPromptText("Qtd.");
        TextField value = new TextField();
        value.setPromptText("Valor");
        
        value.textProperty().addListener((obs, oldText, newText) -> {
            value.setText(mask.Monetaria(newText));
        });
        
        grid.add(new Label("Quantidade:"), 0, 0);
        grid.add(qtd, 1, 0);
        grid.add(new Label("Valor:"), 0, 1);
        grid.add(value, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        qtd.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });
        
        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> qtd.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(qtd.getText(), value.getText());
            }
            return null;
        });
        
        Optional<Pair<String, String>> result = dialog.showAndWait();

        //   result.ifPresent(usernamePassword 
        if (result.isPresent()) {
            ent.setQuantidade_material(Integer.parseInt(result.get().getKey()));
            ent.setValor_unitario_material((float) mask.Monetaria_To_Double(result.get().getValue()));
            
            if (result.get().getValue() != null && result.get().getValue() != null) {
                return true;
            } else {
                return false;
            }
            
        }
        
        return false;
    }
    
    @FXML
    void btnRemoverTabelaEntradaOnAction(ActionEvent event) {
        
        if (tblEntradaMaterial.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        
        Itens.remove(tblEntradaMaterial.getSelectionModel().getSelectedItem());
        
        if (Itens.isEmpty()) {
            btnRemoverTabelaEntrada.setDisable(true);
        }
        completarTabela(Itens);
        
    }
    
    @FXML
    void btnAdicionarBuscarFornecedorOnAction(ActionEvent event) {
        
        txtFornecedor.setText(tblBuscarFornecedor.getSelectionModel().getSelectedItem().getRazao_social());
        txtCNPJ.setText(tblBuscarFornecedor.getSelectionModel().getSelectedItem().getCnpj());
        btnAdicionarFornecedor.setDisable(true);
        btnEditarFornecedor.setDisable(false);
        btnVoltarBuscarFornecedorOnAction(event);
        entrada.setId_fornecedor(tblBuscarFornecedor.getSelectionModel().getSelectedItem());
        
    }
    
    @FXML
    void btnVoltarBuscarFornecedorOnAction(ActionEvent event) {
        
        tabFornecedor.setDisable(true);
        tabEntrada.setDisable(false);
        TabPai.getSelectionModel().select(tabEntrada);
        
    }
    
    @FXML
    void txtBuscadorFornecedorOnAction(ActionEvent event) {
        
    }
    
    void adicionarMaterial() {
        ent.setId_material(tblBuscaMaterial.getSelectionModel().getSelectedItem());
        if (DadosEntrada()) {
            TabPai.getSelectionModel().select(tabLocal);
            tabMaterial.setDisable(true);
            tabLocal.setDisable(false);
        }
    }
    
    @FXML
    void btnAdicionarBuscaMaterialOnAction(ActionEvent event) {
        
        if (tblBuscaMaterial.getSelectionModel().getSelectedItem() != null) {
            
            if (materialEdit) {
                TabPai.getSelectionModel().select(tabEntrada);
                tabMaterial.setDisable(true);
                tabEntrada.setDisable(false);
                materialEdit = false;
                Itens.get(indexMaterial).setId_material(tblBuscaMaterial.getSelectionModel().getSelectedItem());
                indexMaterial = 0;
                completarTabela(Itens);
                tblEntradaMaterial.refresh();
                return;
            }
            adicionarMaterial();
        }
        
    }
    
    @FXML
    void btnVoltarBuscaMaterialOnAction(ActionEvent event) {
        
        if (materialEdit) {
            materialEdit = false;
        }
        
        TabPai.getSelectionModel().select(tabEntrada);
        tabEntrada.setDisable(false);
        tabMaterial.setDisable(true);
        
    }
    
    void cadastrarFornecedor() {
        
        CadastroFornecedorController.setCadastrar(true);
        
        Parent root;
        try {
            root = FXMLLoader.load(CadastroFornecedorController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Fornecedor.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("SystemAutonet - Sistema de Controle Simplificado");
            stage.getIcons().add(new Image("/utilitarios/icones/icone.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            CadastroFornecedorController.setAdicionar(stage);
            stage.showAndWait();
            
            Thread t = new Thread() {
                @Override
                public void start() {
                    
                    if (fornecedorAdd != null) {
                        List<Fornecedor> list = NegociosEstaticos.getNegocioFornecedor().buscarTodos();
                        completarTabelaFornecedor(list);
                        tblBuscarFornecedor.refresh();
                        TabPai.getSelectionModel().select(tabEntrada);
                        tabFornecedor.setDisable(true);
                        tabEntrada.setDisable(false);
                        txtFornecedor.setText(fornecedorAdd.getRazao_social());
                        txtCNPJ.setText(fornecedorAdd.getCnpj());
                        btnAdicionarFornecedor.setDisable(true);
                        btnEditarFornecedor.setDisable(false);
                        
                    }
                }
            };
            t.start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    void cadastrarLocal() {
        CadastroSalaBlocoController.setCadastrar(true);
        
        Parent root;
        try {
            root = FXMLLoader.load(CadastroMaterialController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_SalaBloco.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("SystemAutonet - Sistema de Controle Simplificado");
            stage.getIcons().add(new Image("/utilitarios/icones/icone.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            CadastroSalaBlocoController.setAdicionar(stage);
            stage.showAndWait();
            
            Thread t = new Thread() {
                @Override
                public void start() {
                    
                    if (localAdd != null) {
                        List<Local> list = NegociosEstaticos.getNegocioLocal().buscarTodos();
                        completarTabelaLocal(list);
                        tblBuscarLocal.refresh();
                        for (int i = 0; i < list.size(); i++) {
                            if (tblBuscarLocal.getItems().get(i).getDescricao().equalsIgnoreCase(localAdd.getDescricao())) {
                                tblBuscarLocal.getSelectionModel().select(i);
                                break;
                            }
                        }
                    }
                }
            };
            t.start();
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    void cadastrarMaterial() {
        CadastroMaterialController.setCadastrar(true);
        Parent root;
        try {
            root = FXMLLoader.load(CadastroMaterialController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Material.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("SystemAutonet - Sistema de Controle Simplificado");
            stage.getIcons().add(new Image("/utilitarios/icones/icone.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            CadastroMaterialController.setAdicionar(stage);
            stage.showAndWait();
            
            Thread t = new Thread() {
                @Override
                public void start() {
                    
                    if (materalAdd != null) {
                        List<Material> list = NegociosEstaticos.getNegocioMaterial().buscarTodos();
                        completarTabelaMaterial(list);
                        tblBuscaMaterial.refresh();
                        for (int i = 0; i < list.size(); i++) {
                            if (tblBuscaMaterial.getItems().get(i).getDescricao().equalsIgnoreCase(materalAdd.getDescricao())) {
                                tblBuscaMaterial.getSelectionModel().select(i);
                                break;
                            }
                        }
                        adicionarMaterial();
                    }
                }
            };
            t.start();
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @FXML
    void btnAddFornecedorOnAction(ActionEvent event) {
        cadastrarFornecedor();
    }
    
    @FXML
    void btnAddLocalOnAction(ActionEvent event
    ) {
        cadastrarLocal();
    }
    
    @FXML
    void btnAddMaterialOnAction(ActionEvent event
    ) {
        cadastrarMaterial();
    }
    
    @FXML
    void btnBuscarMaterialOnAction(ActionEvent event
    ) {
        
        Material material = new Material();
        material.setDescricao(txtBuscadorMaterial.getText());
        completarTabelaMaterial(NegociosEstaticos.getNegocioMaterial().buscarPorDescricao(material));
        
    }
    
    @FXML
    void btnBuscaOnAction(ActionEvent event
    ) {
        
        if (rdbCNPJFornecedor.isSelected()) {
            
            char buscar[] = txtBuscadorFornecedor.getText().toCharArray();
            
            if (Validar.isDigit(buscar)) {
                Fornecedor f = new Fornecedor();
                f.setCnpj(txtBuscadorFornecedor.getText());
                List<Fornecedor> lista = NegociosEstaticos.getNegocioFornecedor().buscarPorCnpj(f);
                completarTabelaFornecedor(lista);
            } else {
                try {
                    IncompatibilidadeNumero();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            
        }
        if (rdbNomeFantasiaFornecedor.isSelected()) {
            Fornecedor f = new Fornecedor();
            f.setNome_fantasia(txtBuscadorFornecedor.getText());
            List<Fornecedor> lista = NegociosEstaticos.getNegocioFornecedor().buscarPorNomeFantasia(f);
            completarTabelaFornecedor(lista);
            
        }
        if (rdbPessoaResponsavelFornecedor.isSelected()) {
            Fornecedor f = new Fornecedor();
            f.setPessoa_responsavel(txtBuscadorFornecedor.getText());
            List<Fornecedor> lista = NegociosEstaticos.getNegocioFornecedor().buscarPorPessoaResponsavel(f);
            completarTabelaFornecedor(lista);
            
        }
        if (rdbRazaoSocialFornecedor.isSelected()) {
            Fornecedor f = new Fornecedor();
            f.setRazao_social(txtBuscadorFornecedor.getText());
            List<Fornecedor> lista = NegociosEstaticos.getNegocioFornecedor().buscarPorRazaoSocial(f);
            completarTabelaFornecedor(lista);
        }
    }
    
    @FXML
    void btnAdicionarBuscarLocalOnAction(ActionEvent event
    ) {
        
        if (tblBuscarLocal.getSelectionModel().getSelectedItem() != null) {
            
            if (localEdit) {
                
                TabPai.getSelectionModel().select(tabEntrada);
                tabLocal.setDisable(true);
                tabEntrada.setDisable(false);
                localEdit = false;
                Itens.get(indexLocal).setLocal(tblBuscarLocal.getSelectionModel().getSelectedItem());
                indexLocal = 0;
                completarTabela(Itens);
                tblEntradaMaterial.refresh();
                
                return;
            }
            ent.setLocal(tblBuscarLocal.getSelectionModel().getSelectedItem());
            Itens.add(ent);
            completarTabela(Itens);
            ent = new EntradaMaterial();
            TabPai.getSelectionModel().select(tabEntrada);
            tabEntrada.setDisable(false);
            tabLocal.setDisable(true);
            
            btnRemoverTabelaEntrada.setDisable(false);
            
        }
        
    }
    
    @FXML
    void btnVoltarBuscarLocalOnAction(ActionEvent event
    ) {
        
        if (localEdit) {
            TabPai.getSelectionModel().select(tabEntrada);
            tabEntrada.setDisable(false);
            tabLocal.setDisable(true);
            return;
        }
        TabPai.getSelectionModel().select(tabMaterial);
        tabMaterial.setDisable(false);
        tabLocal.setDisable(true);
        
    }
    
    @FXML
    void btncancelarEntradaOnAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    @FXML
    void btnBuscarLocalOnAction(ActionEvent event
    ) {
        if (rdbNumeroLocal.isSelected()) {
            Local local = new Local();
            char buscar[] = txtBuscadorLocal.getText().toCharArray();
            
            if (Validar.isDigit(buscar)) {
                if (txtBuscadorLocal.getText().isEmpty()) {
                    completarTabelaLocal(NegociosEstaticos.getNegocioLocal().buscarTodos());
                } else {
                    
                    local.setNumero(Integer.parseInt(txtBuscadorLocal.getText()));
                    completarTabelaLocal(NegociosEstaticos.getNegocioLocal().buscarPorNumero(local));
                }
            } else {
                try {
                    LerMessage ler = new LerMessage();
                    Alertas aviso = new Alertas();
                    aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.incompatibilidade.numero"), ler.getMessage("msg.incompatibilidade.numero"));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        if (rdbPessoaRespBuscaLocal.isSelected()) {
            Local local = new Local();
            local.setResponsavel(txtBuscadorLocal.getText());
            completarTabelaLocal(NegociosEstaticos.getNegocioLocal().buscarPorPessoaResponsavel(local));
        }
        if (rdbDescricaoLocal.isSelected()) {
            Local local = new Local();
            local.setDescricao(txtBuscadorLocal.getText());
            completarTabelaLocal(NegociosEstaticos.getNegocioLocal().buscarPorDescricao(local));
        }
    }
    
    @FXML
    
    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        
        try {
            txtValorNF.textProperty().addListener((obs, oldText, newText) -> {
                txtValorNF.setText(mask.Monetaria(newText));
            });
            txtValorMaterialTotalGeral.textProperty().addListener((obs, oldText, newText) -> {
                txtValorMaterialTotalGeral.setText(mask.Monetaria(newText));
            });
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        String pattern = "dd/MM/yyyy";
        dtEntrada.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            Date dt = new Date();
            LocalDate dtL = dt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    if (!date.isAfter(dtL)) {
                        return dateFormatter.format(date);
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            }
            
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        
        dtEntrada.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            
            @Override
            public DateCell call(final DatePicker datePicker) {
                Date dt = new Date();
                LocalDate dtL = dt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        if (item.isAfter(dtL)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        });
        
        tbcQtd.setCellFactory(TextFieldTableCell.<EntradaMaterial, Integer>forTableColumn(new IntegerStringConverter()));
        
        tbcQtd.setOnEditCommit(
                (CellEditEvent<EntradaMaterial, Integer> t) -> {
                    ((EntradaMaterial) t.getTableView().getItems().get(t.getTablePosition().getRow())).setQuantidade_material(t.getNewValue());
                    ((EntradaMaterial) t.getTableView().getItems().get(t.getTablePosition().getRow())).setValor_total(t.getNewValue());
                    completarTabela(t.getTableView().getItems());
                    
                }
        );
        
        tbcValorUnitario.setCellFactory(TextFieldTableCell.<EntradaMaterial>forTableColumn());
        
        tbcValorUnitario.setOnEditCommit(
                (CellEditEvent<EntradaMaterial, String> t) -> {
                    float value = (float) mask.Monetaria_To_Double(t.getNewValue());
                    ((EntradaMaterial) t.getTableView().getItems().get(t.getTablePosition().getRow())).setValor_unitario_material(value);
                    ((EntradaMaterial) t.getTableView().getItems().get(t.getTablePosition().getRow())).setValor_total(value);
                    completarTabela(t.getTableView().getItems());
                }
        );
        
        tblEntradaMaterial.setRowFactory(tv
                -> {
            TableRow<EntradaMaterial> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    
                    EntradaMaterial clickedRow = row.getItem();
                    
                    try {
                        int index = tblEntradaMaterial.getSelectionModel().getSelectedIndex();
                        TablePosition t = tblEntradaMaterial.getSelectionModel().getSelectedCells().get(0);
                        
                        if (t.getColumn() == 0) {
                            TabPai.getSelectionModel().select(tabMaterial);
                            tabMaterial.setDisable(false);
                            tabEntrada.setDisable(true);
                            materialEdit = true;
                            indexMaterial = index;
                        }
                        if (t.getColumn() == 4) {
                            TabPai.getSelectionModel().select(tabLocal);
                            tabEntrada.setDisable(true);
                            tabLocal.setDisable(false);
                            localEdit = true;
                            indexLocal = index;
                        }
                        
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    
                }
            });
            return row;
        }
        );
        
        entrada = new Entrada();
        
        TabPai.getSelectionModel()
                .select(tabEntrada);
        //-------------------------
        tabFornecedor.setDisable(
                true);
        tabLocal.setDisable(
                true);
        tabMaterial.setDisable(
                true);
        //---------------------------

        rdbNomeFantasiaFornecedor.setSelected(
                true);
        rdbDescricaoLocal.setSelected(
                true);
        valorNFObrigatorio.setVisible(
                false);
        dtEntradaObrigatorio.setVisible(
                false);
        numNFObrigatorio.setVisible(
                false);

        //---------
        btnRemoverTabelaEntrada.setDisable(
                true);
        //---------

        new Thread() {
            @Override
            public void run() {
                completarTabelaTodas();
            }
        }
                .start();
        
    }
    
    void completarTabelaFornecedor(List<Fornecedor> lista
    ) {
        ObservableList<Fornecedor> fornecedor = FXCollections.observableArrayList();
        
        for (int i = 0; i < lista.size(); i++) {
            fornecedor.add(lista.get(i));
        }
        
        this.tbcCNPJ.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("cnpj"));
        this.tbcRazaoSocial.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("razao_social"));
        this.tbcNomeFantasia.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("nome_fantasia"));
        this.tbcPessoaResponsavel.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("pessoa_responsavel"));
        tblBuscarFornecedor.setItems(fornecedor);
    }
    
    void completarTabelaMaterial(List<Material> lista
    ) {
        ObservableList<Material> material = FXCollections.observableArrayList();
        
        for (int i = 0; i < lista.size(); i++) {
            material.add(lista.get(i));
        }
        
        this.tbcMaterialBuscaMaterial.setCellValueFactory(new PropertyValueFactory<Material, String>("descricao"));
        this.tbcCategoriaBuscaMaterial.setCellValueFactory(new PropertyValueFactory<Material, String>("CategoriaNome"));
        this.tbcQuantidadeBuscaMaterial.setCellValueFactory(new PropertyValueFactory<Material, Integer>("quantidade"));
        Comparator<Material> cmp = new Comparator<Material>() {
            @Override
            public int compare(Material mat1, Material mat2) {
                int x = mat1.getDescricao().compareTo(mat2.getDescricao());
                if (x != 0) {
                    return x;
                } else {
                    Integer x1 = ((Material) mat1).getQuantidade();
                    Integer x2 = ((Material) mat2).getQuantidade();
                    return x1.compareTo(x2);
                }
            }
            
        };
        Collections.sort(material, cmp);
        
        tblBuscaMaterial.setItems(material);
    }
    
    void completarTabelaLocal(List<Local> lista
    ) {
        ObservableList<Local> local = FXCollections.observableArrayList();
        
        for (int i = 0; i < lista.size(); i++) {
            local.add(lista.get(i));
        }
        
        this.tbcPessoaResponsavelBuscarLocal.setCellValueFactory(new PropertyValueFactory<Local, String>("responsavel"));
        this.tbcNumeroBuscarLocal.setCellValueFactory(new PropertyValueFactory<Local, Integer>("numero"));
        this.tbcDescricaoBuscarLocal.setCellValueFactory(new PropertyValueFactory<Local, String>("descricao"));
        tblBuscarLocal.setItems(local);
        if (rdbDescricaoLocal.isSelected()) {
            Comparator<Local> cmpL = new Comparator<Local>() {
                @Override
                public int compare(Local loc1, Local loc2) {
                    int x = loc1.getDescricao().compareTo(loc2.getDescricao());
                    if (x != 0) {
                        return x;
                    } else {
                        Integer x1 = ((Local) loc1).getNumero();
                        Integer x2 = ((Local) loc2).getNumero();
                        return x1.compareTo(x2);
                    }
                }
            };
            
            Collections.sort(local, cmpL);
        }
        if (rdbNumeroLocal.isSelected()) {
            Comparator<Local> cmpL = new Comparator<Local>() {
                @Override
                public int compare(Local num1, Local num2) {
                    Integer x1 = ((Local) num1).getNumero();
                    Integer x2 = ((Local) num2).getNumero();
                    
                    return x1.compareTo(x2);
                }
            };
            
            Collections.sort(local, cmpL);
        }
        
        if (rdbPessoaRespBuscaLocal.isSelected()) {
            Comparator<Local> cmpL = new Comparator<Local>() {
                @Override
                public int compare(Local loc1, Local loc2) {
                    return loc1.getResponsavel().compareTo(loc2.getResponsavel());
                }
            };
            Collections.sort(local, cmpL);
            
        }
    }
    
    String adjustMoney(String conversor) {
        if (conversor.length() > 0) {
            int i = conversor.length();
            i = i - (conversor.indexOf("."));
            if (i == 2) {
                conversor += "0";
            }
        }
        return conversor;
    }
    
    void completarTabelaTodas() {
        List<Fornecedor> listFornecedor = NegociosEstaticos.getNegocioFornecedor().buscarTodos();
        List<Material> listMaterial = NegociosEstaticos.getNegocioMaterial().buscarTodos();
        List<Local> listLocal = NegociosEstaticos.getNegocioLocal().buscarTodos();
        
        ObservableList<Fornecedor> fornecedor = FXCollections.observableArrayList();
        ObservableList<Material> material = FXCollections.observableArrayList();
        ObservableList<Local> local = FXCollections.observableArrayList();
        
        for (int i = 0; i < listFornecedor.size(); i++) {
            fornecedor.add(listFornecedor.get(i));
        }
        
        this.tbcCNPJ.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("cnpj"));
        this.tbcRazaoSocial.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("razao_social"));
        this.tbcNomeFantasia.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("nome_fantasia"));
        this.tbcPessoaResponsavel.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("pessoa_responsavel"));
        tblBuscarFornecedor.setItems(fornecedor);

        //------------------------------------------------------------------------------------
        for (int i = 0; i < listMaterial.size(); i++) {
            material.add(listMaterial.get(i));
        }
        
        this.tbcMaterialBuscaMaterial.setCellValueFactory(new PropertyValueFactory<Material, String>("descricao"));
        this.tbcCategoriaBuscaMaterial.setCellValueFactory(new PropertyValueFactory<Material, String>("CategoriaNome"));
        this.tbcQuantidadeBuscaMaterial.setCellValueFactory(new PropertyValueFactory<Material, Integer>("quantidade"));
        tblBuscaMaterial.setItems(material);
        
        Comparator<Material> cmp = new Comparator<Material>() {
            @Override
            public int compare(Material mat1, Material mat2) {
                int x = mat1.getDescricao().compareTo(mat2.getDescricao());
                if (x != 0) {
                    return x;
                } else {
                    Integer x1 = ((Material) mat1).getQuantidade();
                    Integer x2 = ((Material) mat2).getQuantidade();
                    return x1.compareTo(x2);
                }
            }
            
        };
        Collections.sort(material, cmp);
        //------------------------------------------------------------------------------------

        for (int i = 0; i < listLocal.size(); i++) {
            local.add(listLocal.get(i));
        }
        this.tbcPessoaResponsavelBuscarLocal.setCellValueFactory(new PropertyValueFactory<Local, String>("responsavel"));
        this.tbcNumeroBuscarLocal.setCellValueFactory(new PropertyValueFactory<Local, Integer>("numero"));
        this.tbcDescricaoBuscarLocal.setCellValueFactory(new PropertyValueFactory<Local, String>("descricao"));
        tblBuscarLocal.setItems(local);
        if (rdbDescricaoLocal.isSelected()) {
            Comparator<Local> cmpL = new Comparator<Local>() {
                @Override
                public int compare(Local loc1, Local loc2) {
                    int x = loc1.getDescricao().compareTo(loc2.getDescricao());
                    if (x != 0) {
                        return x;
                    } else {
                        Integer x1 = ((Local) loc1).getNumero();
                        Integer x2 = ((Local) loc2).getNumero();
                        return x1.compareTo(x2);
                    }
                }
            };
            
            Collections.sort(local, cmpL);
        }
        if (rdbNumeroLocal.isSelected()) {
            Comparator<Local> cmpL = new Comparator<Local>() {
                @Override
                public int compare(Local num1, Local num2) {
                    Integer x1 = ((Local) num1).getNumero();
                    Integer x2 = ((Local) num2).getNumero();
                    
                    return x1.compareTo(x2);
                }
            };
            
            Collections.sort(local, cmpL);
        }
        
        if (rdbPessoaRespBuscaLocal.isSelected()) {
            Comparator<Local> cmpL = new Comparator<Local>() {
                @Override
                public int compare(Local loc1, Local loc2) {
                    return loc1.getResponsavel().compareTo(loc2.getResponsavel());
                }
            };
            Collections.sort(local, cmpL);
            
        }
    }
    
    private void completarTabela(List<EntradaMaterial> lista) {
        
        ObservableList<EntradaMaterial> dado = FXCollections.observableArrayList();
        float sum = 0;
        int qtdItens = 0;
        EntradaMaterial aux = new EntradaMaterial();
        for (int i = 0; i < lista.size(); i++) {
            aux = lista.get(i);
            aux.setValor_total(aux.getQuantidade_material() * aux.getValor_unitario_material());
            sum = aux.getValor_total() + sum;
            lista.set(i, aux);
            qtdItens++;
            dado.add(lista.get(i));
        }
        this.tbcMaterial.setCellValueFactory(new PropertyValueFactory<EntradaMaterial, String>("MaterialNome"));
        this.tbcQtd.setCellValueFactory(new PropertyValueFactory<EntradaMaterial, Integer>("quantidade_material"));
        this.tbcValorUnitario.setCellValueFactory(new PropertyValueFactory<EntradaMaterial, String>("valor_Unitario_Monetario"));
        this.tbcValorTotal.setCellValueFactory(new PropertyValueFactory<EntradaMaterial, Float>("valor_total"));
        this.tbcLocal.setCellValueFactory(new PropertyValueFactory<EntradaMaterial, String>("LocalNome"));
        
        tbcValorTotal.setCellFactory(tc -> new TableCell<EntradaMaterial, Float>() {
            String conversor;
            
            @Override
            protected void updateItem(Float value, boolean empty) {
                super.updateItem(value, empty);
                if (value == null || empty) {
                    setText("");
                } else {
                    conversor = "" + value;
                    conversor = adjustMoney(conversor);
                    setText(mask.Monetaria(conversor));
                }
            }
        }
        );
        
        this.tblEntradaMaterial.setItems(dado);
        
        String conversor = "" + sum;
        
        conversor = adjustMoney(conversor);
        
        txtValorMaterialTotalGeral.setText(mask.Monetaria(conversor));
        conversor = "" + qtdItens;
        
        txtQtdMaterial.setText(conversor);
        
        if (sum
                != 0) {
            if (!txtValorNF.getText().isEmpty()) {
                
                if (sum == mask.Monetaria_To_Double(txtValorNF.getText())) {
                    if (txtNumNF.getText() != null && dtEntrada.getValue() != null && txtFornecedor.getText() != null
                            && txtCNPJ.getText() != null) {
                        btnDarEntrada.setDisable(false);
                    }
                }
            }
        }
        
    }
    
    private void IncompatibilidadeNumero() throws Exception {
        LerMessage ler = new LerMessage();
        Alertas aviso = new Alertas();
        aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.dados.erro"), ler.getMessage("msg.incompatibilidade.numero"));
        
    }
    
}
