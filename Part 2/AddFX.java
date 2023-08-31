/**
 * 
 */
package graded_assignment;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * I declare that this code was written by me.
 * I will not copy or allow others to copy my code.
 * I understand that copying code is considered as plagiarism.
 *
 * 21012014, 3 Aug 2022 5:27:04 pm
 */

public class AddFX extends Application{
	private BorderPane pane = new BorderPane();
	private Label header = new Label("-- Instrument's Details --");
	private Label lId = new Label("ID : ");
	private Label lName = new Label("Name : ");
	private Label lCat = new Label("Category :");
	private Label check = new Label();
	private Label feedback = new Label();
	private TextField id = new TextField();
	private TextField name = new TextField();
	private TextField info = new TextField();
	
	private RadioButton wood = new RadioButton("Woodwind");
	private RadioButton string = new RadioButton("String");
	private RadioButton brass = new RadioButton("Brass");
	private RadioButton truee = new RadioButton("True");
	private RadioButton falsee = new RadioButton("False");
	private CheckBox confirm = new CheckBox("Confirm");
	
	private HBox topHBox = new HBox();
	private HBox one = new HBox();
	private HBox two = new HBox();
	private HBox three = new HBox();
	private HBox four = new HBox();
	private VBox all = new VBox();
	private HBox bot = new HBox();
	
	private Button add = new Button("Add");
	private Button reset = new Button("Reset");
	
	ToggleGroup group = new ToggleGroup();
	ToggleGroup pick = new ToggleGroup();
	
	private static final int MAX_WIDTH = 360;
	private static final int MAX_HEIGHT = 450;
	
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/instruments";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	
	public void start(Stage stage){
		DBUtil.init(JDBC_URL, DB_USERNAME, DB_PASSWORD);
		add.setDisable(true);
		
		ToggleGroup group = new ToggleGroup();
		wood.setToggleGroup(group);
		string.setToggleGroup(group);
		brass.setToggleGroup(group);
				
		truee.setToggleGroup(pick);
		falsee.setToggleGroup(pick);
		
		EventHandler<ActionEvent> buttons = (ActionEvent e) -> pop();
		wood.setOnAction(buttons);
		string.setOnAction(buttons);
		brass.setOnAction(buttons);
		
		EventHandler<ActionEvent> resett = (ActionEvent e) -> reset();
		reset.setOnAction(resett);
		
		EventHandler<ActionEvent> con = (ActionEvent e) -> conf();
		confirm.setOnAction(con);
		
		EventHandler<ActionEvent> addd = (ActionEvent e) -> insert();
		add.setOnAction(addd);
	
		
		// Style
		header.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		header.setTextFill(Color.BLACK);
		confirm.setTextFill(Color.BLACK);
		wood.setFont(Font.font("Verdana", 12));
		wood.setTextFill(Color.BLACK);
		string.setFont(Font.font("Verdana", 12));
		string.setTextFill(Color.BLACK);
		brass.setFont(Font.font("Verdana", 12));
		brass.setTextFill(Color.BLACK);
		truee.setFont(Font.font("Verdana", 12));
		truee.setTextFill(Color.BLACK);
		falsee.setFont(Font.font("Verdana", 12));
		falsee.setTextFill(Color.BLACK);
		lId.setFont(Font.font("Verdana", 12));
		lId.setTextFill(Color.BLACK);
		lName.setFont(Font.font("Verdana", 12));
		lName.setTextFill(Color.BLACK);
		lCat.setFont(Font.font("Verdana", 12));
		lCat.setTextFill(Color.BLACK);
		check.setFont(Font.font("Verdana", 12));
		check.setTextFill(Color.BLACK);
		
		
		// Adjust
		id.setPrefColumnCount(10);
		name.setPrefColumnCount(10);
		info.setPrefColumnCount(5);
		topHBox.getChildren().add(header);
		topHBox.setAlignment(Pos.CENTER);
		topHBox.setPadding(new Insets(30,10,5,10));
		one.getChildren().addAll(lId, id);
		one.setSpacing(30);
		one.setAlignment(Pos.CENTER);
		two.getChildren().addAll(lName, name);
		two.setSpacing(10);
		two.setAlignment(Pos.CENTER);
		three.getChildren().addAll(lCat, wood, string, brass);
		three.setSpacing(15);
		three.setAlignment(Pos.CENTER);
		four.setSpacing(15);
		four.setAlignment(Pos.CENTER);
		add.setPrefWidth(50);
		reset.setPrefWidth(50);
		bot.getChildren().addAll(add, reset);
		bot.setSpacing(25);
		bot.setAlignment(Pos.CENTER);
		bot.setPadding(new Insets(5,10,10,10));
		all.getChildren().addAll(one, two, three, four, confirm, bot, feedback);
		all.setSpacing(25);
		all.setAlignment(Pos.CENTER);
		all.setPadding(new Insets(5,10,10,10));
		
		pane.setTop(topHBox);
		pane.setCenter(all);
		pane.setStyle("-fx-background-color: transparent;");
		
		Scene mainScene = new Scene(pane);
		mainScene.setFill(new LinearGradient(0,0,1,1,true, CycleMethod.NO_CYCLE, new Stop(0, Color.CADETBLUE), new Stop(1, Color.INDIANRED)));
		
		stage.setTitle("Add New Instrument");
		stage.setWidth(MAX_WIDTH);
		stage.setHeight(MAX_HEIGHT);
		stage.setMinHeight(400);
		stage.setMinWidth(300);
		
		stage.setScene(mainScene);
		stage.show();
	}
	
	public void pop() {
		four.getChildren().clear();
		info.setText("");
		if (wood.isSelected()) {
			check.setText("Is it a reed instrument : ");
			four.getChildren().addAll(check, truee, falsee);
		} else if (string.isSelected()) {
			check.setText("Number of strings : ");
			four.getChildren().addAll(check, info);
		} else if (brass.isSelected()) {
			check.setText("Weight : ");
			four.getChildren().addAll(check, info);
		}
	}
	
	public void insert() {
		
		String insertSql = "";
		String i = id.getText();
		String n = name.getText();
		String in = info.getText();
		
		if (wood.isSelected()) {
			if (truee.isSelected()) {
				  insertSql = "INSERT INTO instrument(ID, Name, Category, ReedInstrument) VALUES('" + i + "', '" + n + "', 'Woodwind', 1)";
			  } else {
				  insertSql = "INSERT INTO instrument(ID, Name, Category, ReedInstrument) VALUES('" + i + "', '" + n + "', 'Woodwind', 0)";	
			  }
		} else if (string.isSelected()) {
			insertSql = "INSERT INTO instrument(ID, Name, Category, NumStrings) VALUES('" + i + "', '" + n + "', 'String', " + in + ")";	
		} else if (brass.isSelected()) {
			insertSql = "INSERT INTO instrument(ID, Name, Category, Weight) VALUES('" + i + "', '" + n + "', 'Brass', " + in + ")";
		}
		
		int rowsAdded = DBUtil.execSQL(insertSql);
		if (rowsAdded == 1) {
			feedback.setText("Instrument with ID " + i + " added!");
		} else {
			feedback.setText("Adding failed!");
		}	
	}
	
	public void conf() {
		add.setDisable(false);
		if (!confirm.isSelected()) {
			add.setDisable(true);
		}
	}
	
	public void reset() {
		id.setText("");
		name.setText("");
		info.setText("");
		four.getChildren().clear();
		confirm.setSelected(false);
		add.setDisable(true);
	}
	

}
