package sharefare.aliraza.com;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeFragment extends Fragment {

Button btnaddjourney;
Button btnaddedjourney;
Button btnsearchjourney;
Button btnbookjourney;
@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

    btnaddjourney = (Button) view.findViewById(R.id.btnaddjourney);
    btnaddjourney.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), AddJourney.class);
            getActivity().startActivity(intent);

        }
    });

    btnaddedjourney = (Button) view.findViewById(R.id.btnaddedjourney);
    btnaddedjourney.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), AddedJourney.class);
            getActivity().startActivity(intent);

        }
    });


    btnsearchjourney = (Button) view.findViewById(R.id.btnsearchjourney);
    btnsearchjourney.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), SearchJourney.class);
            getActivity().startActivity(intent);

        }
    });


    btnbookjourney = (Button) view.findViewById(R.id.btnbookjourney);
    btnbookjourney.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), BookJourney.class);
            getActivity().startActivity(intent);

        }
    });


    return view;
    }




    @Override
    public void onViewCreated(View view,  @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");
    }
}
