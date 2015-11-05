package kz.growit.altynorda.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import kz.growit.altynorda.R;
import kz.growit.altynorda.models.Agents;


public class ListAgentAdapter extends RecyclerView.Adapter<ListAgentAdapter.AgentsViewHolder> {

    private ArrayList<Agents> agents;
    private Agents temp;
    public ListAgentAdapter(ArrayList<Agents> agentsList){ //}, ArrayList<Drawable> icons) {
        this.agents = agentsList;
       // this.icons = icons;
    }
    @Override
    public ListAgentAdapter.AgentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.agency_card_layout, parent, false);
        return new AgentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AgentsViewHolder holder, int position) {
        temp = agents.get(position);
        holder.id.setText(String.valueOf(agents.get(position).getId()));
        holder.login.setText(String.valueOf(agents.get(position).getLogin()));
        holder.email.setText(String.valueOf(agents.get(position).getEmail()));
        holder.imageFileName.setText(String.valueOf(agents.get(position).getImageFileName()));

       // String url = AppController.SERVERURL + temp.getImageFileName();
      //  holder.imageView.setImageUrl(url, AppController.getInstance().getImageLoader());
        //holder.imageView.setImageDrawable(icons.get(position));
    }


    @Override
    public int getItemCount() {
        return agents.size();
    }


    public static class AgentsViewHolder extends RecyclerView.ViewHolder{
        protected TextView id, login, email, imageFileName;
        protected NetworkImageView imageView;
        protected CardView cardView;
        public AgentsViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.txtName);
            login = (TextView) itemView.findViewById(R.id.txtSurname);
            email = (TextView) itemView.findViewById(R.id.txtEmail);
            imageFileName = (TextView) itemView.findViewById(R.id.txtAdd);

            imageView = (NetworkImageView) itemView.findViewById(R.id.thumbnailImageViewListingRow1);
            //cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
