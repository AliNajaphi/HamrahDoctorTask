package ir.adrianet.uploaddownloadimage.General;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class mAnimation {

    public static void tremor(View rel)
    {
        Animation anim = new TranslateAnimation(0,15,0,0);
        anim.setDuration(50);
        anim.setFillEnabled(false);
        anim.setFillAfter(false);
        anim.setStartOffset(0);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.setRepeatCount(9);
        rel.startAnimation(anim);
    }
}
