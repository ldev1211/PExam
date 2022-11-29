package ldev.ptithcm.pexam.ui;

import static ldev.ptithcm.pexam.application.ApplicationConfig.NAME_SQLITE;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.pexam.R;
import ldev.ptithcm.pexam.database.Database;

public class MainActivity extends AppCompatActivity {

    Database database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.nightMode));
        window.requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        SharedPreferences sharedPreferences = getSharedPreferences("stateApplication",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        database = new Database(this,NAME_SQLITE,null,1);
        setTheme(R.style.plashScreenTheme);
        if(!sharedPreferences.getBoolean("isLoadDataSqlite",false)){
            Log.d("TAG", "onCreate: Created table");
            database.queryData("DROP TABLE IF EXISTS Note");
            database.queryData("DROP TABLE IF EXISTS ConsequenceExam");
            database.queryData("CREATE TABLE ConsequenceExam(codePart VARCHAR(256), numAnsRight INTEGER,UNIQUE(codePart))");
            database.queryData("CREATE TABLE Note(codeKind VARCHAR(256),nameKind VARCHAR(256),contentQuestion BIGTEXT,linkImg BIGTEXT, ansa BIGTEXT, ansb BIGTEXT, ansc BIGTEXT, ansd BIGTEXT, ansRight BIGTEXT)");
            editor.putBoolean("isLoadDataSqlite",true);
        }
        editor.apply();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
    }
}