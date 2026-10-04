package com.conversa.v1;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class ConversaAccessibilityService extends AccessibilityService {

    private static final String TAG = "CONVERSA_V1";

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        System.out.println(
                TAG + " | Accessibility Service CONNECTED"
        );
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (event == null) {
            return;
        }

        String packageName =
                event.getPackageName() != null
                        ? event.getPackageName().toString()
                        : "unknown";

        int eventType = event.getEventType();

        System.out.println(
                TAG +
                " | EVENT=" +
                eventType +
                " | PACKAGE=" +
                packageName
        );

        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED ||
            eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED ||
            eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED ||
            eventType == AccessibilityEvent.TYPE_VIEW_FOCUSED) {

            AccessibilityNodeInfo root =
                    getRootInActiveWindow();

            if (root != null) {

                System.out.println(
                        TAG + " | Reading current UI tree..."
                );

                readNodeTree(root, 0);

                root.recycle();
            }
        }
    }

    private void readNodeTree(
            AccessibilityNodeInfo node,
            int depth) {

        if (node == null) {
            return;
        }

        String indentation = "";

        for (int i = 0; i < depth; i++) {
            indentation += "  ";
        }

        CharSequence text = node.getText();
        CharSequence description =
                node.getContentDescription();

        CharSequence className =
                node.getClassName();

        boolean clickable =
                node.isClickable();

        boolean enabled =
                node.isEnabled();

        boolean focused =
                node.isFocused();

        boolean visible =
                node.isVisibleToUser();

        StringBuilder output = new StringBuilder();

        output.append(TAG)
                .append(" | ")
                .append(indentation)
                .append("TYPE=")
                .append(className);

        if (text != null && text.length() > 0) {
            output.append(" | TEXT=\"")
                    .append(text)
                    .append("\"");
        }

        if (description != null &&
                description.length() > 0) {

            output.append(" | DESC=\"")
                    .append(description)
                    .append("\"");
        }

        output.append(" | CLICKABLE=")
                .append(clickable)

                .append(" | ENABLED=")
                .append(enabled)

                .append(" | FOCUSED=")
                .append(focused)

                .append(" | VISIBLE=")
                .append(visible);

        System.out.println(output);

        int childCount = node.getChildCount();

        for (int i = 0; i < childCount; i++) {

            AccessibilityNodeInfo child =
                    node.getChild(i);

            if (child != null) {

                readNodeTree(
                        child,
                        depth + 1
                );

                child.recycle();
            }
        }
    }

    @Override
    public void onInterrupt() {

        System.out.println(
                TAG + " | Accessibility Service INTERRUPTED"
        );
    }
}
