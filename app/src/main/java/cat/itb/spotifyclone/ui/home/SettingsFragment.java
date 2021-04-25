package cat.itb.spotifyclone.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import cat.itb.spotifyclone.LoginActivity;
import cat.itb.spotifyclone.R;

public class SettingsFragment extends Fragment {

    private Toolbar toolbar;
    private Button logoutButton;
    private ImageView imageView;
    private TextView username;

    private FirebaseDatabase database;
    static DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Usuarios");

        logoutButton = v.findViewById(R.id.logutButton);
        imageView = v.findViewById(R.id.imageViewProfile);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getContext(), LoginActivity.class);
                SharedPreferences sp = getActivity().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
                sp.edit().clear().apply();
                startActivity(i);
            }
        });

        toolbar = v.findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });
/*
        String strProvider = FirebaseAuth.getInstance().
                getAccessToken(false).getResult().getSignInProvider();

        if (strProvider.equals("google.com")) {
            Picasso.with(v.getContext()).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(imageView);
        }

        String usernamee = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        username = v.findViewById(R.id.username);
        username.setText(usernamee);
*/
        return v;
    }


}