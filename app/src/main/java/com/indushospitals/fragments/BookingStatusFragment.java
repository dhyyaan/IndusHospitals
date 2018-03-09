package com.indushospitals.fragments;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.databinding.FragmentBookingStatusBinding;
import com.indushospitals.utils.ConnectivityReceiver;
import com.sdsmdg.tastytoast.TastyToast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //BookingStatusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookingStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingStatusFragment extends Fragment {




    public BookingStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment BookingStatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingStatusFragment newInstance() {

        return new BookingStatusFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentBookingStatusBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_booking_status, container, false);

        binding.btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginRegister7 = new Intent(MoreActivity.self, MoreActivity.class);
                loginRegister7.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                loginRegister7.putExtra("getfragment", 5);
                loginRegister7.putExtra("getTip",false);
                startActivity(loginRegister7);
                MoreActivity.self.finish();

            }
        });
        binding.btnAnotherBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectivityReceiver.isConnected()){
                    Intent loginRegister = new Intent(MoreActivity.self, MoreActivity.class);
                    loginRegister.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    loginRegister.putExtra("getfragment", 0);
                    loginRegister.putExtra("getTip",false);
                    startActivity(loginRegister);
                    MoreActivity.self.finish();
                }else{
                    TastyToast.makeText(MoreActivity.self,"No internet connection!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }
            }
        });

        return binding.getRoot();
    }
    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_booking_status));
        super.onResume();
    }
}
