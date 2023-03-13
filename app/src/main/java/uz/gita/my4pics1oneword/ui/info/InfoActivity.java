package uz.gita.my4pics1oneword.ui.info;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import uz.gita.my4pics1oneword.R;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        findViewById(R.id.iv_back_info).setOnClickListener(view -> {
            finish();
        });
    }
}