package data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.retrofitlearn.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jomeno on 9/15/2015.
 */
public class CourseAdapter extends ArrayAdapter<PackModel> {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_SEPARATOR = 1;
    private static final int VIEW_TYPE_COUNT = 2;

    //private LayoutInflater mInflater;
    private ArrayList<PackModel> mCourses;
    private boolean isNewSection;
    private int mViewType;

    public CourseAdapter(Context context, int resource, ArrayList<PackModel> objects) {
        super(context, resource, objects);
    }

    public void groupItems(ArrayList<PackModel> objects) {
        mCourses = stanTranslate(objects, new IHeaderGenerator() {
            @Override
            public PackModel generate(PackModel model) {
                PackModel header = new PackModel();
                header.Name = "300 Level";
                header.ViewType = 1;
                return header;
            }
        });

        //remove all elements from array
        clear();
        //add all elements to end of cleared array
        addAll(mCourses);

        //this.notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        PackModel course = getItem(position);

        mViewType = VIEW_TYPE_ITEM;

        switch (course.ViewType){
            case VIEW_TYPE_SEPARATOR:
                mViewType = VIEW_TYPE_SEPARATOR;
                break;
        }

        return mViewType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PackModel course = getItem(position);

        int layout = R.layout.item_section_header;

        // determine view to inflate
        switch (mViewType) {
            case VIEW_TYPE_SEPARATOR:
                layout = R.layout.item_section_header;
                break;
            case VIEW_TYPE_ITEM:
                layout = R.layout.item_section_row;
                break;

        }

        // inflate view if necessary
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.textView.setText(course.Name);

        convertView.setTag(viewHolder);

        return convertView;
    }

    private ArrayList<PackModel> stanTranslate(ArrayList<PackModel> ungrouped, IHeaderGenerator headerGenerator)
    //private ArrayList<PackModel> stanTranslate(ArrayList<PackModel> ungrouped)
    {
        HashMap<String, ArrayList<PackModel>> grouping = new HashMap<>();
        // group items
        for(PackModel p: ungrouped)
        {
            if(grouping.containsKey((p.IsPublished +"")) == false)
            {
                grouping.put(p.IsPublished+"", new ArrayList<PackModel>());
            }
            grouping.get(p.IsPublished+"").add(p);
        }

        // export the new arraylist
        ArrayList<PackModel> joined = new ArrayList<>();
        for(String key : grouping.keySet())
        {
            ArrayList<PackModel> x = grouping.get(key);
            PackModel header = headerGenerator.generate(x.get(0));
            joined.add(header);
            joined.addAll(x);
        }

        return joined;
    }

    private static class ViewHolder{

        //public final TextView headerTextView;
        public final TextView textView;

        public ViewHolder(View view){
            //headerTextView = (TextView)view.findViewById(R.id.item_header_text);
            textView = (TextView)view.findViewById(R.id.item_text);

        }
    }

    interface IHeaderGenerator
    {
        public PackModel generate(PackModel model);
    }

}
