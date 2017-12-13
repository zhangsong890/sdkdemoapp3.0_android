package com.easemob.redpacket.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.easemob.redpacket.R;
import com.easemob.redpacketsdk.constant.RPConstant;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow2.LayoutChatRow;

/**
 * Created by zhangsong on 17-12-11.
 */

public class RedPacketAckChatRow extends LayoutChatRow {
    private static final String TAG = "RedPacketAckChatRow";

    private TextView mTvMessage;

    public RedPacketAckChatRow(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void onViewInflate(View v) {
        mTvMessage = (TextView) v.findViewById(R.id.ease_tv_money_msg);
    }

    @Override
    protected void onViewSetup(EMMessage message) {
        String currentUser = EMClient.getInstance().getCurrentUser();
        String fromUser = message.getStringAttribute(RPConstant.EXTRA_RED_PACKET_SENDER_NAME, "");//红包发送者
        String toUser = message.getStringAttribute(RPConstant.EXTRA_RED_PACKET_RECEIVER_NAME, "");//红包接收者
        String senderId;
        if (message.direct() == EMMessage.Direct.SEND) {
            if (message.getChatType().equals(EMMessage.ChatType.GroupChat)) {
                senderId = message.getStringAttribute(RPConstant.EXTRA_RED_PACKET_SENDER_ID, "");
                if (senderId.equals(currentUser)) {
                    mTvMessage.setText(R.string.msg_take_red_packet);
                } else {
                    mTvMessage.setText(String.format(getContext().getResources().getString(R.string.msg_take_someone_red_packet), fromUser));
                }
            } else {
                mTvMessage.setText(String.format(getContext().getResources().getString(R.string.msg_take_someone_red_packet), fromUser));
            }
        } else {
            mTvMessage.setText(String.format(getContext().getResources().getString(R.string.msg_someone_take_red_packet), toUser));
        }
    }

    @Override
    protected void onViewUpdate(EMMessage message) {
    }
}
