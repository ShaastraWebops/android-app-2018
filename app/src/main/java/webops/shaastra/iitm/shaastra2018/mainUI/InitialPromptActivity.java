package webops.shaastra.iitm.shaastra2018.mainUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import webops.shaastra.iitm.shaastra2018.LoginActivity;
import webops.shaastra.iitm.shaastra2018.R;
import webops.shaastra.iitm.shaastra2018.RegisterActivity;

public class InitialPromptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_prompt);

        Button signUp = (Button) findViewById(R.id.initPromptSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InitialPromptActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });


        Button signIn = (Button) findViewById(R.id.initPromptSignin);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InitialPromptActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }
}
