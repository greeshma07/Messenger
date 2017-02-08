package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextFlow;

public class MyGUI{
	
	final String DEF_WINDOW_COLOR = "pink";
	final String DEF_FONT_SIZE = "size";
	final String DEF_FONT_COLOR = "Color";
	private ScrollPane scrollPane = new ScrollPane();
	
	private TextFlow historyScreen;
	

	private ObservableList<String> windowStyleOptions = 
		    FXCollections.observableArrayList(
		        "pink","blue","green","red","black","orange","gold","silver"
		    );
	private ComboBox<String> windowStyle = new ComboBox<String>(windowStyleOptions);
	
	private ObservableList<String> fontOptions = 
		    FXCollections.observableArrayList(
		        "1","2","3","4","5","6","7","8"
		    );
	private ComboBox<String> fontSize = new ComboBox<String>(fontOptions);

	private ObservableList<String> fontColorOptions = 
		    FXCollections.observableArrayList(
		        "Orange","Blue","Green","Red","Pink","Black","gold","silver"
		    );
	private ComboBox<String> fontColor = new ComboBox<String>(fontColorOptions);
	
	private TextArea chatScreen;
	
	private Button sendBtn;
	private Button clearBtn;
	private ImageView imageView;
	
	public void layoutGUI(BorderPane root) {
		HBox hbox = new HBox();
		VBox vbox = new VBox(10);
		
        imageView = new ImageView();
                
        TextFlow textFlow = new TextFlow();
        textFlow.setPrefWidth(320);
        textFlow.setMaxHeight(595);
        textFlow.getStyleClass().add("textflow");
        textFlow.getStyleClass().add("textarea_margin");
        
        historyScreen =  new TextFlow();
        historyScreen.setPrefWidth(270);
        historyScreen.setPrefHeight(250);
        historyScreen.getStyleClass().add("history"); 
        scrollPane.setContent(historyScreen);
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
                
        chatScreen =  new TextArea();        
        chatScreen.setPrefWidth(270);
        chatScreen.setPrefHeight(200);
        chatScreen.setFont(Font.font(null, FontWeight.NORMAL,15));
        chatScreen.setWrapText(true);
       
        HBox hboxStyle = new HBox();
        windowStyle.getStyleClass().add("combo-box");
        windowStyle.setValue(DEF_WINDOW_COLOR);
        fontSize.setValue(DEF_FONT_SIZE);
        fontColor.setValue(DEF_FONT_COLOR);
        hboxStyle.getChildren().addAll(windowStyle,fontSize,fontColor);
        
        HBox btn = new HBox(15);        
        sendBtn = new Button("Send");
        clearBtn = new Button("Clear");
        sendBtn.setPrefWidth(70);
        sendBtn.setPrefHeight(40);
        clearBtn.setPrefWidth(70);
        clearBtn.setPrefHeight(40);
        btn.getChildren().addAll(sendBtn,clearBtn);
        
        vbox.setPadding(new Insets(15,15,15,15));
        vbox.getChildren().addAll(scrollPane,hboxStyle,chatScreen,btn);
        textFlow.getChildren().add(vbox);
        hbox.getChildren().addAll(imageView,textFlow);
        hbox.setAlignment(Pos.CENTER);
        imageView.setTranslateY(-130);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        root.setCenter(hbox);	
	}
	
	/**
	 * @return the windowStyle
	 */
	public ComboBox<String> getWindowStyle() {
		return windowStyle;
	}


	/**
	 * @return the font
	 */
	public ComboBox<String> getFont() {
		return fontSize;
	}

	/**
	 * @return the fontColor
	 */
	public ComboBox<String> getFontColor() {
		return fontColor;
	}

	/**
	 * @return the historyScreen
	 */
	public TextFlow getHistoryScreen() {
		return historyScreen;
	}

	/**
	 * @return the chatScreen
	 */
	public TextArea getChatScreen() {
		return chatScreen;
	}

	/**
	 * @return the sendBtn
	 */
	public Button getSendBtn() {
		return sendBtn;
	}

	/**
	 * @return the clearBtn
	 */
	public Button getClearBtn() {
		return clearBtn;
	}
	/**
	 * @return the imageView
	 */
	public ImageView getImageView() {
		return imageView;
	}

	/**
	 * @param imageView the imageView to set
	 */
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	
}
