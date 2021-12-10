package ir.adrianet.uploaddownloadimage.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ir.adrianet.uploaddownloadimage.Core.IService;
import ir.adrianet.uploaddownloadimage.Core.Request.ReqUpload;
import ir.adrianet.uploaddownloadimage.Core.Service.UploadDownloadService;
import ir.adrianet.uploaddownloadimage.General.Setting;
import ir.adrianet.uploaddownloadimage.General.TypeConvertor;
import ir.adrianet.uploaddownloadimage.MainActivity;
import ir.adrianet.uploaddownloadimage.R;
import ir.adrianet.uploaddownloadimage.Views.Model.UploadModel;

public class _RelUpload_Item extends RelativeLayout {

    public _RelUpload_Item(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout._rel_upload_item, this, true);
    }

    ProgressBar uploadProgress;
    TextView txtPercent;

    public void OnStart(UploadModel uploadModel,AdapterUpload adapterUpload,int position,FragMain fragMain)
    {
        ((ImageView)findViewById(R.id.imgUpload_Item)).setImageBitmap(GetCompressBitmap(uploadModel.getBitmap()));
        findViewById(R.id.RelUploadItem).getLayoutParams().height = GetHeight();
        uploadProgress = findViewById(R.id.uploadProgress);
        txtPercent = findViewById(R.id.txtPercent);

        if (uploadModel.isFinish())
        {
            uploadProgress.setVisibility(GONE);
            txtPercent.setVisibility(GONE);
            findViewById(R.id.imgFinishUpload).setVisibility(VISIBLE);
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.getGlobal().AddFrag(new FragShowImage().setSha256(uploadModel.getSha256()).setBackFragment(fragMain));
                }
            });
        }
        else
        {

            findViewById(R.id.imgFinishUpload).setVisibility(GONE);
            txtPercent.setVisibility(VISIBLE);
            uploadProgress.setVisibility(VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                uploadProgress.setProgress(uploadModel.getIndexNewUpload(),true);
            else
                uploadProgress.setProgress(uploadModel.getIndexNewUpload());

            uploadProgress.setMax(uploadModel.getUploadList().size()-1);

            if (uploadProgress.getMax() != 0) {
                int percent = (uploadModel.getIndexNewUpload() * 100) / uploadProgress.getMax();
                txtPercent.setText(percent + "%");
            }
            UploadChunks(uploadModel,adapterUpload,position);
        }
    }

    private void UploadChunks(UploadModel uploadModel,AdapterUpload adapterUpload,int position)
    {
        IService<ReqUpload,String> service = new IService<ReqUpload, String>() {
            @Override
            public void SendRequest(ReqUpload req) {
                UploadDownloadService.UploadChunk(uploadModel.getSha256(),req,this);
            }

            @Override
            public void OnSucceed(String result) {
                if (uploadModel.getIndexNewUpload() < (uploadModel.getUploadList().size() -1))
                    uploadModel.setIndexNewUpload(uploadModel.getIndexNewUpload() + 1);
                else
                    uploadModel.setFinish(true);


                adapterUpload.notifyItemChanged(position);
            }

            @Override
            public void OnError(String error, int statusCode) {
                Toast.makeText(getContext(),error,Toast.LENGTH_LONG).show();
                adapterUpload.getItems().remove(position);
                adapterUpload.notifyDataSetChanged();
            }
        };
        service.SendRequest(uploadModel.getUploadList().get(uploadModel.getIndexNewUpload()));


    }

    private Bitmap GetCompressBitmap(Bitmap bitmap)
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        MainActivity.getGlobal().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels/3;
        int height = (width * bitmap.getHeight()) / bitmap.getWidth();
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    private int GetHeight()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        MainActivity.getGlobal().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels - getResources().getDimensionPixelOffset(R.dimen._40sdp);
        return  (int)(width/3);
    }



}
