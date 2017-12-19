package sharefare.aliraza.com;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ViewProfileFragment extends Fragment {

    TextView name;
    TextView age;
    TextView phone;
    TextView email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_profile, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("View Profile");

        name = (TextView) view.findViewById(R.id.tvName);
        age = (TextView) view.findViewById(R.id.tvAge);
        phone = (TextView) view.findViewById(R.id.tvContact);
        email = (TextView) view.findViewById(R.id.tvEmail);

        name.setText(User.getUser().profile.getFirstName() + " " + User.getUser().profile.getLastName());
        email.setText(User.getUser().account.getUsername());
        phone.setText(User.getUser().profile.getPhoneNumber());


    }
}
