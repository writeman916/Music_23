package com.framgia.music_23.utils;

import android.support.annotation.IntDef;

@IntDef({Tab.TAB_HOME, Tab.TAB_SINGER, Tab.TAB_GENRES})
public @interface Tab {
    int TAB_HOME = 0;
    int TAB_SINGER = 1;
    int TAB_GENRES = 2;
}
