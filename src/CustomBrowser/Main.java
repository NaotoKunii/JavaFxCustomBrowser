package CustomBrowser;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application{

    private WebEngine engine;
    private TextField urlBox;

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Custom Browser");

        // 子レイアウトの設定
        BorderPane borderPane = new BorderPane();
        borderPane.setLayoutX(10);
        borderPane.setLayoutY(10);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);

        // ブラウザ部分
        WebView view = new WebView();
        engine = view.getEngine();
        engine.load("http://google.co.jp/");
        view.setPrefSize(1200, 900);
        borderPane.setCenter(view);

        // ページのロードが終了した際の処理
        Worker worker = engine.getLoadWorker();
        worker.stateProperty().addListener((ov, oldState, newState) -> {
            if(newState == Worker.State.SUCCEEDED) {
                String url = engine.getLocation();
                urlBox.setText(url);
            }
        });

        // 水平ボックス
        HBox hbox = new HBox(10);
        hbox.setPrefHeight(40);
        hbox.setAlignment(Pos.TOP_CENTER);
        borderPane.setTop(hbox);

        // テキスト入力
        urlBox = new TextField();
        urlBox.setPrefWidth(700);
        hbox.getChildren().add(urlBox);

        urlBox.setOnAction(event -> loadUrl());

        // ボタン
        Button button = new Button("Open");
        hbox.getChildren().add(button);

        button.setOnAction(event -> loadUrl());

        stage.show();
    }

    private void loadUrl(){
        String url = urlBox.getText();
        if(url != null && !url.trim().isEmpty()){
            engine.load(url);
        }
    }
}