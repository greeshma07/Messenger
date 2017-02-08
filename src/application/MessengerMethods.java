package application;

import java.util.Stack;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MessengerMethods implements MessengerInterface{
	
	@Override
	public void changeHistoryPanelColor(TextFlow historyScrn,Stack<HistoryContent> stack,String windowColor,
			String fontColor) {
		// TODO Auto-generated method stub		
		HistoryContent historyContent = new HistoryContent();
		historyScrn.getChildren().clear();
		for(int i=0;i<stack.size();i++){
			historyContent = stack.get(i);
			Text time = new Text();
			time.setText(historyContent.getLocalTime()+"\n");
			Text name = new Text();
			name.setFill(Paint.valueOf(windowColor));
			name.setText(historyContent.getName());
			Text msg = new Text();
			msg.setText(" "+historyContent.getMessage()+"\n");
			msg.setFill(Paint.valueOf(fontColor));
			historyScrn.getChildren().addAll(time,name,msg);	
		}
	}

	@Override
	public void sendMessage(TextFlow historyScrn,Stack<HistoryContent> stack,
			String sendTime, String text, String windowColor, String textColor) {
		// TODO Auto-generated method stub
		HistoryContent historyContent = new HistoryContent();
		Text time = new Text();
		time.setText(sendTime+"\n");
		Text userName = new Text();
		userName.setFill(Paint.valueOf(windowColor));
		userName.setText(historyContent.getName());
		Text msg = new Text();
		msg.setText(" "+text+"\n");
		msg.setFill(Paint.valueOf(textColor));
		historyContent.setLocalTime(sendTime);
		historyContent.setMessage(text);
		stack.push(historyContent);		
		historyScrn.getChildren().addAll(time,userName,msg);		
	}

	@Override
	public void changeFontSize(TextArea chatSrn, int value) {
		// TODO Auto-generated method stub
		switch(value){
		case 1:	chatSrn.setFont(Font.font(null, FontWeight.NORMAL, value + 12));
				break;
		case 2: chatSrn.setFont(Font.font(null, FontWeight.NORMAL, value + 17));
				break;
		case 3: chatSrn.setFont(Font.font(null, FontWeight.NORMAL, value + 22));
				break;
		case 4:	chatSrn.setFont(Font.font(null, FontWeight.NORMAL, value + 27));
				break;
		case 5: chatSrn.setFont(Font.font(null, FontWeight.NORMAL, value + 32));
				break;
		case 6: chatSrn.setFont(Font.font(null, FontWeight.NORMAL, value + 37));
				break;
		case 7:	chatSrn.setFont(Font.font(null, FontWeight.NORMAL, value + 42));
				break;
		case 8: chatSrn.setFont(Font.font(null, FontWeight.NORMAL, value + 47));
				break;
		}				
	}	
}
