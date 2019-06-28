/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import muttlab.MuttLab;
import muttlab.io.InConsole;
import muttlab.io.OutConsole;
import muttlab.io.OutputManager;
import muttlab.matrix.Matrix;
import muttlab.matrix.VectorMatrix;
import muttlab.operator.Command;
import muttlab.operator.FileCommand;
import muttlab.operator.Parser;

/**
 *
 * @author bens
 */
public class FXMLMuttLabController implements Initializable, OutputManager {
    private MuttLab muttlab;
    private Parser parser;
    private Boolean finished;
    private FileChooser chooser;
    private File csvFile;
    private Deque<Matrix> vectorStack;
    
    @FXML
    private TextArea outputText;
    @FXML
    private HBox commands;
    @FXML
    private TextField inputText;
    private Button FileButton;
    @FXML
    private ToggleGroup mmGroup;
    @FXML
    private VBox fileCommands;
    @FXML
    private ToggleGroup ruleGroup;
    @FXML
    private VBox fileOperators;
    @FXML
    private VBox textFields;
    @FXML
    private VBox createOperators;
    @FXML
    private Button fileButton;
    @FXML
    private Label fileName;
    
    @FXML
    private void handleFile(ActionEvent event) throws FileNotFoundException, IOException{
        InputStream is;
        File fileChoice = this.chooser.showOpenDialog(fileButton.getScene().getWindow());
        
        if(fileChoice != null){
            csvFile = fileChoice;
            fileName.setText(fileChoice.getName());
            
            is = new FileInputStream(fileChoice);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            br.lines()
                    .forEach((String x) -> {
                        VectorMatrix v = new VectorMatrix(x);
                        this.muttlab.push(v);
            });
            is.close();
            br.close();
            this.vectorStack = this.muttlab.getStack();
        } else {
            write("No File selected");
        }
    
    }
    
    private void equals(ActionEvent event){
        String command = ((Button)event.getSource()).getText();
        this.parser = this.muttlab.getParser();
        
        Command command1;
        command1 = inputText.getText().isEmpty() ? this.parser.getCommand(command, null) : this.parser.getCommand(command, inputText.getText());
        
        finished = this.muttlab.processCommandGUI(command1);
        if(finished){
            System.exit(0);
        }
        inputText.clear();
        this.write(this.muttlab.toString());
        
    }
    
    private void createSave(ActionEvent event){
        String command = ((Button)event.getSource()).getText();
        
        this.parser = this.muttlab.getParser();
        
        FileCommand fCommand;
        fCommand = this.parser.getFCommand(command);
        float number = 0;
        
        try {
            number = Float.parseFloat(inputText.getText());
        } catch(NumberFormatException e){
            write("No number entered");
        }
        
        if(csvFile != null){
            fCommand.CreateSavefile(csvFile, number);
        } else {
            write("A file has not been loaded");
        }
        
    }

    private void byElement(ActionEvent event){
        String command = ((Button)event.getSource()).getText();
        
        RadioButton selectedButton = (RadioButton) mmGroup.getSelectedToggle();
        String toggle = selectedButton.getId();
        
        this.parser = this.muttlab.getParser();
        
        FileCommand fCommand;
        fCommand = this.parser.getFCommand(command);
        
        Matrix execute = fCommand.execute(vectorStack, toggle);
        
        write(execute.toString());
    }
    
    private void byElementOperator(ActionEvent event){
        String command = ((Button)event.getSource()).getText();
        
        RadioButton selectedButton = (RadioButton) ruleGroup.getSelectedToggle();
        String toggle = selectedButton.getText();
        
        this.parser = this.muttlab.getParser();
        
        FileCommand fCommand;
        fCommand = this.parser.getFCommand(command);
        
        Matrix execute = fCommand.execute(vectorStack, toggle);
        
        write(execute.toString());
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.muttlab = new MuttLab("basic", new OutConsole(), new InConsole(), true);
        this.finished = false;
        this.chooser = new FileChooser();
        this.chooser.getExtensionFilters()
                .add(new ExtensionFilter("CSV files (*.csv)", "*.csv"));
        
        //Get Original CommandWords
        Set<String> commandWords = this.muttlab.getCommandWords();
        
        //Sort Original CommandWords
        List<String> collect = commandWords.stream().sorted().collect(Collectors.toList());
        Collections.sort(collect);
       
        //Create a button for each original command except unknown
        collect.forEach((String s) -> {
            if(!"unknown".equals(s)){
                Button b = new Button(s);
                b.setOnAction((event) -> this.equals(event));
                b.setMinWidth(55);
                b.setStyle("-fx-background-color: #090a0c,"
                        + "linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),"
                        + "linear-gradient(#20262b, #191d22),"
                        + "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
                        + "-fx-background-radius: 5,4,3,5;"
                        + "-fx-background-insets: 0,1,2,0;"
                        + "-fx-text-fill: white;"
                        + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
                        + "-fx-text-fill: linear-gradient(white, #d0d0d0);"
                        + "-fx-font-size: 12px;"
                        + "-fx-font: Tahoma");
                        
                commands.getChildren().add(b);
            }
        });
        
        //Get file processing commands
        Set<String> fileCommandWords = this.muttlab.getFileCommands();
        
        //Sort File Processing Commands
        List<String> collectCommand = fileCommandWords.stream().sorted().collect(Collectors.toList());
        
        collectCommand.forEach((String s) -> {
            if(!"unknown".equals(s)){
                Button b = new Button(s);
                b.setStyle("-fx-background-color: #000000,"
                                    + "linear-gradient(#7ebcea, #2f4b8f),"
                                    + "linear-gradient(#426ab7, #263e75),"
                                    + "linear-gradient(#395cab, #223768);"
                                    + "-fx-background-insets: 0,1,2,3;"
                                    + "-fx-background-radius: 3,2,2,2;"
                                    + "-fx-text-fill: white;"
                                    + "-fx-font-size: 12px;");
                            b.setMinWidth(70);
                            
                switch (s) {
                    case "Add":
                        {
                            b.setOnAction((event) -> this.byElementOperator(event));
                            fileOperators.getChildren().add(b);
                            break;
                        }
                    case "Create * N":
                    case "Create [N]":
                        {
                            b.setOnAction((event) -> this.createSave(event));
                            createOperators.getChildren().add(b);
                            break;
                        }
                    default:
                        {
                            b.setOnAction((event) -> this.byElement(event));
                            fileCommands.getChildren().add(b);
                            break;
                        }
                }
            }
        });
    }

    @Override
    public void prompt() {outputText.appendText("> ");}

    @Override
    public void write(String str) {outputText.setText(str + '\n');}

    @Override
    public void write(Collection<String> messages) {
        messages.forEach((msg) -> { 
            outputText.setText(msg + '\n');
        });
    }

    @Override
    public void write(String[] messages) {
        for (String msg : messages) 
            outputText.setText(msg + '\n');
    }

    @Override
    public void error(String str) { System.err.println(str); }

    @Override
    public void error(String[] messages) {
        for (String msg : messages) 
            System.err.println(msg);
    }
    
    @Override
    public void fatal(String str) { System.err.println(str); }
    
}