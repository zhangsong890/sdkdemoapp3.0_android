package com.easemob.redpacket.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.easemob.redpacket.R;
import com.easemob.redpacketsdk.constant.RPConstant;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow2.LayoutChatRow;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by zhangsong on 17-12-11.
 */

public class RedPacketChatRow extends LayoutChatRow {
    private static final String TAG = "RedPacketChatRow";

    private TextView mTvGreeting;
    private TextView mTvSponsorName;
    private TextView mTvPacketType;

    public RedPacketChatRow(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void onViewInflate(View v) {
        mTvGreeting = (TextView) v.findViewById(R.id.tv_money_greeting);
        mTvSponsorName = (TextView) v.findViewById(R.id.tv_sponsor_name);
        mTvPacketType = (TextView) v.findViewById(R.id.tv_packet_type);
    }

    @Override
    protected void onViewSetup(EMMessage message) {
        String sponsorName = message.getStringAttribute(RPConstant.EXTRA_SPONSOR_NAME, "");
        String greetings = message.getStringAttribute(RPConstant.EXTRA_RED_PACKET_GREETING, "");
        mTvGreeting.setText(greetings);
        mTvSponsorName.setText(sponsorName);
        String packetType = message.getStringAttribute(RPConstant.MESSAGE_ATTR_RED_PACKET_TYPE, "");
        if (!TextUtils.isEmpty(packetType) && TextUtils.equals(packetType, RPConstant.GROUP_RED_PACKET_TYPE_EXCLUSIVE)) {
            mTvPacketType.setVisibility(VISIBLE);
            mTvPacketType.setText(R.string.exclusive_red_packet);
        } else {
            mTvPacketType.setVisibility(GONE);
        }
    }

    @Override
    protected void onViewUpdate(EMMessage message) {
    }
}
