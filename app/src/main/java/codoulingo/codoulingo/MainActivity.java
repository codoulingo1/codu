package codoulingo.codoulingo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);}

        public void downloadlesson(String ID) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Lessons").child("1");
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String vals = "";
                String folder_main = dataSnapshot.child("LessonID").getValue(String.class) + dataSnapshot.child("LessonName").getValue(String.class);
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    if (snap.getValue(String.class).length() != 0) {
                        Log.d("VAL", "Value is: " + snap.getKey() + "    " + snap.getValue(String.class));
                        ReadWrite.write(folder_main + snap.getKey(), snap.getValue(String.class), MainActivity.this);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("error", "Failed to read value.", error.toException());
            }
        });

        }
        public HashMap<String, String> readqs(String id, String name, String qs_num) {
            String content = ReadWrite.read(id + name + "qs" + qs_num, MainActivity.this);
            HashMap<String, String> hashMap = new HashMap<>();
            String[] arr = content.split("\\[|\\]");
            hashMap.put("type", arr[0]);
            hashMap.put("Content", arr[1]);
            hashMap.put("Image", arr[2]);
            hashMap.put("Answer", arr[3]);
            return hashMap;
        }

}