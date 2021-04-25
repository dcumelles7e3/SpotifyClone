package cat.itb.spotifyclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private Button loginbutton;
    private MaterialButton loginGoogle;
    private FirebaseAuth mAuth;
    private TextView signUp;
    private TextInputEditText etPass, etEmail;
    private SwitchMaterial switchMaterial;
    private int GOOGLE_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        switchMaterial = findViewById(R.id.switx);
        loginbutton = findViewById(R.id.loginbutton);
        signUp = findViewById(R.id.registerText);
        etPass = findViewById(R.id.etpass);
        etEmail = findViewById(R.id.etlSearch);
        loginGoogle = findViewById(R.id.fbutton);

        session();

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etEmail.getText().toString().isEmpty() && !etPass.getText().toString().isEmpty()){
                    mAuth.signInWithEmailAndPassword(etEmail.getText().toString(),etPass.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("email",etEmail.getText().toString());
                                        if (switchMaterial.isChecked()) {
                                            intent.putExtra("check",true);
                                        } else {
                                            intent.putExtra("check",false);
                                        }
                                        startActivity(intent);

                                    } else {
                                        showAlert("Esta cuenta de usuario no existe");
                                    }
                                }
                    });
                }else {
                    showAlert("Por favor, rellena todos los campos");
                }
            }
        });

        loginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), googleSignInOptions);
                googleSignInClient.signOut();

                startActivityForResult(googleSignInClient.getSignInIntent(), GOOGLE_SIGN_IN);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void session() {
        SharedPreferences sp = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String email = sp.getString("email",null);

        if (email != null) {
            View l = getWindow().getDecorView().findViewById(R.id.loginContent);
            l.setVisibility(View.INVISIBLE);

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("email",etEmail.getText().toString());
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        View l = getWindow().getDecorView().findViewById(R.id.loginContent);
        l.setVisibility(View.VISIBLE);
    }

    public void showAlert(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage(text);
        builder.setPositiveButton("Aceptar",null);
        AlertDialog al = builder.create();
        al.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount g = null;
            try {
                g = task.getResult(ApiException.class);

                if (g != null) {
                    AuthCredential credential = GoogleAuthProvider.getCredential(g.getIdToken(),null);
                    GoogleSignInAccount finalG = g;
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("email", finalG.getEmail());
                                if (switchMaterial.isChecked()) {
                                    intent.putExtra("check",true);
                                } else {
                                    intent.putExtra("check",false);
                                }
                                startActivity(intent);

                            } else {
                                showAlert("Esta cuenta de usuario no existe");
                            }
                        }
                    });
                }
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }
}