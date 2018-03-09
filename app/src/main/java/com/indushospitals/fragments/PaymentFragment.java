package com.indushospitals.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.databinding.FragmentPaymentBinding;
//import com.payUMoney.sdk.PayUmoneySdkInitilizer;
//import com.payUMoney.sdk.SdkConstants;


import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class PaymentFragment extends Fragment {
    public static final String TAG = PaymentFragment.class.getSimpleName();

    private static final String ARG_PARAM2 = "param2";

    private  String formsubmitId;

    public PaymentFragment() {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance(String param2) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);
        MoreActivity.self.selApt = false;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

                                            formsubmitId = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentPaymentBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false);

        binding.rlPayUMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //first uncomment  payUMoneysdk in settings.gradle file after that uncomment in app gradle file
                // that after in More activity  onresult then uncomment makePayment() and soon ... calling method after it all working fine
               // makePayment();
            }
        });

      MoreActivity.self.moreActivityBinding.  back.setVisibility(View.VISIBLE);
        MoreActivity.self.moreActivityBinding.    back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MoreActivity.self.onBackPressed();

            }
        });
        return binding.getRoot();
    }


   /*  private double getAmount() {


        Double amount = 10.0;

        if (isDouble("200.0")) {
            amount = Double.parseDouble("200.0");
            return amount;
        } else {
            Toast.makeText(getActivity(), "Paying Default Amount ₹10", Toast.LENGTH_LONG).show();
            return amount;
        }
    }
   private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public void makePayment() {

        String phone = "8882434664";
        String productName = "product_name";
        String firstName = "piyush";
        String txnId = "0nf7" + System.currentTimeMillis();
        String email = "test@payu.in";
        String sUrl = "https://test.payumoney.com/mobileapp/payumoney/success.php";
        String fUrl = "https://test.payumoney.com/mobileapp/payumoney/failure.php";
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        boolean isDebug = true;
        String key = "dRQuiA";
        String merchantId = "4928174" ;

        PayUmoneySdkInitilizer.PaymentParam.Builder builder = new PayUmoneySdkInitilizer.PaymentParam.Builder();


        builder.setAmount(getAmount())
                .setTnxId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(sUrl)
                .setfUrl(fUrl)
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setIsDebug(isDebug)
                .setKey(key)
                .setMerchantId(merchantId);

        PayUmoneySdkInitilizer.PaymentParam paymentParam = builder.build();


    }*/




    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }
/*


    private void calculateServerSideHashAndInitiatePayment(final PayUmoneySdkInitilizer.PaymentParam paymentParam) {

        // Replace your server side hash generator API URL
        String url = "https://test.payumoney.com/payment/op/calculateHashForTest";

        Toast.makeText(getActivity(), "Please wait... Generating hash from server ... ", Toast.LENGTH_LONG).show();
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("pay u money ",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has(SdkConstants.STATUS)) {
                        String status = jsonObject.optString(SdkConstants.STATUS);
                        if (status != null || status.equals("1")) {

                            String hash = jsonObject.getString(SdkConstants.RESULT);
                            Log.i("app_activity", "Server calculated Hash :  " + hash);

                            paymentParam.setMerchantHash(hash);

                            PayUmoneySdkInitilizer.startPaymentActivityForResult(getActivity(), paymentParam);
                        } else {
                            Toast.makeText(getActivity(),
                                    jsonObject.getString(SdkConstants.RESULT),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(),
                            getActivity().getString(R.string.connect_to_internet),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),
                            error.getMessage(),
                            Toast.LENGTH_SHORT).show();

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return paymentParam.getParams();
            }
        };
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
    }*/
    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_payment));
        super.onResume();
    }
/*




    public void payUMoneyStatus(int requestCode, int resultCode, Intent data) {


            if (requestCode == PayUmoneySdkInitilizer.PAYU_SDK_PAYMENT_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    Log.i(TAG, "Success - Payment ID : " + data.getStringExtra(SdkConstants.PAYMENT_ID));
                    String paymentId = data.getStringExtra(SdkConstants.PAYMENT_ID);
                    String name = data.getStringExtra(SdkConstants.NAME);
                    String trasactionStatus = data.getStringExtra(SdkConstants.STATUS);
                    String trasactionId = data.getStringExtra(SdkConstants.TXNID);
                    String trasactionAmount = data.getStringExtra(SdkConstants.AMOUNT);

                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    // Store the Fragment in stack
                    //transaction.addToBackStack(null);
                    transaction.replace(R.id.fragContainer, PaymentStatusFragment.newInstance()).commitAllowingStateLoss();
                    //showDialogMessage("Payment Success Id : " + name + " " + trasactionStatus + "  " + trasactionId + " " + trasactionAmount);

                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Log.i(TAG, "failure");
                    showDialogMessage("cancelled");
                } else if (resultCode == PayUmoneySdkInitilizer.RESULT_FAILED) {
                    Log.i("app_activity", "failure");

                    if (data != null) {
                        if (data.getStringExtra(SdkConstants.RESULT).equals("cancel")) {

                        } else {
                            showDialogMessage("failure");
                        }
                    }
                    //Write your code if there's no result
                } else if (resultCode == PayUmoneySdkInitilizer.RESULT_BACK) {
                    Log.i(TAG, "User returned without login");
                    showDialogMessage("User returned without login");
                }
            }


    }*/
    private static void showDialogMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MoreActivity.self);
        builder.setTitle(TAG);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

}


