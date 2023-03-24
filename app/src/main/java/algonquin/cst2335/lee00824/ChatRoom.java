package algonquin.cst2335.lee00824;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.lee00824.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.lee00824.databinding.ReceiveMessageBinding;
import algonquin.cst2335.lee00824.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ArrayList<ChatMessage> messages ;

    public RecyclerView.Adapter myAdapter;

    ChatRoomViewModel chatModel;

    ChatMessage chat = new ChatMessage("","",false);

    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
    String currentDateAndTime = sdf.format(new Date());
    ChatMessageDAO mDAO;

    Executor thread = Executors.newSingleThreadExecutor();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch( item.getItemId() )
        {
            case R.id.item_1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to delete this message?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0; i < messages.size(); i++) {
                            ChatMessage chat = messages.get(i);
                            messages.remove(chat);
                            thread.execute(() -> {
                                mDAO.deleteMessage(chat);
                            });
                            myAdapter.notifyItemRemoved(i);
                        }}


                });
                builder.setNegativeButton("No", null);
                builder.show();
                break;

            case R.id.item_2:
                // display the about information
                Toast.makeText(this, "Version 1.0, created Juho Lee", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        MessageDatabase db = Room.databaseBuilder(getApplicationContext(),
                MessageDatabase.class, "database-name").build();
        mDAO = db.cmDAO();

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        if(messages == null)
        {
            chatModel.messages.postValue( messages = new ArrayList<>());
            thread.execute(() ->
            {
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database
                runOnUiThread( () ->  binding.recycleView.setAdapter( myAdapter )); //You can then load the RecyclerView
            });
        }

        binding.sendButton.setOnClickListener( click ->{

            String message = binding.textInput.getText().toString();
            boolean sentButton = true;
            chat = new ChatMessage(message, currentDateAndTime, sentButton );
            messages.add(chat);
            thread.execute(() ->
            {
                chat.id = mDAO.insertMessage(chat);
            });
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");

        });

        binding.receiveButton.setOnClickListener( click ->{

            String message = binding.textInput.getText().toString();
            boolean sentButton = false;
            chat = new ChatMessage(message, currentDateAndTime, sentButton );
            messages.add(chat);
            thread.execute(() ->
            {
                chat.id = mDAO.insertMessage(chat);
            });
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

        chatModel.selectedMessage.observe(this, (chat) -> {

            MessageDetailsFragment chatFragment= new MessageDetailsFragment(chat);
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();
            tx.replace(R.id.frame, chatFragment);
            tx.addToBackStack("");
            tx.commit();
        });

    }
    class MyRowHolder extends RecyclerView.ViewHolder {

        public TextView messageText;
        public TextView timeText;

        public MyRowHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(click ->{
                int position = getAbsoluteAdapterPosition();
                ChatMessage selected = messages.get(position);
                chatModel.selectedMessage.postValue(selected);

			/*AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
				builder.setMessage("Do you want to delete the message: " +
						messageText.getText())
				.setTitle("Question:")
				.setNegativeButton("NO",(dialog, cl) -> { })
				.setPositiveButton("YES",(dialog, cl) -> {
					ChatMessage removedMessage = messages.get(position);
					thread.execute(() ->
					{
						removedMessage.id = mDAO.deleteMessage(removedMessage);
					});
					messages.remove(position);
					myAdapter.notifyItemRemoved(position);
					Snackbar.make(messageText,"You deleted message Id# " +
											chat.id,
							Snackbar.LENGTH_LONG)
							.setAction("Undo", clk ->{
								thread.execute(() ->
								{
									removedMessage.id = mDAO.insertMessage(removedMessage);
								});
								messages.add(position,removedMessage);
								myAdapter.notifyItemInserted(position);
							})
							.show();
				})
				.create()
				.show();*/
            });
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
            setSupportActionBar(binding.toolbar);

        };
    }
}