package com.easemob.redpacket.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import com.easemob.redpacket.R;
import com.easemob.redpacketsdk.constant.RPConstant;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow2.LayoutChatRow;

/**
 * Created by zhangsong on 17-12-11.
 */

public class RandomPacketChatRow extends LayoutChatRow {
    private static final String TAG = "RandomPacketChatRow";

    private TextView mTvGreeting;

    public RandomPacketChatRow(Context context, int layoutId) {
        super(context, layoutId);
        keepFontSize();
    }

    @Override
    protected void onViewInflate(View v) {
        mTvGreeting = (TextView) v.findViewById(R.id.tv_money_greeting);
    }

    @Override
    protected void onViewSetup(EMMessage message) {
        String greetings = message.getStringAttribute(RPConstant.EXTRA_RED_PACKET_GREETING, "");
        mTvGreeting.setText(greetings);
    }

    @Override
    protected void onViewUpdate(EMMessage message) {
    }

    private void keepFontSize() {
        Resources res = getContext().getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }
}
