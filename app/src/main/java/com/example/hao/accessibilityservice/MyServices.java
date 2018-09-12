package com.example.hao.accessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.IntentFilter;
import android.drm.DrmStore;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.webkit.WebView;

import java.util.List;

public class MyServices extends AccessibilityService {
    public String TAG = "ZaneSercices";
    private boolean isCliking = true;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            List<AccessibilityNodeInfo> nodes = rootNode.findAccessibilityNodeInfosByText("评");
            List<AccessibilityNodeInfo> listNodesInfos = rootNode.findAccessibilityNodeInfosByViewId("com.jifen.qukan:id/a9u");
            if (listNodesInfos.size() > 0) {
                int childCount = listNodesInfos.get(0).getChildCount();
                if (childCount > 0) {


                    if (listNodesInfos.get(0).getChild(0).getClassName().equals("android.webkit.WebView")) {
                        if (listNodesInfos.get(0).getChild(0).getChildCount() > 0) {
                            if (null != listNodesInfos.get(0).getChild(0).getChild(0)) {
                                if (listNodesInfos.get(0).getChild(0).getChild(0).getClassName().equals("android.webkit.WebView")) {
                                    listNodesInfos.get(0).getChild(0).getChild(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                                    new Handler().postDelayed(() -> {
                                                if (null != listNodesInfos.get(0).getChild(0).getChild(0)) {
                                                    listNodesInfos.get(0).getChild(0).getChild(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
                                                }
                                            },
                                            3000);


                                }
                            }
                        }
                    }
                }
            }
            int count = nodes.size();
            AccessibilityNodeInfo node;
            if (count > 0 && isCliking) {
                isCliking = false;
                for (int i = 0; i < count; i++) {
                    node = nodes.get(i);
                    if (node.getText().toString().contains("评")) {
                        //performGlobalAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                        node.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);

                        isCliking = true;
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
