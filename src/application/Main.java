package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;
import javax.imageio.ImageIO;
 
public class Main extends Application {
	
	Stack<HistoryContent> stack = new Stack<HistoryContent>();
	String path = null;
	public static TextField fnText = new TextField();
	public static TextField lnText = new TextField();
	ToggleGroup group = new ToggleGroup();
	public static RadioButton rb1 = new RadioButton("male");
	public static RadioButton rb2 = new RadioButton("female");
	
	public static ObservableList<String> monthOptions = 
		    FXCollections.observableArrayList(
		        "Jan","Feb","March","April","May","June","July","August","Sep","Oct","Nov","Dec"
		    );					
	public static ComboBox<String> month = new ComboBox<String>(monthOptions);
	public static ObservableList<String> dateOptions = 
		    FXCollections.observableArrayList(
		        "1","2","3","4","5","6","7","8","9","10","11","12"
		    );					
	public static ComboBox<String> date = new ComboBox<String>(dateOptions);
	public static ObservableList<String> yearOptions = 
		    FXCollections.observableArrayList(
		        "1980","1981","1982","1983","1984","1985","1986","1987","1990","1992","1995","1996"
		    );					
	public static ComboBox<String> year = new ComboBox<String>(yearOptions);
	public static CheckBox cb1 = new CheckBox();
	public static CheckBox cb2 = new CheckBox();
	public static CheckBox cb3 = new CheckBox();
	public static CheckBox cb4 = new CheckBox();
	public static CheckBox cb5 = new CheckBox();
	public static CheckBox cb6 = new CheckBox();
	Button openImg = new Button("Open Image");
	Button saveBtn = new Button("Save");
	Button cancelBtn = new Button("Cancel");
	public static ImageView selectedImage = new ImageView();
	Image profImage;
	
		
	@Override
	public void start(Stage primaryStage) throws Exception{
		try {
			String title = "Local Messenger";
			String title2 = "Edit Profile";
			String fn = "First Name:";
			String ln = "Last Name:";
			String gender = "Gender:";
			String dob = "Date of Birth:";
			String hobby = "Hobby:";
			String imageText = "Image:";
			
			rb1.setToggleGroup(group);
			rb2.setToggleGroup(group);
			
			Stage stage = new Stage();	
						
			final FileChooser fileChooser = new FileChooser();	        
			
			MyGUI myGUI = new MyGUI();
			MessengerMethods messengerMethod = new MessengerMethods();
			BorderPane root = new BorderPane();	
			myGUI.layoutGUI(root);
			Scene scene = new Scene(root,500,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			ComboBox<String> windowColor = myGUI.getWindowStyle();
			ComboBox<String> fontSize = myGUI.getFont();
			ComboBox<String> fontColor = myGUI.getFontColor();

			Button sendBtn = myGUI.getSendBtn();
			Button clearBtn = myGUI.getClearBtn();
			
			TextFlow historyScrn = myGUI.getHistoryScreen();
			TextArea textarea = myGUI.getChatScreen();
			TextArea chatSrn = myGUI.getChatScreen();
			
			ImageView imageView = myGUI.getImageView();
			if(DataBaseConnection.isTableExists()){
				String url = DataBaseConnection.getImage();
				System.out.println("url"+url);
				if(!url.equalsIgnoreCase("")){
					BufferedImage bufferedImage = ImageIO.read(new File(url));
					Image image = SwingFXUtils.toFXImage(bufferedImage, null);
					imageView.setImage(image);	
				}
				else{
					Image image = new Image("chat_image.jpg");
					imageView.setImage(image);
				}
			}
			else{
				Image image = new Image("chat_image.jpg");
				imageView.setImage(image);
			}
			
			Platform.runLater(new Runnable() {
	            @Override
	            public void run() {
	            	chatSrn.requestFocus();
	            }
	        });
			
			root.getStyleClass().add("bkgColor");
			windowColor.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					String color;
					root.setStyle("-fx-background-color: "+windowColor.getValue()+";");
					if(fontColor.getValue().equalsIgnoreCase("color"))
						color = "black";
					else
						color = fontColor.getValue();
					messengerMethod.changeHistoryPanelColor(historyScrn,stack,windowColor.getValue(),
							color);					
				}
				
			});	
			
