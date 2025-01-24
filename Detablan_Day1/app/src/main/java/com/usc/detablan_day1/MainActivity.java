package com.usc.detablan_day1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView txtU1;
    TextView txtU2;
    TextView txtU3;
    TextView txtU4;
    ImageView pfp1;
    LinearLayout userDetailsLayout;
    Button showDetailsButton;
    boolean detailsShown = false; // Flag to track details visibility

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtU1 = findViewById(R.id.txt1);
        txtU2 = findViewById(R.id.txt2);
        txtU3 = findViewById(R.id.txt3);
        txtU4 = findViewById(R.id.txt4);
        pfp1 = findViewById(R.id.pfp1);
        userDetailsLayout = findViewById(R.id.userDetailsLayout);
        showDetailsButton = findViewById(R.id.btn1);

        showDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsShown = !detailsShown; // Toggle details visibility

                if (detailsShown) {
                    txtU1.setText("Name: Paul France M. Detablan");
                    txtU2.setText("Course: Bachelors of Science in Computer Science");
                    txtU3.setText("Year: 2");
                    txtU4.setText("WHAM: A Gentleman");
                    pfp1.setImageResource(R.drawable.topg);

                    // Change the height of the ImageView
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) pfp1.getLayoutParams();
                    params.height = 1000; // Set the desired height in pixels (adjust as needed)
                    pfp1.setLayoutParams(params);

                    showDetailsButton.setText("Hide Details");
                } else {
                    txtU1.setText("Name:");
                    txtU2.setText("Course:");
                    txtU3.setText("Year:");
                    txtU4.setText("WHAM:");
                    pfp1.setImageResource(R.drawable.ic_launcher_background);

                    // Reset the height of the ImageView (optional)
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) pfp1.getLayoutParams();
                    params.height = LinearLayout.LayoutParams.WRAP_CONTENT; // Reset to wrap content
                    pfp1.setLayoutParams(params);

                    showDetailsButton.setText("Show Details");
                }
            }
        });
    }
}