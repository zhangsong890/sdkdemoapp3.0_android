package com.hyphenate.chatuidemo.widget;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.conference.ConferenceActivity;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.media.EMCallSurfaceView;
import com.hyphenate.util.EMLog;
import com.superrtc.sdk.VideoView;

/**
 * Created by lzan13 on 2017/3/27.
 * <p>
 * float window control
 */
public class FloatWindow {
    private static final String TAG = "FloatWindow";

    private Context context;

    private static FloatWindow instance;

    private WindowManager windowManager = null;
    private WindowManager.LayoutParams layoutParams = null;

    private View floatView;
    private ImageView avatarView;
    private EMCallSurfaceView localView;

    private int screenWidth;
    private int floatViewWidth;

    public FloatWindow(Context context) {
        this.context = context;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        screenWidth = point.x;
    }

    public static FloatWindow getInstance(Context context) {
        if (instance == null) {
            instance = new FloatWindow(context);
        }
        return instance;
    }

    /**
     * add float window
     */
    public void addFloatWindow(int callType, String username) { // 0: voice call; 1: video call;
        if (floatView != null) {
            return;
        }
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.END | Gravity.TOP;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.format = PixelFormat.TRANSPARENT;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;

        floatView = LayoutInflater.from(context).inflate(R.layout.em_widget_call_float_window, null);
        windowManager.addView(floatView, layoutParams);
        floatView.post(new Runnable() {
            @Override
            public void run() {
                // Get the size of floatView;
                floatViewWidth = floatView.getWidth();
            }
        });
        avatarView = (ImageView) floatView.findViewById(R.id.iv_avatar);
        EaseUserUtils.setUserAvatar(context, username, avatarView);

        updateFloatWindow(callType);

        floatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ConferenceActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                removeFloatWindow();
            }
        });

        floatView.setOnTouchListener(new View.OnTouchListener() {
            boolean result = false;

            int left;
            int top;
            float startX = 0;
            float startY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        result = false;
                        startX = event.getRawX();
                        startY = event.getRawY();

                        left = layoutParams.x;
                        top = layoutParams.y;

                        EMLog.i(TAG, "startX: " + startX + ", startY: " + startY + ", left: " + left + ", top: " + top);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (Math.abs(event.getRawX() - startX) > 20 || Math.abs(event.getRawY() - startY) > 20) {
                            result = true;
                        }

                        int deltaX = (int) (startX - event.getRawX());

                        layoutParams.x = left + deltaX;
                        layoutParams.y = (int) (top + event.getRawY() - startY);
                        EMLog.i(TAG, "startX: " + (event.getRawX() - startX) + ", startY: " + (event.getRawY() - startY)
                                + ", left: " + left + ", top: " + top);
                        windowManager.updateViewLayout(floatView, layoutParams);
                        break;
                    case MotionEvent.ACTION_UP:
                        smoothScrollToBorder();
                        break;
                }
                return result;
            }
        });
    }

    public void updateFloatWindow(int callType) {
        if (callType == 0) {
            floatView.findViewById(R.id.layout_call_voice).setVisibility(View.VISIBLE);
            floatView.findViewById(R.id.layout_call_video).setVisibility(View.GONE);
        } else {
            setupVideoCallView();
        }
    }

    public boolean isShowing() {
        return floatView != null;
    }

    /**
     * set call surface view
     */
    private void setupVideoCallView() {
        floatView.findViewById(R.id.layout_call_voice).setVisibility(View.GONE);
        floatView.findViewById(R.id.layout_call_video).setVisibility(View.VISIBLE);

        RelativeLayout surfaceLayout = (RelativeLayout) floatView.findViewById(R.id.layout_call_video);

        surfaceLayout.removeAllViews();

        localView = new EMCallSurfaceView(context);
        RelativeLayout.LayoutParams localParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        localView.setZOrderOnTop(false);
        localView.setZOrderMediaOverlay(true);
        surfaceLayout.addView(localView, localParams);

        localView.setScaleMode(VideoView.EMCallViewScaleMode.EMCallViewScaleModeAspectFill);
        EMClient.getInstance().conferenceManager().updateLocalSurfaceView(localView);
    }

    /**
     * 停止悬浮窗
     */
    public void removeFloatWindow() {
        Log.i(TAG, "removeFloatWindow: ");
        if (localView != null) {
            if (localView.getRenderer() != null) {
                localView.getRenderer().dispose();
            }
            localView.release();
            localView = null;
        }
        if (windowManager != null && floatView != null) {
            windowManager.removeView(floatView);
            floatView = null;
        }
    }

    private void smoothScrollToBorder() {
        EMLog.i(TAG, "screenWidth: " + screenWidth + ", floatViewWidth: " + floatViewWidth);
        int splitLine = screenWidth / 2 - floatViewWidth / 2;
        final int left = layoutParams.x;
        final int top = layoutParams.y;
        int targetX;

        if (left < splitLine) {
            // 滑动到最左边
            targetX = 0;
        } else {
            // 滑动到最右边
            targetX = screenWidth - floatViewWidth;
        }

        ValueAnimator animator = ValueAnimator.ofInt(left, targetX);
        animator.setDuration(100)
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (floatView == null) return;

                        int value = (int) animation.getAnimatedValue();
                        EMLog.i(TAG, "onAnimationUpdate, value: " + value);
                        layoutParams.x = value;
                        layoutParams.y = top;
                        windowManager.updateViewLayout(floatView, layoutParams);
                    }
                });
        animator.start();
    }
}
