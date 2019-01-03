package stef.view;

import java.lang.reflect.Method;

/**
 *
 * @author StevenDrea
 */
public class MenuItem {

    private Object obj;
    private String label;
    private String target;
    private boolean isExitItem;

    public MenuItem(String label) {
        this(label, null, null);
    }

    public MenuItem(String label, Object obj, String target) {
        this.label = label;
        this.obj = obj;
        this.target = target;
    }

    public String getLabel() {
        return label;
    }

    void invoke() {
        if (target == null) {
            return;
        }
        try {
            Method method = obj.getClass().getMethod(target);
            method.invoke(obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    boolean isExitItem() {
        return isExitItem;
    }

    void setExitItem(boolean isExitItem) {
        this.isExitItem = isExitItem;
    }
}
