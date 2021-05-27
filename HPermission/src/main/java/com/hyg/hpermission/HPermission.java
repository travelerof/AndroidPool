package com.hyg.hpermission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.hyg.hpermission.request.RequestBuilder;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc 发起请求
 */
public final class HPermission {

    public HPermission(){
        throw new RuntimeException("Stub!");
    }

    public static RequestBuilder with(Context context){
        return new RequestBuilder(context);
    }

    public static RequestBuilder with(@NonNull Fragment fragment) {
        return with(fragment.getActivity());
    }
}
