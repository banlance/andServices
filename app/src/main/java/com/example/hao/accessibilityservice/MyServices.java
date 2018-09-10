package com.example.hao.accessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class MyServices extends AccessibilityService {
    public String TAG = "ZaneSercices";
    private boolean isCliking=true;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
//        String pagName = event.getPackageName().toString();
//        int eventType = event.getEventType();
//        Log.d(TAG, "onAccessibilityEvent: .");
//        Log.d(TAG, "pagName: =" + pagName);
//        Log.d(TAG, "eventType: " + eventType);
//        switch (eventType) {
//            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
//                break;
//        }
        //event.setEventTime(3500);
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            List<AccessibilityNodeInfo> nodes = rootNode.findAccessibilityNodeInfosByText("评");
            int count = nodes.size();
            AccessibilityNodeInfo node;
            if (count > 0&&isCliking) {
                isCliking=false;
                for (int i = 0; i < count; i++) {
                    node = nodes.get(i);
                    if (node.getText().toString().contains("评")) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //performGlobalAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                         node.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);

                        isCliking=true;
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onServiceConnected() {
        AccessibilityServiceInfo info = getServiceInfo();
        info.packageNames = new String[]{"com.jifen.qukan"};
        setServiceInfo(info);
        super.onServiceConnected();
    }
}
