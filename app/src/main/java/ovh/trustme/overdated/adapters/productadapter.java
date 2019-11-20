package ovh.trustme.overdated.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ovh.trustme.overdated.ProduitApplication;
import ovh.trustme.overdated.R;
import ovh.trustme.overdated.pojo.Product;

public class productadapter extends BaseAdapter{
    private List<Product> mProducts;
    private final LayoutInflater mInflater;

    public productadapter(List<Product> mProducts){
        this.mProducts = mProducts;
        this.mInflater = LayoutInflater.from(ProduitApplication.getsContext());
    }

    @Override
    public int getCount() {
        return null != mProducts ? mProducts.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null != mProducts ? mProducts.get(position) : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(null == convertView) {
            convertView = mInflater.inflate(R.layout.product_list_element, null);

            // Instantiate the ViewHolder
            holder = new ViewHolder(convertView);
            // Set as tag to the convertView to retrieve it easily
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the current item
        final Product produit = (Product) getItem(position);

        // Set the user name, to do so, retrieve the corresponding TextView
        holder.name.setText(produit.name);
        // Set the user alias, to do so, retrieve the corresponding TextView
        holder.date.setText(produit.date);
        // Set the user alias, to do so, retrieve the corresponding TextView
        holder.dlc_dluo.setText(produit.dlc_dluo);


        return convertView;
    }

    private class ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView date;
        public TextView dlc_dluo;

        public ViewHolder(View view) {
            image = view.findViewById(R.id.list_product_element_picture);
            name = view.findViewById(R.id.list_product_element_name);
            date = view.findViewById(R.id.list_product_element_date);
            dlc_dluo = view.findViewById(R.id.list_product_perempt_type);

        }
    }
}
