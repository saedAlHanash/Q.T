package com.example.wasla.Helpers.View;//package com.example.wasla.Helpers.View;
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//public class Params {
//    /**
//     * change Margins for any view
//     * @param v the view that you need
//     * @param l lift
//     * @param t top
//     * @param r right
//     * @param b bottom
//     */
//    public static void setMargins (View v, int l, int t, int r, int b) {
//        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
//            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
//            p.setMargins(l, t, r, b);
//            v.requestLayout();
//        }
//    }
//
//    public static void setMarenTextView(TextView tv, int L, int T , int R , int B){
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.setMargins(L,T,R,B);
//        tv.setLayoutParams(params);
//    }
//}
