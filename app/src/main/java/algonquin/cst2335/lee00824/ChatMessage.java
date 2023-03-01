package algonquin.cst2335.lee00824;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ChatMessage extends ViewModel {

	private String message;
	private String timeSent;
	private boolean isSentButton;

	public MutableLiveData<ArrayList<ChatMessage>> messages = new MutableLiveData<ArrayList<ChatMessage>>();

	public ChatMessage() {
	}

	public ChatMessage(String m, String t, boolean sent) {
		message = m;
		timeSent = t;
		isSentButton = sent;
	}

	public String getMessage() {
		return message;
	}

	public String getTimeSent() {
		return timeSent;
	}

	public boolean isSentButton() {
		return isSentButton;
	}
}
