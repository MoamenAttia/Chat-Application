package com.moamen.whatsapp.Model.Message;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.moamen.whatsapp.R;

import java.sql.Timestamp;
import java.util.Date;


public class SentMessageHolder extends RecyclerView.ViewHolder {
    public TextView txt, createdAt;

    public SentMessageHolder(View view) {
        super(view);
        createdAt = (TextView) view.findViewById(R.id.text_message_time);
        txt = (TextView) view.findViewById(R.id.text_message_body);
    }

    public void bind(Message message) {
        Timestamp ts = new Timestamp(message.getCreatedAt());
        Date date = ts;
        createdAt.setText(date.toString());
        txt.setText(message.getText());
    }
}