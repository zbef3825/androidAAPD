package practice.jin.aa_pd;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_GRANTED = 0;

    @ViewById
    Button FragButton;

    @ViewById
    Button Cheese;

    @ViewById
    ImageView image;

    @AfterViews
    public void afterViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public void onDetachedFromWindow() {
        FragButton.setVisibility(View.VISIBLE);
        Cheese.setVisibility(View.VISIBLE);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bp = (Bitmap) data.getExtras().get("data");
        image.setImageBitmap(bp);
    }

    @Click(R.id.FragButton)
    void onClickFragButton(){
        FragButton.setVisibility(View.GONE);
        Cheese.setVisibility(View.GONE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content, new OneFragment_());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Click(R.id.Cheese)
    void onClickCheese(){

        MainActivityPermissionsDispatcher.UseCameraWithCheck(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Let's ask Internet Permission
    @NeedsPermission(Manifest.permission.CAMERA)
    public void UseCamera(){
        Log.d("Debugger", "Can I use Camera?");
    }

    // When denied, ask why..?
    @OnPermissionDenied(Manifest.permission.CAMERA)
    public void Why(){
        Log.d("Debugger", "Why...?");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("Debugger", "Asking if I can use it");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
        if(grantResults[0] == PERMISSION_GRANTED) {
            Log.d("Debugger", "Permission Granted!");
        }
        else {
            Log.d("Debugger", "Permission not Granted!");
        }
    }
}
