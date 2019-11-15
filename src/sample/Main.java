package sample;

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    TextField januaryField = new TextField();
    TextField febField = new TextField();
    TextField marchField = new TextField();
    TextField aprilField = new TextField();
    Button submitButton = new Button("Submit");
    MenuBar menuBar = new MenuBar();

    GridPane gridPane = new GridPane();

    float getJan;
    float getFeb;
    float getMarch;
    float getApril;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");

        Menu fileMenu = new Menu("File");
        Menu viewMenu = new Menu("View");
        menuBar.getMenus().addAll(fileMenu, viewMenu);
        MenuItem newMenu = new MenuItem("New");
        MenuItem exitItem = new MenuItem("Exit");

        MenuItem barChart = new MenuItem("BarChart");
        MenuItem pieChart = new MenuItem("PieChart");
        MenuItem lineChart = new MenuItem("LineChart");


        fileMenu.getItems().addAll(newMenu, new SeparatorMenuItem(), exitItem);
        viewMenu.getItems().addAll(barChart, pieChart, lineChart);


        Label januaryLabel = new Label("January:");
        Label febLabel = new Label("February:");
        Label marchLabel = new Label("March:");
        Label aprilLabel = new Label("April:");


        //adding hint to textfields

        januaryField.setPromptText("Enter amount in Inches");
        febField.setPromptText("Enter amount in Inches");
        marchField.setPromptText("Enter amount in Inches");
        aprilField.setPromptText("Enter amount in Inches");

        gridPane.add(januaryLabel, 0, 0);
        gridPane.add(januaryField, 1, 0);
        gridPane.add(febLabel, 0, 1);
        gridPane.add(febField, 1, 1);
        gridPane.add(marchLabel, 0, 2);
        gridPane.add(marchField, 1, 2);
        gridPane.add(aprilLabel, 0, 3);
        gridPane.add(aprilField, 1, 3);

//GridPane Alignment
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));


        submitButton.setAlignment(Pos.CENTER);

//Checks if textfields are empty or filled and sets View to disabled or enabled
        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(januaryField.textProperty(),
                        febField.textProperty(),
                        marchField.textProperty(),
                        aprilField.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (januaryField.getText().isEmpty()
                        || febField.getText().isEmpty()
                        || marchField.getText().isEmpty()
                        || aprilField.getText().isEmpty());
            }

        };
        viewMenu.disableProperty().bind(bb);


        //I couldnt add the submit button to bind for some reason


        /////////////////





        ///forcing textfields to be numeric only

        januaryField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    januaryField.setText(oldValue);
                }
            }
        });
        febField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    febField.setText(oldValue);
                }
            }
        });
        marchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    marchField.setText(oldValue);
                }
            }
        });
        aprilField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    aprilField.setText(oldValue);
                }
            }
        });


        VBox vBox = new VBox(5, gridPane, submitButton);
        vBox.setAlignment(Pos.CENTER);
        VBox vBox1 = new VBox(menuBar, vBox);


        submitButton.setOnAction(actionEvent -> {
            getJan = Float.parseFloat(januaryField.getText());
            getFeb = Float.parseFloat(febField.getText());
            getMarch = Float.parseFloat(marchField.getText());
            getApril = Float.parseFloat(aprilField.getText());

            BarChart(primaryStage, getJan, getFeb, getMarch, getApril);


        });

        //setting actions to menu items

        exitItem.setOnAction(actionEvent -> {
            System.exit(0);
        });

        newMenu.setOnAction(actionEvent -> {
            try {

                start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        barChart.setOnAction(actionEvent -> {
            BarChart(primaryStage, getJan, getFeb, getMarch, getApril);
        });

        pieChart.setOnAction(actionEvent -> {
            PieChart(primaryStage, getJan, getFeb, getMarch, getApril);
        });

        lineChart.setOnAction(actionEvent -> {
            LineChart(primaryStage, getJan, getFeb, getMarch, getApril);
        });

        primaryStage.setScene(new Scene(vBox1, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    public void BarChart(Stage primaryStage, float x1, float x2, float x3, float x4) {
        final CategoryAxis xAxis = new CategoryAxis(); // we are gonna plot against time
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Months");

        yAxis.setLabel("Rainfall");


        XYChart.Series series = new XYChart.Series();

        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
        series.getData().add(new XYChart.Data("January", x1));
        series.getData().add(new XYChart.Data("February", x2));
        series.getData().add(new XYChart.Data("March", x3));
        series.getData().add(new XYChart.Data("April", x4));

        barChart.setTitle("Bar Chart");
        VBox vBox = new VBox(menuBar, barChart);

        Scene scene = new Scene(vBox);
        barChart.getData().add(series);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void PieChart(Stage primaryStage, float x1, float x2, float x3, float x4) {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("January", x1),
                        new PieChart.Data("February", x2),
                        new PieChart.Data("March", x3),
                        new PieChart.Data("April", x4));


        PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Rainfall");
        VBox vBox = new VBox(menuBar, chart);
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public void LineChart(Stage primaryStage, float x1, float x2, float x3, float x4) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Rainfall");
        xAxis.setLabel("Months");
        //creating the chart
        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

        lineChart.setTitle("Line Chart");

        XYChart.Series series = new XYChart.Series();


        series.getData().add(new XYChart.Data("January", x1));
        series.getData().add(new XYChart.Data("February", x2));
        series.getData().add(new XYChart.Data("March", x3));
        series.getData().add(new XYChart.Data("April", x4));

        VBox vBox = new VBox(menuBar, lineChart);
        lineChart.getData().add(series);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}