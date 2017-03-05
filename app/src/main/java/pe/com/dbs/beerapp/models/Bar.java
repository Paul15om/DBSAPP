package pe.com.dbs.beerapp.models;

/**
 * Created by JeralBenites on 25/02/2017.
 */

public class Bar {
    private String mName;
    private String mAddress;
    private String mPhone;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public Bar(String mName, String mAddress, String mPhone) {
        this.mName = mName;
        this.mAddress = mAddress;
        this.mPhone = mPhone;
    }
}
