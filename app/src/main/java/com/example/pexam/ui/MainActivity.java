package com.example.pexam.ui;

import static com.example.pexam.application.ApplicationConfig.NAME_SQLITE;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.pexam.R;
import com.example.pexam.database.Database;
import java.util.Objects;

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
//        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        SharedPreferences sharedPreferences = getSharedPreferences("stateApplication",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        database = new Database(this,NAME_SQLITE,null,1);
        setTheme(R.style.plashScreenTheme);
        if(!sharedPreferences.getBoolean("isLoadDataSqlite",false)){
            database.queryData("CREATE TABLE SettingExamTech(nameSubj VARCHAR(256),part VARCHAR(256),maxNumQuestion INTEGER, time INTEGER)");
            database.queryData("CREATE TABLE SettingExamEconomy(nameSubj VARCHAR(256),part VARCHAR(256),maxNumQuestion INTEGER, time INTEGER)");
            database.queryData("CREATE TABLE SettingExamOfficial(nameSubj VARCHAR(256),part VARCHAR(256),maxNumQuestion INTEGER, time INTEGER)");
            database.queryData("CREATE TABLE SettingExamDefence(nameSubj VARCHAR(256),part VARCHAR(256),maxNumQuestion INTEGER, time INTEGER)");

            database.queryData("CREATE TABLE ConsequenceExamTech(nameSubj VARCHAR(256), part VARCHAR(256),numAnsRight INTEGER)");
            database.queryData("CREATE TABLE ConsequenceExamEconomy(nameSubj VARCHAR(256), part VARCHAR(256),numAnsRight INTEGER)");
            database.queryData("CREATE TABLE ConsequenceExamOfficial(nameSubj VARCHAR(256), part VARCHAR(256),numAnsRight INTEGER)");
            database.queryData("CREATE TABLE ConsequenceExamDefence(nameSubj VARCHAR(256), part VARCHAR(256),numAnsRight INTEGER)");

            database.queryData("CREATE TABLE DetailQuestionTech(nameSubj VARCHAR(256),part VARCHAR(256),question TEXT,ans1 TEXT,ans2 TEXT,ans3 TEXT,ans4 TEXT,ansRight TEXT,isNoted INTEGER)");
            database.queryData("CREATE TABLE DetailQuestionEconomy(nameSubj VARCHAR(256),part VARCHAR(256),question TEXT,ans1 TEXT,ans2 TEXT,ans3 TEXT,ans4 TEXT,ansRight TEXT,isNoted INTEGER)");
            database.queryData("CREATE TABLE DetailQuestionOfficial(nameSubj VARCHAR(256),part VARCHAR(256),question TEXT,ans1 TEXT,ans2 TEXT,ans3 TEXT,ans4 TEXT,ansRight TEXT,isNoted INTEGER)");
            database.queryData("CREATE TABLE DetailQuestionDefence(nameSubj VARCHAR(256),part VARCHAR(256),question TEXT,ans1 TEXT,ans2 TEXT,ans3 TEXT,ans4 TEXT,ansRight TEXT,isNoted INTEGER)");

            database.queryData("CREATE TABLE AllDetailQuestion(kind VARCHAR(256),nameSubj VARCHAR(256),part VARCHAR(256),question TEXT,ans1 TEXT,ans2 TEXT,ans3 TEXT,ans4 TEXT,ansRight TEXT,isNoted INTEGER)");

            database.queryData("INSERT INTO ConsequenceExamTech VALUES ('Cơ sở dữ liệu','On boarding',23)");
            database.queryData("INSERT INTO ConsequenceExamTech VALUES ('Cơ sở dữ liệu','Operation',41)");
            database.queryData("INSERT INTO ConsequenceExamTech VALUES ('Cơ sở dữ liệu','Design',0)");

            database.queryData("INSERT INTO SettingExamTech values ('CTDL & GT','Phần 1',50,1200)");
            database.queryData("INSERT INTO SettingExamTech values ('CTDL & GT','Phần 2',50,1200)");
            database.queryData("INSERT INTO SettingExamTech values ('CTDL & GT','Phần 3',50,660)");

            database.queryData("INSERT INTO SettingExamTech values ('Ngôn ngữ lập trình C++','Chương 1',50,600)");
            database.queryData("INSERT INTO SettingExamTech values ('Ngôn ngữ lập trình C++','Chương 2',50,1200)");

            database.queryData("INSERT INTO SettingExamTech values ('Cơ sở dữ liệu','On boarding',50,1200)");
            database.queryData("INSERT INTO SettingExamTech values ('Cơ sở dữ liệu','Design',8,600)");
            database.queryData("INSERT INTO SettingExamTech values ('Cơ sở dữ liệu','Deliver',50,900)");
            database.queryData("INSERT INTO SettingExamTech values ('Cơ sở dữ liệu','Operation',50,1200)");

            database.queryData("INSERT INTO DetailQuestionTech VALUES ('Cơ sở dữ liệu','Design','D1B là gì?','Là DB','Là DB','Là DB','Là Database','Là Database',1)");
            database.queryData("INSERT INTO DetailQuestionTech VALUES ('Cơ sở dữ liệu','Design','D2B là gì?','Là DB','Là DB','Là DB','Là Database','Là Database',1)");
            database.queryData("INSERT INTO DetailQuestionTech VALUES ('Cơ sở dữ liệu','Design','D3B là gì?','Là DB','Là DB','Là DB','Là Database','Là Database',1)");
            database.queryData("INSERT INTO DetailQuestionTech VALUES ('Cơ sở dữ liệu','Design','D4B là gì?','Là DB','Là DB','Là DB','Là Database','Là Database',1)");
            database.queryData("INSERT INTO DetailQuestionTech VALUES ('Cơ sở dữ liệu','Design','D5B là gì?','Là DB','Là DB','Là DB','Là Database','Là Database',1)");
            database.queryData("INSERT INTO DetailQuestionTech VALUES ('Cơ sở dữ liệu','Design','D6B là gì?','Là DB','Là DB','Là DB','Là Database','Là Database',1)");
            database.queryData("INSERT INTO DetailQuestionTech VALUES ('Cơ sở dữ liệu','Design','D7B là gì?','Là DB','Là DB','Là DB','Là Database','Là Database',1)");
            database.queryData("INSERT INTO DetailQuestionTech VALUES ('Cơ sở dữ liệu','Design','D8B là gì?','Là DB','Là DB','Là DB','Là Database','Là Database',0)");

            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Cơ sở dữ liệu','Design','D1B là gì?','Là DB','Là DB','Là DB','Là Database','Là Database',1)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Cơ sở dữ liệu','Design','D2B là gì?','Là DB','Là DB','Là DB','Là Database','Là Database',1)");

            database.queryData("INSERT INTO SettingExamOfficial values ('Tin cơ sở 2','Ngôn Ngữ C',20,12000)");

            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','Kiểu dữ liệu nào dưới đây không được coi là kiểu dữ liệu cơ bản trong ngôn ngữ lập trình C :','Kiểu mảng.','Kiểu enum.','Kiểu short int.','Kiểu unsigned.','Kiểu mảng.',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','Biến con trỏ có thể chứa :','Địa chỉ vùng nhớ của một biến khác.','Giá trị của một biến khác.','Cả a và b đều đúng.','Cả a và b đều sai.','Địa chỉ vùng nhớ của một biến khác.',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','#include <stdio.h>\nvoid main()\n{\t\t\tint a,b;\n\t\ta=100;\n\t\tb=56;\n\t\tprintf(“%d”,(a<b) ? a:b);\n}','56.',' 100.',' Báo lỗi khi thực hiện xây dựng chương trình.','Cả ba đều sai','56.',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','#include “stdio.h”\nvoid main()\n{\n\t\tint i;\n\t\ti=10;\n\t\tprintf(“%o”,i);\n}','12.','10','8','Kết quả khác.','12.',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','Cho biết giá trị của biểu thức 5>1 :','-1','1','0','Không câu nào đúng.','1',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','Cho biết giá trị của biểu thức 2+4>2&&4<2 :','-1','1','0','Không câu nào đúng','0',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','#include <stdio.h>\nvoid main()\n{\n\t\tint ch=’A’;\n\t\tprintf(“%d”,ch);\n}','A.','a.','65.','Kết quả khác.','65.',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','#include <stdio.h>\nvoid main()\n{\n\t\tint i=98;\n\t\tprintf(“%c”,i);\n};','98.','b.','B.','Kết quả khác.','98.',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','include <stdio.h>\nvoid main()\n{\n\t\tint i=5, j=6;\n\t\ti= i- --j;\n\t\tprintf(“%d”,i);\n};','6.','5.','1.','0.','0.',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','#include <stdio.h>\nvoid main()\n{\n\t\tint i;\n\t\tfor (i=2; ; i++)\n\t\tprintf(“%3d”,i);\n};','Vòng lặp vô hạn.','“  2”.','“  1  2”','Kết quả khác.','Vòng lặp vô hạn.',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','#include <stdio.h>\nvoid main()\n{\n\t\tint sum;\n\t\tsum= 453+343\n\t\tprintf(\"Ket qua la: \" sum) ;\n};','Thiếu  dấu chấm phẩy(;).','Thiếu dấu phẩy (,).',' Thiếu kí tự đặc tả.','Cả 3 ý trên.','Cả 3 ý trên.',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','#include <stdio.h>\nint main()\n{\n\t\tint a=40, b=4;\n\t\twhile(a!=b)\n\t\t{\n\t\t\t\tif (a>b) a=a-b;\n\t\t\t\telse b=b-a;\n\t\t}\n\t\tprintf(\"%d\",a);\n\t\treturn 0 ;\n};','4','12','36',' Kết quả khác','4',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','#include <stdio.h>\nvoid main()\n{\n\t\tint i,k;\n\t\tfor(i=1; ; i++) k=5;\n\t\tprintf(“%d”,i);\n};','0.','5.','Vòng lặp vô hạn.','Kết quả khác.','Vòng lặp vô hạn.',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','#include <stdio.h>\nvoid main()\n{\n\t\tint i=1, k=0;\n\t\tfor (i; i<5; i++) k++;\n\t\tprintf(“%d”,k);\n};','0.','4.','5.','Vòng lặp vô hạn.','4.',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','Phép cộng 1 con trỏ với một số nguyên sẽ là:','Một con trỏ có cùng kiểu.','Một số nguyên.','Cả hai kết quả đều đúng.','Cả hai kết quả đều sai.','Một con trỏ có cùng kiểu.',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','Có các khai báo sau: int x=15; int *p; Muốn p là con trỏ trỏ tới x phải thực hiện lệnh nào:','p=x;','p=&x;','p=*x;','Tất cả các lệnh đều đúng.','p=&x;',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','Cho biết kết quả của đoạn chương trình sau:','14.','15.','16.','Kết quả khác.','15.',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','Phép toán 1 ngôi nào dùng để xác định địa chỉ của đối tượng mà con trỏ chỉ tới:','*;','!;','&;','Kết quả khác.','&;',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','Khi biến con trỏ không chứa bất kì một địa chỉ nào thì giá trị của nó sẽ là:','0.','NULL.','Cả hai phương án trên đều đúng.','Cả hai phương án trên đều sai.','Cả hai phương án trên đều đúng.',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','Những phát biểu nào sau đây là đúng:','Rẽ nhánh là việc chọn ra một trong hai hay nhiều con đường cho việc thực hiện tính toán sau đó.','Lưu đồ có thể có nhiều điểm bắt đầu và kết thức.','Kiểu kí tự chứa một kí tự nằm trong dấu nháy kép.','Trong các lệnh if lồng nhau, else  thuộc về if phía trước gần nó nhất.','Trong các lệnh if lồng nhau, else  thuộc về if phía trước gần nó nhất.',0)");
            database.queryData("INSERT INTO DetailQuestionOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C','#include <stdio.h>\nvoid main()\n{n\t\tint x, *p;\n\t\tx=6; p=&x;\n\t\tprintf(“%d”,x);\n\t\tprintf(“%d”,*p);\n};','69.','66.','Lỗi khi xây dựng chương trình.','Kết quả khác.','66.',0)");

            database.queryData("INSERT INTO ConsequenceExamOfficial VALUES ('Tin cơ sở 2','Ngôn Ngữ C',0)");
            Cursor cursor;
            cursor = database.getData("SELECT * FROM DetailQuestionTech");
            while (cursor.moveToNext()){
                database.queryData("INSERT INTO AllDetailQuestion VALUES ('Kỹ thuật','"
                        +cursor.getString(0)+"','"
                        +cursor.getString(1)+"','"
                        +cursor.getString(2)+"','"
                        +cursor.getString(3)+"','"
                        +cursor.getString(4)+"','"
                        +cursor.getString(5)+"','"
                        +cursor.getString(6)+"','"
                        +cursor.getString(7)+"','"
                        +cursor.getInt(8)+"')"
                );
            }
            cursor = database.getData("SELECT * FROM DetailQuestionEconomy");
            while (cursor.moveToNext()){
                database.queryData("INSERT INTO AllDetailQuestion VALUES ('Kinh tế','"
                        +cursor.getString(0)+"','"
                        +cursor.getString(1)+"','"
                        +cursor.getString(2)+"','"
                        +cursor.getString(3)+"','"
                        +cursor.getString(4)+"','"
                        +cursor.getString(5)+"','"
                        +cursor.getString(6)+"','"
                        +cursor.getString(7)+"','"
                        +cursor.getInt(8)+"')"
                );
            }
            cursor = database.getData("SELECT * FROM DetailQuestionOfficial");
            while (cursor.moveToNext()){
                database.queryData("INSERT INTO AllDetailQuestion VALUES ('Tin cơ sở','"
                        +cursor.getString(0)+"','"
                        +cursor.getString(1)+"','"
                        +cursor.getString(2)+"','"
                        +cursor.getString(3)+"','"
                        +cursor.getString(4)+"','"
                        +cursor.getString(5)+"','"
                        +cursor.getString(6)+"','"
                        +cursor.getString(7)+"','"
                        +cursor.getInt(8)+"')"
                );
            }
            cursor = database.getData("SELECT * FROM DetailQuestionDefence");
            while (cursor.moveToNext()){
                database.queryData("INSERT INTO AllDetailQuestion VALUES ('Quốc phòng','"
                        +cursor.getString(0)+"','"
                        +cursor.getString(1)+"','"
                        +cursor.getString(2)+"','"
                        +cursor.getString(3)+"','"
                        +cursor.getString(4)+"','"
                        +cursor.getString(5)+"','"
                        +cursor.getString(6)+"','"
                        +cursor.getString(7)+"','"
                        +cursor.getInt(8)+"')"
                );
            }
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