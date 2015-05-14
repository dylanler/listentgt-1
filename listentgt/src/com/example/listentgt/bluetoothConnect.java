package com.example.listentgt;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class bluetoothConnect extends Activity{
	
	private static final int REQUEST_ENABLE_BLUETOOTH = 1;
	ArrayAdapter<String> listAdapter;
	Button connectViaBluetooth;
	ListView listView;
	BluetoothAdapter myBluetoothAdapter;
	Set<BluetoothDevice> devices;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetooth_connect);
		initButtons();
		setupBluetooth();
	}
	
	private void initButtons() {
		connectViaBluetooth=(Button)findViewById(R.id.Connect);
		listView=(ListView)findViewById(R.id.listView);
		listAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,0);
		listView.setAdapter(listAdapter);
	}

	private void setupBluetooth() {
		myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(myBluetoothAdapter == null) {
			//Device does not support Bluetooth
			Toast.makeText(getApplicationContext(), "No bluetooth detected on your device", 0).show();
			finish();
		}
		else {
			if(!myBluetoothAdapter.isEnabled()) {
				Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH);
			}
			getPairedDevices();
		}
	}
	
	/**
	 * Grab any paried devices and add it to listAdapter to track
	 */
	private void getPairedDevices() {
		devices = myBluetoothAdapter.getBondedDevices();
		if(devices.size() > 0) {
			for(BluetoothDevice device : devices) {
				listAdapter.add(device.getName() + "\n" + device.getAddress());
			}
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_CANCELED){
			Toast.makeText(getApplicationContext(), "Bluetooth must be enabled to continue", Toast.LENGTH_SHORT).show();
			finish();
		}
	}
}
