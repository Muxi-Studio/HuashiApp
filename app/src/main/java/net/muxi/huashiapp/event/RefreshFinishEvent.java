package net.muxi.huashiapp.event;

/**
 * Created by ybao (ybaovv@gmail.com)
 * Date: 17/2/27
 */

public class RefreshFinishEvent {

    private boolean refreshResult;
    private int code;
    public RefreshFinishEvent(boolean refreshResult){
        this.refreshResult =refreshResult;
    }
    public RefreshFinishEvent(boolean refreshResult,int code) {
        this.refreshResult = refreshResult;
        this.code = code;
    }

    public boolean isRefreshResult() {
        return refreshResult;
    }

    public int getCode(){
        return code;
    }

    public void setRefreshResult(boolean refreshResult) {
        this.refreshResult = refreshResult;
    }
}
