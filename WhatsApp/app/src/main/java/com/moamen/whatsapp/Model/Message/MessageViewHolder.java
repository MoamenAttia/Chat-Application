package com.moamen.whatsapp.Model.Message;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.moamen.whatsapp.R;


public class MessageViewHolder extends RecyclerView.ViewHolder {
    public TextView from, txt;

    public MessageViewHolder(View view) {
        super(view);
        from = (TextView) view.findViewById(R.id.from_text_view);
        txt = (TextView) view.findViewById(R.id.msg_text_view);
    }

}