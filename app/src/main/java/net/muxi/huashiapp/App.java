package net.muxi.huashiapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.BuildConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.muxistudio.appcommon.data.User;
import com.muxistudio.appcommon.user.UserAccountManager;
import com.muxistudio.common.base.Global;
import com.muxistudio.common.util.PreferenceUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.umeng.commonsdk.UMConfigure;


/**
 * Created by ybao on 16/4/18.
 */
public class App extends Application {

    public static String UMENG_APP_KEY = "58b55d3d8f4a9d21ce0013ed";
    public static String PHPSESSID;
    public static Context sContext;
    //获取上次的已经登录的用户账号信息
    public static User sUser = new User();
//    public static User sLibrarayUser = new User();

    private PreferenceUtil sp;

    private ApplicationLike tinkerApplicationLike;
    @Override
    public void onCreate() {
        super.onCreate();

//        tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
//                TinkerPatch.init(tinkerApplicationLike)
//                .reflectPatchLibrary()
//                .setPatchRollbackOnScreenOff(true)
//                .setPatchRestartOnSrceenOff(true);

//        new FetchPatchHandler().fetchPatchWithInterval(3);

        sContext = getApplicationContext();
        Global.setApplication(this);
        UserAccountManager.getInstance().initUser();
//        sp = new PreferenceUtil();
//
//        sUser.setSid(sp.getString(PreferenceUtil.STUDENT_ID, ""));
//        sUser.setPassword(sp.getString(PreferenceUtil.STUDENT_PWD, ""));
//        sLibrarayUser.setSid(sp.getString(PreferenceUtil.LIBRARY_ID, ""));
//        sLibrarayUser.setPassword(sp.getString(PreferenceUtil.LIBRARY_PWD, ""));

        Log.d("somethign", "big "+PreferenceUtil.getString(PreferenceUtil.BIG_SERVER_POOL)+"\n"+"" +
                "jid "+PreferenceUtil.getString(PreferenceUtil.JSESSIONID
        ));
        Fresco.initialize(this);
        initBugly();
        initUMeng();
        initARouter(this);
    }

    private void initBugly() {
        if (!BuildConfig.DEBUG) {
            CrashReport.initCrashReport(getApplicationContext(), "900043675", BuildConfig.DEBUG);
        }
    }
    private void initUMeng(){
        UMConfigure.init(this,UMENG_APP_KEY,null,UMConfigure.DEVICE_TYPE_PHONE,null);
    }

    private void initARouter(Application app){
        if(BuildConfig.DEBUG){
            ARouter.openLog();
            //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
            ARouter.printStackTrace();
        }

        ARouter.init(app);

    }

    public static Context getContext() {
        return sContext;
    }

//    public static void saveLibUser(User libUser) {
//        PreferenceUtil.saveString(PreferenceUtil.LIBRARY_ID, libUser.getSid());
//        PreferenceUtil.saveString(PreferenceUtil.LIBRARY_PWD, libUser.getPassword());
//        sLibrarayUser = libUser;
//    }
//
//    public static void logoutLibUser() {
//        PreferenceUtil.clearString(PreferenceUtil.LIBRARY_ID);
//        PreferenceUtil.clearString(PreferenceUtil.LIBRARY_PWD);
//        PreferenceUtil.clearString(PreferenceUtil.ATTENTION_BOOK_IDS);
//        PreferenceUtil.clearString(PreferenceUtil.BORROW_BOOK_IDS);
//        PreferenceUtil.clearString(PreferenceUtil.PHPSESSID);
//        sLibrarayUser.setSid("");
//        sLibrarayUser.setPassword("");
//        CcnuCrawler2.clearCookieStore();
//    }

//    @De
//    public static void saveUser(User user) {
//        PreferenceUtil.saveString(PreferenceUtil.STUDENT_ID, user.getSid());
//        PreferenceUtil.saveString(PreferenceUtil.STUDENT_PWD, user.getPassword());
//        sUser = user;
//    }

//    public static void logoutUser() {
//        PreferenceUtil.clearString(PreferenceUtil.STUDENT_ID);
//        PreferenceUtil.clearString(PreferenceUtil.STUDENT_PWD);
//        PreferenceUtil.clearString(PreferenceUtil.BIG_SERVER_POOL);
//        PreferenceUtil.clearString(PreferenceUtil.JSESSIONID);
//        PreferenceUtil.clearString(PreferenceUtil.PHPSESSID);
//        sUser.setSid("");
//        sUser.setPassword("");
//        HuaShiDao dao = new HuaShiDao();
//        dao.deleteAllCourse();
//        CcnuCrawler2.clearCookieStore();
//    }

//    public static boolean isInfoLogin() {
//        return !TextUtils.isEmpty(sUser.sid);
//    }
//
//    // 判断是否已经登陆
//    public static boolean isLibLogin() {
//        String phpSess = PreferenceUtil.getString(PreferenceUtil.PHPSESSID);
//        if(!phpSess.equals("")||!TextUtils.isEmpty(phpSess)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
}
