package ir.adrianet.uploaddownloadimage.Views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Callback;

import ir.adrianet.uploaddownloadimage.Core.IService;
import ir.adrianet.uploaddownloadimage.Core.Service.UploadDownloadService;
import ir.adrianet.uploaddownloadimage.General.TypeConvertor;
import ir.adrianet.uploaddownloadimage.General.mFragment;
import ir.adrianet.uploaddownloadimage.MainActivity;
import ir.adrianet.uploaddownloadimage.R;


public class FragShowImage extends mFragment {

    private mFragment backFragment;
    public FragShowImage setBackFragment(mFragment backFragment)
    {
        this.backFragment = backFragment;
        return this;
    }
    private String sha256;
    public FragShowImage setSha256(String sha256)
    {
        this.sha256 = sha256;
        return this;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private View parent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View parent = inflater.inflate(R.layout.frag_show_image, container, false);
        this.parent = parent;

        return parent;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OnStart();
    }

    public void OnStart() {
        parent.findViewById(R.id.btnCloseImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragShowImage.this.onBackPress();
            }
        });


        PhotoView photoView = (PhotoView) parent.findViewById(R.id.imgShow);

        IService<Void,String> service = new IService<Void, String>() {
            @Override
            public void SendRequest(Void req) {
                UploadDownloadService.DownloadImage(sha256,this);
            }

            @Override
            public void OnSucceed(String result) {
                byte[] bytesImage = TypeConvertor.getBytesString(result);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
                photoView.setImageBitmap(bitmap);
                parent.findViewById(R.id.progressAV).setVisibility(View.GONE);

            }

            @Override
            public void OnError(String error, int statusCode) {
                FragShowImage.this.onBackPress();
                Toast.makeText(getContext(),error,Toast.LENGTH_LONG).show();

            }
        };
        service.SendRequest(null);


    }

    @Override
    public void onBackPress() {
        super.onBackPress();
        MainActivity.getGlobal().RemoveFrag(this,backFragment);
    }
}
