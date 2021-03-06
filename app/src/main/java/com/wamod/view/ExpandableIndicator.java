/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.wamod.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.wamod.Resources;

public class ExpandableIndicator extends ImageView {

    private boolean mExpanded;
    private boolean mIsDefaultDirection = true;

    public ExpandableIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isInEditMode()) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final int res = getDrawableResourceId(mExpanded);
            setImageResource(res);
        } else {
            setImageDrawable(getContext().getResources().getDrawable(getDrawableResourceIdLegacy(mExpanded)));
        }
    }

    public void setExpanded(boolean expanded) {
        if (expanded == mExpanded) return;
        mExpanded = expanded;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final int res = getDrawableResourceId(!mExpanded);
            final AnimatedVectorDrawable avd = (AnimatedVectorDrawable) getContext().getDrawable(res).getConstantState().newDrawable();
            setImageDrawable(avd);
            //avd.forceAnimationOnUI();
            avd.start();
        } else {
            setImageDrawable(getContext().getResources().getDrawable(getDrawableResourceIdLegacy(mExpanded)));
        }
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    /** Whether the icons are using the default direction or the opposite */
    public void setDefaultDirection(boolean isDefaultDirection) {
        mIsDefaultDirection = isDefaultDirection;
    }

    private int getDrawableResourceId(boolean expanded) {
        if (mIsDefaultDirection) {
            return expanded ? Resources.getDrawable("ic_volume_collapse_animation")
                            : Resources.getDrawable("ic_volume_expand_animation");
        } else {
            return expanded ? Resources.getDrawable("ic_volume_expand_animation")
                            : Resources.getDrawable("ic_volume_collapse_animation");
        }
    }

    private int getDrawableResourceIdLegacy(boolean expanded) {
        if (mIsDefaultDirection) {
            return expanded ? Resources.getDrawable("ic_volume_collapse_legacy")
                    : Resources.getDrawable("ic_volume_expand_legacy");
        } else {
            return expanded ? Resources.getDrawable("ic_volume_expand_legacy")
                    : Resources.getDrawable("ic_volume_collapse_legacy");
        }
    }
}