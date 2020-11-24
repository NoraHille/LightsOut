package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Optional;

public class LightsOutApplication extends Application {

	private LightsOutButton[][] lights;
	private boolean[][] lastPattern;
	private Button btnReset;
	private static final int NUM_OF_ROWS = 5;
	private static final int NUM_OF_COLS = 5;

	@Override
	public void init() throws Exception {

		lights = new LightsOutButton[NUM_OF_ROWS][NUM_OF_COLS];
		lastPattern = new boolean[NUM_OF_ROWS][NUM_OF_COLS];

		for (int i = 0; i < lights.length; i++) {
			for (int j = 0; j < lights[i].length; j++) {
				//lights[i][j] = new LightsOutButton(i, j);
				lights[i][j] = new LightsOutButton(i, j, false); // alle Lichter an
				lastPattern[i][j] = lights[i][j].isSelected();
				lights[i][j].setOnAction(new LightsOutListener(lights[i][j]));
			}
		}

		btnReset = new Button("Reset");
		btnReset.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Alert dialog = new Alert(AlertType.CONFIRMATION, "Soll das ursprüngliche Muster "
						+ "wieder hergestellt werden?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
				dialog.setHeaderText("Noch einmal?");
				Optional<ButtonType> result = dialog.showAndWait();

				if (result.get() == ButtonType.YES) {
					resetPattern();
				}
				else if (result.get() == ButtonType.NO){
					newPattern();
				}
			}
		});


	}

	private void newPattern() {

		for (int i = 0; i < lights.length; i++) {
			for (int j = 0; j < lights[i].length; j++) {
				lights[i][j].setSelected(Math.random() > 0.5);
				lastPattern[i][j] = lights[i][j].isSelected();
			}
		}
	}

	protected void resetPattern() {
		for (int i = 0; i < lastPattern.length; i++) {
			for (int j = 0; j < lastPattern[i].length; j++) {
				lights[i][j].setSelected(lastPattern[i][j]);
			}
		}

	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Lights out puzzle game!");
		BorderPane root = new BorderPane();

		GridPane center = createLightsPane();
		HBox bottom = createBottomPane();

		root.setCenter(center);
		root.setBottom(bottom);

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("lights_out.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private HBox createBottomPane() {
		HBox hbox = new HBox();
		hbox.getStyleClass().add("hbox");

		hbox.getChildren().add(btnReset);

		return hbox;
	}

	private GridPane createLightsPane() {
		GridPane pane = new GridPane();
		pane.getStyleClass().add("grid-pane");

		for (int i = 0; i < lights.length; i++) {
			for (int j = 0; j < lights[i].length; j++) {
				pane.add(lights[i][j], j, i);
			}
		}

		return pane;
	}

	class LightsOutListener implements EventHandler<ActionEvent> {

		private LightsOutButton light;

		public LightsOutListener(LightsOutButton light) {
			this.light = light;
		}

		@Override
		public void handle(ActionEvent event) {
			int row = light.getRow();
			int col = light.getCol();

			//oberhalb
			if (row > 0) {
				lights[row-1][col].toggle();
			}

			//links
			if (col > 0) {
				lights[row][col-1].toggle();
			}

			//unten
			if (row < lights.length-1) {
				lights[row+1][col].toggle();
			}

			//rechts
			if (col < lights[0].length-1) {
				lights[row][col+1].toggle();
			}


		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
