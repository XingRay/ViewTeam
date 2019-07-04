package com.xingray.sample.page.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xingray.sample.R;
import com.xingray.sample.page.util.ToastUtil;
import com.xingray.viewteam.TeamChangeListener;
import com.xingray.viewteam.ViewTeam;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/**
 * @author leixing
 */
public class JavaTestActivity extends Activity {
    private static final String TAG = "JavaTestActivity";

    public static void start(Activity activity) {
        Intent starter = new Intent(activity.getApplicationContext(), JavaTestActivity.class);
        activity.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_java_test);

        ConstraintLayout clRoot = findViewById(R.id.cl_root);
        View[] views = new View[]{
                findViewById(R.id.bt_0),
                findViewById(R.id.bt_1),
                findViewById(R.id.bt_2),
                findViewById(R.id.bt_3)
        };

        Button button = new Button(getApplicationContext());
        button.setText("team02");
        ConstraintLayout.LayoutParams constraintLayoutParams = new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        constraintLayoutParams.leftToLeft = R.id.cl_root;
        constraintLayoutParams.rightToRight = R.id.cl_root;
        constraintLayoutParams.topToTop = R.id.cl_root;
        constraintLayoutParams.bottomToBottom = R.id.cl_root;
        button.setLayoutParams(constraintLayoutParams);

        final ViewTeam viewTeam = new ViewTeam(clRoot, true)
                .inflate(1, R.layout.team_01).inTeam(1, views)
                .addView(2, button).inTeam(2, views)
                .merge(3, R.layout.team_03).inTeam(3, views);

        findViewById(R.id.bt_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewTeam.setTeam(0);
            }
        });

        findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewTeam.setTeam(1);
            }
        });

        findViewById(R.id.bt_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewTeam.setTeam(2);
            }
        });

        findViewById(R.id.bt_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewTeam.setTeam(3);
            }
        });

        viewTeam.addTeamChangedListener(new Function2<Integer, Integer, Unit>() {
            @Override
            public Unit invoke(Integer oldTeamId, Integer newTeamId) {
                ToastUtil.showToast(JavaTestActivity.this, oldTeamId + "->" + newTeamId);
                return null;
            }
        }).addTeamChangedListener(new TeamChangeListener() {
            @Override
            public void onTeamChanged(int oldTeamId, int newTeamId) {
                Log.i(TAG, oldTeamId + "->" + newTeamId);
            }
        });
    }
}
