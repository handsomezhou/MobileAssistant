
package com.handsomezhou.xcontacts.model;

import android.view.View;
import android.widget.LinearLayout;

import com.handsomezhou.xcontacts.R;


/**
 * custom IconButton's value
 * 
 * @author handsomezhou
 */
public class IconButtonValue {

    private Object mTag;// tag
    private int mIconSelectedUnfocused; // selected_unfocused Icon
    private int mIconSelectedFocused;   // selected_focused Icon
    private int mIconUnselected;        // unselected Icon
    private int mText;// text
    /**
     * {@link LinearLayout.LayoutParams}
     */
    private float mWeight;
    private int mBackgroundResource;
    private int mIconVisibility;
	private int mTextVisibility;

	public IconButtonValue(Object tag, int iconSelectedUnfocused, int text) {
        super();
        initViewOption(tag, iconSelectedUnfocused, iconSelectedUnfocused, iconSelectedUnfocused, text);
    }
    
    public IconButtonValue(Object tag, int iconSelectedUnfocused, int iconUnselected, int text) {
        super();
        initViewOption(tag, iconSelectedUnfocused, iconSelectedUnfocused, iconUnselected, text);
    }

    public IconButtonValue(Object tag,int iconSelectedUnfocused, int iconSelectedFocused, 
            int iconUnselected, int text) {
        super();
        initViewOption(tag, iconSelectedUnfocused, iconSelectedFocused, iconUnselected, text);
    }
    
    public Object getTag() {
        return mTag;
    }

    public void setTag(Object tag) {
        mTag = tag;
    }

    public int getIconSelectedUnfocused() {
        return mIconSelectedUnfocused;
    }

    public void setIconSelectedUnfocused(int iconSelectedUnfocused) {
        mIconSelectedUnfocused = iconSelectedUnfocused;
    }
    
    public int getIconSelectedFocused() {
        return mIconSelectedFocused;
    }

    public void setIconSelectedFocused(int iconSelectedFocused) {
        mIconSelectedFocused = iconSelectedFocused;
    }

    public int getIconUnselected() {
        return mIconUnselected;
    }

    public void setIconUnselected(int iconUnselected) {
        mIconUnselected = iconUnselected;
    }

    public int getText() {
        return mText;
    }

    public void setText(int text) {
        mText = text;
    }
    
    public float getWeight() {
 		return mWeight;
 	}

 	public void setWeight(float weight) {
 		mWeight = weight;
 	}
 	
 	public int getBackgroundResource() {
		return mBackgroundResource;
	}

	public void setBackgroundResource(int backgroundResource) {
		mBackgroundResource = backgroundResource;
	}
 	
	public int getIconVisibility() {
		return mIconVisibility;
	}

	public void setIconVisibility(int iconVisibility) {
		mIconVisibility = iconVisibility;
	}

	public int getTextVisibility() {
		return mTextVisibility;
	}

	public void setTextVisibility(int textVisibility) {
		mTextVisibility = textVisibility;
	}

		
 	 private void initViewOption(Object tag,int iconSelectedUnfocused, int iconSelectedFocused, int iconUnselected, int text){
         setTag(tag);
         setIconSelectedUnfocused(iconSelectedUnfocused);
         setIconSelectedFocused(iconSelectedFocused);
         setIconUnselected(iconUnselected);
         setText(text);
         setWeight(1.0f);
         setBackgroundResource(R.color.transparent);
         setIconVisibility(View.VISIBLE);
         setTextVisibility(View.VISIBLE);
     }
 	 
}