			fontSize.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub		
					messengerMethod.changeFontSize(chatSrn, Integer.parseInt(fontSize.getValue()));
				}				
			});	
			
			fontColor.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					TextArea chatSrn = myGUI.getChatScreen();
					chatSrn.setStyle("-fx-text-fill:" +fontColor.getValue()+";");
					messengerMethod.changeHistoryPanelColor(historyScrn,stack,windowColor.getValue(),
							fontColor.getValue());	
				}
				
			});	
			
			sendBtn.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub	
					String color;
					TextArea chatSrn = myGUI.getChatScreen();
					LocalTime time = LocalTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
					String sendTime = time.format(formatter);
					String text = chatSrn.getText();
					if(fontColor.getValue().equalsIgnoreCase("color"))
						color = "black";
					else
						color = fontColor.getValue();
					if(text !=null && !(text.equalsIgnoreCase("")))
						messengerMethod.sendMessage(historyScrn,stack,sendTime,text,
							windowColor.getValue(),color);
					chatSrn.clear();
				}				
			});	
			
			clearBtn.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub	
					TextArea chatSrn = myGUI.getChatScreen();
					chatSrn.clear();		
				}					
			});
			
			textarea.setOnKeyPressed(new EventHandler<KeyEvent>(){			 
			    @Override
			    public void handle(KeyEvent event) {
			    	String color;
			        if(event.getCode().equals(KeyCode.ENTER)) {
			        	TextArea chatSrn = myGUI.getChatScreen();
						LocalTime time = LocalTime.now();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
						String sendTime = time.format(formatter);
						String text = chatSrn.getText();
						if(fontColor.getValue().equalsIgnoreCase("color"))
							color = "black";
						else
							color = fontColor.getValue();
						if(text !=null && !(text.equalsIgnoreCase("")))
							messengerMethod.sendMessage(historyScrn,stack,sendTime,text,
								windowColor.getValue(),color);
						event.consume();
						chatSrn.clear();
			        }
			    }
			});
			
			imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			     @Override
			     public void handle(MouseEvent event) {
			    	
			    	BorderPane borderPane = new BorderPane();	
			    	
			    	HBox hboxfn = new HBox(70);
			    	HBox hboxln = new HBox(70);
			    	HBox hboxgender = new HBox(90);
			    	HBox hboxdob = new HBox();
			    	HBox hboxhobbyset1 = new HBox(40);
			    	HBox hboxhobbyset2 = new HBox(70);
			    	HBox hboxBtn = new HBox();
			    	HBox hboximage = new HBox(90);
			    	VBox vbox = new VBox(20);
					vbox.setPadding(new Insets(15,15,15,15));
					
					Label labelfn = new Label();
					labelfn.setText(fn);
					fnText.setPrefWidth(300);
					fnText.setPrefHeight(40);
					hboxfn.getChildren().addAll(labelfn,fnText);
					
					Label labelln = new Label();
					labelln.setText(ln);
					lnText.setPrefWidth(300);
					lnText.setPrefHeight(40);
					hboxln.getChildren().addAll(labelln,lnText);
					
					Label labelgender = new Label();
					labelgender.setText(gender);					
					hboxgender.getChildren().addAll(labelgender,rb1,rb2);
					
					Label labeldob = new Label();
					labeldob.setText(dob);
					month.setValue("Jan");
					date.setValue("1");
					year.setValue("1980");
					labeldob.setPadding(new Insets(0,60,0,0));
					month.setMinWidth(102);
					month.setPrefHeight(40);
					date.setMinWidth(98);
					date.setPrefHeight(40);
					year.setMinWidth(102);
					year.setPrefHeight(40);
					hboxdob.getChildren().addAll(labeldob,month,date,year);
					
					Label labelhobby = new Label();
					labelhobby.setText(hobby);
					VBox v = new VBox(20);
					cb1.setText("swimming");
					cb2.setText("running");
					cb3.setText("singing");
					cb4.setText("dancing");
					cb5.setText("reading");
					cb6.setText("listening");
					labelhobby.setPadding(new Insets(0,50,0,0));
					hboxhobbyset1.getChildren().addAll(labelhobby,cb1,cb2,cb3);
					cb4.setTranslateX(128);	
					cb5.setTranslateX(110);	
					cb6.setTranslateX(83);	
					hboxhobbyset2.getChildren().addAll(cb4,cb5,cb6);					
					v.getChildren().addAll(hboxhobbyset1,hboxhobbyset2);
					
					Label labelimage = new Label();
					labelimage.setText(imageText);
					openImg.setPrefWidth(270);
					openImg.setPrefHeight(55);
					hboximage.getChildren().addAll(labelimage,openImg);
										
					saveBtn.setPrefWidth(250);
					saveBtn.setPrefHeight(45);
					cancelBtn.setPrefWidth(250);
					cancelBtn.setPrefHeight(45);
					hboxBtn.getChildren().addAll(saveBtn,cancelBtn);
					
					selectedImage.setFitWidth(140);
					selectedImage.setFitHeight(180);
					Image image = new Image("chat_image.jpg");
					selectedImage.setImage(image);
					
					vbox.getChildren().addAll(hboxfn,hboxln,hboxgender,hboxdob,v,hboximage,
							selectedImage,hboxBtn);
					borderPane.setCenter(vbox);
					borderPane.getStyleClass().add("bkgColor");
					Scene scene = new Scene(borderPane,500,600);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					stage.setTitle(title2);
					stage.setScene(scene);
					try {
						if(DataBaseConnection.isTableExists())
							DataBaseConnection.getRecords();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        stage.show();
			     }
			});
			
			openImg.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                	Stage mainStage = new Stage();
                	fileChooser.setTitle("Open Picture");
                	fileChooser.getExtensionFilters().addAll(
                	         new ExtensionFilter("*.png", "*.jpg", "*.bmp"));
                	File selectedFile = fileChooser.showOpenDialog(mainStage);
                	if (selectedFile != null) {
                		 BufferedImage bufferedImage;
						try {
							bufferedImage = ImageIO.read(selectedFile);
							path = selectedFile.getAbsolutePath();
							profImage = SwingFXUtils.toFXImage(bufferedImage, null);
	                        selectedImage.setImage(profImage);	                        
	                       System.out.println(selectedFile);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                	}                
                }
            });
						
			saveBtn.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					imageView.setImage(profImage);
					char gender = '\0';
					String hobby = "";
					String dob = "";
					try {
						if(rb1.isSelected()){
							gender = rb1.getText().charAt(0);
							System.out.println(gender);
						}
						else if (rb2.isSelected()){
							gender = rb2.getText().charAt(0);
							System.out.println(gender);
						}
						if(cb1.isSelected())
							hobby = hobby+"1";
						if(cb2.isSelected())
							hobby = hobby+"2";
						if(cb3.isSelected())
							hobby = hobby+"3";
						if(cb4.isSelected())
							hobby = hobby+"4";
						if(cb5.isSelected())
							hobby = hobby+"5";
						if(cb6.isSelected())
							hobby = hobby+"6";
						dob = month.getValue()+"-"+date.getValue()+"-"+year.getValue();
						stage.close();	
						DataBaseConnection.getConnection();
						DataBaseConnection.createTable();	
						System.out.println(path);
						if(DataBaseConnection.hasRecords()){
							if(null!=path)
								DataBaseConnection.updateRecord(fnText.getText(),lnText.getText(),
									gender,dob,hobby,path);	
							else{
								String url = DataBaseConnection.getImage();
								if(!url.equalsIgnoreCase("")){
									BufferedImage bufferedImage = ImageIO.read(new File(url));
									Image image = SwingFXUtils.toFXImage(bufferedImage, null);
									imageView.setImage(image);									
								}
								else{
									Image image = new Image("chat_image.jpg");
									imageView.setImage(image);
								}	
								DataBaseConnection.updateRecord(fnText.getText(),lnText.getText(),
										gender,dob,hobby,url);
							}								
						}
						else{	
							if(null==path){
								Image image = new Image("chat_image.jpg");
								imageView.setImage(image);
							}
							DataBaseConnection.insertRecord(fnText.getText(),lnText.getText(),
										gender,dob,hobby,path);							
						}
							
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}					
			});
			
			cancelBtn.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub	
					path=null;
					stage.close();		
				}					
			});
			
			primaryStage.setTitle(title);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);		
	}
	
	public void updateFields(String firstName,String lastName,char gender,String dob,String hobby,
			String imageUrl){
		fnText.setText(firstName);
		lnText.setText(lastName);
		if(gender == 'm'){
			rb1.setSelected(true);
		}
		else if(gender == 'f'){
			rb2.setSelected(true);
		}
		String[] str_array = dob.split("-");
		String m = str_array[0]; 
		String d = str_array[1];
		String y = str_array[2];
		month.setValue(m);
		date.setValue(d);
		year.setValue(y);
		for(int i=0;i<hobby.length();i++){
			switch(hobby.charAt(i)){
			case '1':	cb1.setSelected(true);
					 	break;
			case '2':	cb2.setSelected(true);
						break;
			case '3':   cb3.setSelected(true);
						break;
			case '4':   cb4.setSelected(true);
						break;
			case '5':   cb5.setSelected(true);
						break;
			case '6':   cb6.setSelected(true);
						break;
			}
		}
		try {
			if(!imageUrl.equalsIgnoreCase("")){
				BufferedImage bufferedImage = ImageIO.read(new File(imageUrl));
				Image image = SwingFXUtils.toFXImage(bufferedImage, null);
				selectedImage.setImage(image);
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
