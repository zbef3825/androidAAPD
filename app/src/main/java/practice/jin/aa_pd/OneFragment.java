package practice.jin.aa_pd;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Jinwook on 2016-08-15.
 */
@RuntimePermissions
@EFragment(R.layout.fragment_one)
public class OneFragment extends ParentFragment {

    @NeedsPermission(Manifest.permission.CAMERA)
    public void UseCamera(){
        Log.d("DebuggerFrag", "Can I use Camera?");
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    public void UseCameraDenied(){
        Log.d("DebuggerFrag", "Nope!");
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        OneFragmentPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
        if(grantResults[0] == 0) {
            Log.d("Debugger", "Permission Granted!");
        }
        else {
            Log.d("Debugger", "Permission not Granted!");
        }
    }



    @ViewById
    Button SecondCamera;

    @ViewById
    Button BackButton;

    @Click(R.id.BackButton)
    public void onClickBack(){
        getFragmentManager().popBackStack();
    }
    @Click(R.id.SecondCamera)
    public void onClickCamera(){
        OneFragmentPermissionsDispatcher.UseCameraWithCheck(this);

    }
}
