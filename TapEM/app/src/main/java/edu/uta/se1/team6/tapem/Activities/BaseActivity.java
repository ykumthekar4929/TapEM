package edu.uta.se1.team6.tapem.Activities;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import edu.uta.se1.team6.tapem.Core.AppDetails;
import edu.uta.se1.team6.tapem.R;

/**
 * Created by yashodhan on 3/23/18.
 */

public class BaseActivity extends AppCompatActivity {
    private static final int NUM_OF_ITEMS = 100;
    private static final int NUM_OF_ITEMS_FEW = 3;
    public static String stringSignature = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Suppress title bar for more space
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
        prepareActivity();
    }

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    protected int getScreenHeight() {
        return findViewById(android.R.id.content).getHeight();
    }

    public static ArrayList<String> getDummyData() {
        return getDummyData(NUM_OF_ITEMS);
    }

    public static ArrayList<String> getDummyData(int num) {
        ArrayList<String> items = new ArrayList<String>();
        for (int i = 1; i <= num; i++) {
            items.add("Item " + i);
        }
        return items;
    }

    protected void setDummyData(ListView listView) {
        setDummyData(listView, NUM_OF_ITEMS);
    }

    protected void setDummyDataFew(ListView listView) {
        setDummyData(listView, NUM_OF_ITEMS_FEW);
    }

    protected void setDummyData(ListView listView, int num) {
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getDummyData(num)));
    }

    protected void setDummyDataWithHeader(ListView listView, int headerHeight) {
        setDummyDataWithHeader(listView, headerHeight, NUM_OF_ITEMS);
    }

    protected String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }

    protected void setDummyDataWithHeader(ListView listView, int headerHeight, int num) {
        View headerView = new View(this);
        headerView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, headerHeight));
        headerView.setMinimumHeight(headerHeight);
        // This is required to disable header's list selector effect
        headerView.setClickable(true);
        setDummyDataWithHeader(listView, headerView, num);
    }

    protected void setDummyDataWithHeader(ListView listView, View headerView, int num) {
        listView.addHeaderView(headerView);
        setDummyData(listView, num);
    }

    protected void setDummyData(GridView gridView) {
        gridView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getDummyData()));
    }

    protected void setDummyData(RecyclerView recyclerView) {
        setDummyData(recyclerView, NUM_OF_ITEMS);
    }

    protected void setDummyDataFew(RecyclerView recyclerView) {
        setDummyData(recyclerView, NUM_OF_ITEMS_FEW);
    }

    protected void setDummyData(RecyclerView recyclerView, int num) {
//        recyclerView.setAdapter(new SimpleRecyclerAdapter(this, getDummyData(num)));
    }

    protected void setDummyDataWithHeader(RecyclerView recyclerView, int headerHeight) {
        View headerView = new View(this);
        headerView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, headerHeight));
        headerView.setMinimumHeight(headerHeight);
        // This is required to disable header's list selector effect
        headerView.setClickable(true);
        setDummyDataWithHeader(recyclerView, headerView);
    }

    protected void setDummyDataWithHeader(RecyclerView recyclerView, View headerView) {
//        recyclerView.setAdapter(new SimpleHeaderRecyclerAdapter(this, getDummyData(), headerView));
    }

    @Override
    protected void onStart() {
        super.onStart();
        prepareActivity();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        prepareActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        prepareActivity();
    }

    public void initCommonButtonClickListener() {

    }

    public void prepareActivity() {
        AppDetails.setActivity(this, true);
    }

//	@Override
//	public void onBackPressed() {
//		super.onBackPressed();
//		setBackButtonClickAnimation(BaseActivity.this);
//	}

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setErrorMsg(String msg, EditText et,
                                   boolean isRequestFocus) {
        int ecolor = AppDetails.getActivity().getResources()
                .getColor(R.color.white); // whatever color you want
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(ecolor);
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(msg);
        ssbuilder.setSpan(fgcspan, 0, msg.length(), 0);
        if (isRequestFocus) {
            et.requestFocus();
        }

        et.setError(ssbuilder);
    }



//    public static void setActivityStartAnimation(Activity activity) {
//        activity.overridePendingTransition(R.anim.push_left_in,
//                R.anim.push_left_out);
//    }
//
//    public static void setBackButtonClickAnimation(Activity activity) {
//        activity.overridePendingTransition(R.anim.push_right_in,
//                R.anim.push_right_out);
//    }

//    public static void setFontRobotoBlack(TextView tv, Context activity){
//        Typeface type = Typeface.createFromAsset(activity.getAssets(),"fonts/Roboto-Black.ttf");
//        tv.setTypeface(type);
//    }
//
//    public static void setFontRobotoBlackItalic(TextView tv, Context activity){
//        Typeface type = Typeface.createFromAsset(activity.getAssets(),"fonts/Roboto-BlackItalic.ttf");
//        tv.setTypeface(type);
//    }
//
//    public static void setFontRobotoBold(TextView tv, Context activity){
//        Typeface type = Typeface.createFromAsset(activity.getAssets(),"fonts/Roboto-Bold.ttf");
//        tv.setTypeface(type);
//    }
//
//    public static void setFontRobotoItalic(TextView tv, Context activity){
//        Typeface type = Typeface.createFromAsset(activity.getAssets(),"fonts/Roboto-Italic.ttf");
//        tv.setTypeface(type);
//    }
//
//    public static void setFontRobotoLight(TextView tv, Context activity){
//        Typeface type = Typeface.createFromAsset(activity.getAssets(),"fonts/Roboto-Light.ttf");
//        tv.setTypeface(type);
//    }
//
//    public static void setFontRobotoLightItalic(TextView tv, Context activity){
//        Typeface type = Typeface.createFromAsset(activity.getAssets(),"fonts/Roboto-LightItalic.ttf");
//        tv.setTypeface(type);
//    }
//
//    public static void setFontRobotoMedium(TextView tv, Context activity){
//        Typeface type = Typeface.createFromAsset(activity.getAssets(),"fonts/Roboto-Medium.ttf");
//        tv.setTypeface(type);
//    }
//
//    public static void setFontRobotoMediumItalic(TextView tv, Context activity){
//        Typeface type = Typeface.createFromAsset(activity.getAssets(),"fonts/Roboto-MediumItalic.ttf");
//        tv.setTypeface(type);
//    }
//
//    public static void setFontRobotoRegular(TextView tv, Context activity){
//        Typeface type = Typeface.createFromAsset(activity.getAssets(),"fonts/Roboto-Regular.ttf");
//        tv.setTypeface(type);
//    }
//
//    public static void setFontRobotoThin(TextView tv, Context activity){
//        Typeface type = Typeface.createFromAsset(activity.getAssets(),"fonts/Roboto-Thin.ttf");
//        tv.setTypeface(type);
//    }
//
//    public static void setFontRobotoThinItalic(TextView tv, Context activity){
//        Typeface type = Typeface.createFromAsset(activity.getAssets(),"fonts/Roboto-ThinItalic.ttf");
//        tv.setTypeface(type);
//    }
//
//    public static String getStringSignature(boolean isNew){
//        if(null == stringSignature
//                || isNew){
//            stringSignature = String.valueOf(System.currentTimeMillis());
//        }
//        return stringSignature;
//    }
}
