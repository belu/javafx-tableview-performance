package asobject;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AsObjectMain extends Application {

	private static final int ROWS = 50_000;

	private final ObservableList<AsObjectModel> data = FXCollections.observableArrayList();

	@Override
	public void start(final Stage stage) throws Exception {
		initializeData();

		final Scene scene = new Scene(content(), 800, 600);
		stage.setTitle("JavaFX TableView Performance Demo");
		stage.setScene(scene);
		stage.show();
	}

	private final Random rand = new Random();

	private void initializeData() {
		final List<AsObjectModel> generatedData = IntStream.rangeClosed(1, ROWS)
				.mapToObj(i -> new AsObjectModel(i, rand.nextDouble())).collect(Collectors.toList());
		data.addAll(generatedData);
	}

	private Parent content() throws Exception {
		final TableView<AsObjectModel> tableView = new TableView<>(data);
		tableView.getColumns().addAll(columns());

		final Callback<TableView<AsObjectModel>, Boolean> origSortPolicy = tableView.getSortPolicy();
		tableView.setSortPolicy(new Callback<TableView<AsObjectModel>, Boolean>() {

			@Override
			public Boolean call(final TableView<AsObjectModel> param) {
				final long startT = System.nanoTime();

				final Boolean result = origSortPolicy.call(param);

				final long deltaT = System.nanoTime() - startT;
				System.out.println("Sort took " + NANOSECONDS.toMillis(deltaT) + " millis");

				return result;
			}
		});

		return tableView;
	}

	private List<TableColumn<AsObjectModel, ? extends Object>> columns() {
		final List<TableColumn<AsObjectModel, ?>> columns = new ArrayList<>();

		final TableColumn<AsObjectModel, Integer> idSlowColumn = new TableColumn<>("id (slow)");
		idSlowColumn.setCellValueFactory(cell -> cell.getValue().anInt().asObject());
		columns.add(idSlowColumn);

		final TableColumn<AsObjectModel, Integer> idFast = new TableColumn<>("id (fast)");
		idFast.setCellValueFactory(cell -> cell.getValue().anIntAsObject());
		columns.add(idFast);

		final TableColumn<AsObjectModel, Double> deltaSlow = new TableColumn<>("delta (slow)");
		deltaSlow.setCellValueFactory(cell -> cell.getValue().aDouble().asObject());
		columns.add(deltaSlow);

		final TableColumn<AsObjectModel, Double> deltaFast = new TableColumn<>("delta (fast)");
		deltaFast.setCellValueFactory(cell -> cell.getValue().aDoubleAsObject());
		columns.add(deltaFast);

		return columns;
	}

	public static void main(final String[] args) {
		launch(args);
	}
}
