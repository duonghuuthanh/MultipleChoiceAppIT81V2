/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplechoiceapp;

import com.dht.pojo.Category;
import com.dht.pojo.Choice;
import com.dht.pojo.Question;
import com.dht.services.CategoryServices;
import com.dht.services.QuestionServices;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Admin
 */
public class FXMLDocumentController implements Initializable {
    @FXML private TextField txtContent;
    @FXML private ComboBox<Category> cbCategories;
    @FXML private TextField txtA;
    @FXML private TextField txtB;
    @FXML private TextField txtC;
    @FXML private TextField txtD;
    @FXML private CheckBox chkA;
    @FXML private CheckBox chkB;
    @FXML private CheckBox chkC;
    @FXML private CheckBox chkD;
    @FXML private TableView<Question> tbQuestions;
    @FXML TextField txtKeyword;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buildTable();
        try {
            // TODO
            cbCategories.getItems().addAll(CategoryServices.getCategories());
            loadQuestions("");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        txtKeyword.textProperty().addListener(evt -> {
            loadQuestions(txtKeyword.getText());
        });
        
        tbQuestions.setRowFactory(et -> {
            TableRow r = new TableRow();
            
            r.setOnMouseClicked(evt -> {
                Question q = tbQuestions.getSelectionModel().getSelectedItem();
                txtContent.setText(q.getContent());
                
                
                try {
                    Category c = CategoryServices.getCategoryById(q.getCategory().getId());
                    cbCategories.getSelectionModel().select(c);

                    List<Choice> choices;

                    choices = QuestionServices.getChoicesByQuestionId(q.getId());
                    txtA.setText(choices.get(0).getContent());
                    txtB.setText(choices.get(1).getContent());
                    txtC.setText(choices.get(2).getContent());
                    txtD.setText(choices.get(3).getContent());

                    chkA.setSelected(choices.get(0).isCorrect());
                    chkB.setSelected(choices.get(1).isCorrect());
                    chkC.setSelected(choices.get(2).isCorrect());
                    chkD.setSelected(choices.get(3).isCorrect());
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
            
            return r;
        });
    }    
    
    public void addQuestionHandler(ActionEvent evt) {
        Question q = new Question(UUID.randomUUID().toString(), 
                txtContent.getText(), cbCategories.getSelectionModel().getSelectedItem());
        
        List<Choice> choices = new ArrayList<>();
        choices.add(new Choice(UUID.randomUUID().toString(), 
                txtA.getText(), q, chkA.isSelected()));
        choices.add(new Choice(UUID.randomUUID().toString(), 
                txtB.getText(), q, chkB.isSelected()));
        choices.add(new Choice(UUID.randomUUID().toString(), 
                txtC.getText(), q, chkC.isSelected()));
        choices.add(new Choice(UUID.randomUUID().toString(), 
                txtD.getText(), q, chkD.isSelected()));
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (QuestionServices.addQuestion(q, choices) == true) {
            alert.setContentText("SUCCESSFUL");
            loadQuestions("");
        } else {
            alert.setContentText("FAILED");
        }
        
        alert.show();
    }
    
    private void buildTable() {
        TableColumn colId = new TableColumn("Mã câu hỏi");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn colContent = new TableColumn("Nội dung câu hỏi");
        colContent.setCellValueFactory(new PropertyValueFactory("content"));
        
        TableColumn colAction = new TableColumn();
        colAction.setCellFactory(evt -> {
            TableCell cell = new TableCell();
            Button btn = new Button("Xóa");
            btn.setOnMouseClicked(et -> {
                Button b = (Button) et.getSource();
                TableCell c = (TableCell) b.getParent();
                Question q = (Question) c.getTableRow().getItem();
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Bạn chắc chắn xóa? Nó sẽ xóa các lựa chọn luôn quan!");
                alert.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        try {
                            if (QuestionServices.deleteQuestion(q.getId()) == true)
                                loadQuestions("");
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                
            });
            
            cell.setGraphic(btn);
            
            return cell;
        });
        
        tbQuestions.getColumns().addAll(colId, colContent, colAction);
    }
    
    private void loadQuestions(String kw) {
        try {
//            tbQuestions.getItems().clear();
            tbQuestions.setItems(FXCollections.observableArrayList(QuestionServices.getQuestions(kw)));
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
