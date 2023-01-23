/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pricingtool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author bradl
 */
public class PricingTool extends Application {
    
    String priceListText;
    TextArea priceListLabel;
    ArrayList<String> pricesArrayList;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        priceListLabel = new TextArea("LilCPAP is watching you...");
        VBox main = new VBox();
        HBox col2 = new HBox();
        HBox col3 = new HBox();
        HBox col1 = new HBox();
        HBox header = new HBox();
        TextField keywordInput = new TextField();
        keywordInput.setMinWidth(100); 
        CheckBox requireKeywordsBox = new CheckBox();
        requireKeywordsBox.setText("require in title");
        Label headerText = new Label("EBAY SCRAPER");
        Button btn = new Button();
        TextField input = new TextField();
        input.setMinWidth(100);
        btn.setText("Search Listings");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle (ActionEvent event){
                SearchResults search;
                btn.setText("Searching");
                if(requireKeywordsBox.isSelected() == true){
                    search = new SearchResults(input.getText().trim(), true, keywordInput.getText());
                }else{
                    search = new SearchResults(input.getText().trim(), false, keywordInput.getText());
                }
                try {
                    search.searchEbay();
                    ArrayList<Item> items = search.getItemList();
                    priceListText = "RESULTS: " + items.size()
                                  + "     "
                                  + "AVERAGE PRICE: " + "$" +(int)search.getAveragePrice()
                                  + "\n"
                                  + "TIME FRAME: " + search.getTimeFrame()
                                  + "\n";
                    for (Iterator<Item> it = items.iterator(); it.hasNext();) {
                        Item item = it.next();
                        priceListText = priceListText + 
                                        "\n$" + item.getPrice() +
                                        "   " + item.getDate() + 
                                        "   " + item.getName();
                        
                        priceListLabel.setText(priceListText);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PricingTool.class.getName()).log(Level.SEVERE, null, ex);
                    priceListLabel.setText("!ERROR!");
                }
                btn.setText("Search Listings");
            }
        });
        
        StackPane root = new StackPane();
        root.setId("root");
        
        main.setAlignment(Pos.CENTER);
        main.setSpacing(25);
        main.getChildren().add(header);
        main.getChildren().add(col1);
        main.getChildren().add(col3);
        main.getChildren().add(col2);
        header.getChildren().add(headerText);
        header.setAlignment(Pos.CENTER);
        col1.setAlignment(Pos.CENTER);
        col1.setSpacing(25);
        col1.getChildren().addAll(input, btn);
        col2.setAlignment(Pos.CENTER);
        col2.getChildren().add(priceListLabel);
        col3.setAlignment(Pos.CENTER);
        col3.setSpacing(25);
        col3.getChildren().addAll(keywordInput, requireKeywordsBox);
        root.getChildren().add(main);
        
        Scene scene = new Scene(root, 500, 350);
        scene.getStylesheets().add("Style.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ebay Sold Product Scraper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
