package fi.jamk.exerciseseven;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Ultoros on 3.11.2016.
 */

public class GolfCourseAdapter extends RecyclerView.Adapter<GolfCourseAdapter.ViewHolder>  {

    private List<GolfCourse> golfCourseList;

    public GolfCourseAdapter(List<GolfCourse> mGolfCourseList) {
        this.golfCourseList = mGolfCourseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.golf_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GolfCourse golfCourse = golfCourseList.get(position);
        new DownloadImageTask(holder.golfCourseImageView).execute(golfCourse.imageSite + golfCourse.imageUrl);
        holder.golfCourseNameTextView.setText(golfCourse.field);
        holder.golfCourseAddressTextView.setText(golfCourse.address);
        holder.golfCoursePhoneTextView.setText(golfCourse.phonenumber);
        holder.golfCourseEmailTextView.setText(golfCourse.email);
    }

    @Override
    public int getItemCount() {
        return golfCourseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView golfCourseImageView;
        public TextView golfCourseNameTextView;
        public TextView golfCourseAddressTextView;
        public TextView golfCoursePhoneTextView;
        public TextView golfCourseEmailTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            // get layout ids
            golfCourseImageView = (ImageView) itemView.findViewById(R.id.golfCourseImageView);
            golfCourseNameTextView = (TextView) itemView.findViewById(R.id.golfCourseNameTextView);
            golfCourseAddressTextView = (TextView) itemView.findViewById(R.id.golfCourseAddressTextView);
            golfCoursePhoneTextView = (TextView) itemView.findViewById(R.id.golfCoursePhoneTextView);
            golfCourseEmailTextView = (TextView) itemView.findViewById(R.id.golfCourseEmailTextView);
            // add click listener for a card
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), GolfCourseDetail.class);
                    intent.putExtra("selected", golfCourseList.get(position));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView fieldImage;

        public DownloadImageTask(ImageView mImage) {
            this.fieldImage = mImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            fieldImage.setImageBitmap(result);
        }
    }


}
