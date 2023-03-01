package algonquin.cst2335.lee00824;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.lee00824.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.lee00824.databinding.ReceiveMessageBinding;
import algonquin.cst2335.lee00824.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

        ActivityChatRoomBinding binding;
        ArrayList<ChatMessage> messages ;

        public RecyclerView.Adapter myAdapter;

        ChatMessage chat = new ChatMessage("","",false);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
        String currentDateandTimendTime = sdf.format(new Date());

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            chat = new ViewModelProvider(this).get(ChatMessage.class);

            messages = chat.messages.getValue();

            if(messages == null)
            {
                chat.messages.postValue( messages = new ArrayList<ChatMessage>());
            }

            binding.sendButton.setOnClickListener( click ->{

                String message = binding.textInput.getText().toString();
                boolean sentButton = true;
                chat = new ChatMessage(message,currentDateandTimendTime, sentButton );
                messages.add(chat);
                myAdapter.notifyItemInserted(messages.size()-1);
                binding.textInput.setText("");

            });

            binding.receiveButton.setOnClickListener( click ->{

                String message = binding.textInput.getText().toString();
                boolean sentButton = false;
                chat = new ChatMessage(message,currentDateandTimendTime, sentButton );
                messages.add(chat);
                myAdapter.notifyItemInserted(messages.size()-1);
                binding.textInput.setText("");

            });

            binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {

                @NonNull
                @Override
                public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    if (viewType == 0) {
                        SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                        return new MyRowHolder(binding.getRoot());
                    } else {
                        ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater());
                        return new MyRowHolder(binding.getRoot());
                    }
                }

                @Override
                public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                    ChatMessage chatMessage = messages.get(position);
                    holder.messageText.setText(chatMessage.getMessage());
                    holder.timeText.setText(chatMessage.getTimeSent());
                }

                @Override
                public int getItemCount() {
                    return messages.size();
                }

                public int getItemViewType(int position){
                    ChatMessage chatMessage = messages.get(position);
                    if (chatMessage.isSentButton()) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });

            binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        }
        class MyRowHolder extends RecyclerView.ViewHolder {

            public TextView messageText;
            public TextView timeText;

            public MyRowHolder(@NonNull View itemView) {
                super(itemView);

                messageText = itemView.findViewById(R.id.message);
                timeText = itemView.findViewById(R.id.time);
            }
        }
    }


