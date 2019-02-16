package aaa.bbb.ccc.crawler.presentation.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aaa.bbb.ccc.crawler.R;
import aaa.bbb.ccc.crawler.domain.model.PageOfSite;

public class PageOfSiteRecyclerViewAdapter extends RecyclerView.Adapter<PageOfSiteRecyclerViewAdapter.ViewHolder> {
    private List<PageOfSite> pageOfSites = new ArrayList<>();
    private OnItemClickListener  listener;
    public interface OnItemClickListener {
        void click(PageOfSite pageOfSite);
    }

    public PageOfSiteRecyclerViewAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<PageOfSite> pageOfSites) {
        this.pageOfSites = pageOfSites;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_page, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolde, int i) {
        viewHolde.bind(pageOfSites.get(i));
    }

    @Override
    public int getItemCount() {
        return pageOfSites.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView url;
        TextView countOfImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            url = itemView.findViewById(R.id.url);
            time = itemView.findViewById(R.id.time);
            countOfImage = itemView.findViewById(R.id.count_of_image);
        }

        public void bind(PageOfSite pageOfSite) {

            itemView.setOnClickListener(view -> {
                if(listener!=null){
                    listener.click(pageOfSites.get(getAdapterPosition()));
                }
            });
            url.setText(pageOfSite.getAddress());
            time.setText(pageOfSite.getTime().toString());
            countOfImage.setText(pageOfSite.getImages().size() + "");
        }
    }
}
