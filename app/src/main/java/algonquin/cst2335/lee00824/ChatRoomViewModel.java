package algonquin.cst2335.lee00824;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ChatRoomViewModel extends ViewModel {

    public MutableLiveData<ArrayList<ChatMessage>> messages;

    public ChatRoomViewModel() {
        messages = new MutableLiveData<>();
        messages.setValue(new ArrayList<>());
    }

    public void addMessage(ChatMessage message) {
        ArrayList<ChatMessage> currentMessages = messages.getValue();
        currentMessages.add(message);
        messages.postValue(currentMessages);
    }
}

