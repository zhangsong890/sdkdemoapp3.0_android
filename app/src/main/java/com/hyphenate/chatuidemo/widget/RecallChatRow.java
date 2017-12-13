package com.hyphenate.chatuidemo.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.easeui.widget.chatrow2.LayoutChatRow;

/**
 * Created by zhangsong on 17-12-11.
 */

public class RecallChatRow extends LayoutChatRow {
    private static final String TAG = "RecallChatRow";

    private TextView contentView;

    public RecallChatRow(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void onViewInflate(View v) {
        contentView = (TextView) v.findViewById(R.id.text_content);
    }

    @Override
    protected void onViewSetup(EMMessage message) {
        String messageStr = null;
        if (message.direct() == EMMessage.Direct.SEND) {
            messageStr = String.format(getContext().getString(R.string.msg_recall_by_self));
        } else {
            messageStr = String.format(getContext().getString(R.string.msg_recall_by_user), message.getFrom());
        }
        contentView.setText(messageStr);
    }

    @Override
    protected void onViewUpdate(EMMessage message) {
    }
}
