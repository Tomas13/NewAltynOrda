package kz.growit.altynorda.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import kz.growit.altynorda.AgenciesActivity;
import kz.growit.altynorda.InsideAgencyActivity;
import kz.growit.altynorda.R;
import kz.growit.altynorda.models.Agencies;

/**
 * Created by jean on 9/30/2015.
 */
public class ListAgenciesAdapter extends RecyclerView.Adapter<ListAgenciesAdapter.AgenciesViewHolder> {

    private ArrayList<Agencies> agencies;
    private Agencies temp;
    private AgenciesActivity activity;

    public ListAgenciesAdapter(ArrayList<Agencies> agentciesList, AgenciesActivity activity) {
        this.agencies = agentciesList;
        this.activity = activity;
    }


    @Override
    public ListAgenciesAdapter.AgenciesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.agencies_card_layout, parent, false);
        return new AgenciesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AgenciesViewHolder holder, final int position) {
        temp = agencies.get(position);
        holder.Id.setText(String.valueOf(agencies.get(position).getId()));
        holder.Login.setText(String.valueOf(agencies.get(position).getLogin()));
        holder.Agencyname.setText(String.valueOf(agencies.get(position).getAgencyname()));
        holder.Agencyname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AgencyListingsFragment agencyListingsFragment;
//                agencyListingsFragment = (AgencyListingsFragment) activity.getSupportFragmentManager().findFragmentByTag("agencyListingsFragment");
//                if (agencyListingsFragment == null)
//                    agencyListingsFragment = new AgencyListingsFragment();
//                activity.getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.id_viewpager, agencyListingsFragment, "agencyListingsFragment")
//                        .commit();


                Intent goToAgency = new Intent(v.getContext(), InsideAgencyActivity.class);
//                goToAgency.putExtra("agencies", agencies.get(position).getMobile() + " " + agencies.get(position).getCity());
              //  goToAgency.putExtra("agencies", agencies);
                goToAgency.putExtra("index", position);
                v.getContext().startActivity(goToAgency);

//                Snackbar.make(v, "clicked on " + holder.Agencyname.getText(), Snackbar.LENGTH_SHORT).show();
            }
        });
        holder.Address.setText(String.valueOf(agencies.get(position).getAddress()));
        holder.City.setText(String.valueOf(agencies.get(position).getCity()));
        holder.Phone.setText(String.valueOf(agencies.get(position).getPhone()));
        holder.Mobile.setText(String.valueOf(agencies.get(position).getMobile()));

//        String url = AppController.SERVERURL + temp.getImageFileName();
//        holder.ImageFileName.setImageUrl(url, AppController.getInstance().getImageLoader());
    }


    @Override
    public int getItemCount() {
        return agencies.size();
    }

    public class AgenciesViewHolder extends RecyclerView.ViewHolder {
        private TextView Id;
        private TextView Login, Agencyname, Address, City, Phone, Mobile;
        private NetworkImageView ImageFileName;

        public AgenciesViewHolder(View itemView) {
            super(itemView);
            Id = (TextView) itemView.findViewById(R.id.txtName);
            Login = (TextView) itemView.findViewById(R.id.txtLogin);
            Agencyname = (TextView) itemView.findViewById(R.id.txtAgencyName);
            Address = (TextView) itemView.findViewById(R.id.txtAddress);
            City = (TextView) itemView.findViewById(R.id.txtCity);
            Phone = (TextView) itemView.findViewById(R.id.txtPhone);
            Mobile = (TextView) itemView.findViewById(R.id.txtMobile);

            ImageFileName = (NetworkImageView) itemView.findViewById(R.id.thumbnailImageViewListingRow1);
        }
    }
}
