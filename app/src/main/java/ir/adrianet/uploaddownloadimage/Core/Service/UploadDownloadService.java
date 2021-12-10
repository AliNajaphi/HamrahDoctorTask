package ir.adrianet.uploaddownloadimage.Core.Service;

import ir.adrianet.uploaddownloadimage.Core.IService;
import ir.adrianet.uploaddownloadimage.Core.IView;
import ir.adrianet.uploaddownloadimage.Core.Presenter;
import ir.adrianet.uploaddownloadimage.Core.Request.ReqRegister;
import ir.adrianet.uploaddownloadimage.Core.Request.ReqUpload;
import ir.adrianet.uploaddownloadimage.General.Setting;
import ir.adrianet.uploaddownloadimage.MainActivity;
import ir.adrianet.uploaddownloadimage.R;

public class UploadDownloadService {

    private static String CONTROLLER = "image";

    public static void RegisterImage(ReqRegister reqRegister, final IService<ReqRegister, String> iService)
    {
        Presenter.get_global().PostNotResJson(new IView<String>() {
            @Override
            public void OnSucceed(String object) {
                iService.OnSucceed(object);
            }

            @Override
            public void OnError(String error, int statusCode) {
                switch(statusCode) {
                    case 409:
                        iService.OnError(MainActivity.getGlobal().getResources().getString(R.string.file_exist_server),statusCode);
                        break;
                    case 415:
                        iService.OnError(MainActivity.getGlobal().getResources().getString(R.string.file_not_support),statusCode);
                        break;
                    default:
                        iService.OnError(error,statusCode);
                        break;
                }

            }
        }, Setting.getBaseUrl() + CONTROLLER ,reqRegister);
    }

    public static void UploadChunk(String sha256,ReqUpload reqUpload, final IService<ReqUpload, String> iService)
    {
        Presenter.get_global().PostNotResJson(new IView<String>() {
            @Override
            public void OnSucceed(String object) {
                iService.OnSucceed(object);
            }

            @Override
            public void OnError(String error, int statusCode) {
                switch(statusCode) {
                    case 409:
                        iService.OnError(MainActivity.getGlobal().getResources().getString(R.string.chunk_exist_server),statusCode);
                        break;
                    case 404:
                        iService.OnError(MainActivity.getGlobal().getResources().getString(R.string.file_not_exist),statusCode);
                        break;
                    default:
                        iService.OnError(error,statusCode);
                        break;
                }

            }
        }, Setting.getBaseUrl() + CONTROLLER + "/" + sha256 + "/chunks" ,reqUpload);
    }

    public static void DownloadImage(String sha256, final IService<Void, String> iService)
    {
        Presenter.get_global().GetNotResJson(new IView<String>() {
            @Override
            public void OnSucceed(String object) {
                iService.OnSucceed(object);
            }

            @Override
            public void OnError(String error, int statusCode) {
                iService.OnError(error,statusCode);

            }
        }, Setting.getBaseUrl() + CONTROLLER + "/" + sha256);
    }
}
