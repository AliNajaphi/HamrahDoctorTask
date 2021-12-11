package ir.adrianet.uploaddownloadimage.Views;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.adrianet.uploaddownloadimage.Views.Model.UploadModel;

public class AdapterUpload extends RecyclerView.Adapter<AdapterUpload.MyViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        _RelUpload_Item VIEW;

        public MyViewHolder(_RelUpload_Item view) {
            super(view);
            VIEW = view;
        }
    }

    List<UploadModel> Items;
    FragMain fragMain;
    public AdapterUpload(List<UploadModel> Items,FragMain fragMain) {
        this.Items = Items;
        this.fragMain = fragMain;
    }


    @Override
    public AdapterUpload.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(new _RelUpload_Item(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(AdapterUpload.MyViewHolder holder, int position) {
        holder.VIEW.OnStart(Items.get(position),fragMain);
    }

    public List<UploadModel> getItems() {
        return Items;
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

}
