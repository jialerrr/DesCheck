package rp.edu.sg.c346.id20021576.descheck;

import android.content.Context;
import android.media.Rating;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Hardware> HardwareList;

    public CustomAdapter(Context context, int resource, ArrayList<Hardware> objects){
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        HardwareList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvTitle = rowView.findViewById(R.id.textViewTitle);
        TextView tvPrice = rowView.findViewById(R.id.textViewPrice);
        TextView tvDesc = rowView.findViewById(R.id.textViewDesc);
        RatingBar rtBarList = rowView.findViewById(R.id.ratingBarList);

        Hardware currentHardware = HardwareList.get(position);

        tvTitle.setText(currentHardware.getName());
        tvPrice.setText("$" + String.valueOf(currentHardware.getPrice()));

        rtBarList.setClickable(false);
        rtBarList.setRating(currentHardware.getStars());
        tvDesc.setText(currentHardware.getDesc());
        return rowView;
    }
}
