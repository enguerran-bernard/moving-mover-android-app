package com.movingmover.oem.movingmover.ia_selection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.movingmover.oem.movingmover.R;
import com.movingmover.oem.movingmover.battle.BattleActivity;
import com.movingmover.oem.movingmover.helper.MovingMoverIntent;
import com.movingmover.oem.movingmover.widget.IASelectorAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class IASelectionActivity extends Activity {

    private static final String TAG = IASelectionActivity.class.getSimpleName();
    private Spinner mSpinnerIA1;
    private Spinner mSpinnerIA2;
    private Button mLaunchGameButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ia_selection);

        mSpinnerIA1 = findViewById(R.id.ia1_spinner);
        mSpinnerIA2 = findViewById(R.id.ia2_spinner);
        mLaunchGameButton = findViewById(R.id.launch_game_button);
        mSpinnerIA1.setAdapter(new IASelectorAdapter(this, mSpinnerIA1, initIAListNames(), initIAListIcons()));
        mSpinnerIA2.setAdapter(new IASelectorAdapter(this, mSpinnerIA2, initIAListNames(), initIAListIcons()));

        mLaunchGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View ia1View = mSpinnerIA1.getSelectedView();
                View ia2View = mSpinnerIA2.getSelectedView();

                TextView nameIa1 = ia1View.findViewById(R.id.selector_ia_name);
                ImageView iconIa1 = ia1View.findViewById(R.id.selector_ia_icon);
                TextView nameIa2 = ia2View.findViewById(R.id.selector_ia_name);
                ImageView iconIa2 = ia2View.findViewById(R.id.selector_ia_icon);

                iconIa1.buildDrawingCache();
                iconIa2.buildDrawingCache();

                launchBattleActivity(nameIa1.getText().toString().trim()
                        ,iconIa1.getDrawingCache()
                        , nameIa2.getText().toString().trim()
                        ,iconIa2.getDrawingCache());
            }
        });
    }

    private void launchBattleActivity(String nameIa1, Bitmap iconIa1, String nameIa2, Bitmap iconIa2) {
        Intent intent = new Intent(IASelectionActivity.this, BattleActivity.class);
        intent.putExtra(MovingMoverIntent.EXTRA_IA1_NAME, nameIa1);
        intent.putExtra(MovingMoverIntent.EXTRA_IA2_NAME, nameIa2);
        intent.putExtra(MovingMoverIntent.EXTRA_IA1_ICON, getByteArray(iconIa1));
        intent.putExtra(MovingMoverIntent.EXTRA_IA2_ICON, getByteArray(iconIa2));
        startActivity(intent);
    }

    private byte[] getByteArray(Bitmap icon) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] res = stream.toByteArray();
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    private List<String> initIAListNames() {
        return null;
    }

    private List<Integer> initIAListIcons() {
        return null;
    }
}
