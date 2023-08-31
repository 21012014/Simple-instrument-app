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
import javafx.scene.control.TextField;
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
 * 21012014, 3 Aug 2022 5:28:30 pm
 */

public class DeleteFX extends Application{
	private BorderPane pane = new BorderPane();
	private Label header = new Label("---- Instrument ----");
	private Label display = new Label();
	private TextArea ta = new TextArea();
	private Label mt = new Label("Choose ID of instrument to delete");
	private TextField id = new TextField();
	private CheckBox confirm = new CheckBox("Confirm");
	private Button delete = new Button("Delete");
	private Button reset = new Button("Reset");
	private Label feedback = new Label();
	
	private VBox top = new VBox();
	private VBox mid = new VBox();
	private HBox h = new HBox();
	private HBox tf = new HBox();
	
	private ArrayList<MusicalInstrument> instruments = new ArrayList<MusicalInstrument>();
	
	private static final int MAX_WIDTH = 360;
	private static final int MAX_HEIGHT = 550;
	
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/instruments";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	
	public void start(Stage stage){ 
		DBUtil.init(JDBC_URL, DB_USERNAME, DB_PASSWORD);
		load();
		doLoad();
		delete.setDisable(true);
		
		EventHandler<ActionEvent> resett = (ActionEvent e) -> reset();
		reset.setOnAction(resett);
		EventHandler<ActionEvent> con = (ActionEvent e) -> conf();
		confirm.setOnAction(con);
		EventHandler<ActionEvent> d = (ActionEvent e) -> delet();
		delete.setOnAction(d);
		
		header.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		header.setTextFill(Color.BLACK);
		feedback.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		feedback.setTextFill(Color.BLACK);
		ta.setPrefSize(150, 200);
		ta.setEditable(false);
		top.getChildren().addAll(header, ta);
		top.setAlignment(Pos.CENTER);
		top.setSpacing(10);
		top.setPadding(new Insets(15,50,5,50));
		
		mt.setFont(Font.font("Verdana", 15));
		mt.setTextFill(Color.BLACK);
		id.setPrefColumnCount(4);
		tf.getChildren().add(id);
		tf.setAlignment(Pos.CENTER);
		delete.setPrefWidth(70);
		reset.setPrefWidth(50);
		h.getChildren().addAll(delete, reset);
		h.setAlignment(Pos.CENTER);
		h.setSpacing(20);
		h.setPadding(new Insets(10,10,10,10));
		mid.getChildren().addAll(mt, tf, confirm, h, feedback);
		mid.setAlignment(Pos.CENTER);
		mid.setSpacing(20);
		mid.setPadding(new Insets(15,10,5,10));
		
		pane.setTop(top);
		pane.setCenter(mid);
		pane.setStyle("-fx-background-color: transparent;");
		
		Scene mainScene = new Scene(pane);
		mainScene.setFill(new LinearGradient(0,0,1,1,true, CycleMethod.NO_CYCLE, new Stop(0, Color.CADETBLUE), new Stop(1, Color.INDIANRED)));
		
		stage.setTitle("Delete Instrument");
		stage.setWidth(MAX_WIDTH);
		stage.setHeight(MAX_HEIGHT);
		stage.setMinHeight(400);
		stage.setMinWidth(500);
		
		stage.setScene(mainScene);
		stage.show();
	}

	

	private void doLoad() {
		  String out = "";
		  int count = 0;
			  
		  out = String.format("%-12s %-18s %-10s \n", "ID", "INSTRUMENT", "CATEGORY");
		  for (MusicalInstrument i : instruments) {
			  out += String.format("%-12s %-18s %-10s \n", i.getId(), i.getName(), i.getCategory());
			  count ++;
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
	
	public void delet() {
		String i = id.getText();
		String deleteSQL = "DELETE FROM instrument WHERE ID = '" + i + "'";
		int rowsDeleted = DBUtil.execSQL(deleteSQL);

		if (rowsDeleted == 1) {
			feedback.setText("Instrument with ID: " + i + " removed!");
		} else {
			feedback.setText("Removal failed!");
		}
	}
	
	public void conf() {
		delete.setDisable(false);
		if (!confirm.isSelected()) {
			delete.setDisable(true);
		}
	}
	
	public void reset() {
		id.setText(""); 
		confirm.setSelected(false);
		delete.setDisable(true);
	}

}
