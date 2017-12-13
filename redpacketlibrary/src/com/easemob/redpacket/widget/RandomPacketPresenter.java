package com.easemob.redpacket.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow2.LayoutChatRow;
import com.hyphenate.easeui.widget.chatrow2.LayoutPresenter;

/**
 * Created by zhangsong on 17-12-11.
 */

public class RandomPacketPresenter extends LayoutPresenter {
    private static final String TAG = "RandomPacketPresenter";

    public RandomPacketPresenter(Context context, @LayoutRes int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void onBubbleClick(EMMessage message) {
    }

    @Override
    protected LayoutChatRow onCreateChatRow(Context context, @LayoutRes final int layoutId) {
        return new RandomPacketChatRow(context, layoutId);
    }
}
