package com.hyphenate.chatuidemo.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.widget.chatrow2.LayoutChatRow;

/**
 * Created by zhangsong on 17-12-11.
 */

public class CallChatRow extends LayoutChatRow {
    private static final String TAG = "VoiceCallChatRow";

    private TextView contentView;

    public CallChatRow(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void onViewInflate(View v) {
        contentView = (TextView) v.findViewById(com.hyphenate.easeui.R.id.tv_chatcontent);
    }

    @Override
    protected void onViewSetup(EMMessage message) {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        contentView.setText(txtBody.getMessage());
    }

    @Override
    protected void onViewUpdate(EMMessage message) {
    }
}
