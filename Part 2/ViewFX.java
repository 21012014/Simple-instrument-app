/**
 * 
 */
package graded_assignment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
 * 21012014, 3 Aug 2022 5:23:14 pm
 */

public class ViewFX extends Application{
	private BorderPane pane = new BorderPane();
	private Label cat = new Label("---- Categories ----");
	private Label display = new Label();
	private TextArea ta = new TextArea();
	private Button load = new Button("Load"); 
	private CheckBox wood = new CheckBox("WoodWind");
	private CheckBox string = new CheckBox("String");
	private CheckBox brass = new CheckBox("Brass");
	
	private VBox topp = new VBox();
	private HBox top = new HBox();
	private VBox mid = new VBox();
	private VBox bot = new VBox();
	
	private ArrayList<MusicalInstrument> instruments = new ArrayList<MusicalInstrument>();
	
	private static final int MAX_WIDTH = 700;
	private static final int MAX_HEIGHT = 450;
	
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/instruments";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	
	public void start(Stage stage){ 
		DBUtil.init(JDBC_URL, DB_USERNAME, DB_PASSWORD);
		load();
		
		EventHandler<ActionEvent> handleCheckbox = (ActionEvent e) -> chooseInstrument();
		wood.setOnAction(handleCheckbox);
		string.setOnAction(handleCheckbox);
		brass.setOnAction(handleCheckbox);
		
		wood.setFont(Font.font("Verdana", 16));
		wood.setTextFill(Color.BLACK);
		string.setFont(Font.font("Verdana", 16));
		string.setTextFill(Color.BLACK);
		brass.setFont(Font.font("Verdana", 16));
		brass.setTextFill(Color.BLACK);
		
		cat.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
		top.getChildren().addAll(wood, string, brass);
		top.setAlignment(Pos.CENTER);
		top.setSpacing(10);
		topp.getChildren().addAll(cat, top);
		topp.setAlignment(Pos.CENTER);
		topp.setSpacing(15);
		topp.setPadding(new Insets(15,10,5,10));
		topp.setAlignment(Pos.CENTER);
		topp.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
		
		load.setPrefWidth(100);
		ta.setPrefSize(450, 250);
		ta.setEditable(false);
		
		mid.getChildren().addAll(display, ta);
		mid.setSpacing(5);
		mid.setPadding(new Insets(5,10,10,10));
		mid.setAlignment(Pos.CENTER);
		mid.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
		
		bot.getChildren().addAll(load);
		bot.setSpacing(5);
		bot.setPadding(new Insets(5,5,15,5));
		bot.setAlignment(Pos.CENTER);
		bot.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
		
		pane.setTop(topp);
		pane.setCenter(mid);
		pane.setBottom(bot);
		pane.setStyle("-fx-background-color: transparent;");

		EventHandler<ActionEvent> l = (ActionEvent e) -> doLoad();
		load.setOnAction(l);
		
		Scene mainScene = new Scene(pane);
		mainScene.setFill(new LinearGradient(0,0,1,1,true, CycleMethod.NO_CYCLE, new Stop(0, Color.CADETBLUE), new Stop(1, Color.INDIANRED)));
		
		stage.setTitle("View All Instruments");
		stage.setWidth(MAX_WIDTH);
		stage.setHeight(MAX_HEIGHT);
		stage.setMinHeight(400);
		stage.setMinWidth(500);
		
		stage.setScene(mainScene);
		stage.show();
	}

	private void chooseInstrument() {
		
	}

	private void doLoad() {
		  String out = "";
		  int count = 0;
			  
		  out = String.format("%-8s %-14s %-14s %-15s %18s %15s \n", "ID", "INSTRUMENT", "CATEGORY", "INFO", "LAST SERVICED DATE", "MESSAGE");
		  for (MusicalInstrument i : instruments) {
			  if (i instanceof Strings && string.isSelected()) {
				  Strings s = (Strings)i;
				  out += String.format("%-8s %-14s %-14s %-15s %-18s    %s \n", i.getId(), i.getName(), i.getCategory(), s.getNumOfStrings() + " Strings", s.getLatestServiceDate(), i.getReminder());
				  count ++;
			  } else if (i instanceof Woodwind && wood.isSelected()) {
				  Woodwind w = (Woodwind)i;
				  if (w.isReed()) {
					  out += String.format("%-8s %-14s %-14s %-15s %-18s    %s \n", i.getId(), i.getName(), i.getCategory(), "Is a reed", w.getLatestServiceDate(), i.getReminder());
				  } else {
					  out += String.format("%-8s %-14s %-14s %-15s %-18s    %s \n", i.getId(), i.getName(), i.getCategory(), "Not a reed", w.getLatestServiceDate(), i.getReminder());
				  } count ++;
			  } else if (i instanceof Brass && brass.isSelected()) {
				  Brass b = (Brass)i;
				  out += String.format("%-8s %-14s %-14s %-15s %-18s    %s \n", i.getId(), i.getName(), i.getCategory(), b.getWeight() + " kg", b.getLatestServiceDate(), i.getReminder());
				  count ++;
			  }
		  } 
		  display.setText("Total number of Instruments: " + count);
		  display.setFont(Font.font("Verdana", 13));
		  ta.setFont(Font.font("Consolas", 14));
		  
		  ta.setText(out);
	}
	
	private void load() {
		  try {
				String sql = "SELECT * FROM instrument LEFT JOIN latest_service_date ON instrument.ID = latest_service_date.ID;";
				ResultSet rs = DBUtil.getTable(sql);
				
				while (rs.next()) { 
					String id = rs.getString("ID");
					String name = rs.getString("Name");  
					String cat = rs.getString("Category");
					int reed = rs.getInt("ReedInstrument");
					double weight = rs.getDouble("Weight");
					int numString = rs.getInt("NumStrings");
					String dateS = rs.getString("LatestDateServiced");
					LocalDate date = null;
					if (dateS != null) {
						date = LocalDate.parse(dateS);
					}
					
					if (cat.equals("Woodwind")) {
						if (reed == 1 && dateS != null) {
							instruments.add(new Woodwind(id, name, cat, date, true));													
						} else if (reed == 0 && dateS != null){
							instruments.add(new Woodwind(id, name, cat, date, false));
						} else if (reed == 1 && dateS == null) {    // check if date is null
							instruments.add(new Woodwind(id, name, cat, null, true));													
						} else if (reed == 0 && dateS == null){     // check if date is null
							instruments.add(new Woodwind(id, name, cat, null, false));
						}
					} else if (cat.equals("String")) {
						if (dateS != null) {
							instruments.add(new Strings(id, name, cat, date, numString));													
						} else {    // check if date is null
							instruments.add(new Strings(id, name, cat, null, numString));
						}
					} else if (cat.equals("Brass")) {
						if (dateS != null) {
							instruments.add(new Brass(id, name, cat, date, weight));													
						} else {    // check if date is null
							instruments.add(new Brass(id, name, cat, null, weight));
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	  }

}
