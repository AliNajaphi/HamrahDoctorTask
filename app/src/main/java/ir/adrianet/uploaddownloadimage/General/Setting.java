package ir.adrianet.uploaddownloadimage.General;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.Objects;

import ir.adrianet.uploaddownloadimage.R;

public class Setting {

    public static String getBaseUrl()
    {
        return "https://utest.rxmoein.dev/";
    }

    public static int getChunkSize()
    {
        return 50000;//50kb
    }

    public static void SetSplitRecyclerAdapter(RecyclerView recyclerView, RecyclerView.Adapter mAdapter,int split)
    {
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(split,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


}
