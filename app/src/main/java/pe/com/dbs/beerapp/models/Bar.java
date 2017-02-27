package pe.com.dbs.beerapp.models;

/**
 * Created by JeralBenites on 25/02/2017.
 */

public class Bar {
    private String _Name;
    private String _Address;
    private String _Phone;

    public Bar(String _Name, String _Address, String _Phone) {
        this._Name = _Name;
        this._Address = _Address;
        this._Phone = _Phone;
    }

    public String get_Name() {
        return _Name;
    }

    public void set_Name(String _Name) {
        this._Name = _Name;
    }

    public String get_Address() {
        return _Address;
    }

    public void set_Address(String _Address) {
        this._Address = _Address;
    }

    public String get_Phone() {
        return _Phone;
    }

    public void set_Phone(String _Phone) {
        this._Phone = _Phone;
    }
}
