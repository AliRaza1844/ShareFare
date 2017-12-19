package sharefare.aliraza.com;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class WalletActivity extends Fragment {

    EditText balance;

  @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_activity, container,false);

        balance = (EditText) view.findViewById(R.id.etBalance);

        return view;
  }

    @Override
    public void onViewCreated(View view,  @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Wallet");
        balance.setText(String.valueOf(User.getUser().wallet.getBalance()));

    }
}
