package com.hyg.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.hyg.hlog.HLog;
import com.hyg.utils.encrypt.AESUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author 韩永刚
 * @Date 2021/02/22
 * @Desc
 */
public class SharedPreferencesUtils {

    public static final String TAG = SharedPreferencesUtils.class.getSimpleName();
    /**
     * 是否加密
     */
    public static final boolean ENCRYPT = true;
    private static final String DEFAULT_NAME = "h_share";
    private static SharedPreferencesUtils utils;
    private final SharedPreferences mSharedPreferences;

    private SharedPreferencesUtils(Context context, String name) {
        mSharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesUtils getInstance(Context context, String name) {
        if (TextUtils.isEmpty(name)) {
            name = DEFAULT_NAME;
        }
        if (utils == null) {
            synchronized (name) {
                utils = new SharedPreferencesUtils(context, name);
            }
        }
        return utils;
    }

    public static SharedPreferencesUtils getInstance(Context context) {
        return getInstance(context, null);
    }

    public void put(String key, String value) {
        if (ENCRYPT) {
            putEncrypt(key, value,false);
        }else{
            put(key, value,false);
        }
    }

    public void put(String key, boolean value) {
        if (ENCRYPT) {
            putEncrypt(key,String.valueOf(value),false);
        }else{
            put(key, value, false);
        }
    }

    public void put(String key, int value) {
        if (ENCRYPT) {
            putEncrypt(key,String.valueOf(value),false);
        }else{
            put(key, value, false);
        }
    }

    public void put(String key, float value) {
        if (ENCRYPT) {
            putEncrypt(key,String.valueOf(value),false);
        }else{
            put(key, value, false);
        }
    }

    public void put(@NonNull String key, long value) {
        if (ENCRYPT) {
            putEncrypt(key,String.valueOf(value),false);
        }else{
            put(key, value, false);
        }
    }

    public void putSet(@NonNull String key, Set<String> set){
        mSharedPreferences.edit().putStringSet(key, set).apply();
    }
    public void putInt(@NonNull String key, int value){
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    public void putBoolean(@NonNull String key, boolean value){
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    public void putString(@NonNull String key, String value){
        mSharedPreferences.edit().putString(key, value).apply();
    }
    public void putLong(@NonNull String key, long value){
        mSharedPreferences.edit().putLong(key,value).apply();
    }

    public int getInt(String key, int defaultValue) {
        if (ENCRYPT) {
            return Integer.parseInt(getEncrypt(key,String.valueOf(defaultValue),int.class));
        }
        return Integer.parseInt(get(key,String.valueOf(defaultValue),int.class));
    }

    public float getFloat(String key, float defaultValue) {
        if (ENCRYPT) {
            return Float.parseFloat(getEncrypt(key,String.valueOf(defaultValue),float.class));
        }
        return Float.parseFloat(get(key,String.valueOf(defaultValue),float.class));
    }

    public long getLong(String key, long defaultValue) {
        if (ENCRYPT) {
            return Long.parseLong(getEncrypt(key,String.valueOf(defaultValue),long.class));
        }
        return Long.parseLong(get(key,String.valueOf(defaultValue),long.class));
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        if (ENCRYPT) {
            return Boolean.parseBoolean(getEncrypt(key,String.valueOf(defaultValue),boolean.class));
        }
        return Boolean.parseBoolean(get(key,String.valueOf(defaultValue),boolean.class));
    }

    public String getString(String key, String defaultValue) {
        if (ENCRYPT) {
            return getEncrypt(key,String.valueOf(defaultValue),String.class);
        }
        return get(key, defaultValue, String.class);
    }

    public Set<String> getStringSet(String key, Set<String> defaultValue){
        if (ENCRYPT) {
            return getEncryptSet(key, defaultValue);
        }
        return get(key, defaultValue);
    }
    @SuppressLint("CommitPrefEdits")
    private void put(@NonNull String key, Object value, boolean isCommit){
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        if (value == int.class) {
            edit.putString(key,String.valueOf(value));
        }else if (value == long.class){
            edit.putLong(key,(long)value);
        }else if (value == float.class){
            edit.putFloat(key,(float)value);
        }else if (value == boolean.class){
            edit.putBoolean(key,(boolean)value);
        }else {
            edit.putString(key,(String)value);
        }
        if (isCommit) {
            edit.commit();
        }else {
            edit.apply();
        }
    }
    private void putEncrypt(@NonNull String key, @NonNull String value,boolean isCommit) {
        String encryptKey = AESUtils.encryptAES(key, Constant.AES_KEY);
        String encryptValue = AESUtils.encryptAES(value, Constant.AES_KEY);
        HLog.i(TAG, "=====加密后=====key===" + encryptKey + "=====value====" + encryptValue);
        SharedPreferences.Editor editor = mSharedPreferences.edit().putString(encryptKey, encryptValue);
        if (isCommit) {
            editor.commit();
        }else {
            editor.apply();
        }
    }


    private boolean putEncrypt(@NonNull String key,@NonNull Set<String> set){
        String encryptKey = AESUtils.encryptAES(key,Constant.AES_KEY);
        if (TextUtils.isEmpty(encryptKey)) {
            HLog.e(TAG,"=======加密key失败======");
            return false;
        }
        Set<String> encryptSet = new HashSet<>();
        for (String s : set) {
            String encryptValue = AESUtils.encryptAES(s,Constant.AES_KEY);
            if (TextUtils.isEmpty(encryptKey)) {
                HLog.e(TAG,"=======加密Set value失败======");
                return false;
            }
            encryptSet.add(encryptValue);
        }
        return mSharedPreferences.edit().putStringSet(encryptKey,encryptSet).commit();
    }
    private String get(@NonNull String key, String defalutValue,Class<?> cls){
        String value;
        if (cls == int.class) {
            value = String.valueOf(mSharedPreferences.getInt(key, Integer.parseInt(defalutValue)));
        }else if (cls == float.class){
            value = String.valueOf(mSharedPreferences.getFloat(key, Float.parseFloat(defalutValue)));
        }else if (cls == long.class){
            value = String.valueOf(mSharedPreferences.getLong(key,Long.parseLong(defalutValue)));
        }else if (cls == boolean.class){
            value = String.valueOf(mSharedPreferences.getBoolean(key, Boolean.parseBoolean(defalutValue)));
        }else {
            value = mSharedPreferences.getString(key, defalutValue);
        }
        return value;
    }

    private Set<String> get(@NonNull String key, Set<String> defaultSet){

        return mSharedPreferences.getStringSet(key, defaultSet);
    }
    private String getEncrypt(@NonNull String key,String defaultValue, Class<?> cls){
        String value = getEncrypt(key, "");
        if (TextUtils.equals("",value)) {
            value = get(key,defaultValue,cls);
            if (!TextUtils.equals(value,defaultValue)) {
                putEncrypt(key,value,false);//将未加密的字段加密后重新存放
                remove(key);
                return value;
            }
            return defaultValue;
        }
        return value;
    }

    private String getEncrypt(String key, String defaultValue) {

        String encryptKey = AESUtils.encryptAES(key, Constant.AES_KEY);
        String encryptValue = mSharedPreferences.getString(encryptKey, "");
        if (TextUtils.isEmpty(encryptValue)) {
            return defaultValue;
        }
        String value = AESUtils.decryptAES(encryptValue, Constant.AES_KEY);
        if (TextUtils.isEmpty(value)) {
            HLog.i(TAG, "=====解密失败===" + value);
        }
        return value;
    }

    private Set<String> getEncryptSet(@NonNull String key, Set<String> defaultSet){
        String encryptKey = AESUtils.encryptAES(key,Constant.AES_KEY);
        if (TextUtils.isEmpty(encryptKey)) {
            HLog.e(TAG,"======读取Set key加密失败=======");
            return defaultSet;
        }
        Set<String> encryptSets = mSharedPreferences.getStringSet(encryptKey, null);
        if (encryptSets == null) {//没有读取到加密的Set
            encryptSets = get(key, null);
            if (encryptSets != null) {
                if (putEncrypt(key,encryptSets)) {
                    remove(key);
                }
                return encryptSets;
            }
            return defaultSet;
        }
        Set<String> de_Sets = new HashSet<>();
        for (String encryptSet : encryptSets) {
            String de_Value = AESUtils.decryptAES(encryptSet,Constant.AES_KEY);
            if (TextUtils.isEmpty(de_Value)) {
                HLog.i(TAG,"====读取 Set value失败=======");
                return defaultSet;
            }
            de_Sets.add(de_Value);
        }
        return de_Sets;
    }
    public boolean remove(String key){
        return mSharedPreferences.edit().remove(key).commit();
    }

    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }
}
