package com.moamen.whatsapp.Model.Message;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.moamen.whatsapp.R;


public class SentMessageHolder extends RecyclerView.ViewHolder {
    public TextView txt, createdAt;

    public SentMessageHolder(View view) {
        super(view);
        createdAt = (TextView) view.findViewById(R.id.text_message_time);
        txt = (TextView) view.findViewById(R.id.text_message_body);
    }

    public void bind(Message message) {
        createdAt.setText(String.valueOf(message.getCreatedAt()));
        txt.setText(message.getText());
    }
}