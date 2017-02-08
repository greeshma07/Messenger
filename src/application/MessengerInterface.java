package application;

import java.util.Stack;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;

public interface MessengerInterface {	
	
	public void changeHistoryPanelColor(TextFlow historyScrn,Stack<HistoryContent> stack,String windowColor,
			String fontColor);
	public void sendMessage(TextFlow historyScrn,Stack<HistoryContent> stack,String sendTime,
			String text,String windowColor, String textColor);
	public void changeFontSize(TextArea chatSrn, int value);
}
