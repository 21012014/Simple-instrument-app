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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
 * 21012014, 30 Jul 2022 11:05:01 pm
 */

public class OrchestraFX extends Application {
	private BorderPane pane = new BorderPane();
	private VBox rightVBox = new VBox();
	private VBox leftVBox = new VBox();
	private HBox topHBox = new HBox();
	private ImageView img = new ImageView("https://cdn.dribbble.com/users/497438/screenshots/2084032/xtyf_1.gif");
	
	private Label header = new Label("Instrument Mangement App");
	private Button bView = new Button();
	private Button bAdd = new Button();
	private Button bDelete = new Button();
	
	private static final int MAX_WIDTH = 520;
	private static final int MAX_HEIGHT = 420;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		bView.setPrefWidth(160);
		bAdd.setPrefWidth(160);
		bDelete.setPrefWidth(160);
		
		bView.setText("View Instruments");
		bAdd.setText("Add New instrument");
		bDelete.setText("Delete Instrument Record");
		
		header.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
		header.setTextFill(Color.BLACK);
		
		rightVBox.getChildren().addAll(bView, bAdd, bDelete);
		rightVBox.setPadding(new Insets(40,0,10,30));
		rightVBox.setSpacing(20);
		rightVBox.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
		topHBox.getChildren().add(header);
		topHBox.setAlignment(Pos.CENTER);
		topHBox.setPadding(new Insets(20,10,20,10));
		topHBox.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
		
		img.setFitWidth(250);
		img.setFitHeight(250);
		img.setY(MAX_HEIGHT);
		img.setX(MAX_WIDTH);
		leftVBox.getChildren().add(img);
		leftVBox.setPadding(new Insets(20,15,0,0));
		leftVBox.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
		
		pane.setTop(topHBox);
		pane.setLeft(rightVBox);
		pane.setRight(leftVBox);
		pane.setStyle("-fx-background-color: transparent;");
		
		EventHandler<ActionEvent> bViewA = (ActionEvent e) -> (new ViewFX()).start(new Stage());
		EventHandler<ActionEvent> bA = (ActionEvent e) -> (new AddFX()).start(new Stage());
		EventHandler<ActionEvent> bD = (ActionEvent e) -> (new DeleteFX()).start(new Stage());
		
		bView.setOnAction(bViewA);
		bAdd.setOnAction(bA);
		bDelete.setOnAction(bD);
		
		Scene mainScene = new Scene(pane);
		mainScene.setFill(new LinearGradient(0,0,1,1,true, CycleMethod.NO_CYCLE, new Stop(0, Color.CADETBLUE), new Stop(1, Color.INDIANRED)));
		
		primaryStage.setTitle("Orchestra's Instrument");
		primaryStage.setWidth(MAX_WIDTH);
		primaryStage.setHeight(MAX_HEIGHT);
		primaryStage.setMinHeight(400);
		primaryStage.setMinWidth(500);
		
		primaryStage.setScene(mainScene);
		primaryStage.show();
		
	}

}
