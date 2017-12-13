package com.hyphenate.chatuidemo.widget;

import android.content.Context;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow2.LayoutChatRow;
import com.hyphenate.easeui.widget.chatrow2.LayoutPresenter;

/**
 * Created by zhangsong on 17-12-11.
 */

public class RecallPresenter extends LayoutPresenter {
    private static final String TAG = "EaseRecallPresenter";

    public RecallPresenter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void onBubbleClick(EMMessage message) {
    }

    @Override
    protected LayoutChatRow onCreateChatRow(Context context, int layoutId) {
        return new RecallChatRow(context, layoutId);
    }
}
