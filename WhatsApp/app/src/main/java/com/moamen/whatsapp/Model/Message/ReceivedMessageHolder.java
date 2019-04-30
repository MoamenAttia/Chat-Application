package com.moamen.whatsapp.Model.Message;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.moamen.whatsapp.R;

import java.sql.Timestamp;
import java.util.Date;


public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
    public TextView from, txt, createdAt;

    public ReceivedMessageHolder(View view) {
        super(view);
        from = (TextView) view.findViewById(R.id.text_message_name);
        txt = (TextView) view.findViewById(R.id.text_message_body);
        createdAt = (TextView) view.findViewById(R.id.text_message_time);
    }

    public void bind(Message message) {
        Timestamp ts = new Timestamp(message.getCreatedAt());
        Date date = ts;
        createdAt.setText(date.toString());
        txt.setText(message.getText());
        from.setText(message.getFrom());
    }
}