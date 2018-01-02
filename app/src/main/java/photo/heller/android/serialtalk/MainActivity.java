package photo.heller.android.serialtalk;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    static final String TAG = MainActivity.class.getSimpleName();

    ToggleButton mSerialButton;
    TextView mSerialView;

    BluetoothSerial mBluetoothSerial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBluetoothSerial = new BluetoothSerial(this, new BluetoothSerial.MessageHandler() {
            @Override
            public int read(int bufferSize, byte[] buffer) {
                return 0;
            }

        }, "HC");

        mBluetoothSerial.connect();

        mSerialButton = (ToggleButton) findViewById(R.id.toggleButton);
        mSerialView = (TextView) findViewById(R.id.serialStatusTextView);
        mSerialButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "IS CONNECTD: " + mBluetoothSerial.connected);
                if (isChecked) {
                    // The toggle is enabled
                    Log.d(TAG, "IS CHECKED");
                    mSerialView.setText("Lights are on!");
                    try {
                        mBluetoothSerial.write("1".getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // The toggle is disabled
                    Log.d(TAG, "IS NOT CHECKED");
                    mSerialView.setText("Lights are off!");
                    try {
                        mBluetoothSerial.write("0".getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
